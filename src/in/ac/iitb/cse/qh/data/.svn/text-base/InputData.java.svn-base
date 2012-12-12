package in.ac.iitb.cse.qh.data;

import in.ac.iitb.cse.qh.meta.ConfusionMatrixLoader;
import in.ac.iitb.cse.qh.meta.ModelParamLoader;
import in.ac.iitb.cse.qh.meta.Optimizer;
import in.ac.iitb.cse.qh.meta.PredictionsLoader;
import in.ac.iitb.cse.qh.meta.TargetStateCalculator;
import in.ac.iitb.cse.qh.util.MetaConstants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class InputData {
	private List<InputPredictionInstance> predInstances;
	private ConfusionMatrix confMatrix;
	private BiasMatrix biasMatrix;
	private DisplayMatrix dispMatrix;
	private ModelParams params;

	private double initialStepSize = MetaConstants.INITIAL_STEP;
	private int maxIterations = 10;

	public double getInitialStepSize() {
		return initialStepSize;
	}

	public void setInitialStepSize(double initialStepSize) {
		System.out.println("setting intial step size: " + initialStepSize);
		this.initialStepSize = initialStepSize;
	}

	public int getMaxIterations() {
		return maxIterations;
	}

	public void setMaxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
	}

	public DisplayMatrix getDispMatrix() {
		if (null == dispMatrix)
			loadData(MetaConstants.IN_FILE_PATH);
		return dispMatrix;
	}

	public void setDispMatrix(DisplayMatrix dispMatrix) {
		this.dispMatrix = dispMatrix;
	}

	public BiasMatrix getBiasMatrix() {
		if (null == biasMatrix)
			loadData(MetaConstants.IN_FILE_PATH);
		return biasMatrix;
	}

	public void setBiasMatrix(BiasMatrix biasMatrix) {
		this.biasMatrix = biasMatrix;
	}

	public ModelParams getParams() {
		return params;
	}

	public void setParams(ModelParams params) {
		this.params = params;
	}

	public List<InputPredictionInstance> getPredInstances() {
		return predInstances;
	}

	public void setPredInstances(List<InputPredictionInstance> predInstances) {
		this.predInstances = predInstances;
	}

	public ConfusionMatrix getConfMatrix() {
		if (null == confMatrix)
			loadData(MetaConstants.IN_FILE_PATH);
		return confMatrix;
	}

	public void setConfMatrix(ConfusionMatrix confMatrix) {
		this.confMatrix = confMatrix;
	}

	public boolean loadData(String inFile) {
		boolean blnStatus = true;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inFile));
			setPredInstances(PredictionsLoader.load(reader));
			setConfMatrix(ConfusionMatrixLoader.load(reader));
			setParams(ModelParamLoader.load(reader));
			setDispMatrix(confMatrix);
			setBiasMatrix(dispMatrix);
		} catch (IOException e) {
			blnStatus = false;
		}
		return blnStatus;
	}

	public void setDispMatrix(ConfusionMatrix mat) {
		int[][] c = mat.getMatrix();
		int[][] b = new int[mat.getNumLables()][mat.getNumLables()];
		for (int i = 0; i < mat.getNumLables(); i++)
			for (int j = 0; j < mat.getNumLables(); j++)
				b[i][j] = c[i][j];
		dispMatrix = new DisplayMatrix();
		dispMatrix.setMatrix(b);
	}

	public void setBiasMatrix(DisplayMatrix mat) {
		int[][] c = mat.getMatrix();
		int[][] b = new int[2][2];
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 2; j++)
				b[i][j] = c[i][j];
		biasMatrix = new BiasMatrix();
		biasMatrix.setMatrix(b);
		biasMatrix.setNumLabels(2);
	}

	public void serialize(String filepath) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
		writer.append(MetaConstants.IN_PRED_BEGIN);
		for (InputPredictionInstance instance : predInstances) {
			writer.newLine();
			writer.append(instance.getTrueLabel() + " "
					+ instance.getPredLabel() + " ");
			for (double y : instance.getYi())
				writer.append(y + " ");
		}
		writer.newLine();
		writer.append(MetaConstants.IN_PRED_END);
		writer.newLine();

		writer.append(MetaConstants.IN_CONFUSION_BEGIN);
		writer.newLine();
		int[][] matrix = confMatrix.getMatrix();
		for (int i = 0; i < confMatrix.getNumLables(); i++)
			for (int j = 0; j < confMatrix.getNumLables(); j++)
				writer.append(matrix[i][j] + " ");
		writer.newLine();
		writer.append(MetaConstants.IN_CONFUSION_END);
		writer.newLine();

		writer.append(MetaConstants.IN_MODEL_BEGIN);
		writer.newLine();
		writer.append(params.getParams().length + " ");
		for (double param : params.getParams())
			writer.append(param + " ");
		writer.newLine();
		writer.append(MetaConstants.IN_MODEL_END);

		writer.close();
	}

	public String update() {
		String result = "success";
		setBiasMatrix(dispMatrix);
		CurrentState cstate = CurrentState
				.createCurrentState(getPredInstances());

		TargetStateCalculator tstateCalc = new TargetStateCalculator(this,
				cstate);
		TargetState tstate = tstateCalc.calculate();

		Optimizer optimizer = new Optimizer(this, cstate, tstate, null);
		ModelParams params = null;
		try {
			params = optimizer.optimize();
		} catch (Exception e) {
			params = null;
		}
		if (null != params)
			setDispMatrix(confMatrix);
		else
			result = "failure";

		return result;
	}
}
