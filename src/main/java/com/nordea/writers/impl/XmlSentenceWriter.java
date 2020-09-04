package com.nordea.writers.impl;

import java.io.BufferedWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.nordea.domain.Sentence;
import com.nordea.writers.SentenceWriter;

@Service("xmlSentenceWriter")
public class XmlSentenceWriter implements SentenceWriter {

	@Override
	public String prepareSentence(Sentence sentence) {
		StringBuilder sb = new StringBuilder();
		sb.append("<sentence>");
		sentence.getWords().forEach(word -> {
				sb.append("<word>");
				sb.append(word);
				sb.append("</word>");
		});
		sb.append("</sentence>");
		
		return sb.toString();
	}

	@Override
	public void init(BufferedWriter bw) {
		try {
			bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
			bw.newLine();
			bw.write("<text>");
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void close(BufferedWriter bw) {
		try {
			bw.write("</text>");
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
