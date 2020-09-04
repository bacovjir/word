package com.nordea.domain;

import java.util.List;

//import com.thoughtworks.xstream.annotations.XStreamAlias;

//@XStreamAlias("sentence")
public class Sentence {

	
//	@XStreamAlias("word")
	private List<String> words;

	public Sentence(List<String> words) {
		super();
		this.words = words;
	}

	public List<String> getWords() {
		return words;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}

}
