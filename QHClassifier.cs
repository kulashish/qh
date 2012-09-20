using System;

class QHClassifier
{
	public static void Main(String[] args)
	{
		try
		{
			// Load the model
			java.io.ObjectInputStream stream = new java.io.ObjectInputStream(new java.io.FileInputStream("iris_j48.model"));
			weka.classifiers.Classifier qhClassifier = (weka.classifiers.Classifier)stream.readObject();
			stream.close();
			
			// This model was trained on 66% of instances from the iris dataset. Test the model on remaining 34% instances.
			weka.core.Instances insts = new weka.core.Instances(new java.io.FileReader("iris.arff"));
             		insts.setClassIndex(insts.numAttributes() - 1);
			int percentSplit = 66;
			int trainSize = insts.numInstances() * percentSplit / 100;
			int testSize = insts.numInstances() - trainSize;
			int numCorrect = 0;
		        for (int i = trainSize; i < insts.numInstances(); i++)
             		{
                 		weka.core.Instance currentInst = insts.instance(i);
                 		double predictedClass = qhClassifier.classifyInstance(currentInst);
                 		if (predictedClass == insts.instance(i).classValue())
                     			numCorrect++;
             		}
             		Console.WriteLine(numCorrect + " out of " + testSize + " correct (" + (double)((double)numCorrect / (double)testSize * 100.0) + "%)");
		}
		catch(java.lang.Exception e)
		{
			e.printStackTrace();
		}
	}
}
