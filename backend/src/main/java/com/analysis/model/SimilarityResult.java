package com.analysis.model;

public class SimilarityResult {
    private String fileName;
    private double similarityPercentage;
    private String plagiarismLevel;

    public SimilarityResult() {}

    public SimilarityResult(String fileName, double similarityPercentage) {
        this.fileName = fileName;
        this.similarityPercentage = Math.round(similarityPercentage * 100.0) / 100.0;
        this.plagiarismLevel = classifyPlagiarism(similarityPercentage);
    }

    private String classifyPlagiarism(double percentage) {
        if (percentage >= 70) return "HIGH";
        else if (percentage >= 30) return "MEDIUM";
        else return "LOW";
    }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public double getSimilarityPercentage() { return similarityPercentage; }
    public void setSimilarityPercentage(double similarityPercentage) {
        this.similarityPercentage = similarityPercentage;
    }

    public String getPlagiarismLevel() { return plagiarismLevel; }
    public void setPlagiarismLevel(String plagiarismLevel) { this.plagiarismLevel = plagiarismLevel; }
}
