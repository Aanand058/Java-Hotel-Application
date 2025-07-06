# Java Hotel Management System

A comprehensive hotel management application built with JavaFX and Java networking, featuring a client-server architecture for room booking, guest management, and administrative functions.

## 🏨 Features

### Core Functionality
- **Room Management**: Support for Single, Double, Deluxe, and Penthouse rooms
- **Guest Booking**: Book rooms as a guest with detailed guest information
- **Admin Panel**: Administrative interface for hotel management
- **Real-time Availability**: Check room availability and booking status
- **Billing System**: Generate and manage bills for guests
- **Checkout Process**: Streamlined guest checkout procedures
- **Database Integration**: MySQL database for persistent data storage

### Technical Features
- **Client-Server Architecture**: Multi-threaded server handling multiple client connections
- **JavaFX UI**: Modern, responsive user interface
- **Network Communication**: Real-time communication between client and server
- **User Authentication**: Secure login system with admin and guest roles

## 🏗️ Project Structure

```
Java-Hotel-Application/
├── Hotel App/                    # Client Application
│   ├── src/
│   │   ├── application/         # Main application files
│   │   ├── controller/          # JavaFX controllers
│   │   ├── database/           # Database access layer
│   │   ├── model/              # Data models (Room, Guest, etc.)
│   │   └── views/              # FXML UI files
│   └── bin/                    # Compiled classes
├── Server_Code/                 # Server Application
│   ├── src/
│   │   └── application/        # Server and client handler
│   └── bin/                    # Compiled classes
└── usersTable.sql              # Database schema and sample data
```

## 🚀 Prerequisites

- **Java Development Kit (JDK)**: Version 11 or higher
- **JavaFX**: Included with JDK 11+ or separate installation
- **MySQL Database**: Version 5.7 or higher
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code (recommended)

## 🛠️ Tools Used

### Development Tools
- **Eclipse IDE**: Primary development environment for Java development
- **Scene Builder**: Visual FXML editor for designing JavaFX user interfaces
- **MySQL Workbench**: Database design, development, and administration tool

### Additional Tools
- **JavaFX SDK**: For creating rich desktop applications
- **MySQL Connector/J**: JDBC driver for MySQL database connectivity

## 📦 Installation & Setup

### 1. Database Setup

1. **Install MySQL** if not already installed
2. **Create Database**:
   ```sql
   CREATE DATABASE apd;
   USE apd;
   ```
3. **Run SQL Script**:
   ```bash
   mysql -u your_username -p apd < usersTable.sql
   ```

### 2. Server Setup

1. **Navigate to Server Directory**:
   ```bash
   cd Server_Code
   ```

2. **Compile Server Code**:
   ```bash
   javac -d bin src/application/*.java
   ```

3. **Run Server**:
   ```bash
   java -cp bin application.ServerCode
   ```

   The server will start on port 6000 and display connection status.

### 3. Client Setup

1. **Navigate to Hotel App Directory**:
   ```bash
   cd "Hotel App"
   ```

2. **Compile Client Code**:
   ```bash
   javac -d bin --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml src/application/*.java src/controller/*.java src/database/*.java src/model/*.java
   ```

3. **Run Client Application**:
   ```bash
   java -cp bin --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml application.Main
   ```

## 👥 User Accounts

### Admin Accounts
- **Username**: `admin`, **Password**: `admin`
- **Username**: `Aanand`, **Password**: `a1m2i3t4`
- **Username**: `Sam`, **Password**: `Sam@098`
- **Username**: `Ali`, **Password**: `Ali123`

### Guest Accounts
- **Username**: `Aman`, **Password**: `Aman123`
- **Username**: `Vlad`, **Password**: `Vlad123`

## 🎯 Usage Guide

### For Guests
1. **Launch the application**
2. **Click "Booking as Guest"** on the home screen
3. **Enter guest details** (name, contact information)
4. **Select room type** and check availability
5. **Complete booking** and receive confirmation

### For Administrators
1. **Launch the application**
2. **Click "Login As Admin"** on the home screen
3. **Enter admin credentials**
4. **Access admin panel** with options:
   - View current bookings
   - Check room availability
   - Manage bills and services
   - Process checkouts

## 🔧 Configuration

### Database Connection
Update database connection settings in `Hotel App/src/database/DatabaseAccess.java`:
```java
// Modify these values according to your MySQL setup
private static final String URL = "jdbc:mysql://localhost:3306/apd";
private static final String USERNAME = "your_username";
private static final String PASSWORD = "your_password";
```

### Server Configuration
The server runs on port 6000 by default. To change the port, modify `Server_Code/src/application/ServerCode.java`:
```java
ServerSocket ss = new ServerSocket(YOUR_PORT_NUMBER);
```

## 🛠️ Development

### Adding New Features
1. **Models**: Add new data classes in `src/model/`
2. **Controllers**: Create JavaFX controllers in `src/controller/`
3. **Views**: Design UI with FXML in `src/views/`
4. **Database**: Update schema and access methods

### Building with IDE
1. **Import as JavaFX project** in your IDE
2. **Set JavaFX SDK path** in project settings
3. **Configure run configurations** for both client and server
4. **Set up database connection** in your IDE

## 🐛 Troubleshooting

### Common Issues

**Connection Refused Error**:
- Ensure server is running before starting client
- Check if port 6000 is available
- Verify firewall settings

**Database Connection Error**:
- Verify MySQL service is running
- Check database credentials
- Ensure database 'apd' exists

**JavaFX Module Not Found**:
- Download JavaFX SDK separately if using JDK 11+
- Add JavaFX modules to classpath
- Use `--add-modules` flag when running

**Compilation Errors**:
- Ensure all dependencies are in classpath
- Check Java version compatibility
- Verify source file encoding (UTF-8)

## 🤝 Contributing

This is a personal project, but suggestions and improvements are welcome!

## 📞 Support

For issues or questions:
1. Check the troubleshooting section
2. Review error logs in console output
3. Verify all prerequisites are met

---

**Note**: This application demonstrates Java networking, JavaFX UI development, and database integration concepts. It's designed for learning and portfolio purposes.
