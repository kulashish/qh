package in.ac.iitb.cse.qh.classifiers;

import in.ac.iitb.cse.qh.util.WekaUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import weka.core.Instance;
import weka.core.Instances;

public class ClassifyDataFromModel {

	/**
	 * @param args
	 */
	
	ModifiedLogistic modLog;
	String modelFile;
	String testFile;
	Instances instances;
	
	public ClassifyDataFromModel(String modelFile, String testFile) throws Exception {
		this.modelFile=modelFile;
		this.testFile=testFile;
		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(modelFile));
		modLog = (ModifiedLogistic) ois.readObject();
		ois.close();
		
		instances=WekaUtil.getInstances(testFile);
	}
	
	public void classifyAll() throws Exception
	{
		for(int i=0;i< instances.numInstances();i++)
		{
			Instance inst = instances.get(i);
			System.out.println(modLog.classifyInstance(inst));
		}
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		ClassifyDataFromModel classify =  new ClassifyDataFromModel(args[0], args[1]);
		classify.classifyAll();
	}

}
