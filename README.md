# AREP_TALLER07
# **Proyecto: Stream de Posts (Twitter Fake)**

## **Contexto del Proyecto**

Este proyecto es una aplicación web inspirada en Twitter, donde los usuarios pueden registrarse, hacer posts de hasta 140 caracteres y visualizarlos en un stream único. El objetivo principal es demostrar el desarrollo de una aplicación full-stack, desde el backend hasta el frontend, utilizando tecnologías modernas y servicios en la nube de AWS.

El proyecto se divide en varias fases:
1. **Desarrollo del monolito**: Una aplicación Spring Boot que maneja usuarios, posts y el stream único.
2. **Frontend**: Una aplicación JavaScript que consume el API del backend.
3. **Despliegue en la nube**: Uso de Amazon S3 para el frontend y EC2 para el backend.
4. **Seguridad**: Implementación de autenticación y autorización usando JWT y AWS Cognito.
5. **Microservicios**: Migración del monolito a una arquitectura de microservicios desplegados en AWS Lambda.

---



## **Requisitos**

- **Backend**: Spring Boot (Java).
- **Frontend**: JavaScript, HTML, CSS.
- **Despliegue**:
  - Frontend: Amazon S3.
  - Backend: Amazon EC2 (monolito) y AWS Lambda (microservicios).
- **Seguridad**: JWT con AWS Cognito.

---

## **Arquitectura del Proyecto**

El proyecto sigue una arquitectura de tres capas:

1. **Frontend**: Aplicación web estática alojada en **Amazon S3**.
2. **Backend**:
   - **Monolito**: Aplicación Spring Boot desplegada en **EC2**.
   - **Microservicios**: Tres servicios independientes desplegados en **AWS Lambda**.
3. **Seguridad**: Autenticación y autorización con **JWT** y **AWS Cognito**.

---
## Arquitectura
```Bash
AREP_TALLER07/
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/edu/eci/arep/arep_taller07/config
│   │   │   │   ├── SecurityConfiguration.java
│   │   │   │   ├── CorsConfig.java
│   │   │   │   ├── CognitoLogoutHandler.java
│   │   │   │   ├── .java
│   │   │   ├── com/edu/eci/arep/clase6/Contoller
│   │   │   │   ├── PropertyController.java
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── HelloController.java
│   │   │   ├── com/edu/eci/arep/clase6/Model
│   │   │   │   ├── Property.java
│   │   │   │   ├── User.java
│   │   │   ├── com/edu/eci/arep/clase6/dto
│   │   │   │   ├── UserDto.java
│   │   │   ├── com/edu/eci/arep/clase6/Repository
│   │   │   │   ├── PropertyRepository.java
│   │   │   │   ├── UserRepository.java
│   │   │   ├── com/edu/eci/arep/clase6/Service
│   │   │   │   ├── PropertyService.java
│   │   │   │   ├── UserService.java
│   │   │   ├── WebSecure.java
│   ├── resources/
│   │   ├── img
│   │   ├── keystore
│   │   │   │── keystore.12
│   │   │   │── server.cer
│   │   ├── static
│   │   │   │── style.css
│   │   │   │── script.js
│   │   │   ├── index.html
│   │   │   │── loginscript.js
│   │   │   ├── login.html
│   │   ├── truststore
│   │   │   │── truststore.12
│   │   ├── application.properties
│── pom.xml
│── README.md
│── Dockerfile
│── docker-compose.yml
```


## **Entidades del Sistema**

El sistema está compuesto por las siguientes entidades:

1. **Usuario**:
   - `id`: Identificador único.
   - `name`: Nombre del usuario.
   - `email`: Correo electrónico.
   - `password`: Contraseña.

2. **Post**:
   - `id`: Identificador único.
   - `content`: Contenido del post (máximo 140 caracteres).
   - `user`: Usuario que hizo el post.

3. **Stream**:
   - `id`: Identificador único.
   - `posts`: Lista de posts en el stream.

---

## **Instalación y Despliegue**

### **1. Frontend (Amazon S3)**
1. **Construye el Frontend**:
   - Genera los archivos estáticos (HTML, CSS, JS) usando tu herramienta preferida (por ejemplo, `npm run build`).
2. **Sube los Archivos a S3**:
   - Crea un bucket en S3 y habilita el **hosting estático**.
   - Sube los archivos estáticos al bucket.
   - Configura los permisos para que los archivos sean públicos.
3. **Accede a la Aplicación**:
   - La URL de tu aplicación será:
     ```
     http://<nombre-del-bucket>.s3-website-<region>.amazonaws.com
     ```

### **2. Backend (Spring Boot en EC2)**
1. **Empaqueta la Aplicación**:
   - Genera el archivo JAR:
     ```bash
     mvn clean package
     ```
2. **Despliega en EC2**:
   - Conéctate a tu instancia EC2 usando SSH.
   - Copia el archivo JAR a la instancia:
     ```bash
     scp -i myfirstkey.pem target/mi-aplicacion.jar ec2-user@<ip-publica>:/home/ec2-user/
     ```
   - Ejecuta la aplicación:
     ```bash
     java -jar mi-aplicacion.jar
     ```




## **Seguridad con JWT y Cognito**

1. **Configura AWS Cognito**:
   - Crea un **User Pool** en Cognito.
   - Configura los **App Clients** para tu aplicación.
2. **Integra JWT en el Backend**:
   - Usa una librería como **Spring Security** para validar los tokens JWT.
3. **Protege los Endpoints**:
   - Asegura los endpoints del backend para que requieran autenticación.

---

## **Microservices Architecture**

### **Descripción**

El monolito original se dividió en tres microservicios independientes, cada uno con una responsabilidad específica:
1. **Users Service**: Maneja la autenticación y gestión de usuarios.
2. **Posts Service**: Maneja la creación y lectura de posts.
3. **Stream Service**: Maneja el stream único de posts.



## **Contribuidores**

- Diego Chicuazuque
- Manuel Suarez
- Yeltzyn Sierra

---

## **Licencia**

Este proyecto está bajo la licencia [MIT](https://opensource.org/licenses/MIT).

---
