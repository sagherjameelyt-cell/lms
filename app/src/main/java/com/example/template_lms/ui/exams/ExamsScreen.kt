package com.example.template_lms.ui.exams

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.template_lms.BottomNavigationBar

// --- Grade Book Data ---
data class Grade(val subject: String, val grade: String, val marks: Int)
data class GradeBook(
    val semester: String,
    val gpa: Double,
    val grades: List<Grade>,
    val pdfUrl: String
)
val gradeBookData = GradeBook(
    semester = "5th Semester",
    gpa = 3.91,
    grades = listOf(
        Grade("Data Structures", "A", 85),
        Grade("Operating Systems", "A-", 80),
        Grade("Database Systems", "B+", 78),
        Grade("Software Engineering", "A", 88),
        Grade("Computer Networks", "B", 75)
    ),
    pdfUrl = ""
)

// --- Date Sheet Data ---
data class DateSheetInfo(
    val examName: String,
    val semester: String,
    val program: String,
    val uploadDate: String,
    val fileType: String = "PDF",
    val status: String
)
val dateSheetInfo = DateSheetInfo(
    examName = "Final Term Examination",
    semester = "5th Semester",
    program = "BS-IT",
    uploadDate = "20 Nov 2023",
    status = "Latest"
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamsScreen(navController: NavController, openDrawer: () -> Unit) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Grade Book", "Date Sheet")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Exams") },
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
        Column(modifier = Modifier.padding(padding)) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(text = { Text(title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index })
                }
            }
            when (selectedTab) {
                0 -> GradeBookPage()
                1 -> DateSheetPage()
            }
        }
    }
}

@Composable
fun GradeBookPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = gradeBookData.semester,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        if (gradeBookData.grades.isNotEmpty()) {
            PerformanceSummaryCard(gradeBookData.gpa)
            Spacer(modifier = Modifier.height(16.dp))
            GradesListCard(gradeBookData.grades)
            Spacer(modifier = Modifier.height(24.dp))
            GradeActionButtons()
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Results are provisional and subject to verification.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        } else {
            EmptyState("Grade book is not available yet. Please check later.")
        }
    }
}

@Composable
fun DateSheetPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "${dateSheetInfo.program} | ${dateSheetInfo.semester}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        DateSheetCard(dateSheetInfo)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Please follow the official date sheet. Any changes will be updated here.",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun PerformanceSummaryCard(gpa: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("GPA", style = MaterialTheme.typography.titleMedium)
            Text(
                text = gpa.toString(),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text("Excellent", color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun GradesListCard(grades: List<Grade>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            grades.forEach { grade ->
                GradeRow(grade)
            }
        }
    }
}

@Composable
fun GradeRow(grade: Grade) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.Star, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(grade.subject, fontWeight = FontWeight.Bold)
            Text("Marks: ${grade.marks}", fontSize = 12.sp, color = Color.Gray)
        }
        Text(grade.grade, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun GradeActionButtons() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
        ) {
            Button(onClick = { /* Handle View */ }, modifier = Modifier.weight(1f)) {
                Icon(Icons.Filled.RemoveRedEye, contentDescription = "View")
                Spacer(modifier = Modifier.width(8.dp))
                Text("View Report")
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSheetCard(info: DateSheetInfo) {
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
            Text(text = info.examName, style = MaterialTheme.typography.titleLarge)
            Text(
                text = "${info.semester} | ${info.program}",
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
                    text = "Uploaded: ${info.uploadDate}",
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
                    Text("View")
                }
                Button(onClick = { /* Handle Download */ }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.Download, contentDescription = "Download")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Download")
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
