package com.example.template_lms.ui.e_notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.template_lms.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ENotificationsScreen(navController: NavController, openDrawer: () -> Unit) {
    var selectedNotification by remember { mutableStateOf<Notification?>(null) }

    if (selectedNotification == null) {
        NotificationListPage(navController, openDrawer, onNotificationClick = { 
            it.isRead = true
            selectedNotification = it 
        })
    } else {
        NotificationDetailPage(notification = selectedNotification!!, onBack = { selectedNotification = null })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationListPage(
    navController: NavController,
    openDrawer: () -> Unit,
    onNotificationClick: (Notification) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("All", "Academic", "Exams", "Fees", "System")
    val notificationsList = remember { mutableStateListOf(*notifications.toTypedArray()) }

    val unreadCount = notificationsList.count { !it.isRead }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("E-Notifications") },
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    TextButton(onClick = { notificationsList.forEach { it.isRead = true } }) {
                        Icon(Icons.Default.DoneAll, contentDescription = "Mark all as read", tint = MaterialTheme.colorScheme.onPrimary)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Mark all as read", color = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { 
                            if (title == "All" && unreadCount > 0) {
                                BadgedBox(badge = { Badge { Text("$unreadCount") } }) {
                                    Text(title)
                                }
                            } else {
                                Text(title)
                            }
                        },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
            NotificationList(selectedTab, onNotificationClick, notificationsList)
        }
    }
}

@Composable
fun NotificationList(selectedTab: Int, onNotificationClick: (Notification) -> Unit, notificationsList: List<Notification>) {
    val filteredNotifications = when (selectedTab) {
        1 -> notificationsList.filter { it.type == NotificationType.ACADEMIC }
        2 -> notificationsList.filter { it.type == NotificationType.EXAM }
        3 -> notificationsList.filter { it.type == NotificationType.FEE }
        4 -> notificationsList.filter { it.type == NotificationType.SYSTEM }
        else -> notificationsList
    }

    if (filteredNotifications.isEmpty()) {
        EmptyState("You have no new notifications.")
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredNotifications) { notification ->
                NotificationItem(notification, onNotificationClick)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationItem(notification: Notification, onNotificationClick: (Notification) -> Unit) {
    val cardColors = if (notification.isRead) {
        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    } else {
        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        onClick = { onNotificationClick(notification) },
        colors = cardColors
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = notification.type.icon,
                contentDescription = notification.type.name,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(notification.title, fontWeight = FontWeight.Bold)
                Text(notification.description, style = MaterialTheme.typography.bodyMedium)
                Text(notification.date, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            if (!notification.isRead) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationDetailPage(notification: Notification, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(notification.title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(notification.longDescription, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
            if (notification.attachmentUrl != null) {
                Button(onClick = { /* Handle attachment view */ }) {
                    Text("View Attachment")
                }
            }
        }
    }
}


@Composable
fun EmptyState(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}
