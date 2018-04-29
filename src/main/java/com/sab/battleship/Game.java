package com.sab.battleship;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sab.battleship.Ship.Orientation;


public class Game {
	private static final Logger logger = LoggerFactory.getLogger(Game.class);
	private static final String P1_CFG_FILE_PATH = "resources/p1.cfg";
	private static final String P2_CFG_FILE_PATH = "resources/p2.cfg";
	private static final String P1_DEFAULT_NAME = "P1";
	private static final String P2_DEFAULT_NAME = "P2";
	private Player p1, p2;
	private Scanner stdin;
	
	public Game() {
		System.out.println("Starting a game of battleship...");
		stdin = new Scanner(System.in);
		logger.info("Starting game of battleship");
	}

	/*
	 * Initialize the game with configuration of both the players
	 */
	public void init() throws Exception {
		System.out.print(String.format("Name of player 1: [%s]", P1_DEFAULT_NAME));
		String name = stdin.nextLine();
		if (name==null || name.length()==0)
			name=P1_DEFAULT_NAME;
		p1 = new Player(name);
		
		System.out.println();
		
		System.out.print(String.format("Name of player 2: [%s]", P2_DEFAULT_NAME));
		name = stdin.nextLine();
		
		System.out.println();
		
		if (name==null || name.length()==0)
			name=P2_DEFAULT_NAME;
		p2 = new Player(name);

		setupPlayer(p1, P1_CFG_FILE_PATH);
		setupPlayer(p2, P2_CFG_FILE_PATH);
	}
	
	/*
	 * Setup the board for given player. Read the path of file with configuration of ships
	 * for given player. Add these ships to the board.
	 */
	private void setupPlayer(Player player, String defaultCfgFilePath) throws Exception {
		logger.info("Setting up configuration for player {}",player.getName());
		//read path of file containing configuration of ships for given player
		System.out.println(String.format("Path of file with configuration of ships for %s: [%s]", player.getName(), defaultCfgFilePath));
		String path = stdin.nextLine();
		System.out.println();
		if (path==null || path.length()==0) {
			logger.info("Config file not entered. Using default config file {}", defaultCfgFilePath);
			path=defaultCfgFilePath;
		}
		// add ships to player's board
		logger.info("Retrieving configuration from file {}", path);
		try (BufferedReader rdr = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
			//format of each line in config file: <size>,<location>,<HORIZONTAL|VERTICAL>
			String configStr ;
			while ((configStr= rdr.readLine()) != null) {
				logger.info("Parsing ship configuration '{}'", configStr);
				String[] shipConfig = configStr.split(",");
				int size = Integer.valueOf(shipConfig[0]);
				Position location = Position.valueOf(shipConfig[1]);
				Orientation dir = Orientation.valueOf(shipConfig[2]);
				player.addShip(new Ship(size , location, dir));
			}
		} catch (Exception e) {
			logger.error("Error configuring player {}", player.getName(), e);
			throw e;
		}
	}

	/*
	 * Keep playing with each player taking turns to strike on opponent's board
	 * till a player has sunk all the ships on opponent's board
	 */
	private void play() throws Exception {
		// player 1 will strike on p2's board
		// player 2 will strike on p1's board
		logger.info("Beginning to play the game");
		System.out.println("Battle begins...");
		Board p1Board = p1.getBoard();
		Board p2Board = p2.getBoard();
		Player currentPlayer = p1;
		StrikeResult result = StrikeResult.MISS;
		while (result != StrikeResult.WIN) {
			Position pos = getNextStrikePosition(currentPlayer);
			logger.info("Player {} striking at position {}", currentPlayer.getName(), pos);
			if (currentPlayer == p1) {
				result = p2Board.strike(pos);
				currentPlayer = p2;
			} else {
				result = p1Board.strike(pos);
				currentPlayer = p1;
			}
			logger.info("Result of this strike - {}", result);
			System.out.println(result);
		}
		System.out.printf("Congratulations %s, you have won the battle!!!%n", 
				(currentPlayer==p1?p2:p1).getName());
	}

	/*
	 * Read from stdin the location to strike on opponent's board.
	 */
	private Position getNextStrikePosition(Player player) {
		Position pos = null;
		while (pos == null) {
			try {
				System.out.print(String.format("Strike position of %s:", player.getName()));
				logger.info("Waiting for strike position of player {}", player.getName());
				// position on board in the form a1, c2, etc.
				pos = Position.valueOf(stdin.nextLine());
			} catch (Exception e) {
				//input position is invalid. Row ID may not be in a-j range, or column ID
				//may not be in 1-10 range.
				logger.error("Invalid strike position {}", pos, e);
				System.out.println("Invalid strike position. Enter again");
			}
		}
		return pos;
	}

	public static void main(String[] args) {
		try {
			Game game = new Game();
			game.init();
			game.play();
		} catch (Exception e) {
			System.out.println("Error during the game: " + e.getMessage());
			logger.error("Error in the game: ", e);
		}
	}

	
}