package com.example.template_lms.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item { Header() }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { SubjectsGrid(navController) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { QuickActionsRow(navController) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { AlertsList() }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { ProductivityWidgets(navController) }
    }
}

@Composable
fun Header() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text("Welcome back, Student", style = MaterialTheme.typography.titleLarge)
            Text("BS-IT | 5th Semester", style = MaterialTheme.typography.bodyMedium)
        }
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
fun SubjectsGrid(navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.height(300.dp) // Adjust height as needed
    ) {
        items(subjects) { subject ->
            SubjectCard(subject, navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectCard(subject: Subject, navController: NavController) {
    Card(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = { /* Navigate to subject detail */ }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(modifier = Modifier.fillMaxWidth().height(4.dp).background(subject.color))
            Spacer(modifier = Modifier.height(8.dp))
            Text(subject.name, fontWeight = FontWeight.Bold)
            Text(subject.code)
            Text("${subject.credits} Cr")
        }
    }
}

@Composable
fun QuickActionsRow(navController: NavController) {
    LazyRow {
        items(quickActions) { action ->
            QuickActionCard(action, navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickActionCard(action: QuickAction, navController: NavController) {
    Card(
        modifier = Modifier.padding(8.dp).width(120.dp),
        onClick = { navController.navigate(action.route) }
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(action.icon, contentDescription = action.title)
            Spacer(modifier = Modifier.height(8.dp))
            Text(action.title, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
fun AlertsList() {
    Column {
        Text("Important Alerts", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        alerts.forEach { alert ->
            AlertCard(alert)
        }
    }
}

@Composable
fun AlertCard(alert: Alert) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(alert.title, fontWeight = FontWeight.Bold)
                Text(alert.description)
            }
            Text(alert.date)
        }
    }
}

@Composable
fun ProductivityWidgets(navController: NavController) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        productivityWidgets.forEach { widget ->
            ProductivityWidget(widget, navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductivityWidget(widget: QuickAction, navController: NavController) {
    Card(
        modifier = Modifier.padding(8.dp).width(120.dp),
        onClick = { navController.navigate(widget.route) }
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(widget.icon, contentDescription = widget.title)
            Spacer(modifier = Modifier.height(8.dp))
            Text(widget.title, style = MaterialTheme.typography.labelSmall)
        }
    }
}
