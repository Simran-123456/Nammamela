# Namma Mela – Event & Cultural Management Platform

Namma Mela is a modern Android application developed to simplify the discovery, booking, and management of local cultural events, exhibitions, workshops, and community programs.

The platform connects users with nearby events through a user-friendly digital system powered by Firebase and modern Android technologies. It provides an easy way to explore events, register participation, and manage bookings digitally.

---

# Features

## Event Explorer
- Browse cultural programs and events
- Category-based event discovery
- Modern user-friendly interface
- Search and filter functionality

## User Authentication
- Secure login and signup system
- Firebase Authentication integration
- User profile management

## Event Booking System
- Book seats for events
- View booking history
- Real-time registration updates

## Smart Notifications
- Event reminders and updates
- Important announcements

## Local Storage Support
- Offline data access using Room Database
- Faster app performance

## Media & Gallery
- Event posters and images
- Promotional media support

## Organizer Management
- Add and manage events
- Update event details
- Track registrations

---

# Technologies Used

| Category | Technologies |
|----------|--------------|
| Programming Language | Kotlin |
| UI Design | XML |
| Architecture | MVVM |
| Backend Services | Firebase |
| Authentication | Firebase Authentication |
| Database | Firebase Firestore |
| Local Storage | Room Database |
| IDE | Android Studio |
| Version Control | GitHub |
| UI Components | RecyclerView, CardView |
| Design System | Material Design |

---

# System Architecture

The application follows MVVM (Model-View-ViewModel) architecture for scalable and maintainable development.

User
   ↓
Android Application UI
   ↓
ViewModel Layer
   ↓
Repository Layer
   ↓
Firebase / Room Database

---

# Module Flow

Authentication Module
        ↓
Home Module
        ↓
 ├── Event Explorer
 ├── Booking System
 ├── Notifications
 ├── Booking History
 └── User Profile

---

# Problem Statement

Many local cultural events and exhibitions lack proper digital visibility and management systems.

Users often:
- cannot discover nearby events easily,
- face manual booking difficulties,
- do not receive proper event updates,
- and struggle with offline registration systems.

Similarly, organizers face challenges in:
- promoting events digitally,
- managing registrations,
- and reaching larger audiences.

Namma Mela solves these problems by creating a centralized digital event management platform.

---

# demo:https://appetize.io/app/b_e7ydux2je56x27on25mbdzq4rm

# Objectives

- Simplify event discovery
- Digitize event booking systems
- Improve accessibility to local cultural programs
- Provide secure user authentication
- Build a scalable Android application using modern technologies

---

# Installation & Setup

## Prerequisites

- Android Studio Iguana or newer
- JDK 17
- Firebase Project

---

# Clone Repository

```bash
git clone https://github.com/Simran-123456/NammaMela.git
