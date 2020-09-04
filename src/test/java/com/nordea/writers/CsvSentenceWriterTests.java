package com.nordea.writers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.nordea.domain.Sentence;
import com.nordea.service.ServiceProvider;
import com.nordea.writers.impl.CsvSentenceWriter;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class CsvSentenceWriterTests {

	@Mock
	private ServiceProvider serviceProvider;
	@InjectMocks
	private CsvSentenceWriter writer = new CsvSentenceWriter(serviceProvider);

	@Test
	public void testPreparingSentence() {
		Sentence sentence = getSentence1();
		writer.init(null);
		String result = writer.prepareSentence(sentence);

		assertEquals("Sentence 1, one, two, three", result);
	}

	@Test
	public void testMax() {
		writer.init(null);
		writer.prepareSentence(getSentence1());
		writer.prepareSentence(getSentence2());

		assertEquals(CsvSentenceWriter.maxSize, 4);
	}

	@Test
	public void testMax2() {
		writer.init(null);
		writer.prepareSentence(getSentence2());
		writer.prepareSentence(getSentence1());

		assertEquals(CsvSentenceWriter.maxSize, 4);
	}

	public Sentence getSentence1() {
		List<String> words = Arrays.asList(new String[]{"one", "two", "three"}); 
		return new Sentence(words);
	}

	public Sentence getSentence2() {
		List<String> words = Arrays.asList(new String[]{"one", "two", "three", "four"});
		return new Sentence(words);
	}
}
