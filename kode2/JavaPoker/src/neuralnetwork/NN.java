package neuralnetwork;

import game.GameSettings;
import game.essentials.Card;

import java.util.Random;

import montecarlo.Probability;
import montecarlo.ProbabilityCalc;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.DataSet;
import org.neuroph.core.learning.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.Perceptron;

public class NN {
	private final static String FILENAME = "or_perceptron.nnet";
	private NeuralNetwork neural;

	public static void main(String[] args) {
		new NN();
	}

	public NN() {
		 createNN();
		 testData("3s 3c", 1);
//		createNN2();
//		test2(0.2, 0.8);
//		test2(0.2, 0.7);
//		test2(0.1, 0.1);
	}

	public void createNN() {
		// create training set
		DataSet trainingSet = new DataSet(2, 1);
		addEntry(trainingSet, "As Ac", 1, 0.85);
		addEntry(trainingSet, "As Ks", 1, 0.67);
		addEntry(trainingSet, "As Qh", 1, 0.64);
		addEntry(trainingSet, "Ks 9s", 1, 0.60);
		addEntry(trainingSet, "Qs Tc", 1, 0.57);
		addEntry(trainingSet, "9s 6c", 1, 0.449);
		addEntry(trainingSet, "8s 8c", 1, 0.69);
		addEntry(trainingSet, "4s 2c", 1, 0.32);
		addEntry(trainingSet, "3s 2h", 1, 0.31);
		
		
		
		// create new perceptron network
		neural = new MultiLayerPerceptron(2,1);

		// learn the training set
		System.out.println("learning");
		neural.learn(trainingSet);
		System.out.println("finished learning");
	}

	private void testData(String hand, int opps) {
		System.out.println("tester");
		Probability p = ProbabilityCalc.getProbability(
				Card.createMultiple(hand), null, 1);

		// set network input
		neural.setInput(p.percent(), opps / (GameSettings.TABLE_SEATS - 1));
		// calculate network
		neural.calculate();
		// get network output
		double[] networkOutput = neural.getOutput();

		System.out.println(hand + " (" + p.percent() + ") " + opps + "  = "
				+ networkOutput[0]);

	}

	private void addEntry(DataSet set, String hand, int opps, double exp) {
		Probability p = ProbabilityCalc.getProbability(
				Card.createMultiple(hand), null, 1);

		set.addRow(new double[] { p.percent(),
				opps / (GameSettings.TABLE_SEATS - 1) }, new double[] { exp });
		System.out.println(hand + " (" + p.percent() + ") " + opps + "  = "
				+ exp);

	}

}
