package poker;

public interface IPlayerObserver {
	public void yourTurn();
	public void newRound();
	public void finishedRound();
}
