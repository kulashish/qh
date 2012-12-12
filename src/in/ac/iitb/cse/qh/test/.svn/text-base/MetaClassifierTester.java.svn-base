package in.ac.iitb.cse.qh.test;

import cc.mallet.types.FeatureConjunction.List;
import in.ac.iitb.cse.qh.data.BiasMatrix;
import in.ac.iitb.cse.qh.data.CurrentState;
import in.ac.iitb.cse.qh.data.InputData;
import in.ac.iitb.cse.qh.data.InputPredictionInstance;
import in.ac.iitb.cse.qh.data.ModelParams;
import in.ac.iitb.cse.qh.data.TargetState;
import in.ac.iitb.cse.qh.meta.ClassifierProxy;
import in.ac.iitb.cse.qh.meta.Optimizer;
import in.ac.iitb.cse.qh.meta.TargetStateCalculator;

public class MetaClassifierTester {

	public static void main(String[] args) {
		MetaClassifierTester tester = new MetaClassifierTester();
		// InputData in = new InputData();
		System.out.println("Loading Input data");
		// in.loadData(MetaConstants.IN_FILE_PATH);
		InputData in = null;
		ClassifierProxy proxy = new ClassifierProxy();
		try {
			System.out.println("classifier proxy instance : " + proxy);
			in = proxy.computeInitialState();
			java.util.List<InputPredictionInstance> insts = in
					.getPredInstances();
			double[] y = null;
			System.out.println("Input data loaded");
		in.setMaxIterations(5);

			tester.changebias(in.getBiasMatrix());
			//
			CurrentState cstate = CurrentState.createCurrentState(in
					.getPredInstances());
			// //
			//cstate.serialize("/home/ashish/Documents/QuickHealRD/cstate.dat");
			//
			System.out.println("Setting target state");
			TargetStateCalculator tstateCalc = new TargetStateCalculator(in,
					cstate);
			TargetState tstate = tstateCalc.calculate();
			// //
			//tstate.serialize("/home/ashish/Documents/QuickHealRD/tstate.dat");
			// //
			System.out.println("optimizing");
			in.setMaxIterations(10);
			Optimizer optimizer = new Optimizer(in, cstate, tstate, proxy);
			//ModelParams params = optimizer.optimize();
			ModelParams params = optimizer.optimize2();
			System.out.println("optimized params : " + params);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void changebias(BiasMatrix biasMatrix) {
		biasMatrix.setFp(biasMatrix.getFp() - 10);
		//biasMatrix.setFn(biasMatrix.getFn() - 5);
	}
}
