package com.nordea.parsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.nordea.domain.Sentence;
import com.nordea.parsers.impl.SimpleSenteceParser;
import com.nordea.service.UtilService;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class SimpleSenteceParserTest {

	@Mock
	private UtilService utilService;
	@InjectMocks
	SimpleSenteceParser parser = new SimpleSenteceParser(utilService);

	@Test
	public void testSentenceAndHalf() {
		when(utilService.cleanupLine("Test some line.")).thenReturn("Test some line");

		List<String> all = Arrays.asList(new String[] {"Test some line.", " Not whole"});
		List<String> part = Arrays.asList(new String[] {"Not whole"});
		when(utilService.findSentences("Test some line. Not whole")).thenReturn(all);
		when(utilService.findSentences(" Not whole")).thenReturn(part);

		when(utilService.isUncompleteSentence(all)).thenReturn(false);
		when(utilService.isUncompleteSentence(part)).thenReturn(true);

		String line = "Test some line. Not whole";
		StringBuilder sb = new StringBuilder();
		List<Sentence> sentences = parser.processLine(line, sb);

		assertEquals(1, sentences.size());
		assertEquals(" Not whole", sb.toString());
	}

	@Test
	public void testTwoSentences() {

		when(utilService.cleanupLine("Test some line.")).thenReturn("Test some line");
		when(utilService.cleanupLine("Second sentence.")).thenReturn("Second sentence.");

		List<String> first = Arrays.asList(new String[] {"Test some line.", " Second sentence."});
		List<String> second = Arrays.asList(new String[] {"Second sentence."});

		when(utilService.findSentences("Test some line. Second sentence.")).thenReturn(first );
		when(utilService.findSentences(" Second sentence.")).thenReturn(second );

		when(utilService.isUncompleteSentence(first)).thenReturn(false);
		when(utilService.isUncompleteSentence(second)).thenReturn(false);

		String line = "Test some line. Second sentence.";
		StringBuilder sb = new StringBuilder();
		List<Sentence> sentences = parser.processLine(line, sb);

		assertEquals(2, sentences.size());
		assertEquals("", sb.toString());
	}


	@Test
	public void testNotWholeSentence() {
		List<String> first = Arrays.asList(new String[] {"Not whole sentence"});
		when(utilService.findSentences("Not whole sentence")).thenReturn(first );

		when(utilService.isUncompleteSentence(first)).thenReturn(true);

		String line = "Not whole sentence";
		StringBuilder sb = new StringBuilder();
		List<Sentence> sentences = parser.processLine(line, sb);

		assertEquals(0, sentences.size());
		assertEquals("Not whole sentence", sb.toString());
	}

	@Test
	public void testEmptyLine() {
		List<String> first = Arrays.asList(new String[] {""});
		when(utilService.findSentences("")).thenReturn(first );

		when(utilService.isUncompleteSentence(first)).thenReturn(true);

		String line = "";
		StringBuilder sb = new StringBuilder();
		List<Sentence> sentences = parser.processLine(line, sb);

		assertEquals(0, sentences.size());
		assertEquals("", sb.toString());
	}

}
