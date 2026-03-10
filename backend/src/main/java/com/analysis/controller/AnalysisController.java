package com.analysis.controller;

import com.analysis.model.RedundancyResult;
import com.analysis.model.SimilarityResult;
import com.analysis.service.DocumentService;
import com.analysis.service.SimilarityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class AnalysisController {

    private final SimilarityService similarityService;
    private final DocumentService documentService;

    public AnalysisController(SimilarityService similarityService, DocumentService documentService) {
        this.similarityService = similarityService;
        this.documentService = documentService;
    }

    // ── Home Page ──
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // ── Module 1: Similarity Analysis ──
    @PostMapping("/analyze-similarity")
    public String analyzeSimilarity(
            @RequestParam("mainFile") MultipartFile mainFile,
            @RequestParam("referenceFiles") List<MultipartFile> referenceFiles,
            Model model) {

        try {
            if (mainFile.isEmpty()) {
                model.addAttribute("error", "Please upload a main document.");
                return "index";
            }
            if (referenceFiles.isEmpty() || referenceFiles.get(0).isEmpty()) {
                model.addAttribute("error", "Please upload at least one reference document.");
                return "index";
            }

            List<SimilarityResult> results = similarityService.analyzeSimilarity(mainFile, referenceFiles);

            model.addAttribute("module", "similarity");
            model.addAttribute("mainFileName", mainFile.getOriginalFilename());
            model.addAttribute("results", results);
            model.addAttribute("highestMatch", results.isEmpty() ? null : results.get(0));

            return "result";

        } catch (Exception e) {
            model.addAttribute("error", "Error processing files: " + e.getMessage());
            return "index";
        }
    }

    // ── Module 2: Redundancy Detection ──
    @PostMapping("/detect-redundancy")
    public String detectRedundancy(
            @RequestParam("docFile") MultipartFile docFile,
            Model model) {

        try {
            if (docFile.isEmpty()) {
                model.addAttribute("error", "Please upload a document.");
                return "index";
            }

            List<String> sentences = documentService.extractSentences(docFile);
            List<RedundancyResult> redundancies = similarityService.detectRedundancy(docFile);

            model.addAttribute("module", "redundancy");
            model.addAttribute("fileName", docFile.getOriginalFilename());
            model.addAttribute("totalSentences", sentences.size());
            model.addAttribute("redundancies", redundancies);

            return "result";

        } catch (Exception e) {
            model.addAttribute("error", "Error processing file: " + e.getMessage());
            return "index";
        }
    }
}
