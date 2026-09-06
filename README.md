# St Mary's Digital Library System

A Java-based library management system built for a fictional university library, designed to replace manual book-tracking processes with a persistent, database-backed application.

## Overview
St Mary's University Library needed a way to manage book inventory, member records, and borrowing transactions without relying on manual, error-prone processes. This project implements a full object-oriented solution in Java, using SQLite for persistent storage and a GUI for day-to-day staff use.

## Features
- **Book management** — add, search, update, and delete books (title, author, category, availability status)
- **Member management** — register and maintain student/staff borrowing records
- **Borrowing transactions** — record loans, due dates, and returns; track status (Borrowed, Returned, Overdue)
- **Persistent SQLite database** — all data stored and retrieved via SQLite JDBC, with CRUD operations acting directly on the database
- **Graphical user interface** (Swing/JavaFX — *fill in which one you used*) — dashboard view with tabs/navigation for Books, Members, and Borrowing Records, plus forms and confirmation dialogues for key actions
- **Input validation & error handling** — numeric ID checks, email format validation, graceful handling of invalid input and database connection failures, with clear confirmation/error messages
- **Data tables** — sortable, filterable views by category, author, or member, with search functionality

## Tech Stack
Java (JDK 22+) · SQLite · SQLite JDBC · Java Swing · Java Collections Framework
