package com.nordea.test;

import java.util.Stack;

public class Balance {

	private static final String OPENINGS = "[{(";
	private static final String ALLOWED_CHARS = "[{()}]";

	public boolean isBalanced(String input) {
		if (input == null || !containsAllChars(input)) return false;

		Stack<Character> stack = new Stack<>();

		char lastElement = 0;

		for(char currentElement : input.toCharArray()) {
			lastElement = stack.isEmpty() ? 0 : stack.peek();
			if (isBegining(currentElement)) {
				stack.push(currentElement);
			} else if ( isInproperClosing(lastElement, currentElement)) {
				break;
			} else if ( isEndClosing(lastElement, currentElement)) {
				stack.pop();
			}
		}

		return stack.isEmpty();
	}

	public boolean containsAllChars(String b) {
		return ALLOWED_CHARS.chars().distinct().allMatch(ch -> b.contains(String.valueOf((char) ch)));
	}

	public boolean isBegining(char current) {
		return OPENINGS.indexOf(current) != -1;
	}

	public boolean isEndClosing(char lastElement, char current) {
		return ('[' == lastElement && ']' == current) ||
				('{' == lastElement && '}' == current) ||
				('(' == lastElement && ')' == current);
	}

	public boolean isInproperClosing(char lastElement, char current) {
		return ('[' == lastElement && ']' != current) ||
				('{' == lastElement && '}' != current) ||
				('(' == lastElement && ')' != current);

	}

}
