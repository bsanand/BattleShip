package com.sab.battleship.test;

import org.testng.annotations.Test;
import static org.testng.Assert.fail;
import static org.testng.Assert.assertEquals;

import com.sab.battleship.Board;
import com.sab.battleship.Position;
import com.sab.battleship.Ship;
import com.sab.battleship.Ship.Orientation;
import com.sab.battleship.StrikeResult;

public class BoardTest {
	
	Board board = new Board();
	
	@Test
	public void addShipTest() {
		
		try {
			board.addShip(new Ship(4, Position.valueOf("d5"), Orientation.HORIZONTAL));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected error adding ship to board");
		}
		try {
			board.addShip(new Ship(3, Position.valueOf("d8"), Orientation.HORIZONTAL));
			fail("Ship added successfully in error");
		} catch (Exception e) {
			System.out.println("Expected error adding ship overlapping another ship: "+e.getMessage());
		}
	}
	
	@Test(dependsOnMethods="addShipTest")
	public void strikeTest() {
		StrikeResult result;
		try {
			result = board.strike(Position.valueOf("d6"));
			assertEquals(result, StrikeResult.HIT);
			
			result = board.strike(Position.valueOf("e8"));
			assertEquals(result, StrikeResult.MISS);
			
			result = board.strike(Position.valueOf("d8"));
			assertEquals(result, StrikeResult.HIT);
			
			result = board.strike(Position.valueOf("d7"));
			assertEquals(result, StrikeResult.HIT);
			
			result = board.strike(Position.valueOf("d5"));
			assertEquals(result, StrikeResult.WIN);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Error in strikeTest", e);
		}
	}
}
