package com.example.template_lms.ui.schemeofstudies

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
import androidx.navigation.NavController
import com.example.template_lms.BottomNavigationBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchemeOfStudiesScreen(navController: NavController, openDrawer: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scheme of Studies") },
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
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        SchemeOfStudiesPage(modifier = Modifier.padding(padding))
    }
}

@Composable
fun SchemeOfStudiesPage(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        if (schemeOfStudiesInfo != null) {
            Text(
                text = "${schemeOfStudiesInfo.program} | ${schemeOfStudiesInfo.session}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )

            SchemeOfStudiesCard(schemeOfStudiesInfo)

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "This scheme of studies defines the official course structure. Any revisions will be updated here.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        } else {
            EmptyState("Scheme of studies is not available yet. Please check back later.")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchemeOfStudiesCard(info: SchemeOfStudiesInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Badge(
                    containerColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Text(
                        text = info.status,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onPrimary)
                    )
                }
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.Article,
                contentDescription = "PDF Icon",
                modifier = Modifier.size(60.dp).padding(top = 8.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = info.program, style = MaterialTheme.typography.titleLarge)
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
                    text = "Uploaded: ${info.uploaded_on}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = "File type: ${info.fileType}",
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
                    Text("View Scheme")
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
