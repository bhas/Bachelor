package montecarlo;

public class Probability {
	public int wins;
	public int draws;
	public int loses;
	public int total;

	public Probability(int wins, int draws, int loses) {
		add(wins, draws, loses);
	}

	public void add(int w, int d, int l) {
		wins += w;
		draws += d;
		loses += l;
		total = wins + draws + loses;
	}
	
	public void add(Probability p) {
		wins += p.wins;
		draws += p.draws;
		loses += p.loses;
		total = wins + draws + loses;
	}

	public double percent() {
		return wins / (total * 1.);
	}
}
