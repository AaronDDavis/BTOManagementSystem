# BTO Management System

A comprehensive, command-line based application for managing Singapore's Build-To-Order (BTO) housing projects. This system is designed to serve multiple user roles including Applicants, HDB Officers, and HDB Managers, providing a centralized platform for all BTO-related activities.

This project was individually developed as a requirement for the SC2002 Object-Oriented Design & Programming course at Nanyang Technological University.

<br>

---

## Core Features

The system is built around three main user roles, each with a distinct set of capabilities:

#### **Applicant**
* **View & Filter Projects**: Browse BTO projects open to their user group (e.g., based on marital status and age).
* **Apply for BTO**: Submit an application for an eligible project.
* **Manage Applications**: View application status and request withdrawal.
* **Manage Enquiries**: Create, view, edit, and delete enquiries for projects.

#### **HDB Officer**
* *(Inherits all Applicant capabilities)* 
* **Join Project Teams**: Register to be part of a BTO project's management team.
* **Manage Enquiries**: View and reply to enquiries for projects they handle.
* **Flat Selection**: Process successful applications by booking flats for applicants.
* **Generate Receipts**: Create official receipts for applicants who have successfully booked a flat.

#### **HDB Manager**
* **Project Lifecycle Management**: Create, edit, and delete BTO project listings.
* **Control Project Visibility**: Toggle project visibility for applicants.
* **Staff Management**: Approve or reject HDB Officer registrations for projects.
* **Application Oversight**: Approve or reject BTO applications and withdrawal requests.
* **Report Generation**: Generate filterable reports on applicants and their flat choices.
* **Global Enquiry View**: View and reply to enquiries across ALL projects.

<br>

---

## Architectural Highlights

This project was carefully structured with modern software design principles and patterns in mind, emphasizing maintainability, scalability, and separation of concerns.

#### **Layered Architecture**
The application is designed using a classic multi-layered architecture, which decouples different parts of the system for higher cohesion and lower coupling.

* **Presentation Layer (`userinterface`, `display`)**: Manages all Command-Line Interface (CLI) interactions, user input, and formatted output. It is responsible for *how* data is presented to the user.
* **Business Logic Layer (`userctrl`, `application`, `project`, `enquiry`)**: Contains the core application logic, business rules, and workflows. This layer orchestrates the operations and enforces system constraints (e.g., checking if an applicant is eligible to apply for a project).
* **Data Access Layer (`database`, `databasemgr`, `reader`, `writer`)**: Handles all data persistence. It abstracts the data storage mechanism (in this case, text files) from the rest of the application, allowing for easier migration to a database in the future.
* **Domain Model (`user`, `project`, `application`, `enquiry` classes)**: Represents the core entities of the system, encapsulating both data and behavior.

This separation ensures that changes in one layer (e.g., switching the UI from CLI to a GUI) have minimal impact on the others.

#### **SOLID Principles in Practice**
* **S** - **Single Responsibility Principle (SRP)**: Each class has a focused role. For example, `ProjectReader` is solely responsible for reading project data from a file, while `ProjectDisplayer` is only concerned with formatting project details for the console.
* **O** - **Open/Closed Principle (OCP)**: The system is open to extension but closed for modification. New application types (e.g., `BTOApplication`, `WithdrawalApplication`) can be added by extending the abstract `Application` class without altering the core logic that processes applications.
* **L** - **Liskov Substitution Principle (LSP)**: Subclasses are fully substitutable for their base classes. An `HDBOfficer` object can be used wherever an `Applicant` is expected, as it inherits and extends its functionality.
* **I** - **Interface Segregation Principle (ISP)**: The use of lightweight, role-specific interfaces like `IDatabase<T>`, `IReader<T>`, and `IDisplayer<T>` ensures that classes do not depend on methods they don't use.
* **D** - **Dependency Inversion Principle (DIP)**: High-level modules depend on abstractions (interfaces) rather than concrete implementations. For instance, UI and control layers interact with the `IDatabase<T>` interface, not the concrete `Database<T>` class, making the storage mechanism swappable.

#### **Design Patterns**
* **Factory Pattern**: The `ApplicationMgr` and `EnquiryMgr` classes act as factories. They encapsulate the logic for creating different types of `Application` and `Enquiry` objects, centralizing instantiation rules and hiding complexity from the client code.
* **Template Method Pattern**: The abstract `ItemDisplayer<T>` class defines a template for displaying a list of items (`display(List<T>)`) but allows subclasses (`ProjectDisplayer`, `EnquiryDisplayer`) to define the specific rendering logic for a single item by overriding the abstract `display(T)` method.
* **Strategy Pattern (Implicit)**: The `ItemDisplayer<T>` hierarchy also functions as a Strategy pattern. The algorithm for displaying items can be changed at runtime by using a different concrete `ItemDisplayer` subclass, effectively changing the display strategy.

<br>

---

## Tech Stack

* **Language**: Java (JDK) 
* **IDE**: Developed using Eclipse
<!--* **Version Control**: Git & GitHub -->

<br>

---

## Setup and Usage

Follow these steps to get the application running on your local machine.

1.  **Clone the Repository**
    ```bash
    git clone [https://github.com/AaronDDavis/BTOManagementSystem.git](https://github.com/AaronDDavis/BTOManagementSystem.git)
    cd BTOManagementSystem
    ```

2.  **Compile the Code**
    Navigate to the `src` directory and compile all Java files.
    ```bash
    cd src
    javac main/BTOManagementSystem.java -d ../bin
    ```
    *(Note: This command assumes all source files are correctly placed within their package directories inside `src`)*

3.  **Run the Application**
    From the `bin` directory, execute the main class.
    ```bash
    cd ../bin
    java main.BTOManagementSystem
    ```

4.  **Login Credentials**
    The system is initialized with a list of users from the data files.
    * **User ID**: NRIC (e.g., S1234567A) 
    * **Default Password**: `password` 

<br>

---

## Project Structure

The source code is organized into logical packages to maintain a clean and scalable structure:

src  
├── **application/** --- Application classes (BTO, Withdrawal, etc.)  
├── **database/** ------Generic in-memory database implementation  
├── **databasemgr/** --Managers for database operations  
├── **display/** ---------Classes for formatting and displaying items  
├── **enquiry/** --------Enquiry entity and manager  
├── **main/** -----------Main application entry point  
├── **misc/** -----------Utility classes (ID creators, type checkers)  
├── **project/** ---------Project entity and manager  
├── **reader/** ----------Classes for reading data from files  
├── **user/** ------------User entities (Applicant, Officer, Manager)  
├── **userctrl/** ---------Business logic controllers for each user type  
├── **userinterface/** ---CLI-based user interfaces for each role  
└── **writer/** -----------Classes for writing data to files
