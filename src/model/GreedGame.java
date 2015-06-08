package model;

/***********************************************************************
 * @author Matt Conflitti
 * @version 1.006052015
 * 
 * GreedGame stores the Game of Greed information and methods necessary
 * to carry out gameplay following the specific rules of the game.
 **********************************************************************/

public class GreedGame {

	/** array stores Player objects */
	private Player[] players;

	/** array stores Die objects */
	private Die[] dice;

	/** array stores dice value frequencies */
	private int[] freq;

	/** stores number of players in game */
	private int numPlayers;

	/** the current roll score */
	private int rollScore;

	/** current turn score */
	private int turnScore;

	/** tracks player turn number */
	private int playerTurn;

	/** winning score */
	private int winScore;

	/** how many dice are available */
	private int diceAvail;

	/** number of dice constant */
	private final int NBR_OF_DICE = 6;
	
	/*******************************************************************
	 * 
	 * Default constructor that sets the GreedGame to starting values
	 *  
	 * @param playerTurn sets starting player of game
	 * @param winScore sets winning score of game
	 ******************************************************************/

	public GreedGame(int playerTurn, int winScore) {
		
		this.playerTurn = playerTurn;
		this.winScore = winScore;
		this.dice = new Die[NBR_OF_DICE];
		this.freq = new int[NBR_OF_DICE];
		this.rollScore = 0;
		this.turnScore = 0;
		this.diceAvail = NBR_OF_DICE;

		//instantiate each die and set initial frequency
		for(int i = 0; i<NBR_OF_DICE; i++){
			this.dice[i] = new Die();
			this.freq[i] = 0;
		}
	}

	/*******************************************************************
	 * Checks that param is greater than 2 and then instantiates the 
	 * correct about of Player objects  
	 * 
	 * @param num number of players to instantiate
	 ******************************************************************/
	
	public void makePlayers(int num) {

		//throws exception if param is less than 2
		if(num < 2)
			throw new IllegalArgumentException();

		//updates class fields
		this.numPlayers = num;
		this.players = new Player[num];

		//instantiates Player objects in array
		for(int i = 0; i<numPlayers; i++){
			this.players[i] = new Player(i);
		}
	}
	
	/*******************************************************************
	 * Returns the player object at index of of the current playerTurn.
	 * 
	 * @return player object from array at index of playerTurn
	 ******************************************************************/

	public Player getCurrPlayer() {
		return players[playerTurn];
	}
	
	/*******************************************************************
	 * Adds turn score to current player's existing score
	 * 
	 * @param score points to be added to existing score
	 ******************************************************************/

	public void addCurrPlayerScore(int score) {
		for(Player p : players) {
			if(p.getId() == playerTurn)
				p.setGameScore(p.getGameScore()+score);
		}
	}

	/*******************************************************************
	 * "passes dice" and updates playerTurn. Resets dice and updates
	 * current player's score
	 ******************************************************************/
	
	public void passDice() {

		addCurrPlayerScore(turnScore);

		//reset turnScore and dice availability
		turnScore = 0;
		diceAvail = 6;
		resetDiceAvail();

		//step turn forward
		if(playerTurn+1 == players.length)
			playerTurn = 0;
		else {
			playerTurn++;
		}
	}

	/*******************************************************************
	 * Rolls dice and gets new random face values for each
	 ******************************************************************/
	
	public void rollDice() {
		for(int i = 0; i<dice.length; i++) {
			dice[i].roll();
		}
	}

	/*******************************************************************
	 * Creates a string to display the dice values nicely
	 * 
	 * @return str string of dice values
	 ******************************************************************/
	
	public String displayDice() {
		String str = "";
		for(int i = 0; i<dice.length; i++) {
			
			//only shows dice that are currently available
			if(dice[i].getIsAvailable())
				str += "" + dice[i] + " ";
		}
		
		for(int i=0; i<6-diceAvail; i++) {
			str+="  ";
		}

		return str;

	}

