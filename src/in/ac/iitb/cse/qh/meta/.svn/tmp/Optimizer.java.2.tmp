package in.ac.iitb.cse.qh.meta;

import in.ac.iitb.cse.qh.data.ConfusionMatrix;
import in.ac.iitb.cse.qh.data.CurrentState;
import in.ac.iitb.cse.qh.data.CurrentStateVector;
import in.ac.iitb.cse.qh.data.InputData;
import in.ac.iitb.cse.qh.data.ModelParams;
import in.ac.iitb.cse.qh.data.TargetState;
import in.ac.iitb.cse.qh.data.TargetStateVector;
import in.ac.iitb.cse.qh.util.KLDivergenceCalculator;
import in.ac.iitb.cse.qh.util.MetaConstants;
import in.ac.iitb.cse.qh.util.WekaUtil;

import java.io.IOException;
import java.util.Arrays;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.Optimization;
import weka.core.Utils;

import Jama.Matrix;

public class Optimizer {
	private InputData data;
	private CurrentState cState;
	private TargetState tState;
	private int numLabels;
	private double divergence;
	private InputData newData;
	private int numWeights;
	// private HyperparameterLearner hyperLearner;
	private ClassifierProxy classifier;

	protected boolean m_Debug;

	private int m_MaxIts = -1;
	
	private class OptEng extends Optimization {

		@Override
		public String getRevision() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected double[] evaluateGradient(double[] x) throws Exception {
			return computeGradient(x);
		}

		@Override
		protected double objectiveFunction(double[] x) throws Exception {
			getNewState(x);
			return KLDivergenceCalculator.calculate(cState, tState);
		}

	}

	public Optimizer(InputData in, CurrentState curr, TargetState target,
			ClassifierProxy classifier) {
		this.classifier = null != classifier ? classifier
				: new ClassifierProxy();
		data = in;
		cState = curr;
		tState = target;
		numLabels = MetaConstants.NUMBER_CLASSLABELS;
		numWeights = data.getParams().getParams().length;
		// hyperLearner = new HyperparameterLearner(classifier, data.getParams()
		// .getParams());
	}

	// public double[] computeGradient(double[] theta) {
	// double beta = MetaConstants.SCALING_BETA;
	// ClassifierProxy classifier = new ClassifierProxy();
	// int numInstances = cState.getTrainingSize();
	// CurrentStateVector[] cStateVectors = cState.getP();
	// TargetStateVector[] tStateVectors = tState.getS();
	// System.out.println("Target state vectors : " + tStateVectors);
	// double[][] jacobian = null;
	// Matrix jacMatrix = null;
	// Matrix curVector = null;
	// Matrix tarVector = null;
	// Matrix idenMatrix = null;
	// Matrix oneVector = new Matrix(numLabels, 1, 1.0d); // M x 1
	//
	// Matrix gradMatrix = new Matrix(theta.length, 1); // D x 1
	// for (int i = 0; i < numInstances; i++) {
	// jacobian = classifier.computeJacobian(i, theta);
	// jacMatrix = new Matrix(jacobian); // M x D
	// curVector = new Matrix(cStateVectors[i].getPi(), 1); // M x 1
	// tarVector = new Matrix(tStateVectors[i].getSi(), 1); // M x 1
	// idenMatrix = Matrix.identity(numLabels, numLabels); // M x M
	//
	// gradMatrix.plusEquals(jacMatrix
	// .transpose()
	// .times(idenMatrix.minus(curVector.transpose().times(
	// oneVector.transpose())))
	// .times(tarVector.transpose()));
	// }
	// return gradMatrix.times(-beta).getRowPackedCopy();
	// }

