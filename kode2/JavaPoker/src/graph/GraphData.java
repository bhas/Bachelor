package graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class GraphData {
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
	
	public class Entry {
		public final double x;
		public final double y;

		public Entry(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}
}
