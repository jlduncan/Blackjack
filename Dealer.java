package blackjack;



public class Dealer extends Player
{
	
	public static enum Decision {HIT, STAND;}
	
	public Dealer()
	{
		
	}//
	
	//accessors/mutators
	
	
	//methods
	public Decision dealerTurn()
	{
		if (getScore() < 17)
		{
			return Decision.HIT;
		}
		else
		{
			return Decision.STAND;
		}
	}//end method dealerTurn
	
}//end class Dealer
