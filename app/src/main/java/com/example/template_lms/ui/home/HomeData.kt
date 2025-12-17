package com.example.template_lms.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Subject(
    val name: String,
    val code: String,
    val teacher: String,
    val credits: Int,
    val color: Color
)

data class QuickAction(
    val title: String,
    val icon: ImageVector,
    val route: String
)

data class Alert(
    val title: String,
    val description: String,
    val date: String
)

val subjects = listOf(
    Subject("Data Structures", "CS-301", "Dr. Smith", 3, Color(0xFF6200EE)),
    Subject("Algorithms", "CS-302", "Prof. Jones", 3, Color(0xFF03DAC5)),
    Subject("Database Systems", "CS-303", "Dr. Williams", 3, Color(0xFF3700B3)),
    Subject("Operating Systems", "CS-304", "Prof. Brown", 3, Color(0xFF018786)),
    Subject("Computer Networks", "CS-305", "Dr. Davis", 3, Color(0xFFB00020)),
    Subject("Software Engineering", "CS-306", "Prof. Miller", 3, Color(0xFFFF0266))
)

val quickActions = listOf(
    QuickAction("Date Sheet", Icons.Default.DateRange, "datesheet"),
    QuickAction("Grade Book", Icons.Default.Assessment, "gradebook"),
    QuickAction("Calendar", Icons.Default.CalendarMonth, "academic_calendar"),
    QuickAction("Scheme", Icons.AutoMirrored.Filled.Article, "scheme_of_studies"),
    QuickAction("Notifications", Icons.Default.Notifications, "e_notifications"),
    QuickAction("Services", Icons.Default.Payment, "student_services")
)

val alerts = listOf(
    Alert("Exam Notification", "Final term exams start next week.", "Aug 15"),
    Alert("Assignment Due", "Submit your CS-301 assignment by tomorrow.", "Aug 18"),
    Alert("Fee Reminder", "Last day to pay fees without a fine.", "Aug 20")
)

val productivityWidgets = listOf(
    QuickAction("To-Do List", Icons.Default.Checklist, "todo_list"),
    QuickAction("Personal Diary", Icons.Default.Book, "personal_diary"),
    QuickAction("Assignments", Icons.AutoMirrored.Filled.Assignment, "assignment")
)
