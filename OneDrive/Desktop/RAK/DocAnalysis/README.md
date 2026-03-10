# Document Analysis System

A Java Spring Boot application for Document Similarity Analysis and Redundancy Detection.

**Author:** RAKSHITHA U | ECE Student

---

## Project Structure

```
document-analysis-system/
├── backend/                        ← Spring Boot Java project (import into Eclipse)
│   ├── .project                    ← Eclipse project file
│   ├── .classpath                  ← Eclipse classpath
│   ├── .settings/
│   ├── pom.xml                     ← Maven dependencies
│   └── src/main/
│       ├── java/com/analysis/
│       │   ├── DocumentAnalysisApplication.java   ← Main entry point
│       │   ├── controller/
│       │   │   ├── AnalysisController.java        ← Thymeleaf pages
│       │   │   └── ApiController.java             ← REST JSON API
│       │   ├── service/
│       │   │   ├── DocumentService.java           ← DOCX text extraction
│       │   │   └── SimilarityService.java         ← Cosine similarity algorithm
│       │   └── model/
│       │       ├── SimilarityResult.java
│       │       └── RedundancyResult.java
│       └── resources/
│           ├── application.properties
│           └── templates/
│               ├── index.html     ← Home page (Thymeleaf)
│               └── result.html    ← Results page (Thymeleaf)
│
└── frontend/                      ← Standalone HTML/CSS/JS (no build needed)
    ├── index.html                  ← Open directly in browser
    ├── css/
    │   └── style.css
    └── js/
        └── app.js                 ← Calls backend REST API
```

---

## How to Run in Eclipse

### Step 1: Import the Backend
1. Open Eclipse
2. Go to **File → Import → Maven → Existing Maven Projects**
3. Browse to the `backend/` folder
4. Click **Finish**
5. Wait for Maven to download all dependencies

### Step 2: Run the Application
1. In **Package Explorer**, expand `src/main/java/com/analysis`
2. Right-click `DocumentAnalysisApplication.java`
3. Select **Run As → Java Application** (or Spring Boot App if you have STS plugin)
4. Wait for: `Document Analysis System is RUNNING!` in the Console

### Step 3: Open the App

**Option A – Built-in UI (Thymeleaf):**
Open browser → `http://localhost:8080`

**Option B – Standalone Frontend:**
Open `frontend/index.html` directly in your browser
(Backend must still be running for this to work)

---

## Features

### Module 1: Document Similarity Analysis
- Upload one main DOCX + multiple reference DOCX files
- Calculates cosine similarity between each pair
- Classifies plagiarism: **LOW** (0-30%) / **MEDIUM** (30-70%) / **HIGH** (70-100%)
- Shows highest matching document

### Module 2: Redundancy Detection
- Upload a single DOCX file
- Detects sentence pairs with >70% similarity
- Provides merge suggestions for redundant sentences

---

## Technology Stack

| Layer      | Technology                    |
|------------|-------------------------------|
| Language   | Java 17                       |
| Framework  | Spring Boot 3.2.0             |
| DOCX Read  | Apache POI 5.2.3              |
| Templates  | Thymeleaf                     |
| Frontend   | Bootstrap 5 + Vanilla JS      |
| Build      | Maven 3.6+                    |

---

## Troubleshooting

**Port 8080 already in use:**
Edit `backend/src/main/resources/application.properties`:
```
server.port=8081
```
Then update `API_BASE` in `frontend/js/app.js` to match.

**Java version error:**
Make sure Java 17 is installed: `java -version`

**File upload fails:**
Only `.docx` files are supported, max 10MB.

**Frontend shows "Could not connect":**
Make sure the backend Spring Boot app is running first.
