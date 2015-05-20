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
	}

	public void createNN() {
		// create training set
		DataSet trainingSet = new DataSet(2, 1);

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
