package in.ac.iitb.cse.qh.data;

public class ModelParams {
	private int numParams;
	private double[] params;
	private boolean optim = false;

	public boolean isOptim() {
		return optim;
	}

	public void setOptim(boolean optim) {
		this.optim = optim;
	}

	public double[] getParams() {
		return params;
	}

	public void setParams(double[] params) {
		this.params = params;
	}

}
