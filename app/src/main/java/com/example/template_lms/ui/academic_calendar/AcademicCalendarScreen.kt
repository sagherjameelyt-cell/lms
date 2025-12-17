package com.example.template_lms.ui.academic_calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcademicCalendarScreen(navController: NavController, openDrawer: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Academic Calendar") },
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            AcademicCalendarPage()
        }
    }
}

@Composable
fun AcademicCalendarPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        if (academicCalendarInfo != null) {
            Text(
                text = "Session / Year: ${academicCalendarInfo.session}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )

            AcademicCalendarCard(academicCalendarInfo)

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "This calendar contains official academic dates. Any updates will be reflected here.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        } else {
            EmptyState(message = "Academic calendar not available yet. Please check back later.")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcademicCalendarCard(info: AcademicCalendarInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Article,
                contentDescription = "PDF Icon",
                modifier = Modifier.size(60.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = info.title, style = MaterialTheme.typography.titleLarge)
            Text(
                text = "Session: ${info.session}",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = "Uploaded: ${info.uploadedOn}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = "File type: PDF",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
            ) {
                Button(onClick = { /* Handle View */ }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.RemoveRedEye, contentDescription = "View")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("View Calendar")
                }
                Button(onClick = { /* Handle Download */ }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.Download, contentDescription = "Download")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Download PDF")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(onClick = { /* Handle Share */ }, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Filled.Share, contentDescription = "Share")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Share")
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

data class AcademicCalendarInfo(
    val title: String,
    val session: String,
    val pdfUrl: String,
    val uploadedOn: String
)

// This would typically come from a ViewModel or API call
val academicCalendarInfo = AcademicCalendarInfo(
    title = "Academic Calendar 2025",
    session = "Spring & Fall",
    pdfUrl = "https://server.com/academic_calendar.pdf",
    uploadedOn = "2025-01-10"
)
