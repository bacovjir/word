package com.nordea.service.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nordea.Properties;
import com.nordea.exceptions.CustomException;
import com.nordea.service.UtilService;

@Service
public class UtilServiceImpl implements UtilService {

	private Properties properties;

	public UtilServiceImpl(Properties properties) {
		super();
		this.properties = properties;
	}

	@Override
	public String cleanupLine(String line) {
		//TODO: 
		return line.replaceAll("-","").replaceAll(",","").replaceAll("\\.","").replaceAll("\\s+"," ").trim();
	}

	@Override
	public void writeToFile(String line, BufferedWriter bw) {
		try {
			bw.write(line);
			bw.newLine();
		} catch (IOException e) {
			throw new CustomException("Cannot write to file.");
		}
	}

	@Override
	public List<String> findSentences(String line) {
		// TODO: implement NLP
		List<String> result = new ArrayList<>();

		String temp = "";
		BreakIterator bi = BreakIterator.getSentenceInstance();
		bi.setText(line);
		int index = 0;
		while (bi.next() != BreakIterator.DONE) {
			String sentence = line.substring(index, bi.current());

			boolean endsWithDict = properties.getDictionary().stream().anyMatch(sentence::contains);
			
			if (endsWithDict) {
				temp = sentence;
			} else if (!temp.isBlank()) {
				result.add((temp += sentence).trim());
				temp = "";
			} else {
				result.add(sentence.trim());
				temp = "";
			}

			index = bi.current();
		}

		return result;
	}
	
	private static final String[] ENDINGS = new String[] {".", "!", "?"};

	public boolean isUncompleteSentence(List<String> sentences) {
		return sentences.size() == 1 && !Arrays.stream(ENDINGS).anyMatch(sentences.get(0)::contains);
	}
}
