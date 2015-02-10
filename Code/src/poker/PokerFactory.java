package poker;

public class PokerFactory {
	
	public PlayerControl[] createGame(String p1, String p2, int BB, int startChips) {
		Game game = new Game(BB, startChips);
		PlayerControl player1 = new PlayerControl(p1, game);
		PlayerControl player2 = new PlayerControl(p2, game);
		game.setPlayers(player1, player2);
		
		return new PlayerControl[] { player1, player2 };
	}
}
