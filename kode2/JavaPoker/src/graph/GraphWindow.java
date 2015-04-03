package graph;

import java.awt.Dimension;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GraphWindow extends JFrame {
	
	public GraphWindow(Graph graph) {
		super("Graph");
		add(graph);
		setSize(new Dimension(1300,600));
		setVisible(true);
	}
	
	public static void main(String[] args) {
		GraphData gd = new GraphData();
		gd.addEntry(1, .9);
		gd.addEntry(2, .5);
		gd.addEntry(4, .15);
		gd.addEntry(3, .35);
		
		Graph g = new Graph(gd);
		g.setDescriptions("Monte Carlo tests", "Probability");
		g.setViewY(0, 1);
		g.setViewX(0, 5);
		
		new GraphWindow(g);
	}
}
