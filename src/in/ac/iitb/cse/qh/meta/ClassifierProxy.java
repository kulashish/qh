package in.ac.iitb.cse.qh.meta;

import in.ac.iitb.cse.qh.classifiers.ModifiedLogistic;
import in.ac.iitb.cse.qh.data.ConfusionMatrix;
import in.ac.iitb.cse.qh.data.InputData;
import in.ac.iitb.cse.qh.data.InputPredictionInstance;
import in.ac.iitb.cse.qh.data.ModelParams;
import in.ac.iitb.cse.qh.naiveBayes.NaiveBayesParam;
import in.ac.iitb.cse.qh.util.MetaConstants;
import in.ac.iitb.cse.qh.util.WekaUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import weka.core.Instance;
import weka.core.Instances;

public class ClassifierProxy {

//	private NaiveBayesParam nbClassfier;
	private ModifiedLogistic mlrClassifier;

	private String tPath;
	private String hPath;
	private Instances trainInstances;
	private Instances holdoutInstances;

	public Instances getholdoutInstances() {
		return holdoutInstances;
	}

	public Instances gettrainInstances() {
		return trainInstances;
	}

	public ModifiedLogistic getClassifier() {
		// if (null == nbClassfier)
		// nbClassfier = new NaiveBayesParam(MetaConstants.TRAIN_FILE_PATH,
		// MetaConstants.TRAIN_FILE_PATH);
		if (null == mlrClassifier) {
			System.out.println("creating new instance of classifer");
			mlrClassifier = new ModifiedLogistic();
		}
		return mlrClassifier;
	}

	// public double[][] computeJacobian(int instanceIndex, double[] params) {
	// return getClassifier().calcJacobian(instanceIndex);
	// }
	//
	// public ConfusionMatrix computeConfusionMatrix(ModelParams params) {
	// return getClassifier().calcNewState(params.getParams()).getConfMatrix();
	// }
	//

	public InputData computeInitialState(String train, String hold) throws Exception {
		tPath = train;
		hPath = hold;
		mlrClassifier = null;
		InputData in = computeNewState(null);
		try {
			in.serialize(MetaConstants.IN_FILE_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return in;
	}

	public InputData computeNewState(ModelParams params) throws Exception {
//		System.out.println("compute new state...");
		InputData dat = new InputData();
		List<InputPredictionInstance> predInst = new ArrayList<InputPredictionInstance>();
		ConfusionMatrix confMatrix = new ConfusionMatrix();

		getClassifier().setMaxIts(-1);
		if (null != params) {
			// for (int i = 0; i < params.getParams().length; i++)
			// System.out.print(params.getParams()[i] + ", ");
			// System.out.println();
//			System.out.println("set hyper parameters...");
			getClassifier().setHyperparameters(params.getParams());
			getClassifier().setMaxIts(-1);
		} else {
			trainInstances = WekaUtil
					.getInstances(tPath);
			holdoutInstances = WekaUtil
					.getInstances(hPath);
		}

		ModifiedLogistic ml = getClassifier();
		ml.buildClassifier(trainInstances);
		ModelParams modPar = new ModelParams();
		modPar.setParams(ml.getHyperparameters());

		double dist[][] = new double[holdoutInstances.numInstances()][holdoutInstances
				.numClasses()];

		int countFN = 0;
		int countFP = 0;
		int countTN = 0;
		int countTP = 0;
		double pred = 0.0d;
		double actual = 0.0d;
		int i = -1;

		for (Instance instance : holdoutInstances) {
			dist[++i] = ml.distributionForInstance(instance);

			pred = dist[i][0] >= dist[i][1] ? 0 : 1;
			// pred = dist[i][1] > 0 ? 1 : 0;
			actual = instance.classValue();
			predInst.add(new InputPredictionInstance((int) actual, (int) pred,
					dist[i]));

			if (pred != actual) {
				if (pred == 1)
					countFP++;
				else
					countFN++;
			} else {
				if (pred == 1)
					countTP++;
				else
					countTN++;
			}
		}

		int mat[][] = { { countTN, countFP }, { countFN, countTP } };
		confMatrix.setMatrix(mat);
		dat.setConfMatrix(confMatrix);
		dat.setParams(modPar);
		dat.setPredInstances(predInst);
		try {
			dat.serialize(MetaConstants.NEW_FILE_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dat;
	}

	/*
	 * Returns gradient of P_i with respect to w computed at w* where P_i is ith
	 * label
	 */
	public double[][] computeJacobian(int i) {
		double[][] jac = getClassifier().computeJacobian(i);
		// for (int a = 0; a < jac.length; a++) {
		// for (int j = 0; j < jac[a].length; j++)
		// System.out.print(jac[a][j] + " ");
		// System.out.println();
		// }
		return jac;
	}

	/*
	 * Covariance matrix C as defined in A. Ng's paper
	 */
	public double[][] getCovariance() {
		double diag[] = getClassifier().getCovariance();
		return getCovariance(diag);
	}

	public double[][] getCovariance(double diag[]) {
		double c[][] = new double[diag.length][diag.length];
		for (int i = 0; i < diag.length; i++)
			for (int j = 0; j < diag.length; j++)
				if (i == j) {
					c[i][j] = Math.exp(diag[i]);
//					if (Double.isNaN(c[i][j]) || Double.isInfinite(c[i][j]))
						System.out.print(c[i][j]+", ");
				} else
					c[i][j] = 1d;
		System.out.println();
		return c;
	}

	/*
	 * Hessian of the training log loss evaluated at w*. Refer A. Ng's paper
	 * equation (6)
	 */
	public double[][] getHessian() {
		System.out.println(this);
		return getClassifier().getHessian();
	}

	/*
	 * Indicator matrix I as defined in A. Ng's paper. Refer equation (6)
	 */
	public double[][] getIndicator() {
		return getClassifier().getIndicator();
	}

	public double[] getWeights() {
		return getClassifier().getWeights();
	}

	public double[] getData(int index) {
		return getClassifier().getData(index);
	}
}
