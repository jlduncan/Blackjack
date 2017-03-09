package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck 
{
	//data fields
	public List<Card> deckOfCards;
	
	//constructor(s)
	public Deck(int numberofdecks)
	{
		deckOfCards = new ArrayList<Card>();
		for(int j = 0; j < numberofdecks; j++)
		{
			int suitIndex = 0;
			int counter;
			//this outer for loop handles populates the master deck with multiple decks of cards
		
			for(int i = 0; i < 52; i++)
			{
				if(i%13 == 0 && i > 0)
				{
					suitIndex++;
					
				}//end if
				//I'm really quite proud of this line below :)
				deckOfCards.add(new Card(Card.Suit.values()[suitIndex], Card.Value.values()[i%13]));
			}//end for
		
			
		}//end for
	}//end constructor
	
	
	//getters/setters
	
	//methods
	
	public Card deal()
	{
		//the first card off the top of the deck
		return deckOfCards.remove(0);
	}//end method deal
	
	public void shuffle()
	{
		Collections.shuffle(deckOfCards);
	}//end method shuffle
	
	//used at the end of the game, to add cards back to the deck
	public void returnToHand()
	{
		
	}//end returnToHand
	
}//end class Deck
