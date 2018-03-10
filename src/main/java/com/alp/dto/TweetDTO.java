package com.alp.dto;

import java.util.List;

public class TweetDTO {
    private String name;
    private List<WordDTO> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WordDTO> getChildren() {
        return children;
    }

    public void setChildren(List<WordDTO> children) {
        this.children = children;
    }

}
