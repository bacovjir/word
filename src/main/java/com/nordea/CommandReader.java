package com.nordea;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.nordea.service.InputService;

@Component
public class CommandReader implements CommandLineRunner {

	private static final String DEFAULT_OUTPUT = "csv";
	private static final String DEFAULT_INPUT = "small.in";

	@Autowired
	private InputService service;

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		System.out.println("What is you favorite output (csv|xml):");
		String output = scanner.nextLine();

		if (output.isEmpty()) {
			System.out.println("Using default '" + DEFAULT_OUTPUT + "'");
			output = DEFAULT_OUTPUT;
		}

		System.out.println("Provide path to input file:");
		String input = scanner.nextLine();

		if (input.isEmpty()) {
			System.out.println("Using default '"+DEFAULT_INPUT+"'");
			input = DEFAULT_INPUT;
		}

		try {
			service.processInput(input, output);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		scanner.close();
		System.exit(0);
	}

}

