package com.nordea.parsers;

import java.util.List;

import com.nordea.domain.Sentence;

public interface SentenceParser {

	public List<Sentence> processLine(String line, StringBuilder sb);
}
