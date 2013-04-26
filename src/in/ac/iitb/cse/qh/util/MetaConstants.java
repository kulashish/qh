package in.ac.iitb.cse.qh.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class MetaConstants {

	public static final int MAX_POWER = 300;
	public static final int NUMBER_CLASSLABELS = 2;
	// public static final double SCALING_BETA = 0.00001;
	public static final double SCALING_BETA = 10d;
	public static final double INITIAL_STEP = 5.0e-2d;
	public static final int MAX_OPTIM_ITERATIONS = 5;
	// public static final String ROOT_PATH = ConfigureProperties.getInstance()
	// .getProperty("root_path");
	public static String ROOT_PATH = "/home/agam/";
	public static String UPLOAD_PATH = ROOT_PATH + "/meta/";
	public static String MODEL_DOWNLOAD_PATH = ROOT_PATH + "/model/";
	public static final String TRAIN_FILE_PATH = "/media/F/Acads/iitb/mtp/QuickHeal/data/split10k-test.arff";
	public static final String HOLDOUT_FILE_PATH = "/media/F/Acads/iitb/mtp/QuickHeal/data/split3per.arff";
	public static String IN_FILE_PATH = ROOT_PATH + "/lr.dat";
	public static String NEW_FILE_PATH = ROOT_PATH + "/lr_last.dat";
	public static String CSTATE_FILE_PATH = ROOT_PATH + "/cstate.dat";
	public static String TSTATE_FILE_PATH = ROOT_PATH + "/tstate.dat";
	public static String OPTIMIZED_FILE_PATH = ROOT_PATH + "/optimized.dat";
	public static final String BEAN_DIVERGENCE_CHART = "metachart";

	public static final String IN_PRED_BEGIN = "@begin_prediction";
	public static final String IN_PRED_END = "@end_prediction";
	public static final String IN_CONFUSION_BEGIN = "@begin_confusion";
	public static final String IN_CONFUSION_END = "@end_confusion";
	public static final String IN_MODEL_BEGIN = "@begin_model";
	public static final String IN_MODEL_END = "@end_model";

	public static final String IN_WMODEL_BEGIN = "@begin_Wmodel";
	public static final String IN_WMODEL_END = "@end_Wmodel";

	public static void configure() {
		UPLOAD_PATH = ROOT_PATH + "/meta/";
		IN_FILE_PATH = ROOT_PATH + "/lr.dat";
		NEW_FILE_PATH = ROOT_PATH + "/lr_last.dat";
		CSTATE_FILE_PATH = ROOT_PATH + "/cstate.dat";
		TSTATE_FILE_PATH = ROOT_PATH + "/tstate.dat";
		OPTIMIZED_FILE_PATH = ROOT_PATH + "/optimized.dat";
		MODEL_DOWNLOAD_PATH = ROOT_PATH + "/model/";
	}
}
