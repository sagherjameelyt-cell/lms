package com.example.template_lms.ui.todo_list

import androidx.compose.ui.graphics.Color

enum class Priority(val color: Color) {
    HIGH(Color.Red),
    MEDIUM(Color(0xFFFFA500)), // Orange
    LOW(Color.Green)
}

data class TodoItem(
    val id: Int,
    var title: String,
    var description: String,
    var dueDate: String,
    var priority: Priority,
    var isCompleted: Boolean = false,
    val category: String? = null
)

val todoList = mutableListOf(
    TodoItem(
        id = 1,
        title = "Submit Physics Assignment",
        description = "Complete and upload the assignment on the portal.",
        dueDate = "2024-08-20 | 11:59 PM",
        priority = Priority.HIGH,
        category = "Assignment"
    ),
    TodoItem(
        id = 2,
        title = "Prepare for Chemistry Exam",
        description = "Revise chapters 4 and 5.",
        dueDate = "2024-08-22 | 09:00 AM",
        priority = Priority.HIGH,
        category = "Exam"
    ),
    TodoItem(
        id = 3,
        title = "Pay Semester Fee",
        description = "Last date for fee payment without fine.",
        dueDate = "2024-08-25 | 05:00 PM",
        priority = Priority.MEDIUM,
        category = "Fee"
    ),
    TodoItem(
        id = 4,
        title = "Group Meeting for Project",
        description = "Discuss project milestones with the team.",
        dueDate = "2024-08-19 | 03:00 PM",
        priority = Priority.MEDIUM,
        isCompleted = true
    ),
    TodoItem(
        id = 5,
        title = "Read Chapter 3 of History",
        description = "Prepare for the upcoming quiz.",
        dueDate = "2024-08-21 | 10:00 PM",
        priority = Priority.LOW
    )
)
