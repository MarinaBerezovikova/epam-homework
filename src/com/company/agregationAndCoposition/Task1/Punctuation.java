package com.company.agregationAndCoposition.Task1;

public class Punctuation implements PartOfSentence {

    private final String punctuation;

    public Punctuation(String str) {
        punctuation = str;
    }

    public String getPunctuation() {
        return punctuation;
    }

    @Override
    public StringBuilder getContent() {
        return new StringBuilder(this.punctuation);
    }

    public String toString() {
        return getPunctuation();
    }
}
