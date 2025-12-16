package com.example.template_lms.ui.lecture_schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class TimetableEntry(
    val day: String,
    val timeSlot: Int,
    val subject: String,
    val room: String,
    val teacher: String,
    val duration: Int = 1
)

// Hardcoded data for CS-BSIT 1B SS
val timeTableData = listOf(
    TimetableEntry("Mo", 7, "Application of I.C.T", "4.G.4 (CS Lab)", "Kiran Asghar"),
    TimetableEntry("Mo", 8, "Islamic Studies", "4.1.18", "Sarah Saeed"),
    TimetableEntry("Mo", 9, "Zero-Math-I", "4.G.9", "Ghulam Murtaza"),
    TimetableEntry("Mo", 10, "Functional English", "3.G.8", "Riaz Ahmad"),
    TimetableEntry("Mo", 12, "Zero-Math-I", "3.G.7", "Ghulam Murtaza"),

    TimetableEntry("Tu", 7, "Application of I.C.T", "4.G.5 (CS Lab)", "Kiran Asghar"),
    TimetableEntry("Tu", 8, "Programming Fundamentals", "3.G.5", "Faiga Iram"),
    TimetableEntry("Tu", 9, "Applied Physics", "4.G.8", "Dr. Uzma"),
    TimetableEntry("Tu", 10, "Discrete Structures (CR 1)", "3.1.3", "Muhammad Imran"),
    TimetableEntry("Tu", 11, "Functional English", "3.G.4", "Riaz Ahmad"),

    TimetableEntry("We", 7, "Application of I.C.T", "4.1.4", "Kiran Asghar"),
    TimetableEntry("We", 8, "Islamic Studies", "4.1.18", "Sarah Saeed"),
    TimetableEntry("We", 9, "Seminar Hall 2", "Seminar Hall 2", "Ghulam Murtaza"),
    TimetableEntry("We", 10, "Programming Fundamentals", "4.1.6", "Dr. Uzma"),
    TimetableEntry("We", 11, "Programming Fundamentals Lab", "3.G.7", "Faiga Iram"),

    TimetableEntry("Th", 7, "Applied Physics", "Seminar Hall 2", "Dr. Uzma"),
    TimetableEntry("Th", 8, "Programming Fundamentals", "4.G.20 (CS Lab)", "Faiga Iram"),
    TimetableEntry("Th", 9, "Discrete Structures (CR 1)", "4.G.16", "Muhammad Imran"),
    TimetableEntry("Th", 10, "Pakistan Studies", "4.1.18", "Maria Sarfraz"),
    TimetableEntry("Th", 12, "Seminar Hall 2", "Seminar Hall 2", "Faiga Iram"),

    TimetableEntry("Fr", 7, "Understanding of Holy Quran", "3.G.7", "Hafiz Irfan Sohail"),
    TimetableEntry("Fr", 8, "Discrete Structures (CR 1)", "3.G.9", "Muhammad Imran"),
    TimetableEntry("Fr", 9, "Pakistan Studies", "3.G.7", "Maria Sarfraz"),
    TimetableEntry("Fr", 10, "Programming Fundamentals", "4.1.6", "Faiga Iram"),
    TimetableEntry("Fr", 11, "Functional English", "4.1.3 (Court Room)", "Riaz Ahmad"),
    TimetableEntry("Fr", 12, "Programming Fundamentals Lab", "Seminar Hall 2", "Faiga Iram")
)

val daysMap = mapOf(
    "Mo" to "Monday",
    "Tu" to "Tuesday",
    "We" to "Wednesday",
    "Th" to "Thursday",
    "Fr" to "Friday"
)

val timeSlotMap = mapOf(
    1 to "8-9 AM", 2 to "9-10 AM", 3 to "10-11 AM", 4 to "11-12 PM",
    5 to "12-1 PM", 6 to "1-2 PM", 7 to "2-3 PM", 8 to "3-4 PM",
    9 to "4-5 PM", 10 to "5-6 PM", 11 to "6-7 PM", 12 to "7-8 PM"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetableScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CS-BSIT 1B SS - Fall 2025") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        TabbedTimetable(modifier = Modifier.padding(padding), data = timeTableData)
    }
}

@Composable
fun TabbedTimetable(modifier: Modifier = Modifier, data: List<TimetableEntry>) {
    val groupedData = data.groupBy { it.day }
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = daysMap.keys.toList()

    Column(modifier = modifier) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabTitles.forEachIndexed { index, dayKey ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(dayKey) }
                )
            }
        }

        val selectedDayKey = tabTitles[selectedTabIndex]
        val entriesForSelectedDay = groupedData[selectedDayKey] ?: emptyList()

        if (entriesForSelectedDay.isNotEmpty()) {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(entriesForSelectedDay) { entry ->
                    LectureInfoCard(entry = entry)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("No classes today")
            }
        }
    }
}

@Composable
fun LectureInfoCard(entry: TimetableEntry) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.width(80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = timeSlotMap[entry.timeSlot] ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = entry.subject, style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp), fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Teacher: ${entry.teacher}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Room: ${entry.room}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
        }
    }
}
