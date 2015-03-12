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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handStarted(Player dealer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actorRotated(Player actor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerUpdated(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void boardUpdated(List<Card> cards, int bet, int pot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerActed(Player player) {
		// TODO Auto-generated method stub
		
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
