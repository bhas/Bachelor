package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class GraphData {
	private static final double NOISE = 0.;
	private TreeSet<Entry> entries;

	public GraphData() {
		entries = new TreeSet<Entry>(new Comparator<Entry>() {
			@Override
			public int compare(Entry e1, Entry e2) {
				if (e1.x == e2.x)
					return 0;

				if (e1.x > e2.x)
					return 1;
				else
					return -1;

			}
		});
	}

	public GraphData(ArrayList<Entry> data) {
		this();
		entries.addAll(data);
	}
	
	public Range getRange() {
		ArrayList<Double> vals = new ArrayList<Double>();
		for(Entry e : entries) {
			vals.add(e.y);
		}
		Collections.sort(vals);
		int ignores = (int) (NOISE * vals.size());
		return new Range(vals.get(ignores), vals.get(vals.size()-ignores-1));
	}

	public void addEntry(double x, double y) {
		entries.add(new Entry(x, y));
	}
	
	public SortedSet<Entry> getEntries() {
		return entries;
	}
	
	public double first() {
		return entries.first().x;
	}
	
	public double last() {
		return entries.last().x;
	}
	
	public SortedSet<Entry> getEntries(int xMin, int xMax) {
		Entry e1 = new Entry(xMin, 0);
		Entry e2 = new Entry(xMin, 0);
		return entries.subSet(e1, e2);
	}
	
	public class Range {
		public double min;
		public double max;
		
		public Range(double min, double max) {
			this.min = min;
			this.max = max;
		}
		
		@Override
		public String toString() {
			return "["+min + " - " + max+"]";
		}
	}
	
	public class Entry {
		public final double x;
		public final double y;

		public Entry(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}
}
