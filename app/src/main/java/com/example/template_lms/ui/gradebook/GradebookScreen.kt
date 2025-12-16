package com.example.template_lms.ui.gradebook

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.template_lms.BottomNavigationBar

data class Subject(
    val title: String,
    val code: String,
    val discipline: String,
    val credits: String,
    val icon: ImageVector
)

val subjects = listOf(
    Subject("Organic Chemistry II", "CHEM 301", "Science/Chemistry", "4 Credits (3 Lecture, 1 Lab)", Icons.Filled.Science),
    Subject("Introduction to Psychology", "PSYC 101", "Social Sciences/Psychology", "3 Credits", Icons.Filled.Group),
    Subject("Calculus III", "MATH 203", "Mathematics/Mathematics", "4 Credits", Icons.Filled.Calculate),
    Subject("World History: 1500-Present", "HIST 102", "Humanities/History", "3 Credits", Icons.Filled.Public),
    Subject("Public Speaking", "COMM 101", "Humanities/Communication", "2 Credits", Icons.Filled.RecordVoiceOver),
    Subject("Introduction to Programming", "CS 101", "Engineering/Computer Science", "4 Credits (3 Lecture, 1 Lab)", Icons.Filled.Computer),
    Subject("Principles of Economics", "ECON 201", "Social Sciences/Economics", "3 Credits", Icons.Filled.AttachMoney),
    Subject("Human Anatomy and Physiology", "BIOL 250", "Science/Biology", "4 Credits (3 Lecture, 1 Lab)", Icons.Filled.AccessibilityNew),
    Subject("Introduction to Sociology", "SOCI 101", "Social Sciences/Sociology", "3 Credits", Icons.Filled.Groups),
    Subject("Business Law", "BUS 301", "Business/Law", "3 Credits", Icons.Filled.Gavel)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradebookScreen(navController: NavController, openDrawer: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gradebook") },
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
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            item {
                Text(
                    text = "BSIT-E1 5th",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
            items(subjects) { subject ->
                SubjectCard(subject)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun SubjectCard(subject: Subject) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    subject.icon,
                    contentDescription = subject.discipline,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(subject.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                InfoRow(label = "Code:", value = subject.code)
                InfoRow(label = "Discipline:", value = subject.discipline)
                InfoRow(label = "Credits:", value = subject.credits)
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$label ",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GradebookScreenPreview() {
    MaterialTheme {
        GradebookScreen(navController = rememberNavController(), openDrawer = {})
    }
}
