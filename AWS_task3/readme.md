# Text File Processor Lambda

A **Java 17 AWS Lambda function** that automatically handles `.txt` file uploads to an **S3 bucket**.  
The Lambda function calculates **line**, **word**, and **character** counts, generates a **100-character snippet**, and saves the processed data into a **DynamoDB** table.

---

## Project Overview

### Workflow Summary
1. **S3 Bucket** – Accepts `.txt` file uploads and triggers the Lambda function.  
2. **Lambda Function** – Processes every uploaded file by:
   - Counting total lines, words, and characters  
   - Extracting the first 100 characters as a text preview  
   - Saving the results in DynamoDB  
3. **DynamoDB Table** – Retains processed data and file metadata.

---

## Project Structure
<pre>
   TextFileProcessor/
├── pom.xml
├── README.md
└── src
└── main
└── java
└── com
└── example
└── TextFileProcessor.java
</pre>


- **TextFileProcessor.java** — Core Lambda handler logic  
- **pom.xml** — Maven setup and dependency management  

---

## AWS Resources

### 1. S3 Bucket
- **Name:** `file-processing-bucket-<anupama>`  
- **Trigger:** `ObjectCreated` event for `.txt` uploads
- <img width="1347" height="604" alt="image" src="https://github.com/user-attachments/assets/f9962cfd-3102-4bf1-8bc6-1b0e10587dbd" />
 

### 2. DynamoDB Table
- **Table Name:** `FileProcessingResults`  
- **Partition Key:** `fileName` (String)  
- **Attributes:** `lineCount`, `wordCount`, `charCount`, `preview`, `processedDate`
  <img width="1341" height="639" alt="image" src="https://github.com/user-attachments/assets/a2f82f1f-9a63-4b5f-b5f6-10edc79626e8" />


### 3. IAM Role for Lambda
Required permissions:
- Read access to S3  
- Write access to DynamoDB  
- Standard Lambda execution rights (CloudWatch logging)

---

## Build Instructions

Make sure **Java 17** and **Maven** are installed on your system.

From the project’s root directory, run:
```bash
mvn clean package

## Resulting file
target/TextFileProcessor-1.0.jar

