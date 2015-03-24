package graph;

import graph.GraphData.Entry;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Graph extends JComponent {
	private static final float LINE_SIZE = 1.4f;
	private static final int DOT_SIZE = 6;
	private static final DecimalFormat DF_X = new DecimalFormat("0");
	private static final DecimalFormat DF_Y = new DecimalFormat("0.00");
	private static final int VER_SECTIONS = 10;
	private static final int HOR_SECTIONS = 5;

	// view dimensions
	private double vxMin, vxMax, vyMin, vyMax;
	private Rectangle2D gbox;
	private String xDesc, yDesc;
	private ArrayList<GraphData> datasets;
	private Color[] colors = new Color[] { new Color(40,40,40), Color.BLUE, Color.RED,
			new Color(0, 220, 0), Color.ORANGE, Color.PINK };

	public Graph() {
		// setSize(500,700);
		vxMin = 0;
		vxMax = 10;
		vyMin = 0;
		vyMax = 100;
		gbox = new Rectangle2D.Double(100, 10, 600, 400);

		datasets = new ArrayList<GraphData>();
	}

	public Graph(GraphData data) {
		this();
		datasets.add(data);
	}

	public void setViewX(double min, double max) {
		vxMin = min;
		vxMax = max;
	}

	public void setViewY(double min, double max) {
		vyMin = min;
		vyMax = max;
	}

	public void setDescriptions(String x, String y) {
		xDesc = x;
		yDesc = y;
	}

	public void addDataset(GraphData data) {
		datasets.add(data);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(new Color(200, 200, 200));
		g2.fillRect(0, 0, getWidth(), getHeight());

		drawGraphContainer(g2);
		g2.setClip(gbox);
		for (int i = 0; i < datasets.size(); i++) {
			drawDataset(g2, datasets.get(i), colors[i]);
		}
	}

	private void drawGraphContainer(Graphics2D g) {
		int x1 = (int) gbox.getX();
		int x2 = (int) gbox.getMaxX();
		int y2 = (int) gbox.getMaxY();

		// background
		g.setColor(Color.white);
		g.fill(gbox);

		// horizontal value lines
		g.setColor(new Color(210, 210, 210));
		for (int i = 1; i <= VER_SECTIONS; i++) {
			int y = (int) (y2 - (gbox.getHeight() * i / VER_SECTIONS));
			g.drawLine(x1, y, x2, y);
		}

		// black lines left and bottom
		g.setColor(Color.black);
		g.setStroke(new BasicStroke(1.5f));
		g.draw(gbox);

		drawYs(g);
		drawXs(g);
		drawDescriptions(g);
	}

	private void drawDescriptions(Graphics2D g) {
		FontMetrics fm = g.getFontMetrics();
		if (xDesc != null) {
			int sw = fm.stringWidth(xDesc);
			g.drawString(xDesc, (int) gbox.getCenterX() - sw/2, (int) gbox.getMaxY() + 40);
		}
		if (yDesc != null) {
			int sw = fm.stringWidth(yDesc);
			g.drawString(yDesc, (int) gbox.getX() - 40 - sw, (int) gbox.getCenterY());
		}
	}

	private void drawXs(Graphics2D g) {
		FontMetrics fm = g.getFontMetrics();
		g.setColor(Color.BLACK);
		int y = (int) gbox.getMaxY() + 16;
		double sectionSize = (vxMax - vxMin) / HOR_SECTIONS;

		for (int i = 0; i <= HOR_SECTIONS; i++) {
			String s = DF_X.format(vxMin + i * sectionSize);
			int sw = fm.stringWidth(s);
			int x = (int) (gbox.getX() + (gbox.getWidth() * i / HOR_SECTIONS));

			g.drawString(s, x - sw / 2, y);
		}
	}

	private void drawYs(Graphics2D g) {
		FontMetrics fm = g.getFontMetrics();
		g.setColor(Color.BLACK);
		int x = (int) gbox.getX() - 10;
		double sectionSize = (vyMax - vyMin) / VER_SECTIONS;

		for (int i = 0; i <= VER_SECTIONS; i++) {
			String s = DF_Y.format(vyMin + i * sectionSize);
			int sw = fm.stringWidth(s);
			int y = (int) (gbox.getMaxY() - (gbox.getHeight() * i / VER_SECTIONS));
			double val = vyMin + i * sectionSize;
			g.drawString(DF_Y.format(val), x - sw, y + 5);
		}
	}

	private void drawDataset(Graphics2D g, GraphData data, Color c) {
		Point p0 = null;
		g.setColor(c);
		g.setStroke(new BasicStroke(LINE_SIZE));
		for (Entry e : data.getEntries()) {
			Point p = getPoint(e);
			if (p0 != null) {
				g.drawLine(p0.x, p0.y, p.x, p.y);
			}

			dot(g, p);
			p0 = p;
		}
	}

	private void dot(Graphics2D g, Point p) {
		Ellipse2D dot = new Ellipse2D.Double(p.x - DOT_SIZE / 2, p.y - DOT_SIZE
				/ 2, DOT_SIZE, DOT_SIZE);
		g.fill(dot);
	}

	private Point getPoint(Entry e) {
		double rx = (e.x - vxMin) / (vxMax - vxMin * 1.);
		int x = (int) (rx * gbox.getWidth() + gbox.getX());
		double ry = (e.y - vyMin) / (vyMax - vyMin * 1.);
		int y = (int) (gbox.getMaxY() - (ry * gbox.getHeight()));
		return new Point(x, y);
	}

}
