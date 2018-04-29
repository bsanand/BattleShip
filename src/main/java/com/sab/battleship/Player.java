package com.sab.battleship;

public class Player {
	String name;
	Board board;

	Player(String name) {
		this.name = name;
		this.board = new Board();
	}
	
	public Board getBoard() {
		return board;
	}

	public String getName() {
		return name;
	}

	public void addShip(Ship ship) throws Exception{
		board.addShip(ship);		
	}
}
