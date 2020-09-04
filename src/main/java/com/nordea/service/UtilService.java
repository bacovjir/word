package com.nordea.service;

import java.io.BufferedWriter;
import java.util.List;

public interface UtilService {

	public String cleanupLine(String line);

	public void writeToFile(String line, BufferedWriter bw);

	public List<String> findSentences(String line);

	public boolean isUncompleteSentence(List<String> sentences);
}