	/*******************************************************************
	 * Creates a string to display the roll score, turn score, and
	 * current player's game score.
	 * 
	 * @return str string of scores
	 ******************************************************************/
	
	public String displayTurn() {
		String str ="Roll Score: " + getRollScore() + "\t" +
				"Turn Score: " + getTurnScore() + "\t" +
				"Game Score: " + getCurrPlayer().getGameScore();

		return str;
	}

	/*******************************************************************
	 * Helper method to sort the values of the available dice into 
	 * numerical categories to be later manipulated and scored
	 ******************************************************************/
	
	private void setFreq() {
		
		//first reset all frequencies to 0
		for(int i = 0; i<freq.length; i++) {
			freq[i] = 0;
		}

		//for each dice check availability and then get value and sort
		for(int i=0; i<dice.length;i++) {
			if(dice[i].getIsAvailable()) {
				int val = dice[i].getFaceValue();
				if(val == 1)
					freq[0]++;
				if(val == 2)
					freq[1]++;
				if(val == 3)
					freq[2]++;
				if(val == 4)
					freq[3]++;
				if(val == 5)
					freq[4]++;
				if(val == 6)
					freq[5]++;
			}
		}
	}

	/*******************************************************************
	 * Helper method resets diceAvail to 6 and changes each Die object's
	 * isAvailable field to true
	 ******************************************************************/
	
	private void resetDiceAvail() {
		diceAvail = 6;
		for(int i = 0; i<diceAvail; i++) {
			dice[i].setIsAvailable(true);
		}
	}

	/*******************************************************************
	 * Helper method updates the die object's availability based on
	 * the diceAvail field's int value
	 ******************************************************************/
	
	private void setDiceAvail() {

		//get current dice availability count
		int currAvail = 0;
		for(int i = 0; i<dice.length; i++) {
			if(dice[i].getIsAvailable())
				currAvail++;
		}

		//if diceAvail is negative set to zero
		if(diceAvail < 0)
			diceAvail = 0;

		//if no diceAvail reset them all to available
		if(diceAvail == 0) {
			resetDiceAvail();
		} else {
			
			//calculate how many should be turned off
			//loop through and turn off correct amount
			for(int i = 0; i<currAvail-diceAvail; i++) {
				int j =0;
				for(j = i; j<dice.length;j++){
					if(dice[j].getIsAvailable())
						break;
				}
				dice[j].setIsAvailable(false);
			}
		}

	}
	
	/*******************************************************************
	 * Simulates a players turn after rolling the dice.
	 ******************************************************************/

	public void turn() {

		//sets frequencies, calcs score, sets dice visibility
		setFreq();
		rollScore();
		setDiceAvail();
		
		//updates turn score
		if(rollScore == 0)
			setTurnScore(0);
		else
			setTurnScore(rollScore+turnScore);

	}
	
	/*******************************************************************
	 * Creates string to display game scores of both players at the 
	 * current point in the game.
	 * 
	 * @return str string of players' game scores
	 ******************************************************************/

	public String displayGameScore() {
		String str = "Game Scores ==> ";
		
		for(Player p : players) {
			str += "Player " + (p.getId()+1) + ": " + p.getGameScore()+
					"\t";
		}

		return str;
	}

	/*******************************************************************
	 * Checks if dice are in a straight pattern and returns the given
	 * score.
	 * 
	 * @return score value to add if method returns score
	 ******************************************************************/
	
	private int straightScore() {
		int score = 0;
		if(freq[0] == 1 && freq[1] == 1 && 
				freq[2] == 1 && freq[3] == 1 && 
				freq[4] == 1 && freq[5] == 1){
			diceAvail -= 6;
			score+= 1200;
		}

		return score;	
	}

	private int pairScore() {
		int pairs = 0, score = 0;
		for(int i = 0; i<freq.length; i++) {
			if(freq[i] == 2) {
				pairs++;
			}
		}
		if(pairs == 3){
			diceAvail -= 6;
			score+= 800;
		}


		return score;
	}

