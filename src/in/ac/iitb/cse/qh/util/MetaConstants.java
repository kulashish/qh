package in.ac.iitb.cse.qh.util;

public interface MetaConstants {

	public static final int MAX_POWER = 300;
	public static final int NUMBER_CLASSLABELS = 2;
	// public static final double SCALING_BETA = 0.00001;
	public static final double SCALING_BETA = 10d;
	public static final double INITIAL_STEP = 5.0e-2d;
	public static final int MAX_OPTIM_ITERATIONS = 2;
	public static final String UPLOAD_PATH = "/home/ashish/meta/";
	//public static final String TRAIN_FILE_PATH = "/media/F/Acads/iitb/mtp/QuickHeal/data/split1lac.arff";
	//public static final String TRAIN_FILE_PATH = "/media/F/Acads/iitb/mtp/QuickHeal/data/split1per1.arff";
	//public static final String TRAIN_FILE_PATH = "/media/F/Acads/iitb/mtp/QuickHeal/data/split10k-train.arff";
	//public static final String TRAIN_FILE_PATH = "/home/agam/mtp/QuickHeal/data/split10k-train.arff";
	//public static final String TRAIN_FILE_PATH = "/media/F/Acads/iitb/mtp/QuickHeal/data/split1s.arff";
//	public static final String TRAIN_FILE_PATH ="/media/F/Acads/iitb/mtp/QuickHeal/data/split10k-test.arff";
	//public static final String TRAIN_FILE_PATH ="/home/agam/mtp/QuickHeal/data/split10k-test.arff";
	
	//public static final String TRAIN_FILE_PATH = "/media/F/Acads/iitb/mtp/QuickHeal/data/split2k.arff";
	
//	public static final String TRAIN_FILE_PATH = "/home/ashish/Documents/QuickHealRD/data/split10k-train.arff";
//	public static final String HOLDOUT_FILE_PATH ="/home/ashish/Documents/QuickHealRD/data/split10k-test.arff";
	public static final String TRAIN_FILE_PATH = "/home/ashish/Documents/wiki annotation work/10K-train-random.arff";
	public static final String HOLDOUT_FILE_PATH ="/home/ashish/Documents/wiki annotation work/10K-test-random.arff";
	
	//public static final String HOLDOUT_FILE_PATH ="/media/F/Acads/iitb/mtp/QuickHeal/data/split10k-test.arff";
	//public static final String HOLDOUT_FILE_PATH = "/media/F/Acads/iitb/mtp/QuickHeal/data/split10k-train.arff";
	//public static final String HOLDOUT_FILE_PATH = "/media/F/Acads/iitb/mtp/QuickHeal/data/split1per1.arff";
	//public static final String HOLDOUT_FILE_PATH = "/home/agam/mtp/QuickHeal/data/split3per.arff";
//	public static final String HOLDOUT_FILE_PATH = "/media/F/Acads/iitb/mtp/QuickHeal/data/split3per.arff";
	
	//public static final String HOLDOUT_FILE_PATH = "/media/F/Acads/iitb/mtp/QuickHeal/data/split12.arff";
	
	public static final String IN_FILE_PATH = "/home/ashish/lr.dat";
	public static final String NEW_FILE_PATH = "/home/ashish/lr_last.dat";
	public static final String CSTATE_FILE_PATH = "/home/ashish/cstate.dat";
	public static final String TSTATE_FILE_PATH = "/home/ashish/tstate.dat";
	public static final String OPTIMIZED_FILE_PATH = "/home/ashish/optimized.dat";
	public static final String BEAN_DIVERGENCE_CHART = "metachart";

	public static final String IN_PRED_BEGIN = "@begin_prediction";
	public static final String IN_PRED_END = "@end_prediction";
	public static final String IN_CONFUSION_BEGIN = "@begin_confusion";
	public static final String IN_CONFUSION_END = "@end_confusion";
	public static final String IN_MODEL_BEGIN = "@begin_model";
	public static final String IN_MODEL_END = "@end_model";
}
