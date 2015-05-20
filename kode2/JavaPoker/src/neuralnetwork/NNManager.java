package neuralnetwork;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import montecarlo.ProbabilityCalc;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.Perceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.nnet.learning.PerceptronLearning;
import org.neuroph.util.TransferFunctionType;

import data.analyse.DataAnalyser;
import data.analyse.DataAnalyser.State;
import data.analyse.DataReader;
import data.analyse.DataReader.DataHolder;

public class NNManager {
	private final String datasetFile, nnFile;
	private NeuralNetwork neural;
	private DataSet dataset;
	private SupervisedLearning learning;

	public NNManager(int version, int r, ArrayList<String> games, String name) {
		datasetFile = "nn/default-data" + version;
		nnFile = "nn/default-nn" + version;
		if (version == 1) {
			designNN1();
			// createDataset(100);
		} else if (version == 2) {
			designNN2();
			// createDataset(400);
			loadDataset(datasetFile);
			// test2();
		} else if (version == 3) {
			designNN2();
			createDataset(r, games, name);
		}
	}

	private Neuron getNeuron(int layer, int index) {
		return neural.getLayerAt(layer).getNeuronAt(index);
	}

	public void createDataset(int maxRounds, ArrayList<String> games, String name) {
		DataReader dr = new DataReader();
		DataAnalyser da = new DataAnalyser();

		try {
			int round = 0;
			for (int e = 0; e < 200; e++) {

				DataHolder data = dr.find(games.get(e));
				while (data != null && round < 200) {
					System.out.println("Game: " + data.id);
					int totalChips = 0;
					for (int[] i : data.profits) {
						totalChips += i[2];
					}

					for (int i = 0; i < data.hands.length; i++) {
						if (!data.hands[i].equals("-") && data.players[i].equals(name) ) {
							// found a player with a hand

							// pre-flop
							State[] states = da.getStates(i, data.preflopActs,
									0, data.profits[i][2]);
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
							states = da.getStates(i, data.turnActs,
									data.flopPot, postBal);
							saveStates(data.hands[i], data.cc.substring(0, 11),
									states, totalChips);
							postBal = states[states.length - 1].postBal;

							// river
							states = da.getStates(i, data.riverActs,
									data.turnPot, postBal);
							saveStates(data.hands[i], data.cc, states,
									totalChips);
						}
					}
					round++;
					data = dr.next();
					System.out.println("done with " + round);
				}
			}

			System.out.println("Data loaded!");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			dataset.saveAsTxt("nn/PokiSimA" + ".txt", ",");
			// dataset.save(datasetFile + "test");
			dr.closeConnection();
		}

	}

	private void saveStates(String wc, String cc, State[] states, int totalChips) {
		for (State s : states) {
			double[] inputData = toInputData(wc, cc, s.numOfOpponents,
					s.preBal, s.cost, s.profit, totalChips);
			double[] outputData = s.aggressive ? new double[] { 0, 1 }
					: new double[] { 1, 0 };
			dataset.addRow(inputData, outputData);
		}

	}

	public void test(String wc, String cc, int opponents) {
		double[] inputData = toInputData(wc, cc, opponents, 0, 0, 0, 0);
		neural.setInput(inputData);
		neural.calculate();
		double[] networkOutput = neural.getOutput();
		System.out.println(Arrays.toString(inputData) + " -> "
				+ Arrays.toString(networkOutput));

	}

	public void loadDataset(String file) {
		dataset = DataSet.load(file);
	}

	public void save() {
		neural.save(nnFile);
	}

	public void train() {
		System.out.println("Learning started");
		neural.learn(dataset);

		System.out.println("finished learning");
	}

	private double[] toInputData(String wc, String cc, int opponents,
			int chips, int cost, int profit, int totalChips) {
		// String s = "input: wc:" + wc + " cc:" + cc + " opps:" + opponents
		// + " chips:" + chips + " cost:" + cost + " profit:" + profit;
		double[] inputData = new double[5];
		inputData[0] = ProbabilityCalc.getProbability(wc, cc, 1).percent();
		inputData[1] = opponents / 9.0;
		inputData[2] = chips / (double) totalChips;
		inputData[3] = cost / (double) totalChips;
		inputData[4] = profit / (double) totalChips;
		// System.out.println(s + " -> " + Arrays.toString(inputData));
		return inputData;
	}

	public void designNN2() {
		// create new neural network
		neural = new MultiLayerPerceptron(5, 2, 2);
		dataset = new DataSet(5, 2);
		learning = new BackPropagation();
		learning.setMaxIterations(500);
		learning.setMaxError(0.001);
		learning.setLearningRate(0.5);
		learning.setNeuralNetwork(neural);
		neural.setLearningRule(learning);
	}

	public void designNN1() {
		// create new neural network
		neural = new Perceptron(2, 2, TransferFunctionType.SIGMOID);
		dataset = new DataSet(2, 2);

		learning = new PerceptronLearning();
		learning.setMaxIterations(500);
		learning.setMaxError(0.001);
		learning.setLearningRate(0.5);
		learning.setNeuralNetwork(neural);
		neural.setLearningRule(learning);
	}
}
