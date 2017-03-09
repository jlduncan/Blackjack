package blackjack;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public abstract class Player 
{
	//data fields
		protected List<Card> hand;
		//constructor
		public Player()
		{
			hand = new ArrayList();
		}//end constructor
		
		//accessors/mutators
		
		//methods
		public void draw(Card card)
		{
			hand.add(card);
			
		}//end method draw
		
		public int getScore()
		{
			int score = 0;
			int aces = 0;
			for(int i = 0; i < hand.size(); i++)
			{
				score += hand.get(i).getPoints();
				//working out how to handle aces...
				if(hand.get(i).getValue() == Card.Value.ACE)
				{
					aces++;
				}//end if
			}//end for
			
			for (int i = 0; i < aces; i++)
			{
				if(score <= 11)
				{
					score += 10;
				}
			}
			//JOptionPane.showMessageDialog(null,  score);

			return score;
		}//end method getScore
		
}//end class Player
