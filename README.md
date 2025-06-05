# 🍷 Wine Description System

The **Wine Description System** is a full-stack Java web application that enables users to describe wines using natural language and receive intelligent wine suggestions based on a sommelier-curated database. It integrates Spring Boot, machine learning (Stanford CoreNLP & WEKA), and MySQL to provide an AI-powered matching engine alongside social features like friend requests.

---

## 📌 Highlights

- 🧠 **AI-Powered Search**: Matches user-submitted descriptions to wines using Stanford CoreNLP + WEKA.
- 🔐 **User Authentication**: Secure login and registration system.
- 🍷 **Sommelier Wine Matching**: Compares user keywords to a professional wine dataset.
- 🤝 **Friend System**: Send/receive friend requests and manage your network.
- 💡 **Pure Java NLP Pipeline**: Implements CoreNLP for tokenization, NER, and keyword extraction.
- 📐 **Clean MVC Architecture**: Modular design with Controllers, Services, and Repositories.

---

## 🛠️ Tech Stack

| Layer          | Technology                   |
|----------------|-------------------------------|
| Language       | Java 23                       |
| Backend        | Spring Boot 3.4.2             |
| Frontend       | HTML5, CSS3, JSS              |
| Database       | MySQL (Localhost)             |
| NLP / ML       | Stanford CoreNLP 4.5.9, WEKA 3.8.6 |
| Build Tool     | Maven                         |
| IDE            | IntelliJ IDEA Ultimate        |

> ✅ *Tested primarily on macOS & Windows with IntelliJ IDEA Ultimate.*

---

## ⚙️ How It Works

1. **Input**: User submits a wine description.
2. **Text Preprocessing**: Cleaned and tokenized using Stanford CoreNLP.
3. **Keyword Extraction**: Entities and descriptors are extracted.
4. **Matching**: Description is matched to wines using a scoring system powered by WEKA.
5. **Output**: Returns top 10 wine matches in a clean tabular interface.

---

## 🧪 Core Features

- ✅ User registration and secure login
- ✅ NLP-powered wine description matching
- ✅ View and favorite matched wines
- ✅ Friend system with request/approval flow
- 🚧 *(Upcoming)* Add Word Embeddings for a More Accurate Model
- 🚧 *(Upcoming)* Redesigning Weight Mechanics
- 🚧 *(Upcoming)* Redesigning Frontend W/ React & Angular


---



## 🖥️ Getting Started

### ✅ Requirements

- ✅ Java 17
- ✅ MySQL Server running locally (default port `3306`)
- ✅ IntelliJ IDEA (recommended for easiest setup)
- ✅ Internet access to download dependencies

### 📦 Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/wine-description-system.git
   cd wine-description-system
2. Install Dependencies
   - Download and add:
      - Stanford CoreNLP 4.5.9
      - WEKA 3.8.6
   - Add both to your project’s classpath/module dependencies.
3. Configure the MySQL Database
   - Ensure MySQL server is running locally.
   - Create a database schema (e.g. wine_system).
   - Update src/main/resources/application.properties with:
      - spring.datasource.url=jdbc:mysql://localhost:port#/DBname
      - spring.datasource.username=your_username
      - spring.datasource.password=your_password
      - spring.jpa.hibernate.ddl-auto=update
4. Build and Run
      ```bash
   mvn clean install
   mvn spring-boot:run
5. Access the Web App
   - Visit: [http://localhost:8080/]([url](http://localhost:8080/))
