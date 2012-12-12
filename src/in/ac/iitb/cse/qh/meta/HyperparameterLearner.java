package in.ac.iitb.cse.qh.meta;

import Jama.Matrix;

public class HyperparameterLearner {
	private double[][] cov; // n x n
	private double[][] hessian; // n x n
	private double[] weights; // n
	private double[] params; // k
	private double[][] indicator; // n x k
	private int numWeights; // n
	private int numParams; // k

	public HyperparameterLearner(ClassifierProxy classifier, double[] hyper) {
		cov = classifier.getCovariance(hyper);
		hessian = classifier.getHessian();
		weights = classifier.getWeights();
		indicator = classifier.getIndicator();
		numWeights = weights.length;
	}

	/*
	 * Returns the n x k Jacobian matrix J_d whose (i, j)th entry is dw_i/dd_j
	 */
	public Matrix computeJacobian(double[] hyper) {
		params = hyper;
		numParams = params.length;
		Matrix result = null;
		// double[][] b = new double[numWeights][numParams];
		// for (int i = 0; i < numWeights; i++)
		// for (int j = 0; j < numParams; j++) {
		// System.out.println("ind: " + indicator[i][j]);
		// // b[i][j] = indicator[i][j] * weights[i] * Math.exp(params[j]);
		// // System.out.println("b: " + b[i][j]);
		// }
		Matrix matB = new Matrix(indicator);
		Matrix matC = new Matrix(cov);
		Matrix matH = new Matrix(hessian);
		System.out.println("Determinant="+matC.plus(matH).det());
		result = matC.plus(matH).inverse().times(matB).times(-1.0d);
		for (int i = 0; i < result.getRowDimension(); i++)
			for (int j = 0; j < result.getColumnDimension(); j++) {
				if (Double.isNaN(result.get(i, j)))
					System.out.println("jacobian is NAN!! " + result.get(i, j));
				if(Double.isInfinite(result.get(i, j)))
					System.out.println("jacobian is Infinite!! " + result.get(i, j));
				if (Double.isNaN(matB.get(i, j)) || Double.isInfinite(matB.get(i, j)))
					System.out.println("matB is Infinite!! " + matB.get(i, j));
			}
		return result;
	}
}