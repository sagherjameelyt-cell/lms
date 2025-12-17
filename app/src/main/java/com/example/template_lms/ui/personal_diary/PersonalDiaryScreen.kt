package com.example.template_lms.ui.personal_diary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.template_lms.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDiaryScreen(navController: NavController, openDrawer: () -> Unit) {
    val entries = remember { mutableStateListOf(*diaryEntries.toTypedArray()) }
    var showDialog by remember { mutableStateOf(false) }
    var editingEntry by remember { mutableStateOf<DiaryEntry?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Personal Diary") },
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
        bottomBar = { BottomNavigationBar(navController) },
        floatingActionButton = {
            FloatingActionButton(onClick = { 
                editingEntry = null
                showDialog = true 
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Entry")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (entries.isEmpty()) {
                EmptyState("Start writing your first diary entry.")
            } else {
                LazyColumn(modifier = Modifier.padding(8.dp)) {
                    items(entries.sortedByDescending { it.date }) { entry ->
                        DiaryEntryCard(
                            entry = entry,
                            onClick = { 
                                editingEntry = entry
                                showDialog = true
                             },
                            onDelete = { entries.remove(entry) }
                        )
                    }
                }
            }
        }

        if (showDialog) {
            EntryDialog(
                entry = editingEntry,
                onDismiss = { showDialog = false },
                onSave = { 
                    if (editingEntry == null) entries.add(it)
                    else {
                        val index = entries.indexOf(editingEntry)
                        if (index != -1) entries[index] = it
                    }
                    showDialog = false
                 }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryEntryCard(entry: DiaryEntry, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        onClick = onClick,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(entry.date, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Icon(entry.mood.icon, contentDescription = "Mood")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.Lock, contentDescription = "Private")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(entry.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(entry.content, maxLines = 2, style = MaterialTheme.typography.bodyMedium)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
                IconButton(onClick = onClick) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
            }
        }
    }
}

@Composable
fun EntryDialog(entry: DiaryEntry?, onDismiss: () -> Unit, onSave: (DiaryEntry) -> Unit) {
    var title by remember { mutableStateOf(entry?.title ?: "") }
    var content by remember { mutableStateOf(entry?.content ?: "") }
    var mood by remember { mutableStateOf(entry?.mood ?: Mood.NEUTRAL) }

    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(if (entry == null) "Add Entry" else "Edit Entry", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
                OutlinedTextField(value = content, onValueChange = { content = it }, label = { Text("Content") }, modifier = Modifier.height(200.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Mood:")
                    Mood.entries.forEach { moodOption ->
                        IconButton(onClick = { mood = moodOption }) {
                            Icon(moodOption.icon, contentDescription = moodOption.name, tint = if (mood == moodOption) MaterialTheme.colorScheme.primary else Color.Gray)
                        }
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("Cancel") }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        onSave(
                            entry?.copy(title = title, content = content, mood = mood)
                                ?: DiaryEntry(id = (diaryEntries.maxOfOrNull { it.id } ?: 0) + 1, title = title, content = content, date = java.time.LocalDate.now().toString(), mood = mood)
                        )
                    }) { Text("Save") }
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
