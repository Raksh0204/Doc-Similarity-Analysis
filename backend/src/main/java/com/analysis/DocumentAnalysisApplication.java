package com.analysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DocumentAnalysisApplication {
    public static void main(String[] args) {
        SpringApplication.run(DocumentAnalysisApplication.class, args);
        System.out.println("==============================================");
        System.out.println("  Document Analysis System is RUNNING!");
        System.out.println("  Open: http://localhost:8080");
        System.out.println("==============================================");
    }
}
