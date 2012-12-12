package in.ac.iitb.cse.qh.data;

import in.ac.iitb.cse.qh.util.MetaConstants;

public class ConfusionMatrix {
	private int numLables;
	private int[][] matrix;

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
				System.out.print(matrix[i][j]+ " ");
		System.out.println();
	}

}
