package com.nordea.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.nordea.exceptions.CustomException;
import com.nordea.service.ServiceProvider;
import com.nordea.writers.SentenceWriter;

@Service
public class ServiceProviderImpl implements ServiceProvider {

	@Autowired
	private ApplicationContext context;

	@Override
	public SentenceWriter getSentenceWriter(String output) {
		try {
			return (SentenceWriter)context.getBean(output.toLowerCase() + "SentenceWriter");
		} catch (Exception e) {
			throw new CustomException("Please provide one of supported outputs: csv, xml");
		}
	}

	@Override
	public BufferedWriter getBufferedWriter(String output) {
		try {
			File fout = new File("out."+output);
			FileOutputStream fos = new FileOutputStream(fout);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			return bw;
		} catch (Exception e) {
			throw new CustomException("Cannot write to output file " + e.getMessage());
		}
	}
}
