package com.nordea.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.stereotype.Service;

import com.nordea.aspects.Timer;
import com.nordea.domain.Sentence;
import com.nordea.exceptions.CustomException;
import com.nordea.parsers.SentenceParser;
import com.nordea.service.InputService;
import com.nordea.service.ServiceProvider;
import com.nordea.service.UtilService;
import com.nordea.writers.SentenceWriter;

@Service
public class InputServiceImpl implements InputService {

	private SentenceParser parser;

	private ServiceProvider serviceProvider;

	private UtilService utilService;

	public InputServiceImpl(SentenceParser parser, ServiceProvider serviceProvider,
			UtilService utilService) {
		super();
		this.parser = parser;
		this.serviceProvider = serviceProvider;
		this.utilService = utilService;
	}

	@Timer
	@Override
	public void processInput(String input, String output) {
		SentenceWriter writer = serviceProvider.getSentenceWriter(output);
		BufferedWriter bw = serviceProvider.getBufferedWriter(output);
		StringBuilder sentenceBuffer = new StringBuilder();

		try (LineIterator it = FileUtils.lineIterator(new File(input) , "UTF-8")) {
			writer.init(bw);

			while (it.hasNext()) {
				List<Sentence> sentences = parser.processLine(it.nextLine(), sentenceBuffer);

				sentences.stream()
				.map(sentence -> writer.prepareSentence(sentence))
				.forEach(stringResult -> utilService.writeToFile(stringResult, bw));

			}

			writer.close(bw);

		} catch (Exception e) {
			throw new CustomException("Could not process file: " + e.getMessage());
		}
	}

}
