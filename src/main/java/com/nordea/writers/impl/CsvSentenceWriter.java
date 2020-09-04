package com.nordea.writers.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.stereotype.Service;

import com.nordea.domain.Sentence;
import com.nordea.service.ServiceProvider;
import com.nordea.writers.SentenceWriter;

@Service("csvSentenceWriter")
public class CsvSentenceWriter implements SentenceWriter {

	public static long sequence = 1;

	public static int maxSize = 0;

	private ServiceProvider serviceProvider;

	public CsvSentenceWriter(ServiceProvider serviceProvider) {
		super();
		this.serviceProvider = serviceProvider;
	}

	@Override
	public void init(BufferedWriter bw) {
		maxSize = 0;
		sequence = 1;
	}

	@Override
	public String prepareSentence(Sentence sentence) {
		maxSize = sentence.getWords().size() > maxSize ? sentence.getWords().size() : maxSize;

		StringBuilder sb = new StringBuilder();
		sb.append("Sentence ");
		sb.append(sequence++);

		sentence.getWords().forEach(word -> {
			sb.append(", ");
			sb.append(word);
		});

		return sb.toString();
	}

	@Override
	public void close(BufferedWriter bw) {
		try {
			bw.close();

			StringBuilder sb = new StringBuilder();
			for(int i = 1; i < maxSize;i++) {
				sb.append(", ");
				sb.append("Word " + i);
			}

			BufferedWriter tempBw = serviceProvider.getBufferedWriter("temp");

			// Write header with longest Word label (maxSize)
			tempBw.write(sb.toString());
			tempBw.newLine();

			try (LineIterator it = FileUtils.lineIterator(new File("out.csv") , "UTF-8")) {	
				while (it.hasNext()) {
					tempBw.write(it.nextLine());
					tempBw.newLine();
				}
			}

			tempBw.close();

			FileUtils.forceDelete(new File("out.csv"));
			new File("out.temp").renameTo(new File("out.csv"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
