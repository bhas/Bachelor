package game.player;

import game.essentials.Card;
import game.essentials.GameState;
import game.essentials.PokerAction;

import java.util.List;
import java.util.Set;

public interface IPlayer {
	
	public void gameStarted(List<IPlayer> players);
	
	public void roundStarted(GameState gs);
	
	public void cardsDealt(List<Card> hand, List<Card> cc);
	
	public void playerActed(IPlayer player, PokerAction action, int value, GameState gs);
	
	public void roundEnded(int result, GameState gs);
    
    public PokerAction act(int minBet, int currentBet, Set<PokerAction> allowedActions);
    
    public void gameEnded(IPlayer winner, int balance);
    
    public String getName();

}
