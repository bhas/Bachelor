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
		 testData("Ah Ac", 1);
		 testData("Ah Ac", 4);
	}

	public void createNN() {
		// create training set
		DataSet trainingSet = new DataSet(2, 1);
		addEntry(trainingSet, "As Ac", 2, 0.85);
		addEntry(trainingSet, "As Ks", 2, 0.67);
		addEntry(trainingSet, "As Qh", 2, 0.64);
		addEntry(trainingSet, "Ks 9s", 2, 0.60);
		addEntry(trainingSet, "Qs Tc", 2, 0.57);
		addEntry(trainingSet, "9s 6c", 2, 0.44);
		addEntry(trainingSet, "8s 8c", 2, 0.69);
		addEntry(trainingSet, "4s 2c", 2, 0.32);
		addEntry(trainingSet, "3s 2h", 2, 0.31);
		addEntry(trainingSet, "Js 6s", 2, 0.47);
		addEntry(trainingSet, "Qs 5c", 2, 0.47);
		addEntry(trainingSet, "4s 2s", 2, 0.34);
		addEntry(trainingSet, "5s 2c", 2, 0.31);
		addEntry(trainingSet, "5s 4h", 2, 0.35);
		addEntry(trainingSet, "7s 4c", 2, 0.35);
		addEntry(trainingSet, "8s 4s", 2, 0.40);
		addEntry(trainingSet, "Ts 2c", 2, 0.39);
		
		addEntry(trainingSet, "As Ac", 3, 0.75);
		addEntry(trainingSet, "As Ks", 3, 0.55);
		addEntry(trainingSet, "As Qh", 3, 0.53);
		addEntry(trainingSet, "Ks 9s", 3, 0.50);
		addEntry(trainingSet, "Qs Tc", 3, 0.47);
		addEntry(trainingSet, "9s 6c", 3, 0.34);
		addEntry(trainingSet, "8s 8c", 3, 0.59);
		addEntry(trainingSet, "4s 2c", 3, 0.22);
		addEntry(trainingSet, "3s 2h", 3, 0.21);
		addEntry(trainingSet, "Js 6s", 3, 0.27);
		addEntry(trainingSet, "Qs 5c", 3, 0.37);
		addEntry(trainingSet, "4s 2s", 3, 0.24);
		addEntry(trainingSet, "5s 2c", 3, 0.21);
		addEntry(trainingSet, "5s 4h", 3, 0.25);
		addEntry(trainingSet, "7s 4c", 3, 0.25);
		addEntry(trainingSet, "8s 4s", 3, 0.30);
		addEntry(trainingSet, "Ts 2c", 3, 0.29);
		
		addEntry(trainingSet, "As Ac", 4, 0.65);
		addEntry(trainingSet, "As Ks", 4, 0.50);
		addEntry(trainingSet, "As Qh", 4, 0.48);
		addEntry(trainingSet, "Ks 9s", 4, 0.45);
		addEntry(trainingSet, "Qs Tc", 4, 0.42);
		addEntry(trainingSet, "9s 6c", 4, 0.29);
		addEntry(trainingSet, "8s 8c", 4, 0.54);
		addEntry(trainingSet, "4s 2c", 4, 0.17);
		addEntry(trainingSet, "3s 2h", 4, 0.16);
		addEntry(trainingSet, "Js 6s", 4, 0.22);
		addEntry(trainingSet, "Qs 5c", 4, 0.32);
		addEntry(trainingSet, "4s 2s", 4, 0.19);
		addEntry(trainingSet, "5s 2c", 4, 0.161);
		addEntry(trainingSet, "5s 4h", 4, 0.20);
		addEntry(trainingSet, "7s 4c", 4, 0.20);
		addEntry(trainingSet, "8s 4s", 4, 0.25);
		addEntry(trainingSet, "Ts 2c", 4, 0.24);

		
		
		// create new perceptron network
		neural = new MultiLayerPerceptron(2,1);

		// learn the training set
		System.out.println("learning");
		neural.learn(trainingSet);
		System.out.println("finished learning");
	}

	private void testData(String hand, int opps) {
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
