package model;

/**
 * The Player class represents a player in the Greed game.
 * 
 * @author Nandigam
 *
 */
public class Player {
	
	/** player id */
	private int id;
	
	/** player name */
	private String name;
	
	/** player's score in the game */
	private int gameScore;	
	
	/** how many games player won */
	private int gamesWon;
	
	
	/**
	 * Constructs a new Player with the specified id.
	 * 
	 * @param id player's id
	 */
	public Player(int id) {
		this.id = id;
	}

	/**
	 * Returns the id of player.
	 * 
	 * @return player's id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Returns the number of games won.
	 * 
	 * @return amount of games won
	 */
	public int getGamesWon() {
		return gamesWon;
	}
	
	/**
	 * Sets amount of games won;
	 * 
	 * @param num amount of games won
	 */
	public void setGamesWon(int num) {
		gamesWon = num;
	}
	
	/**
	 * Returns the name of player.
	 * 
	 * @return player's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of player using the given parameter value.
	 * 
	 * @param name value used to set the name of player
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the player's score in the game.
	 * 
	 * @return player's score in the game
	 */
	public int getGameScore() {
		return gameScore;
	}
	
	/**
	 * Sets the game score of a player.
	 * 
	 * @param score value used to set the game score of player
	 */
	public void setGameScore(int score) {
		this.gameScore = score;
	}
		
	/**
	 * Returns a String representing a player.
	 * 
	 * @return string form of this player
	 */
	public String toString() {
		return id + "";
	}
}