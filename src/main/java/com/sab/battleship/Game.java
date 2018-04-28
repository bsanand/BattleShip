package com.sab.battleship;

import java.util.Scanner;

import com.sab.battleship.Ship.Orientation;

public class Game {

	Player p1, p2;
	Scanner stdin;
			
	public Game() {
		stdin = new Scanner(System.in);
		setupPlayer1();
		setupPlayer2();
		
	}

	private void setupPlayer1() {
		p1 = new Player();
		Board board = p1.getBoard();
		//add ships to player's board
		Ship ship ;

		ship = new Ship(1, new Position('a',1), Orientation.HORIZONTAL);
		board.addShip(ship);
	}

	private void setupPlayer2() {
		p2 = new Player();
		Board board = p2.getBoard();
		//add ships to player's board
		Ship ship ;

		ship = new Ship(1, new Position('b',1), Orientation.HORIZONTAL);
		board.addShip(ship);
	}
	
	private void play() throws Exception {
		//player 1 will strike on p2's board
		//player 2 will strike on p1's board
		Board p1Board = p1.getBoard();
		Board p2Board = p2.getBoard();
		Player currentPlayer = p1;
		while(true) {
			Position pos = getNextStrikePosition();
			if (currentPlayer == p1) {
				System.out.println(p2Board.strike(pos));
				currentPlayer = p2;
			} else { 
				System.out.println(p1Board.strike(pos));
				currentPlayer = p1;
			}
		}
	}
	
	private Position getNextStrikePosition() {
		String str = stdin.nextLine();
		//str should be in the form a1, c2, etc.
		return new Position(str.charAt(0), str.charAt(1)-48);
	}

	public static void main(String[] args) {
		try {
			Game game = new Game();
			game.play();
		} catch (Exception e) {
			System.out.println("Error during the game: "+e.getMessage());
			e.printStackTrace();
		}
	}

}
