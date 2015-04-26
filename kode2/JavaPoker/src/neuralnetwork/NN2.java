package neuralnetwork;

import game.GameSettings;
import game.essentials.Card;
import montecarlo.Probability;
import montecarlo.ProbabilityCalc;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.DataSet;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.nnet.Perceptron;
import org.neuroph.nnet.learning.PerceptronLearning;
import org.neuroph.util.TransferFunctionType;

public class NN2 {
	private final static String FILENAME = "nn2.nnet";
	private NeuralNetwork neural;

	public static void main(String[] args) {
		NN2 nn2 = new NN2();
	}

	public static void printProb(String hand) {
		Probability p = ProbabilityCalc.getProbability(hand, null, 1);
		System.out.println(hand + " - " + p.percent());
	}

	public NN2() {
		createNN();
		testData("Kh Kc", 1);
		testData("Kh Kc", 3);
		testData("Kh Kc", 5);
		testData("2c 7d", 5);
		testData("2s 6c", 4);
	}

	public void createNN() {
		// create training set
		DataSet trainingSet = new DataSet(2, 1);
		addEntry(trainingSet, "As Ac", 1);
		addEntry(trainingSet, "As Ks", 1);
		addEntry(trainingSet, "As Qh", 1);
		addEntry(trainingSet, "Ks 9s", 1);
		addEntry(trainingSet, "Qs Tc", 1);
		addEntry(trainingSet, "9s 6c", 1);
		addEntry(trainingSet, "4s 2c", 1);
		addEntry(trainingSet, "8s 8c", 1);

		addEntry(trainingSet, "As Ac", 3);
		addEntry(trainingSet, "As Ks", 3);
		addEntry(trainingSet, "As Qh", 3);
		addEntry(trainingSet, "Ks 9s", 3);
		addEntry(trainingSet, "Qs Tc", 3);
		addEntry(trainingSet, "9s 6c", 3);
		addEntry(trainingSet, "4s 2c", 3);
		addEntry(trainingSet, "8s 8c", 3);

		// create new perceptron network
		neural = new Perceptron(2, 1, TransferFunctionType.SIGMOID);

		// learn the training set
		SupervisedLearning learningRule = new PerceptronLearning();
		learningRule.setMaxIterations(8000);
		learningRule.setMaxError(0.00001);
		learningRule.setLearningRate(0.5);
		System.out.println("learning");
		neural.setLearningRule(learningRule);
		neural.learn(trainingSet);
		System.out.println("finished learning");
	}

	private void testData(String hand, int opps) {
		Probability p = ProbabilityCalc.getProbability(
				Card.createMultiple(hand), null, 1);
		double opp2 = opps / (GameSettings.TABLE_SEATS - 1.);

		// set network input
		neural.setInput(p.percent(), opp2);
		// calculate network
		neural.calculate();
		// get network output
		double[] networkOutput = neural.getOutput();

		System.out.println(hand + " (" + p.percent() + ") " + opp2 + "  = "
				+ networkOutput[0]);

	}

	private void addEntry(DataSet set, String hand, int opps) {
		double str = ProbabilityCalc.getProbability(
				Card.createMultiple(hand), null, 1).percent();
		double opp2 = opps / (GameSettings.TABLE_SEATS - 1.);
		double exp = str - 0.4 * str * opp2;

		set.addRow(new double[] { str, opp2 }, new double[] { exp });
		System.out.println(hand + " (" + str + ") " + opp2 + "  = "
				+ exp);

	}
}
