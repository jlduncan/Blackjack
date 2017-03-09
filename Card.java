package blackjack;

public class Card 
{
	//data fields
	public static enum Suit {HEARTS, DIAMONDS, SPADES, CLUBS;}
	public static enum Value {
		ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), 
		NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10);
		private int points;

		private Value(int points) 
		{
	          this.points = points;
	    }//end Value
	}
	
	private Suit suit;
	private Value value;
	
	//constructors
	public Card(Suit suit, Value value)
	{
		this.suit = suit;
		this.value = value;
	}//end constructor
	
	
	//accessors/mutators
	public Suit getSuit()
	{
		return suit;
	}//end getSuit
	
	public Value getValue()
	{
		return value;
	}//end method getValue
	
	public int getPoints()
	{
		return value.points;
	}
	
	public String getCardFileName()
	{
		String cardfilename = ("image/" + suit + value + ".png");
		return cardfilename;
	}
	
	//methods
	
}
