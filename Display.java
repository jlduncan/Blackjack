package blackjack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



public class Display extends JFrame
{
	
	//constants/enums
	
	public enum VictoryStatus{PLAYER_WON, DEALER_WON, DRAW};
	
	//data fields...so many data fields...
	
	//GUI components
	JPanel jpnlgameboard;
	JPanel jpnlgamebuttons;
	JPanel jpnlscores;
	JPanel jpnldealer;
	JPanel jpnlplayer;
	JPanel jpnlplayerbuttons;
	JPanel jpnlupperpanel;
	JPanel jpnlbattlefield;
	
	
	
	JButton jbttnhitme;
	JButton jbttnstand;
	
	JButton jbttnstart;
	JButton jbttnexit;
	
	JLabel jlblNumberOfHands;
	JLabel jlblPlayerScore;
	JLabel jlblDealerScore;
	
	//other fields
	Game game;
	int numberOfHands;
	int playerScore;
	int dealerScore;
	int numberOfDecks;
	
	
	public Display()
	{
		Color gameboardcolor = new Color(0, 204, 0);
		//adding in the GUI
		jpnlgameboard = new JPanel(new BorderLayout());
		jpnlgameboard.setBackground(gameboardcolor);
		jpnlscores = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		jpnlscores.setBackground(gameboardcolor);
		jpnldealer = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 50));
		jpnldealer.setBackground(gameboardcolor);
		jpnlplayer = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 50));
		jpnlplayer.setBackground(gameboardcolor);
		
		jpnlbattlefield = new JPanel(new GridLayout(2,1));
		jpnlplayerbuttons = new JPanel(new FlowLayout());
		jpnlplayerbuttons.setBackground(gameboardcolor);
		jpnlgamebuttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
		jpnlupperpanel = new JPanel(new GridLayout(1, 2));
		jpnlgamebuttons.setBackground(gameboardcolor);
		
		
		jbttnstart = new JButton("New Game");
		jbttnexit = new JButton("Exit");
		jbttnhitme = new JButton("Hit");
		jbttnstand = new JButton("Stand");
		
		
		//button listeners -- creating anonymous listeners since there's not a lot of complicated logic here, just
		//a single method call
		jbttnstart.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				newGame();
			}
		});
		
		jbttnexit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		jbttnhitme.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				hitMe(game.player);
				
			}
		});
		
		jbttnstand.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				jbttnhitme.setEnabled(false);
				jbttnstand.setEnabled(false);
				dealerTurn();
				
			}
		});
		
		//these two buttons need to start off closed, since the game hasn't begun yet
		jbttnhitme.setEnabled(false);
		jbttnstand.setEnabled(false);
		
		jlblNumberOfHands = new JLabel("Total hands played: 0");
		jlblPlayerScore = new JLabel("Player Score: 0");
		jlblDealerScore = new JLabel("Dealer Score: 0");
		
		jpnlscores.add(jlblNumberOfHands);
		jpnlscores.add(jlblPlayerScore);
		jpnlscores.add(jlblDealerScore);
		
		jpnlbattlefield.add(jpnldealer);
		jpnlbattlefield.add(jpnlplayer);

		
		jpnlplayerbuttons.add(jbttnhitme);
		jpnlplayerbuttons.add(jbttnstand);

		jpnlgamebuttons.add(jbttnstart);
		jpnlgamebuttons.add(jbttnexit);
		
		jpnlupperpanel.add(jpnlscores);
		jpnlupperpanel.add(jpnlgamebuttons);
		
		//adding our subpanels
		jpnlgameboard.add(jpnlupperpanel, BorderLayout.NORTH);
		jpnlgameboard.add(jpnlbattlefield, BorderLayout.CENTER);		
		jpnlgameboard.add(jpnlplayerbuttons, BorderLayout.SOUTH);
		
		add(jpnlgameboard);
		
		//start new game
		numberOfHands = 0;
		playerScore = 0;
		dealerScore = 0;
		numberOfDecks = vegasDecks();
		
		
		
	}//end constructor
	
	
	public static void main(String [] args)
	{
		Display frame = new Display();
		frame.setSize(700, 500);
		//frame.pack();
		frame.setTitle("Blackjack");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}//end main method
	
	
	public void calculateScores()
	{
		VictoryStatus victory;
		//dealer wins
		if(game.dealer.getScore() > game.player.getScore())
		{
			victory = VictoryStatus.DEALER_WON;
		}
		//player wins
		else if (game.dealer.getScore() < game.player.getScore())
		{
			victory = VictoryStatus.PLAYER_WON;

		}
		//draw
		else
		{
			victory = VictoryStatus.DRAW;
		}
		gameOver(victory);
	}//end method calculateScores
	
	public void checkForBust(Player player)
	{
		if(player instanceof Dealer)
		{
			
			if(player.getScore() > 21)
			{
				JOptionPane.showMessageDialog(null, "Dealer has gone bust!");
				gameOver(VictoryStatus.PLAYER_WON);
			}//end if
		}//end if
		else
		{
			
			if(player.getScore() > 21)
			{
				JOptionPane.showMessageDialog(null, "You've gone bust!");
				gameOver(VictoryStatus.DEALER_WON);
				
			}//end if
			
		}//end else
	}//end method checkForBust
	
	//triggered once the user clicks on jbttnstand ("Stand")
	public void dealerTurn()
	{
		//turn over hole card
		
		jpnldealer.remove(1);
		jpnldealer.add(new JLabel(new ImageIcon(game.dealer.hand.get(game.dealer.hand.size()-1).getCardFileName())));
		jpnldealer.revalidate();
		
		//check for bust
		checkForBust(game.dealer);
		//is the first draw a winner?
		if(game.dealer.getScore() > game.player.getScore())
		{
			
		}//end if
		
		//retrieves decision from dealer until dealer decides to stand
		boolean continueToHit = false;
		do
		{
			//JOptionPane.showMessageDialog(null, "Dealer score:" + game.dealer.getScore());
			//JOptionPane.showMessageDialog(null, "Player score:" + game.player.getScore());

			if(game.dealer.dealerTurn() == Dealer.Decision.HIT)
			{
				continueToHit = true;
				hitMe(game.dealer);
			}
			else
			{
				continueToHit = false;
			}
		}while (continueToHit);
		
		if (game.dealer.getScore() <= 21) //in other words, we haven't gone bust
		{
			calculateScores();
		}
		
	}//end method dealerTurn
	
	public void gameOver(VictoryStatus status)
	{
		//dealer wins
		if(status == VictoryStatus.DEALER_WON)
		{
			JOptionPane.showMessageDialog(null, "You've lost!");
			dealerScore += 1;
			
		}
		//player wins
		else if(status == VictoryStatus.PLAYER_WON)
		{
			JOptionPane.showMessageDialog(null, "You've won!");
			playerScore += 1;

		}
		//draw
		else
		{
			JOptionPane.showMessageDialog(null, "Game is a draw!");
		}
		
		jbttnhitme.setEnabled(false);
		jbttnstand.setEnabled(false);
		jbttnstart.setEnabled(true);
		numberOfHands += 1;
		updateScoreboard();
		
	}//end method gameOver
	
	//calling super class, will work with either dealer or human player
	public void hitMe(Player player)
	{
		player.draw(game.deck.deal());
		//JOptionPane.showMessageDialog(null, "Dealer score after hit:" + game.dealer.getScore());

		if(player instanceof Dealer)
		{
			//JOptionPane.showMessageDialog(null, "hitMe, sez the the dealer!");
			jpnldealer.add(new JLabel(new ImageIcon(game.dealer.hand.get(game.dealer.hand.size()-1).getCardFileName())));
			jpnldealer.revalidate();
						
		}//end if
		else
		{
			jpnlplayer.add(new JLabel(new ImageIcon(game.player.hand.get(game.player.hand.size()-1).getCardFileName())));
			jpnlplayer.revalidate();			
		}//end else
		
		checkForBust(player);
				
	}//end method hitMe
	
	
	
	//starting a new game
		public void newGame()
		{		
			//cleaning up after the last game
			jpnldealer.removeAll();
			jpnldealer.updateUI();
			jpnlplayer.removeAll();
			jpnlplayer.updateUI();
			
			//disabling start button to prevent the player from circumventing a loss
			jbttnstart.setEnabled(false);
			//brand-new instance of Game
			game = new Game(numberOfDecks);
			//deal cards
			game.player.draw(game.deck.deal());
			jpnlplayer.add(new JLabel(new ImageIcon(game.player.hand.get(game.player.hand.size()-1).getCardFileName())));
			game.dealer.draw(game.deck.deal());
			jpnldealer.add(new JLabel(new ImageIcon(game.dealer.hand.get(game.dealer.hand.size()-1).getCardFileName())));
			game.player.draw(game.deck.deal());
			jpnlplayer.add(new JLabel(new ImageIcon(game.player.hand.get(game.player.hand.size()-1).getCardFileName())));
			game.dealer.draw(game.deck.deal());
			jpnldealer.add(new JLabel(new ImageIcon("image/hole.png")));
			jbttnhitme.setEnabled(true);
			jbttnstand.setEnabled(true);
			revalidate();
			//we need to make sure our player hasn't busted on their first draw
			checkForBust(game.player);
			//JOptionPane.showMessageDialog(null, "Dealer score before hit:" + game.dealer.getScore());

		}//end method newGame
		
	
	
	public void updateScoreboard()
	{
		jlblNumberOfHands.setText("Total hands played: " + numberOfHands);
		jlblPlayerScore.setText("Player Score: " + playerScore);
		jlblDealerScore.setText("Dealer Score: " + dealerScore);
	}//end method updateScoreboard
	
	
	
	public int vegasDecks()
	{
		int number = 1;
		String input;
		boolean isValid = true;
		do
		{
			isValid = true;
			input = JOptionPane.showInputDialog(null, "We use Vegas decks 'round here!\n Please enter the number"
				+ " of decks you'd like to use (1-5)");
			try
			{
				number = Integer.parseInt(input);
			}
			catch(NumberFormatException ex)
			{
				isValid = false;
				JOptionPane.showMessageDialog(null, "Not a valid number! Please try again.");
			}
		
			if(isValid) //we can skip this if we don't have a number for comparison
			{
				if(number < 1 || number > 5)
				{
					JOptionPane.showMessageDialog(null, "Number is either out of range. Please try again");
					isValid = false;
				}//end if
			}//end if
				
		}while (!isValid);
		return number;
	}//end method vegasDecks
}//end class Display
