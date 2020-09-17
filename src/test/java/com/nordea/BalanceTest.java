package com.nordea;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.nordea.test.Balance;

public class BalanceTest {

	Balance program = new Balance();

	@Test
	public void whenBalencedInputThenTrue() {
		String balancedExp = "[()]{}{[()()]()}";

		boolean result = program.isBalanced(balancedExp);

		assertEquals(true, result);

	}

	@Test
	public void whenNotAllowedCharAndBalencedInputThenFalse() {
		String balancedExp = "a[()]{}{[()()]()}";

		boolean result = program.isBalanced(balancedExp);

		assertEquals(true, result);

	}

	@Test
	public void whenUnBalencedInputThenFalse() {
		String unBalancedExp = "[(])";

		boolean result = program.isBalanced(unBalancedExp);

		assertEquals(false, result);
	}

	@Test
	public void whenStartCharIsWrongThenFalse() {
		String unBalancedExp = "}(])";

		boolean result = program.isBalanced(unBalancedExp);

		assertEquals(false, result);
	}

	@Test
	public void whenInputIsNullThenReturnFalse() {
		String nullExp = null;

		boolean result = program.isBalanced(nullExp);

		assertEquals(false, result);
	}

	@Test
	public void whenCharBelongsToBeginingReturnTrue() {

		boolean result1 = program.isBegining('[');
		boolean result2 = program.isBegining('{');
		boolean result3 = program.isBegining('(');


		assertEquals(true, result1);
		assertEquals(true, result2);
		assertEquals(true, result3);
	}

	@Test
	public void whenCharDontBelongsToBeginingReturnfalse() {

		boolean result1 = program.isBegining(']');
		boolean result2 = program.isBegining('}');
		boolean result3 = program.isBegining(')');

		assertEquals(false, result1);
		assertEquals(false, result2);
		assertEquals(false, result3);
	}

	@Test
	public void whenEndClosinReturnTrue() {

		boolean result1 = program.isEndClosing('[', ']');
		boolean result2 = program.isEndClosing('{', '}');
		boolean result3 = program.isEndClosing('(', ')');

		assertEquals(true, result1);
		assertEquals(true, result2);
		assertEquals(true, result3);
	}

	@Test
	public void whenNotEndClosingReturnFalse() {

		boolean result1 = program.isEndClosing('[', '{');
		boolean result2 = program.isEndClosing('[', '(');

		assertEquals(false, result1);
		assertEquals(false, result2);
	}

	@Test
	public void whenInproperClosingReturnTrue() {

		boolean result1 = program.isInproperClosing('[', '{');
		boolean result2 = program.isInproperClosing('[', '(');

		assertEquals(true, result1);
		assertEquals(true, result2);
	}

	@Test
	public void whenNotInproperClosingReturnTrue() {

		boolean result1 = program.isInproperClosing('[', ']');
		boolean result2 = program.isInproperClosing('{', '}');
		boolean result3 = program.isInproperClosing('(', ')');

		assertEquals(false, result1);
		assertEquals(false, result2);
		assertEquals(false, result3);
	}

	@Test
	public void whenAllowedCharThenTrue() {
		String balancedExp = "{}[]()";

		boolean result = program.containsAllChars(balancedExp);

		assertEquals(true, result);

	}

	@Test
	public void whenNotAllowedCharThenfalse() {
		String balancedExp = "a{";

		boolean result = program.containsAllChars(balancedExp);

		assertEquals(false, result);

	}
}