	public double[] computeGradient(double[] theta) throws Exception {
		int numInstances = cState.getTrainingSize();
		CurrentStateVector[] cStateVectors = cState.getP();
		TargetStateVector[] tStateVectors = tState.getS();
		double[][] jacobian = null;
		Matrix jacMatrix = null;

		Matrix gradMatrix = new Matrix(numWeights, 1); // D x 1
		double[] grads = new double[numWeights];
		Arrays.fill(grads, 0.0d);
		Matrix dataMat = null;
		double temp = 0.0d;
		HyperparameterLearner hyperLearner = new HyperparameterLearner(
				classifier, theta);
		System.out.println("numInstances="+numInstances);

		Instances holdoutInstances = WekaUtil
				.getInstances(MetaConstants.HOLDOUT_FILE_PATH);
		int i=0;
		for (Instance instance : holdoutInstances) {
			double[] instDat = new double[instance.numAttributes()];
			int j = 1;
			instDat[0] = 1;
			for (int k = 0; k < instance.numAttributes()-1; k++) {
				instDat[j++] = instance.value(k);
			}

			dataMat = new Matrix(instDat, 1);
			
		////for (int i = 0; i < numInstances; i++) {
			// jacobian = classifier.computeJacobian(i); // D x M
			// jacMatrix = new Matrix(jacobian);
			////dataMat = new Matrix(classifier.getData(i), 1);
			// System.out.println("data matrix dimensions: "
			// + dataMat.getRowDimension() + " x "
			// + dataMat.getColumnDimension());

			temp = tStateVectors[i].getSi()[0] * cStateVectors[i].getPi()[1]
					- tStateVectors[i].getSi()[1] * cStateVectors[i].getPi()[0];
			if (Double.isNaN(temp))
				System.out.println("Temp is NAN!!!");

			gradMatrix.plusEquals(dataMat.times(temp).transpose());
			i++;
		}
		for (i = 0; i < numWeights; i++)
			// if (Double.isNaN(gradMatrix.get(i, 0)))
			System.out.println(gradMatrix.get(i, 0));
		Matrix hyperJacMat = hyperLearner.computeJacobian(theta);
		System.out.println("dimensions of hyperparam jacobian: "
				+ hyperJacMat.getRowDimension() + " x "
				+ hyperJacMat.getColumnDimension());
		return hyperJacMat.transpose().times(gradMatrix).getRowPackedCopy();
	}

	public ModelParams optimize2() throws Exception {
		// Initialize
		double theta[] = data.getParams().getParams();
		double[][] b = new double[2][theta.length]; // Boundary constraints, N/A

		for (int p = 0; p < theta.length; p++) {
			theta[p] = 1.0;
			b[0][p] = -MetaConstants.MAX_POWER;
			b[1][p] = MetaConstants.MAX_POWER;
		}

		OptEng opt = new OptEng();
		opt.setDebug(m_Debug);
		// opt.setWeights(weights);
		// opt.setClassLabels(Y);

		int iterCount=0;
		
		do
		{
			m_MaxIts=1;
			opt.setMaxIteration(m_MaxIts);
			iterCount++;
			System.out.println("\nRunning hyperparameterLearning iteration count="+iterCount);
			theta = opt.findArgmin(theta, b);
			if(null == theta)
				theta = opt.getVarbValues();
//			while (theta == null) {
//				theta = opt.getVarbValues();
//				if (m_Debug)
//					System.out.println("200 iterations finished, not enough!");
//				iterCount++;
//				System.out.println("Running hyperparameterLearning iteration count="+iterCount);
//				theta = opt.findArgmin(theta, b);
//			}
		}while(!optimized(theta) && iterCount < data.getMaxIterations());
		
//		if (m_MaxIts == -1) { // Search until convergence
//			theta = opt.findArgmin(theta, b);
//			while (theta == null) {
//				theta = opt.getVarbValues();
//				if (m_Debug)
//					System.out.println("200 iterations finished, not enough!");
//				theta = opt.findArgmin(theta, b);
//				//if(optimized(theta))
//					//break;
//			}
//			if (m_Debug)
//				System.out.println(" -------------<Converged>--------------");
//		} else {
//			opt.setMaxIteration(m_MaxIts);
//			theta = opt.findArgmin(theta, b);
//			if (theta == null) // Not enough, but use the current value
//				theta = opt.getVarbValues();
//		}

		double m_objectiveVal = -opt.getMinFunction(); // Log-likelihood
		
		ModelParams optimParams = null;
		optimParams = new ModelParams();
		optimParams.setParams(theta);
		classifier.serializeModel();
		return optimParams;
	}

