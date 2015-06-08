package view;

import model.*;
import java.util.*;

/***********************************************************************
 * @author Matt Conflitti
 * @version 1.006052015
 * 
 * GreedCLI class imports the GreedGame class and creates a command line
 * user interface to play the Game of Greed utilizing the GreedGame
 * class.
 **********************************************************************/

public class GreedCLI {

	/** scanner to take user input */
	private Scanner scnr;

	/** stores number of players playing */
	private int numPlayers;

	/** stores which player will start each game */
	private int playerStart;

	/** temporarily stores the win score */
	private int winScore;

	/** tracks total number of games played */
	private int gamesPlayed;

	/** array tracks each players wins */
	private int[] playersWon;

	/*******************************************************************
	 * 
	 * Default constructor that sets the GreedCLI to starting values  
	 * 
	 ******************************************************************/

	public GreedCLI() {
		scnr = new Scanner(System.in);
		numPlayers = 0;
		playerStart = -1;
		winScore = 0;
		gamesPlayed = 0;

	}

	/*******************************************************************
	 * Play method uses while loops to get input from the user and play
	 * out the game until someone wins and the users decide to quit 
	 * game.
	 ******************************************************************/
	
	public void play() {

		System.out.println("WELCOME TO THE GAME OF GREED...\n");
		
		//Prompt user for number of players
		while(numPlayers < 2 || numPlayers > 4) {
			System.out.print("How many players? ");
			numPlayers = scnr.nextInt();
		}
		
		//instantiate playersWon array to size of numPlayers to track
		//how many times each player wins
		playersWon = new int[numPlayers];
		
		//prompt user for winning score
		while(winScore < 1000 || winScore > 10000) {
			System.out.print("Points to win the game? ");
			winScore = scnr.nextInt();
		}

		//while another game is desired, play new game
		while(true) {

			//creates a string to show player start options
			String playerDashString = "";

			for(int i = 1; i<=numPlayers-1; i++) {
				playerDashString += "" + i + " - ";
			}

			playerDashString += "" + numPlayers;

			//prompt user to select starting player
			while(playerStart < 0) {
				System.out.print("Player to start the game (" + 
						playerDashString + ")? ");
				int val = scnr.nextInt();
				if(val > 0 && val <= numPlayers)	
					playerStart = val-1;
			}
			
			//instantiate GreedGame with desired specs and num players
			GreedGame game = new GreedGame(playerStart, winScore);
			game.makePlayers(numPlayers);

			//while the game is not yet won, continue playing
			while(!game.isWon()) {
				
				//notify whose turn it is
				System.out.print("Player " + 
						(game.getCurrPlayer().getId()+1) + "'s " +
						"turn --> Press ENTER to roll dice");
				scnr.nextLine();
				scnr.nextLine();

				//infinite loop until broken by user input
				while(true) {
					
					//roll dice, calc score
					game.rollDice();
					System.out.print("You rolled: " + game.displayDice()
							+ "\t");
					game.turn();
					System.out.println(game.displayTurn());

					//break loop, pass dice if rolled zero points
					if(game.getRollScore() == 0) {
						game.setTurnScore(0);
						game.passDice();
						break;
					}

					//prompt to roll again if rules allow
					char response;
					System.out.print("Would you like to roll again" + 
							"(Y,y/N,n)? ");
					response = scnr.next().charAt(0);

					//break loop, pass dice if user is done with turn
					if(response == 'n' || response == 'N') {
						game.passDice();
						break;
					}
				}
				
				//after each turn display players' scores
				System.out.println(game.displayGameScore());
			}

			//display player that has won current game
			if(playersWon[game.getWinnerId()]>0)
				System.out.println("Player " + (game.getWinnerId()+1) 
						+ " wins again!");
			else
				System.out.println("Player " + (game.getWinnerId()+1) 
						+ " wins!");

			//add win to array, add game to total played, and reset
			//starting player
			playersWon[game.getWinnerId()]++;
			gamesPlayed++;
			playerStart = -1;

			//display game record
			System.out.println("Number of games played: " + 
					gamesPlayed);
			for(int i = 0; i<playersWon.length; i++) {
				System.out.println("Games won by Player " + (i+1) + ": "
						+ playersWon[i]);
			}

			//prompt to play again
			char response;
			System.out.print("Would you like to play again (Y,y/N,n)?");
			response = scnr.next().charAt(0);

			//break loop game loop
			if(response == 'n' || response == 'N') {
				break;
			}

		}
		
		//goodbye message. end of program.
		System.out.println("Bye. Thanks for playing.");

	}

	/*******************************************************************
	 * Main method instantiates the GreedCLI class and invokes the play
	 * method to start the game.
	 ******************************************************************/
	
	public static void main(String[] args) {
		GreedCLI cli = new GreedCLI();
		cli.play();
	}
}
