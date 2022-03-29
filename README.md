# revision
Java Web Application. The following technologies are used:

- Java 8, Servlets, JSP, Ajax
- JDBC, PostgreSQL
- Maven
- Google OAuth2, Google Drive API, Google Sheets API

The application hosted on https://revision-web.herokuapp.com/

It's a personal online dictionary. You can add your words, group them by sections and repeat.

Features:
- Registration and login are based on filters, sessions and cookies
- All CRUD operations with dictionaries, sections and words. Saving entities to PostgreSQL
- Importing words from CSV file and Google Sheets
- Repetition mechanism based on ajax without page refresh
- Sending an email to the administrator when a new user register

