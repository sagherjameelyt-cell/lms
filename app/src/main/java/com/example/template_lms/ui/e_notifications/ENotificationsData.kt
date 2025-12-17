package com.example.template_lms.ui.e_notifications

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class NotificationType(val icon: ImageVector) {
    ACADEMIC(Icons.Default.Book),
    EXAM(Icons.Default.Event),
    FEE(Icons.Default.CreditCard),
    SYSTEM(Icons.Default.Settings)
}

data class Notification(
    val id: Int,
    val title: String,
    val description: String,
    val longDescription: String,
    val date: String,
    val type: NotificationType,
    var isRead: Boolean = false,
    val attachmentUrl: String? = null
)

val notifications = listOf(
    Notification(
        id = 1,
        title = "Date Sheet Uploaded",
        description = "The date sheet for the final term examination has been uploaded.",
        longDescription = "The date sheet for the final term examination has been uploaded. Please review it carefully and report any clashes to the examination office by the 25th of this month.",
        date = "2024-08-15 | 10:30 AM",
        type = NotificationType.EXAM,
        isRead = false,
        attachmentUrl = "/datesheet" // Example of an internal link
    ),
    Notification(
        id = 2,
        title = "Fee Due Reminder",
        description = "Your semester fee is due on the 20th of August.",
        longDescription = "This is a reminder that your semester fee is due on the 20th of August. Late fees will be applied after this date. You can pay your fees through the student portal.",
        date = "2024-08-12 | 02:00 PM",
        type = NotificationType.FEE,
        isRead = false
    ),
    Notification(
        id = 3,
        title = "Class Schedule Updated",
        description = "The class schedule for Monday has been revised.",
        longDescription = "The class schedule for Monday, August 19th, has been revised. The 'Software Engineering' class will now be held at 11:00 AM instead of 09:00 AM. Please check the updated lecture schedule.",
        date = "2024-08-11 | 05:00 PM",
        type = NotificationType.ACADEMIC,
        isRead = true
    ),
    Notification(
        id = 4,
        title = "System Maintenance",
        description = "The student portal will be down for maintenance on Sunday.",
        longDescription = "The student portal will be unavailable from 02:00 AM to 04:00 AM on Sunday, August 18th, for scheduled maintenance. We apologize for any inconvenience.",
        date = "2024-08-10 | 09:00 AM",
        type = NotificationType.SYSTEM,
        isRead = true
    ),
    Notification(
        id = 5,
        title = "Result Published",
        description = "Mid-term exam results are now available.",
        longDescription = "The results for the mid-term examinations have been published. You can view your grade book on the student portal.",
        date = "2024-08-09 | 11:00 AM",
        type = NotificationType.EXAM,
        isRead = true,
        attachmentUrl = "/gradebook"
    )
)