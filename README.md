# **AREP_TALLER07**
## **Project: Post Streaming Platform (Fake Twitter) 🐦**

### **Project Context** 📖
This project is a web application inspired by Twitter, where users can register, create posts (up to 140 characters), and view them in a single stream. The main objective is to develop a full-stack application, from backend to frontend, using modern technologies and AWS cloud services.

The project consists of multiple phases:
1. **Monolithic Development** 🏗️: A Spring Boot application handling users, posts, and the post stream.
2. **Frontend Implementation** 🎨: A JavaScript-based web app consuming the backend API.
3. **Cloud Deployment** ☁️: Using Amazon S3 for frontend and EC2 for backend.
4. **Security Implementation** 🔐: Authentication and authorization with JWT and AWS Cognito.
5. **Microservices Migration** ⚡: Converting the monolith into a microservices architecture deployed on AWS Lambda.

---

## **Requirements** 🛠️
- **Backend**: Spring Boot (Java) ☕
- **Frontend**: JavaScript, HTML, CSS 🌐
- **Cloud Deployment**:
  - Frontend: **Amazon S3** 📦
  - Backend: **Amazon EC2** (monolith) and **AWS Lambda** (microservices) 🌍
- **Security**: JWT with **AWS Cognito** 🔑

---

## **Project Architecture** 🏛️
The project follows a three-tier architecture:

1. **Frontend**: A static web application hosted on **Amazon S3**.
2. **Backend**:
   - **Monolith**: A Spring Boot application deployed on **EC2**.
   - **Microservices**: Three independent services deployed on **AWS Lambda**.
3. **Security**: Authentication and authorization using **JWT** and **AWS Cognito**.

---

## **Project Structure** 📂
```bash
AREP_TALLER07/
│── Back-end/
│ ├── src/
│ │ ├── main/
│ │ │ ├── java/
│ │ │ │ ├── com/eci/arep/arep_taller07/
│ │ │ │ │ ├── config/
│ │ │ │ │ │ ├── CognitoLogoutHandler.java
│ │ │ │ │ │ ├── CorsConfig.java
│ │ │ │ │ │ ├── SecurityConfiguration.java
│ │ │ │ │ │ ├── WebConfiguration.java
│ │ │ │ │ ├── controller/
│ │ │ │ │ │ ├── AuthController.java
│ │ │ │ │ │ ├── PostController.java
│ │ │ │ │ │ ├── PostStreamController.java
│ │ │ │ │ │ ├── UserController.java
│ │ │ │ │ ├── model/
│ │ │ │ │ │ ├── Post.java
│ │ │ │ │ │ ├── PostStream.java
│ │ │ │ │ │ ├── User.java
│ │ │ │ │ ├── repository/
│ │ │ │ │ │ ├── PostRepository.java
│ │ │ │ │ │ ├── PostStreamRepository.java
│ │ │ │ │ │ ├── UserRepository.java
│ │ │ │ │ ├── service/
│ │ │ │ │ │ ├── PostService.java
│ │ │ │ │ │ ├── PostStreamService.java
│ │ │ │ │ │ ├── UserService.java
│ │ │ │ │ ├── AppSpringBoot.java
│ │ │ ├── resources/
│ │ │ │ ├── application.properties
│ │ │ │ ├── login.html
│ ├── target/
│ ├── pom.xml
│── Front-end/
│ ├── index.html
│ ├── script.js
│ ├── styles.css
│── README.md
```

---

## **Entities** 📜

### **1. User** 👤
- `id`: Unique identifier.
- `name`: User's name.
- `email`: User's email.
- `password`: Hashed password.

### **2. Post** ✍️
- `id`: Unique identifier.
- `content`: Post content (max 140 characters).
- `user`: The user who created the post.

### **3. Stream** 📢
- `id`: Unique identifier.
- `posts`: List of posts in the stream.

---

## **Installation & Deployment** 🚀

### **1. Frontend Deployment (Amazon S3)** 🌍
1. **Build the frontend**:
   ```bash
   npm run build
   ```
2. **Upload the static files to S3**:
   - Create an S3 bucket.
   - Enable **static website hosting**.
   - Upload `index.html`, `styles.css`, and `script.js`.
   - Set public read permissions.
3. **Access the application**:
   ```
   http://<bucket-name>.s3-website-<region>.amazonaws.com
   ```

### **2. Backend Deployment (Spring Boot on EC2)** 🖥️
1. **Package the application**:
   ```bash
   mvn clean package
   ```
2. **Deploy to EC2**:
   ```bash
   scp -i mykey.pem target/app.jar ec2-user@<ec2-ip>:/home/ec2-user/
   ssh -i mykey.pem ec2-user@<ec2-ip>
   java -jar app.jar
   ```

### **3. Docker Deployment (Optional)** 🐳
1. **Build Docker image**:
   ```bash
   docker build -t my-app .
   ```
2. **Run Docker container**:
   ```bash
   docker run -p 8080:8080 my-app
   ```

---

## **Security with JWT & AWS Cognito** 🔐

### **1. AWS Cognito Setup**
1. Create a **User Pool** in AWS Cognito.
2. Configure **App Clients**.

### **2. Backend JWT Integration**
1. Validate JWT using **Spring Security**.
2. Secure API endpoints.

---

## **Microservices Architecture** 🏗️

The original monolith was split into three independent microservices:
1. **Users Service** 👤: Handles user authentication and management.
2. **Posts Service** ✍️: Manages post creation and retrieval.
3. **Stream Service** 📢: Manages the global post stream.

These services are deployed on **AWS Lambda**.

---

## **Contributors** 🤝
- **Diego Chicuazuque**
- **Manuel Suárez**
- **Yeltzyn Sierra**

---

## **License** 📜
This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).

---

