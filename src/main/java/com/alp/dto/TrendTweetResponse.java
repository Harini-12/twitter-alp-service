package com.alp.dto;

import java.util.List;

public class TrendTweetResponse {
    String name;
    List<TweetDTO> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TweetDTO> getChildren() {
        return children;
    }

    public void setChildren(List<TweetDTO> children) {
        this.children = children;
    }
}
