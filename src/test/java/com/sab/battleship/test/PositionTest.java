package com.sab.battleship.test;

import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.util.Arrays;

import com.sab.battleship.Position;

public class PositionTest {

	@Test
	public void positiveTestValueOf() {
		Position pos;
		try {
			pos = Position.valueOf("c6");
			assertEquals(pos.getRow(), 2);
			assertEquals(pos.getColumn(), 5);

			pos = Position.valueOf("a10");
			assertEquals(pos.getRow(), 0);
			assertEquals(pos.getColumn(), 9);
		} catch (Exception e) {
			fail("Unexpected test error", e);
		}
	}

	@Test
	public void negativeTestValueOf() {
		Arrays.asList("a11", "m5").forEach(s -> {
			try {
				Position.valueOf(s);
				fail("Position "+s+" parsed successfully in error");
			} catch (Exception e) {
				System.out.printf("Expected error: %s%n", e.getMessage());
			}
		});
	}

}
