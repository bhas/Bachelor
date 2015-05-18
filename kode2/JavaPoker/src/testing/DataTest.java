package testing;

import graph.Graph;
import graph.GraphData;
import graph.GraphWindow;

import java.util.Random;

public class DataTest {
	private static Random r = new Random();

	public static void main(String[] args) {
		GraphData gda = new GraphData();
		GraphData gdd = new GraphData();

		for (int i = 0; i < 100; i++) {
			gdd.addEntry(i, r.nextDouble() * 0.48 + 0.2);
			gda.addEntry(i, r.nextDouble() * 0.4 + 0.45);
		}

		double[] dataA = new double[] { 0.91, 0.92, 1, 0.36, 0.32, 0.95, 0.17,
				0.31, 0.97, 0.92, 0.32, 0.32, 0.99 };

		double[] dataD = new double[] { 0.11, 0.17, 0.14, 0.81, 0.12, 0.85,
				0.18, 0.83, 0.89, 0.14, 0.11, 0.19 };

		for (int i = 0; i < dataD.length; i++) {
			gda.addEntry(7 * i + 0.5, dataA[i]);
			gdd.addEntry(i * 7 + 0.5, dataD[i]);
		}

		Graph g = new Graph(gda);
		g.addDataset(gdd);
		g.setDescriptions("", "Hand strength");
		g.setViewX(0, 100);
		g.setViewY(0, 1);
		new GraphWindow(g);

	}
}
