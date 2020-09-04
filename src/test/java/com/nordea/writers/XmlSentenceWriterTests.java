package com.nordea.writers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.BreakIterator;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.nordea.domain.Sentence;
import com.nordea.writers.impl.XmlSentenceWriter;

public class XmlSentenceWriterTests {

	private XmlSentenceWriter writer = new XmlSentenceWriter();

	@Test
	public void testPreparingSentence() {
		Sentence sentence = getSentence();
		String result = writer.prepareSentence(sentence);

		assertEquals("<sentence><word>one</word><word>two</word><word>three</word></sentence>", result);
	}

	public Sentence getSentence() {
		List<String> words = Arrays.asList(new String[]{"one", "two", "three"}); 
		return new Sentence(words);
	}

	@Test
	public void testBreak() {
		BreakIterator bi = BreakIterator.getSentenceInstance();
		String text = "What	he  shouted was shocking:  停在那儿, 你这肮脏的掠夺者! I couldn't understand a word,perhaps because Chinese \r\n" +
				" isn't my mother tongue. I was just standing there watching Mr. Young marching around - he \r\n" +
				"was    furious.   Why was he directing  his  anger at me? Little did I know 	about 	that.";
		bi.setText(text );
		int index = 0;
		while (bi.next() != BreakIterator.DONE) {
		    String sentence = text.substring(index, bi.current());
		    index = bi.current();
		}
	}
}
