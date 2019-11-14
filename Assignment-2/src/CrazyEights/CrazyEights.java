package CrazyEights;

import java.util.Scanner;

//Starts the game
public class CrazyEights 
{
	public static void main(String[] args) 
	{ 
		GameController controller= new GameController();   
		controller.runGame();
	}
}

//Handles all input and display for the game
class IOHandler implements IGameView
{
	Scanner  sc = new Scanner(System.in);
	int input= 0;
	
	public void display(String message) 
	{
		System.out.println(message);	
	}
	
	public int getInput() 
	{
		input = sc.nextInt();
		return input;
	}
}

//Controls the game and its players
class GameController implements IGameControl
{
	IGameView h = new IOHandler();
	private int numPlayers;
	private Deck deck;
	private Card topCard;
	private Hand[] players;
	private Hand winner;
	
	public GameController()
	{
		numPlayers = 0;
		init();
	}
	
	public void init()
	{
		String str = "This program lets you simulate the card game Crazy Eights \n" +
				"using computer players. Please enter the number of players";
		h.display(str);
		numPlayers = h.getInput();
		players = new Hand[numPlayers];
	    deck = new Deck();   
	    deck.shuffle();
	    
	    for(int i = 1; i <= players.length; i++)
	    {
	    	players[i -1] = new Hand(i);
	    }
	    
	    for(int i = 1; i <= 7; i++)
	    {
	    	for(int j = 0; j < players.length; j++)
	    	{
	    		players[j].addCard(deck.dealCard());
	    	}
	    }
	}
	
	public void runGame()
	{
		int playAgain = 0;
		
		do
		{
			playGame();
			h.display("Play again? (0 for no, 1 for yes):");
			playAgain = h.getInput();
		}while(playAgain != 0);
		
	}
	
	/**
	 * refactoring: 
	 * moved the player turn and game end checking into a
	 * new method that does solely that I also changed the method name
	 * to playGame to make more sense
	 * 
	 * <p>
	 * Level of cohesion: 
	 * Informational Cohesion
	 *
	 * @Pre:  the game is valid
	 * @post: 
	*/
	public void playGame()
	{
		winner = null;
		
		topCard = deck.dealCard();
		
		h.display("The top card is the " + topCard);
		while(true)
		{
			for(int i = 0; i < players.length; i++)
			{
				takeTurn(i);
				
				if(deck.cardsLeft() == 0)
					break;
			}
			
			if(deck.cardsLeft() == 0)
				break;
		}
		
		checkGameEnd();
	}
	
	/**
	 * refactoring: 
	 * added this method to take a player's turn instead of doing it while playing the round
	 * 
	 * <p>
	 * Level of cohesion: 
	 * Communicational Cohesion 
	 *
	 * @Pre:  The player exists in the simulation
	 * @post: 
	*/
	public void takeTurn(int player)
	{
		Card playerCard = players[player].playCard(topCard);
		while(playerCard == null)
		{
			if(deck.cardsLeft() == 0)
			{
				break;
			}
			players[player].addCard(deck.dealCard());
			playerCard = players[player].playCard(topCard);
		}
		
		while(playerCard.getValue() == topCard.getValue())
		{
			Card playerCard2 = players[player].playSameRankCard(topCard);
			if(playerCard2 != null)
			{
				topCard = playerCard;
				h.display("Player " + players[player].getPlayerNum() + 
						" played: " + topCard);
				playerCard = playerCard2;
			}
			else
				break;
		}		
		
		topCard = playerCard;
		
		h.display("Player " + players[player].getPlayerNum() + 
				" played: " + topCard);
		
		if(players[player].getCardCount() == 0)
		{
			winner = players[player];
		}	
	}
	
	/**
	 * refactoring: 
	 * added this method to check why the game is ending
	 * 
	 * <p>
	 * Level of cohesion: 
	 * Functional Cohesion
	 *
	 * @Pre:  The game is over
	 * @post: 
	*/
	public void checkGameEnd()
	{
		if(deck.cardsLeft() == 0)
		{
			if(winner != null)
				h.display("Player " + winner.getPlayerNum() + " wins");
			else
				h.display("Game is inconclusive, deck is empty");
		}
	}
}
