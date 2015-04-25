package graph;

import graph.GraphData.Entry;
import graph.GraphData.Range;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
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
	private static final DecimalFormat DF_Y = new DecimalFormat("0.000");
	private static final int VER_SECTIONS = 10;
	private static final int HOR_SECTIONS = 5;

	// view dimensions
	private double vxMin, vxMax, vyMin, vyMax;
	private Rectangle2D gbox;
	private String xDesc, yDesc;
	private ArrayList<GraphData> datasets;
	private Color[] colors = new Color[] { Color.RED, Color.BLUE, Color.GREEN,
			new Color(50, 50, 50), Color.ORANGE, Color.PINK };
	private Color indicatorColor = new Color(0, 0, 0);
	private double expVal;
	private boolean drawExp, drawLines, drawRanges;

	public Graph() {
		vxMin = 0;
		vxMax = 10;
		vyMin = 0;
		vyMax = 100;
		gbox = new Rectangle2D.Double(100, 10, 600, 300);

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

	public void setExpectedVal(double val) {
		drawExp = true;
		expVal = val;
	}

	public void setViewY(double min, double max) {
		vyMin = min;
		vyMax = max;
	}

	public void setDescriptions(String x, String y) {
		xDesc = x;
		yDesc = y;
	}

	public void drawLines(boolean b) {
		drawLines = b;
	}

	public void drawRanges(boolean b) {
		drawRanges = b;
	}

	public void addDataset(GraphData data) {
		datasets.add(data);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(Color.white);
		g2.fillRect(0, 0, getWidth(), getHeight());

		drawGraphContainer(g2);
		if (drawExp)
			drawIndicator(g2, expVal);
		for (int i = 0; i < datasets.size(); i++) {
			drawDataset((Graphics2D) g2.create(), datasets.get(i), colors[i]);
		}
	}

	private void drawGraphContainer(Graphics2D g) {
		int x1 = (int) gbox.getX();
		int x2 = (int) gbox.getMaxX();
		int y2 = (int) gbox.getMaxY();

		// background
		g.setColor(new Color(230, 240, 255));
		g.fill(gbox);

		// horizontal value lines
		g.setColor(new Color(0, 0, 0, 60));
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
			g.drawString(xDesc, (int) gbox.getCenterX() - sw / 2,
					(int) gbox.getMaxY() + 40);
		}
		if (yDesc != null) {
			int sw = fm.stringWidth(yDesc);
			g.drawString(yDesc, (int) gbox.getX() - 40 - sw,
					(int) gbox.getCenterY());
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

	private void drawIndicator(Graphics2D g, double val) {
		g.setColor(indicatorColor);
		Stroke dashed = new BasicStroke(1.5f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL, 0, new float[] { 8, 15 }, 0);
		g.setStroke(dashed);
		int y = getYCoord(val);
		g.drawLine((int) gbox.getX() + 1, y, (int) gbox.getMaxX() - 1, y);
		int x = (int) gbox.getMaxX() + 5;
		g.drawString(val + "", x, y + 5);
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
		if (drawRanges) {
			Range r = data.getRange();
			drawIndicator(g, r.min);
			drawIndicator(g, r.max);
		}

		g.setClip(gbox);

		Point p0 = null;
		g.setColor(c);
		g.setStroke(new BasicStroke(LINE_SIZE));
		for (Entry e : data.getEntries()) {
			Point p = getPoint(e);
			if (p0 != null && drawLines) {
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
		int x = getXCoord(e.x);
		int y = getYCoord(e.y);
		return new Point(x, y);
	}

	private int getXCoord(double valX) {
		double rx = (valX - vxMin) / (vxMax - vxMin * 1.);
		return (int) (rx * gbox.getWidth() + gbox.getX());
	}

	private int getYCoord(double valY) {
		double ry = (valY - vyMin) / (vyMax - vyMin * 1.);
		return (int) (gbox.getMaxY() - (ry * gbox.getHeight()));
	}

}
