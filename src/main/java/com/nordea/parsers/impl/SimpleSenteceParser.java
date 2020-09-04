package com.nordea.parsers.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nordea.domain.Sentence;
import com.nordea.parsers.SentenceParser;
import com.nordea.service.UtilService;

@Service
public class SimpleSenteceParser implements SentenceParser{

	private UtilService utilService;

	public SimpleSenteceParser(UtilService utilService) {
		super();
		this.utilService = utilService;
	}

	@Override
	public List<Sentence> processLine(String line, StringBuilder sb) {
		List<Sentence> result = new ArrayList<Sentence>(); 

		List<String> sentences = utilService.findSentences(line);

		if (sentences.size() > 0 && !utilService.isUncompleteSentence(sentences)) {
			sb.append(sentences.get(0));
			String sentence = utilService.cleanupLine(sb.toString());
			List<String> words = Arrays.asList(sentence.split(" "));
			words.sort((a1, a2) -> a1.compareToIgnoreCase(a2));
			Sentence tempSentence = new Sentence(words);
			result.add(tempSentence);

			if (sentences.size() > 1) {
				sb.setLength(0);
				line = line.replace(sentences.get(0), "");
				List<Sentence> response = processLine(line, sb);
				result.addAll(response);
			}
			if (sentences.size() == 1) {
				sb.setLength(0);
			}
		} else {
			sb.append(line);
		}


		return result;
	}

	

}
