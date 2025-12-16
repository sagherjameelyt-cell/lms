package com.example.template_lms.ui.lecture_schedule

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.template_lms.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LectureScheduleScreen(navController: NavController, openDrawer: () -> Unit) {
    val scheduleItems = listOf(
        "CS-BSIT 1(M)",
        "CS-BSIT 3(M)",
        "CS-BSIT 5(M)",
        "CS-BSIT 7A(M)",
        "CS-BSIT 7B(M)",
        "CS-BSIT 1A(SS)",
        "CS-BSIT 1B(SS)",
        "CS-BSIT 1C(SS)",
        "CS-BSIT 3A(SS)",
        "CS-BSIT 3B(SS)",
        "CS-BSIT 3C(SS)",
        "CS-BSIT 5A(SS)",
        "CS-BSIT 5B(SS)",
        "CS-BSIT 5C(SS)",
        "CS-BSIT 7A(SS)",
        "CS-BSIT 7B(SS)"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lecture Schedule") },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(scheduleItems) { item ->
                ScheduleCard(text = item)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleCard(text: String) {
    val context = LocalContext.current
    Card(
        onClick = {
            context.startActivity(Intent(context, TimetableActivity::class.java))
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
