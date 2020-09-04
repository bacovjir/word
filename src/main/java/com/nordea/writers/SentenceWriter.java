package com.nordea.writers;

import java.io.BufferedWriter;

import com.nordea.domain.Sentence;

public interface SentenceWriter {

	default public void init(BufferedWriter bw) {
		// not all implementation will create header
	}
	
	public String prepareSentence(Sentence sentence);
	
	public void close(BufferedWriter bw);
}
