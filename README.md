# Swiftech

## Overview

The **Swiftech** application is a mobile system developed using **Jetpack Compose Material 3**. It
allows students to evaluate their courses and instructors digitally, removing the need for
paper-based evaluation forms. The system is currently offline, designed as a prototype for a future
online version that will sync data across devices using a cloud database.

## Features

### Authentication

- **Login/Register** (Only the admin can register users)

### User Roles & Access

#### Admin

- Can manage:
    - **Students** (Create, Read, Update, Delete)
    - **Teachers** (Create, Read, Update, Delete)
    - **Courses** (Create, Read, Update, Delete)
    - **Evaluation Forms** (Create, Read, Update, Delete)

#### Teacher

- Can view:
    - **Their Courses**
    - **Evaluation Forms** (Read-only, cannot modify responses)

#### Student

- Can view:
    - **Their Courses** (With assigned teachers for evaluation)
    - **Evaluation Forms** (Fill out for assigned teachers)

## Data Flow

1. **Admin registers users** (Students & Teachers)
2. **Admin creates and assigns courses** to teachers
3. **Users log in** to access their respective dashboards
4. **Students evaluate their teachers** based on assigned courses
5. **Teachers view evaluation results** (Read-only)
6. **Admin oversees the entire system** and manages users, courses, & evaluations

## Entities & Relationships

### **User** (Abstract class/interface)

- `id: String`
- `name: String`
- `email: String`
- `password: String`
- `role: Enum (Admin, Teacher, Student)`

### **Admin** (Extends User)

- Can create, update, and delete students, teachers, courses, and evaluation forms

### **Teacher** (Extends User)

- `courses: List<Course>`
- Can view assigned courses and evaluations

### **Student** (Extends User)

- `courses: List<Course>`
- Can submit evaluation forms

### **Course**

- `id: String`
- `name: String`
- `teacher: Teacher`
- `students: List<Student>`

### **EvaluationForm**

- `id: String`
- `course: Course`
- `teacher: Teacher`
- `student: Student`
- `responses: Map<String, String>` (Question -> Answer pairs)

## Future Plans

- Implement **online database** for real-time synchronization
- Add **data analytics & reports** for evaluation results
- Enable **multi-device access** via cloud sync

## Installation & Setup

1. Clone the repository:
   ```sh
   git clone https://github.com/ralphmarondev/Swiftech.git
   ```
2. Open the project in **Android Studio**
3. Run the project on an **Android emulator or device**

## Tech Stack

- **Jetpack Compose Material 3** (UI)
- **Room Database** (Local storage)
- **Koin** (Dependency Injection)
- **MVVM Architecture** (Separation of concerns)
- **Kotlin Coroutines & Flow** (Asynchronous programming)