	private int sixOfKindScore() {
		int score = 0;
		for(int i = 0; i<freq.length; i++) {
			if(i == 0) {
				if(freq[i] == 6) {
					score+= 8000;
					diceAvail -= 6;
				}
			}

			else if(freq[i] == 6) {
				score+= ((i+1)*100)*8;
				diceAvail -= 6;
			}
		}

		return score;

	}

	private int fiveOfKindScore() {
		int score = 0;
		for(int i = 0; i<freq.length; i++) {
			if(i == 0) {
				if(freq[i] == 5) {
					score+= 4000;
					diceAvail -= 5;
				}
			}

			else if(freq[i] == 5) {
				score+= ((i+1)*100)*4;
				diceAvail -= 5;
			}
		}

		return score;
	}

	private int fourOfKindScore() {
		int score =0;
		for(int i = 0; i<freq.length; i++) {
			if(i == 0) {
				if(freq[i] == 4) {
					score+= 2000;
					diceAvail -= 4;
				}
			}

			else if(freq[i] == 4) {
				score+= ((i+1)*100)*2;
				diceAvail -= 4;
			}
		}
		return score;
	}

	private int threeOfKindScore() {
		int score = 0;
		for(int i = 0; i<freq.length; i++) {
			if(i == 0) {
				if(freq[i] == 3) {
					score+= 1000;
					diceAvail -= 3;
				}
			}

			else if(freq[i] == 3) {
				score+= (i+1)*100;
				diceAvail -= 3;
			}
		}

		return score;
	}

	private int oneScore() {
		int score = 0;
		if(freq[0] < 3 && freq[0] > 0 && pairScore() == 0 && 
				straightScore() == 0) {
			score+= freq[0] * 100;
			diceAvail -= freq[0];
		}

		return score;
	}

	private int fiveScore() {
		int score = 0;
		if(freq[4] < 3 && freq[4] > 0 && pairScore() == 0 && 
				straightScore() == 0) {
			score+= freq[4] * 50;
			diceAvail -= freq[4];
		}

		return score;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	public void rollScore() {
		rollScore = straightScore() + pairScore() + sixOfKindScore() +
				fiveOfKindScore() + fourOfKindScore() + 
				threeOfKindScore() + oneScore() + fiveScore();
	}

	public int getRollScore() {
		return rollScore;
	}

	public boolean isWon() {
		boolean won = false;
		for(Player p : players) {
			if(p.getGameScore() >= winScore) {
				won = true;
			}
		}
		return won;
	}

	public int getWinnerId() {
		for(Player p : players) {
			if(p.getGameScore() >= winScore) {
				return p.getId();
			}
		}
		return -1;
	}

	public void setRollScore(int rollScore) {
		this.rollScore = rollScore;
	}

	public int getTurnScore() {
		return turnScore;
	}

	public void setTurnScore(int turnScore) {
		this.turnScore = turnScore;
	}

	public int getPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(int playerTurn) {
		this.playerTurn = playerTurn;
	}

	public int getWinScore() {
		return winScore;
	}

	public void setWinScore(int winScore) {
		this.winScore = winScore;
	}

	public int getDiceAvail() {
		return diceAvail;
	}

	public void setDiceAvail(int diceAvail) {
		this.diceAvail = diceAvail;
	}

	public void setDice(int a, int b, int c, int d, int e, int f) {
		dice[0].setFaceValue(a);
		dice[1].setFaceValue(b);
		dice[2].setFaceValue(c);
		dice[3].setFaceValue(d);
		dice[4].setFaceValue(e);
		dice[5].setFaceValue(f);
	}

	public static void main(String[] args) {
		GreedGame game = new GreedGame(1,1000);
		game.makePlayers(2);

		game.rollDice();

		//set dice manually to test scoring
		game.setDice(2,2,2,5,5,5);

		System.out.print("You rolled: " + game.displayDice() + "\t");
		game.turn();
		System.out.println(game.displayTurn());

	}

}
