package bots;

import java.util.List;
import java.util.Set;

import texasholdem.Card;
import texasholdem.Player;
import texasholdem.TableType;
import actions.Action;

public class CallBot extends Bot {

	@Override
	public void messageReceived(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void joinedTable(TableType type, int bigBlind, List<Player> players) {
		System.out.println("CallBot Game started");
	}

	@Override
	public void handStarted(Player dealer) {
		System.out.println("hand started");		
	}

	@Override
	public void actorRotated(Player actor) {
		System.out.println("waiting for "+ actor.getName());
		
	}

	@Override
	public void playerUpdated(Player player) {
		System.out.println("Player updated");
		
	}

	@Override
	public void boardUpdated(List<Card> cards, int bet, int pot) {
		System.out.println("Board updated");
		for(Card c : cards){
			System.out.println("card: " + c);
		}
		
	}

	@Override
	public void playerActed(Player player) {
		System.out.println(player.getName() +  " acted");
		
	}

	@Override
	public Action act(int minBet, int currentBet, Set<Action> allowedActions) {
        if (allowedActions.contains(Action.CHECK)) {
            return Action.CHECK;
        } else {
            return Action.CALL;
        }
    }

}
