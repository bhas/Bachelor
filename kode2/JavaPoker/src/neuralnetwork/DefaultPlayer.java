package neuralnetwork;

import java.io.IOException;
import java.util.Arrays;

import montecarlo.ProbabilityCalc;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.learning.DataSet;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.core.transfer.Sigmoid;
import org.neuroph.core.transfer.Step;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.Perceptron;
import org.neuroph.nnet.learning.PerceptronLearning;
import org.neuroph.util.TransferFunctionType;

import data.analyse.DataAnalyser;
import data.analyse.DataAnalyser.State;
import data.analyse.DataReader;
import data.analyse.DataReader.DataHolder;

public class DefaultPlayer {
	private NeuralNetwork neural;
	private DataSet dataset;
	public static String datasetFile = "nn-dataset";
	public static String neuralNetwork = "nn-trained";
	private SupervisedLearning learning;

	public static void main(String[] args) throws IOException {
		long startTime = System.nanoTime();
		//code

		DefaultPlayer dp = new DefaultPlayer();
		dp.designNN1();
		dp.loadData();
		dp.train();
		
		// defensive
		dp.test("Ac Ad", null, 1); //d
		dp.test("Qh 9d", null, 1); //d
		dp.test("2h 5h", null, 1); //d
		
		dp.test("Ac Ad", null, 3); //d
		dp.test("Qh 9d", null, 3); //d
		dp.test("2h 5h", null, 3); //d
		
		dp.test("Ac Ad", null, 6); //d
		dp.test("Qh 9d", null, 6); //d
		dp.test("2h 5h", null, 6); //d
		
		dp.test("Ac Ad", null, 9); //d
		dp.test("Qh 9d", null, 9); //d
		dp.test("2h 5h", null, 9); //d
		
		dp.test("Ac Ad", "5d 9s Kc", 3); //d
		dp.test("Ac Ad", "5d 9s Kc 2h", 3); //a
		dp.test("Ac Ad", "5d 9s Kc 2h", 2); //d
		dp.test("Ad Kd", "Qd Jd Td 2h 7c", 9);//d
		long endTime = System.nanoTime();
		System.out.println("Took "+ (endTime - startTime )/1000000000.0 + " seconds"); 
		
		//aggressive
		
	}

	private Neuron getNeuron(int layer, int index) {
		return neural.getLayerAt(layer).getNeuronAt(index);
	}

	private void loadData() {
		DataReader dr = new DataReader();
		DataAnalyser da = new DataAnalyser();

		try {
			int a = 0;
			DataHolder data = dr.next();
			while (data != null ) {
				System.out.println("Game: " + data.id);
				int totalChips = 0;
				for (int[] i : data.profits) {
					totalChips += i[2];
				}

				for (int i = 0; i < data.hands.length; i++) {
					if (!data.hands[i].equals("-")) {
						// found a player with a hand

						// pre-flop
						State[] states = da.getStates(i, data.preflopActs, 0,
								data.profits[i][2]);
						saveStates(data.hands[i], null, states, totalChips);
						// balance after preflop
						int postBal = states[states.length - 1].postBal;

						// flop
						states = da.getStates(i, data.flopActs,
								data.preflopPot, postBal);
						saveStates(data.hands[i], data.cc.substring(0, 8),
								states, totalChips);
						postBal = states[states.length - 1].postBal;

						// turn
						states = da.getStates(i, data.turnActs, data.flopPot,
								postBal);
						saveStates(data.hands[i], data.cc.substring(0, 11),
								states, totalChips);
						postBal = states[states.length - 1].postBal;

						// river
						states = da.getStates(i, data.riverActs, data.turnPot,
								postBal);
						saveStates(data.hands[i], data.cc, states, totalChips);
					}
				}
				a++;
				data = dr.next();
				System.out.println("done with " + a);
				dataset.save(datasetFile);
			}
			dataset.save(datasetFile);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			dr.closeConnection();
		}
	}

	private void saveStates(String wc, String cc, State[] states, int totalChips) {
		for (State s : states) {
			double[] inputData = toInputData(wc, cc, s.numOfOpponents,
					s.preBal, s.cost, s.profit, totalChips);
			double[] outputData = s.aggressive ? new double[] { 0, 1 }
					: new double[] { 1, 0 };
//			System.out.println(Arrays.toString(inputData) + " "
//					+ Arrays.toString(outputData));
			dataset.addRow(inputData, outputData);
		}

	}

	private void test(String wc, String cc, int opponents) {
		double[] inputData = toInputData(wc, cc, opponents, 0, 0,
				0, 0);
		neural.setInput(inputData);
		neural.calculate();
		double[] networkOutput = neural.getOutput();
		System.out.println(Arrays.toString(inputData) + " -> " + Arrays.toString(networkOutput));

	}

	private void train() {
		System.out.println("learning");
		DataSet dataset = DataSet.load(datasetFile);
		neural.setLearningRule(learning);
		neural.learn(dataset);
		neural.save(neuralNetwork);
		System.out.println("finished learning");
	}

	private double[] toInputData(String wc, String cc, int opponents,
			int chips, int cost, int profit, int totalChips) {
		// String s = "input: wc:" + wc + " cc:" + cc + " opps:" + opponents
		// + " chips:" + chips + " cost:" + cost + " profit:" + profit;
		double[] inputData = new double[2];
		inputData[0] = ProbabilityCalc.getProbability(wc, cc, 1).percent();
		inputData[1] = opponents / 9.0;
		// inputData[2] = chips / (double) totalChips;
		// inputData[3] = cost / (double) totalChips;
		// inputData[4] = profit / (double) totalChips;
		// System.out.println(s + " -> " + Arrays.toString(inputData));
		return inputData;

	}

	public void designNN2() {
		// create new neural network
		neural = new MultiLayerPerceptron(5, 2, 2);

		Neuron n = getNeuron(1, 0);

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
	}

	public void designNN1() {
		// create new neural network
		neural = new Perceptron(2, 2, TransferFunctionType.SIGMOID);
		dataset = new DataSet(2, 2);

		learning = new PerceptronLearning();
		learning.setMaxIterations(10000);
		learning.setMaxError(0.001);
		learning.setLearningRate(0.5);
	}

}
