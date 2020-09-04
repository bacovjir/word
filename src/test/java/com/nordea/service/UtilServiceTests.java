package com.nordea.service;

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

import com.nordea.Properties;
import com.nordea.service.impl.UtilServiceImpl;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UtilServiceTests {

	@Mock
	private Properties properties;
	@InjectMocks
	private UtilService utilService = new UtilServiceImpl(properties);

	@Test
	public void testCleanup() {
		String line = "Test some   line  .  Not   whole";

		String result = utilService.cleanupLine(line);

		assertEquals("Test some line Not whole", result);

	}
	
	@Test
	public void testCleanup2() {
		String line = "Test -somethink - here";

		String result = utilService.cleanupLine(line);

		assertEquals("Test somethink here", result);

	}
	
	@Test
	public void testCleanup3() {
		String line = "Test somethink, here";

		String result = utilService.cleanupLine(line);

		assertEquals("Test somethink here", result);

	}
	
	@Test
	public void testFindSentence() {
		when(properties.getDictionary()).thenReturn(Arrays.asList(new String[] {"Mr"}));
		List<String> result = utilService.findSentences("Mr. Blue is here.");
		
		assertEquals(1, result.size());

		
	}
	
	@Test
	public void testFindSentence1() {
		when(properties.getDictionary()).thenReturn(Arrays.asList(new String[] {"Mr"}));
		List<String> result = utilService.findSentences("Mr. Blue is here. Hello world.");
		
		assertEquals(2, result.size());
		
	}
	
	@Test
	public void testFindSentence2() {
		when(properties.getDictionary()).thenReturn(Arrays.asList(new String[] {"Mr"}));
		List<String> result = utilService.findSentences("Hello world! where are");
		
		assertEquals(2, result.size());

	}
	
	@Test
	public void testFindSentenceNoDictionary() {
		when(properties.getDictionary()).thenReturn(Arrays.asList(new String[] {}));
		List<String> result = utilService.findSentences("Hello world! where are");

		assertEquals(2, result.size());
		
		assertEquals(result.get(0), "Hello world!");
		assertEquals(result.get(1), "where are");
		
	}
	
	@Test
	public void testIsUncompleteSentence() {
	
		List<String> sentences = Arrays.asList(new String[] {"Sentence one. Second"});
		boolean result = utilService.isUncompleteSentence(sentences);
		
		assertEquals(false, result);
	}
	
	@Test
	public void testIsUncompleteSentence2() {
	
		List<String> sentences = Arrays.asList(new String[] {"Sentence one Second"});
		boolean result = utilService.isUncompleteSentence(sentences);
		
		assertEquals(true, result);
	}
}
