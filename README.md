# Contact Management System (CMS) - Backend API

A robust, secure, and scalable Contact Management System built with Spring Boot 3.5.4 and Java 17, featuring JWT authentication and role-based access control.
git 
## 📋 Table of Contents
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Prerequisites](#-prerequisites)
- [Setup & Installation](#-setup--installation)
- [API Documentation](#-api-documentation)
- [Environment Variables](#-environment-variables)
- [Deployment](#-deployment)
- [License](#-license)

## ✨ Features
- 🔐 JWT-based authentication
- 👥 User roles (ADMIN, USER)
- 📇 Contact management (CRUD operations)
- 🏷️ Tagging system for contacts
- 🔍 Search and filter contacts
- 📱 RESTful API design
- 🔄 CORS support
- 📝 Input validation
- 🛡️ Secure password hashing

## 🛠 Tech Stack
- **Backend**: Spring Boot 3.5.4
- **Language**: Java 17
- **Authentication**: JWT
- **Database**: H2 (embedded, for development)
- **Build Tool**: Maven
- **API Documentation**: OpenAPI (Swagger)
- **Validation**: Jakarta Bean Validation

## 📋 Prerequisites
- Java 17 or higher
- Maven 3.6.3 or higher
- H2 Database (embedded)

## 🚀 Setup & Installation

1. **Clone the repository**
   ```bash
   git clone [your-repository-url]
   cd cms
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

## 🔌 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Authentication
All endpoints except `/auth/**` require authentication. Include the JWT token in the Authorization header:

```
Authorization: Bearer your.jwt.token.here
```

### Endpoints

#### Authentication
- `POST /auth/signin` - User login
- `POST /auth/signup` - Register new user

#### Contacts
- `GET /contacts` - Get all contacts (supports search and tag filtering)
- `POST /contacts` - Create new contact
- `GET /contacts/{id}` - Get contact by ID
- `PUT /contacts/{id}` - Update contact
- `DELETE /contacts/{id}` - Delete contact

#### Tags
- `GET /tags` - Get all tags
- `POST /tags` - Create new tag
- `DELETE /tags/{id}` - Delete tag

### Example Requests

**Login**
```http
POST /api/auth/signin
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

**Create Contact**
```http
POST /api/contacts
Authorization: Bearer your.jwt.token.here
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "phone": "123-456-7890",
  "company": "Acme Inc",
  "tagIds": [1, 2]
}
```

## 🔧 Environment Variables
Create a `.env` file in the root directory with the following variables:

```properties
# JWT Configuration
JWT_SECRET=your-secret-key-here
JWT_EXPIRATION_MS=86400000

# Admin User
ADMIN_USERNAME=admin
ADMIN_EMAIL=admin@example.com
ADMIN_PASSWORD=admin123

# Database (H2)
SPRING_DATASOURCE_URL=jdbc:h2:mem:contactdb
SPRING_DATASOURCE_USERNAME=sa
SPRING_DATASOURCE_PASSWORD=password
SPRING_H2_CONSOLE_PATH=/h2-console
```

## 🚀 Deployment

### Build for Production
```bash
mvn clean package -DskipTests
```

### Run JAR File
```bash
java -jar target/cms-0.0.1-SNAPSHOT.jar
```

### Docker Deployment
1. Build the Docker image:
   ```bash
   docker build -t cms-backend .
   ```

2. Run the container:
   ```bash
   docker run -p 8080:8080 cms-backend
   ```

## 📝 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support
For support, please contact the development team or open an issue in the repository.

---

<div align="center">
  Made with ❤️ by Your Team Name
</div>
