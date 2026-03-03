# 📚 BookUp - Personal Library Manager

BookUp is a desktop application built with Java and JavaFX that allows users to seamlessly track, manage, and filter their personal reading habits. Originally developed as a university project to explore Object-Oriented Programming (OOP) concepts.

## 💡 The Motivation

As a reader, I used to rely on basic note apps on my phone to keep track of my reading lists. While functional at first, it quickly became disorganized and inefficient as my library grew. I couldn't easily filter or sort my backlog which is quite annoying when you like to organize your books alphabetically), or keep detailed notes and ratings tied to specific books. So, I built BookUp to solve this personal pain point. I wanted a dedicated, visual tool to organize my books by their exact status, rate my favorites, and store my thoughts all in one clean, centralized place.

## ✨ Features

* **Library Tracking:** Add books to library and categorize them by status: *To Read*, *Currently Reading*, or *Read*.
* **Detailed Logging:** Store book data including title, author, publication year, genres, personal ratings, and custom notes.
* **Dynamic Filtering:** Instantly sort books by reading status.
* **Data Persistence:** Automatically saves and loads the library to a local ".txt" file to avoid losing the data.
* **Export Functionality:** Export the entire categorized library into a cleanly formatted text document for sharing or backup.

## 🧠 OOP Concepts

This project serves as a practical application of core Object-Oriented Programming principles:

* **Inheritance & Polymorphism:** Utilizes an abstract base `Book` class extended by specific concrete classes (`BookRead`, `BookToRead`, `CurrentBook`). Methods like `getExportString()` are overridden in subclasses to provide polymorphic behavior.
* **Encapsulation:** Strict use of private fields with public getters/setters protects the internal state of data models.
* **Separation of Concerns (MVC):** The user interface (JavaFX/FXML) is completely decoupled from the underlying business logic (`LibraryManager`) via dedicated Controller classes.
* **Singleton Pattern Approach:** Uses a centralized data access class (`LmAccess`) to ensure the application maintains a single, consistent state of the library across all views.

## 🛠️ Tech Stack

* **Language:** Java (JDK 21+)
* **GUI Framework:** JavaFX
* **Architecture:** Model-View-Controller (MVC)
* **Data Storage:** Custom flat-file serialization

## 🚀 How to Run Locally

1. Ensure you have the Java Development Kit (JDK) and JavaFX SDK installed on your machine.
2. Clone this repository:
   ```bash
   git clone [https://github.com/yourusername/BookUp.git](https://github.com/yourusername/BookUp.git)
