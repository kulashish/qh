package in.ac.iitb.cse.qh.test;

import cc.mallet.types.FeatureConjunction.List;
import java.util.Date;
import in.ac.iitb.cse.qh.data.BiasMatrix;
import in.ac.iitb.cse.qh.data.CurrentState;
import in.ac.iitb.cse.qh.data.CurrentStateVector;
import in.ac.iitb.cse.qh.data.InputData;
import in.ac.iitb.cse.qh.data.InputPredictionInstance;
import in.ac.iitb.cse.qh.data.ModelParams;
import in.ac.iitb.cse.qh.data.TargetState;
import in.ac.iitb.cse.qh.meta.ClassifierProxy;
import in.ac.iitb.cse.qh.meta.Optimizer;
import in.ac.iitb.cse.qh.meta.TargetStateCalculator;
import in.ac.iitb.cse.qh.util.KLDivergenceCalculator;
import in.ac.iitb.cse.qh.util.MetaConstants;

public class MetaClassifierTester {

	public static void main(String[] args) {
		Date date = new Date();
		long start=date.getTime();
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
		in.setMaxIterations(10);

			tester.changebias(in.getBiasMatrix());
			//
			CurrentState cstate = CurrentState.createCurrentState(in
					.getPredInstances());
			
			int i=0;
			for(CurrentStateVector cs : cstate.getP())
			{
				if(cs.getPi()[0] == 0)
				{
					System.out.println("i = "+i);
					System.out.println("p0 = "+cs.getPi()[0]);
				}
				if(cs.getPi()[1] == 0)
				{
					System.out.println("i = "+i);
					System.out.println("p1 = "+cs.getPi()[1]);
				}
				i++;
			}
			
			// //
			cstate.serialize(MetaConstants.CSTATE_FILE_PATH);
			//
			System.out.println("Setting target state");
			TargetStateCalculator tstateCalc = new TargetStateCalculator(in,
					cstate);
			TargetState tstate = tstateCalc.calculate();
			// //
			tstate.serialize(MetaConstants.TSTATE_FILE_PATH);
			// //
			double kldiv = KLDivergenceCalculator.calculate(cstate, tstate);
			System.out.println("Initial kldiv="+kldiv);
			
			System.out.println("optimizing");
			in.setMaxIterations(10);
			Optimizer optimizer = new Optimizer(in, cstate, tstate, proxy);
			//ModelParams params = optimizer.optimize();
			ModelParams params = optimizer.optimize2();
			System.out.println("optimized params : " + params);
		} catch (Exception e) {
			e.printStackTrace();
		}

		date= new Date();  
		long end=date.getTime();
		System.out.println("start="+start);
		System.out.println("end="+end);
		System.out.println("Time="+(end-start)/60000+" minutes");
	}

	private void changebias(BiasMatrix biasMatrix) {
		//biasMatrix.setFp(biasMatrix.getFp() -2);
		biasMatrix.setFp(biasMatrix.getFp() -8000);
		//biasMatrix.setFn(biasMatrix.getFn() - 5);
	}
}