	// 2nd iteration should always be robust.
	// have lot more examples for testing.
	// training on larger data set.
	
	public ModelParams optimize() throws Exception {
		int iterCount = 0;
		divergence = KLDivergenceCalculator.calculate(cState, tState);
		System.out.println("KL Divergence : " + divergence);
		// ((DivergenceChart) BeanFinder
		// .findBean(MetaConstants.BEAN_DIVERGENCE_CHART)).update(
		// iterCount, divergence);
		double delta[] = null;
		double theta[] = data.getParams().getParams();
		double newDivergence = 0.0d;
		double initialStep = data.getInitialStepSize();
		do {
			delta = computeGradient(theta);
			for (double step = initialStep; iterCount < data.getMaxIterations(); step /= 10) {
				System.out.println("Step size : " + step);
				System.out.println("KL divergence before taking a step : "
						+ KLDivergenceCalculator.calculate(cState, tState));
				for (int i = 0; i < delta.length; i++) {
					if (Double.isNaN(delta[i]))
						System.out.print("delta is NAN!!" + delta[i] + ", ");
					theta[i] -= step * delta[i];
					System.out.print((theta[i]) + ", ");
				}
				System.out.println();
				getNewState(theta);
				iterCount++;
				newDivergence = KLDivergenceCalculator
						.calculate(cState, tState);
				System.out.println("KL Divergence : " + newDivergence);
				// ((DivergenceChart) BeanFinder
				// .findBean(MetaConstants.BEAN_DIVERGENCE_CHART)).update(
				// iterCount, newDivergence);
				if (newDivergence < divergence) {
					divergence = newDivergence;
					initialStep = step;
					break;
				}
				// before taking the next step size, reset theta to what it was
				for (int i = 0; i < delta.length; i++)
					theta[i] += step * delta[i];
				getNewState(theta); // Added for checking. Not required.
			}
		} while (!optimized(theta) && iterCount < data.getMaxIterations());
		ModelParams optimParams = null;
		if (iterCount < data.getMaxIterations()) {
			optimParams = new ModelParams();
			optimParams.setParams(theta);
		}
		return optimParams;
	}

	private void getNewState(double[] theta) throws Exception {
		ModelParams params = new ModelParams();
		params.setParams(theta);
		newData = classifier.computeNewState(params);
		cState = CurrentState.createCurrentState(newData.getPredInstances());
	}

	private boolean optimized(double[] theta) {
		// ModelParams params = new ModelParams();
		// params.setParams(theta);
		// InputData newData = new ClassifierProxy().computeNewState(params);
		// cState = CurrentState.createCurrentState(newData.getPredInstances());
		// System.out.println("KL Divergence : "
		// + KLDivergenceCalculator.calculate(cState, tState));
		ConfusionMatrix newConf = newData.getConfMatrix();
		newConf.display();
		int[][] nc = newConf.getMatrix();
		int[][] c = data.getConfMatrix().getMatrix();
		int[][] b = data.getBiasMatrix().getMatrix();
		boolean blnOptim = true;
		for (int i = 0; i < numLabels; i++)
			for (int j = 0; j < numLabels; j++)
				if (b[i][j] > c[i][j])
					blnOptim = nc[i][j] >= b[i][j];
				else if (b[i][j] < c[i][j])
					blnOptim = nc[i][j] <= b[i][j];

		if (blnOptim) {
			try {
				System.out.println("Serializing data after optimization...");
				newData.serialize(MetaConstants.OPTIMIZED_FILE_PATH);
			} catch (IOException e) {
				e.printStackTrace();
			}
			data.setConfMatrix(newConf);
		}

		return blnOptim;
	}

	private void displayMat(Matrix mat) {
		double[] vals = mat.getRowPackedCopy();
		for (int i = 0; i < vals.length; i++)
			System.out.print(vals[i] + ", ");
	}

	public void setDebug(boolean debug) {
		m_Debug = debug;
	}
}
