package testing;

import java.io.IOException;

import neuralnetwork.NNManager;

public class NNTest {
	public static void main(String[] args) throws IOException {

		NNManager dp = new NNManager(2);
//		dp.loadDataset("nn/default-data1");
		dp.createDataset(200);
//		dp.train(0, 100);
//		dp.test("Ac Kc", "Qc Tc Jd", 9);

		// dp.train();

		// defensive
		// dp.test("Ac Ad", null, 1); // d
		// dp.test("Qh 9d", null, 1); // d
		// dp.test("2h 5h", null, 1); // d
		//
		// dp.test("Ac Ad", null, 3); // d
		// dp.test("Qh 9d", null, 3); // d
		// dp.test("2h 5h", null, 3); // d
		//
		// dp.test("Ac Ad", null, 6); // d
		// dp.test("Qh 9d", null, 6); // d
		// dp.test("2h 5h", null, 6); // d
		//
		// dp.test("Ac Ad", null, 9); // d
		// dp.test("Qh 9d", null, 9); // d
		// dp.test("2h 5h", null, 9); // d
		//
		// dp.test("Ac Ad", "5d 9s Kc", 3); // d
		// dp.test("Ac Ad", "5d 9s Kc 2h", 3); // a
		// dp.test("Ac Ad", "5d 9s Kc 2h", 2); // d
		// dp.test("Ad Kd", "Qd Jd Td 2h 7c", 9);// d
		// long endTime = System.nanoTime();
		// System.out.println("Took " + (endTime - startTime) / 1000000000.0
		// + " seconds");

		// aggressive

	}
}
