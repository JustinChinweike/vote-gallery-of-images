# Vote Gallery of Images

## Project Description
`Vote Gallery of Images` is a Java-based web application that allows users to upload images, view a gallery, and vote on images. The application is built using Maven, SQL, and deployed on Apache Tomcat.

## Features
- **User Authentication**: Secure login system.
- **Image Upload**: Users can upload images to the gallery.
- **Voting System**: Users can vote on images, with constraints to prevent duplicate votes.
- **Image Gallery**: Displays uploaded images with sorting and filtering options.

## Technologies Used
- **Java**: Backend logic using servlets.
- **Maven**: Dependency management.
- **SQL**: Database schema for users, images, and votes.
- **Apache Tomcat**: Application deployment.
- **JSP**: Frontend rendering.

## Database Schema
The application uses the following database schema:
- **Users Table**: Stores user accounts.
- **Images Table**: Stores image file paths and uploader references.
- **Votes Table**: Stores votes with constraints to prevent duplicates.
