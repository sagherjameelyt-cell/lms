package com.example.template_lms.ui.todo_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.template_lms.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(navController: NavController, openDrawer: () -> Unit) {
    val tasks = remember { mutableStateListOf(*todoList.toTypedArray()) }
    var showDialog by remember { mutableStateOf(false) }
    var editingTask by remember { mutableStateOf<TodoItem?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("To-Do List") },
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
                editingTask = null
                showDialog = true
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Task")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            SummaryBar(tasks)
            if (tasks.isEmpty()) {
                EmptyState("You have no tasks yet. Add your first task.")
            } else {
                LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
                    items(tasks) { task ->
                        TodoItemRow(task,
                            onToggle = { 
                                val index = tasks.indexOf(task)
                                tasks[index] = task.copy(isCompleted = !task.isCompleted)
                             },
                            onEdit = { 
                                editingTask = it
                                showDialog = true
                             },
                            onDelete = { tasks.remove(it) })
                    }
                }
            }
        }

        if (showDialog) {
            TaskDialog(
                task = editingTask,
                onDismiss = { showDialog = false },
                onSave = { 
                    if (editingTask == null) tasks.add(it)
                    else {
                        val index = tasks.indexOf(editingTask)
                        if (index != -1) tasks[index] = it
                    }
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun SummaryBar(tasks: List<TodoItem>) {
    val total = tasks.size
    val pending = tasks.count { !it.isCompleted }
    val done = tasks.count { it.isCompleted }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text("Total: $total")
        Text("Pending: $pending")
        Text("Done: $done")
    }
}

@Composable
fun TodoItemRow(
    task: TodoItem,
    onToggle: (TodoItem) -> Unit,
    onEdit: (TodoItem) -> Unit,
    onDelete: (TodoItem) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = task.isCompleted, onCheckedChange = { onToggle(task) })
            Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                Text(
                    text = task.title,
                    fontWeight = FontWeight.Bold,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null
                )
                Text(task.description,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null)
                Text(task.dueDate, color = task.priority.color, fontWeight = FontWeight.SemiBold,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null)
            }
            IconButton(onClick = { onEdit(task) }) {
                Icon(Icons.Filled.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = { onDelete(task) }) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
fun TaskDialog(
    task: TodoItem?,
    onDismiss: () -> Unit,
    onSave: (TodoItem) -> Unit
) {
    var title by remember { mutableStateOf(task?.title ?: "") }
    var description by remember { mutableStateOf(task?.description ?: "") }
    var dueDate by remember { mutableStateOf(task?.dueDate ?: "") }
    var priority by remember { mutableStateOf(task?.priority ?: Priority.MEDIUM) }

    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(if (task == null) "Add Task" else "Edit Task", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
                OutlinedTextField(value = dueDate, onValueChange = { dueDate = it }, label = { Text("Due Date") })
                // Add priority dropdown here
                Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("Cancel") }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        onSave(
                            task?.copy(title = title, description = description, dueDate = dueDate, priority = priority) 
                                ?: TodoItem(id = (todoList.maxOfOrNull { it.id } ?: 0) + 1, title = title, description = description, dueDate = dueDate, priority = priority)
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
