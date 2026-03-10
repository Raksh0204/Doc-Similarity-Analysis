package com.analysis.model;

public class RedundancyResult {
    private String sentence1;
    private String sentence2;
    private double similarityPercentage;
    private String mergeSuggestion;

    public RedundancyResult() {}

    public RedundancyResult(String sentence1, String sentence2, double similarityPercentage) {
        this.sentence1 = sentence1;
        this.sentence2 = sentence2;
        this.similarityPercentage = Math.round(similarityPercentage * 100.0) / 100.0;
        // Suggest the longer sentence as the merged version
        this.mergeSuggestion = sentence1.length() >= sentence2.length() ? sentence1 : sentence2;
    }

    public String getSentence1() { return sentence1; }
    public void setSentence1(String sentence1) { this.sentence1 = sentence1; }

    public String getSentence2() { return sentence2; }
    public void setSentence2(String sentence2) { this.sentence2 = sentence2; }

    public double getSimilarityPercentage() { return similarityPercentage; }
    public void setSimilarityPercentage(double similarityPercentage) {
        this.similarityPercentage = similarityPercentage;
    }

    public String getMergeSuggestion() { return mergeSuggestion; }
    public void setMergeSuggestion(String mergeSuggestion) { this.mergeSuggestion = mergeSuggestion; }
}
