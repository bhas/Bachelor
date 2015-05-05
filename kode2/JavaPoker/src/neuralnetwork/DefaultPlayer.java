package neuralnetwork;

import java.io.IOException;

import montecarlo.Probability;
import montecarlo.ProbabilityCalc;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.learning.DataSet;
import org.neuroph.core.transfer.Step;
import org.neuroph.nnet.MultiLayerPerceptron;

import data.analyse.DataReader;
import data.analyse.DataReader.DataHolder;

public class DefaultPlayer {
	private final static String FILENAME = "DefaultPlayer.nnet";
	private NeuralNetwork neural;

	public static void main(String[] args) throws IOException {
//		DefaultPlayer dp = new DefaultPlayer();
//		dp.designNN();
		train();
	}

	private Neuron getNeuron(int layer, int index) {
		return neural.getLayerAt(layer).getNeuronAt(index);
	}

	public static void train() throws IOException {
//		DataSet trainingSet = new DataSet(5, 2);
		DataReader dr = new DataReader();
		for(int i = 0; i<100; i++){
			DataHolder dh = dr.next();

			for(int j = 0; j<dh.hands.length; j++){
				if(!dh.hands[j].contains("-")){
					
				}
			}
		}		
		
	}
	
	public void cardsAvailable(String[] cards){
	}

	public void addEntry(DataSet data, String wc, String cc, int opponents,
			int chips, int cost, int profit, int totalChips, int expD, int expA) {
		Probability p = ProbabilityCalc.getProbability(wc, cc, opponents);
		double[] inputData = new double[5];
		inputData[0] = ProbabilityCalc.getProbability(wc, cc, opponents)
				.percent();
		inputData[1] = opponents / 9.0;
		inputData[2] = chips / (double) totalChips;
		inputData[3] = cost / (double) totalChips;
		inputData[4] = profit / (double) totalChips;
		data.addRow(inputData, new double[] { expD, expA });

	}

	public void designNN() {
		// create new neural network
		neural = new MultiLayerPerceptron(5, 2, 2);

		Neuron n = getNeuron(1, 0);
		System.out.println(n.getTransferFunction());
		System.out.println(n.getInputFunction());
		System.out.println(n.getInputConnections().length);

		n.removeAllConnections();
		n.addInputConnection(getNeuron(0, 0));
		n.addInputConnection(getNeuron(0, 1));
		n.addInputConnection(getNeuron(0, 5));

		n = getNeuron(1, 1);
		n.removeAllConnections();
		n.addInputConnection(getNeuron(0, 2));
		n.addInputConnection(getNeuron(0, 3));
		n.addInputConnection(getNeuron(0, 4));
		n.addInputConnection(getNeuron(0, 5));

		n = getNeuron(2, 0);
		n.setTransferFunction(new Step());
		n.removeAllConnections();
		n.addInputConnection(getNeuron(1, 0));
		n.addInputConnection(getNeuron(1, 1));
		n.addInputConnection(getNeuron(1, 2));

		n = getNeuron(2, 1);
		n.setTransferFunction(new Step());
		n.removeAllConnections();
		n.addInputConnection(getNeuron(1, 0));
		n.addInputConnection(getNeuron(1, 1));
		n.addInputConnection(getNeuron(1, 2));

		// learn the training set
		// SupervisedLearning learningRule = new PerceptronLearning();
		// learningRule.setMaxIterations(8000);
		// learningRule.setMaxError(0.00001);
		// learningRule.setLearningRate(0.5);
		// System.out.println("learning");
		// neural.setLearningRule(learningRule);
		// neural.learn(trainingSet);
		// System.out.println("finished learning");
	}

}
