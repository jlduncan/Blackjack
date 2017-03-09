package blackjack;

import java.util.ArrayList;
import java.util.List;

public class Game 
{
	//data fields
	Deck deck;
	HumanPlayer player;
	Dealer dealer;
	public List<Player> players;
	
	
	
	//constructor
	public Game(int numberofdecks)
	{
		deck = new Deck(numberofdecks);
		player = new HumanPlayer();
		dealer = new Dealer();
		
		players = new ArrayList<Player>();
		players.add(player);
		players.add(dealer);
		
		deck.shuffle();
		
		
		
	}//end constructor
	
	
	
}//end class Game
