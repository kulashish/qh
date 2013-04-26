package in.ac.iitb.cse.qh.data;

import in.ac.iitb.cse.qh.util.MetaConstants;

public class ConfusionMatrix {
	private int numLables;
	private int[][] matrix;
	private int tp = 0;
	private int fp = 0;
	private int fn = 0;
	private int tn = 0;

	public ConfusionMatrix() {
		this(MetaConstants.NUMBER_CLASSLABELS);
	}

	public ConfusionMatrix(int labels) {
		numLables = labels;
		matrix = new int[numLables][numLables];
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
		tp = matrix[1][1];
		fp = matrix[0][1];
		tn = matrix[0][0];
		fn = matrix[1][0];
	}

	public int getNumLables() {
		return numLables;
	}

	public void setNumLables(int numLables) {
		this.numLables = numLables;
	}

	public void display() {
		System.out.println("Confusion matrix :");
		for (int i = 0; i < numLables; i++)
			for (int j = 0; j < numLables; j++)
				System.out.print(matrix[i][j] + " ");
		System.out.println();
	}

	public int getTp() {
		return tp;
	}

	public void setTp(int tp) {
		this.tp = tp;
		matrix[1][1] = tp;
	}

	public int getFp() {
		return fp;
	}

	public void setFp(int fp) {
		this.fp = fp;
		matrix[0][1] = fp;
	}

	public int getFn() {
		return fn;
	}

	public void setFn(int fn) {
		this.fn = fn;
		matrix[1][0] = fn;
	}

	public int getTn() {
		return tn;
	}

	public void setTn(int tn) {
		this.tn = tn;
		matrix[0][0] = tn;
	}

}
