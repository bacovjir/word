package com.nordea.service;

import java.io.BufferedWriter;

import com.nordea.writers.SentenceWriter;

public interface ServiceProvider {

	public SentenceWriter getSentenceWriter(String output);

	public BufferedWriter getBufferedWriter(String output);

}
