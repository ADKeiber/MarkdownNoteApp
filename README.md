# Markdown Note API
API that is used to upload Markdown files, convert those files to HTML, and grammar check those files

<b> Table of Contents </b> <br>
- [Description](#description)
  - [Endpoint Operations](#endpoint-operations)
  - [Application Technical Features](#application-technical-features)
- [How to Run](#how-to-run)

## Description
Markdown Note API is a Spring Boot application that allows users to create/manage markdown/html files and check for proper grammar. <br>
This application includes no security related to an individuals account and just simply checks that a username doesn't already exist when
creating a new user. <br>
<br>
<b>NOTE:</b> This application is part of a learning experience and is the fifth project in my backend learning journey. The path of my journey closely follows this roadmap... https://roadmap.sh/backend/project-ideas <br>
**https://roadmap.sh/projects/markdown-note-taking-app**
<br>
![image](https://github.com/user-attachments/assets/9c2f1db0-4475-4edc-8fcb-61406dc5eb8c)
<br>
### Endpoint Operations

* Create a New User
* Retrieve the existing user's ID
* Upload a Markdown File
* Update an existing markdown file
* Retrieve a file whos version of a specified type (Markdown or HTML)
* Check the grammar of an uploaded file
* Get all supported languages of the grammar checker
* Get all file IDs associated with a user's ID
  
### Application Technical Features
* Spring Boot 3.4.0
* Java 17
* MySQL database
* Lombok

## How to Run 
To run the application, you must have Maven and java installed <br>

My local environment versions:
- Maven 3.9.9
- Java 17.0.9
- MySql 8.0
