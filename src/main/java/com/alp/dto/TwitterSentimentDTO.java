package com.alp.dto;

import java.util.List;

public class TwitterSentimentDTO {
	private String key;
	private List<SentimentDTO> values;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<SentimentDTO> getValues() {
		return values;
	}

	public void setValues(List<SentimentDTO> values) {
		this.values = values;
	}

}
