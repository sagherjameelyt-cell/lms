package com.example.template_lms

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.template_lms.ui.academic_calendar.AcademicCalendarScreen
import com.example.template_lms.ui.datesheet.DatesheetScreen
import com.example.template_lms.ui.e_notifications.ENotificationsScreen
import com.example.template_lms.ui.gradebook.GradebookScreen
import com.example.template_lms.ui.lecture_schedule.LectureScheduleScreen
import com.example.template_lms.ui.logout.LogoutScreen
import com.example.template_lms.ui.personal_diary.PersonalDiaryScreen
import com.example.template_lms.ui.schemeofstudies.SchemeOfStudiesScreen
import com.example.template_lms.ui.student_services.StudentServicesScreen
import com.example.template_lms.ui.theme.TemplateLMSTheme
import com.example.template_lms.ui.todo_list.TodoListScreen
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemplateLMSTheme {
                AppNavigation()
            }
        }
    }
}

// --- Data classes & Val definitions ---

data class DashboardItem(val label: String, val icon: ImageVector, val count: Int? = null)

val schoolUpdates = listOf(
    DashboardItem("News", Icons.AutoMirrored.Filled.Article, 3),
    DashboardItem("Events", Icons.Filled.Email, 5),
    DashboardItem("Bulletin", Icons.Filled.Campaign, 11)
)

val academics = listOf(
    DashboardItem("Assignment", Icons.AutoMirrored.Filled.Assignment, 3),
    DashboardItem("Events", Icons.Filled.LocalCafe, 5),
    DashboardItem("Attendance", Icons.Filled.Person, 3),
    DashboardItem("Fee", Icons.Filled.Payment, 5)
)

val communication = listOf(
    DashboardItem("Chat", Icons.AutoMirrored.Filled.Chat, 3),
    DashboardItem("SMS", Icons.Filled.Sms, 5)
)

val edisappToday = listOf(
    DashboardItem("Edu-news", Icons.Filled.Newspaper),
    DashboardItem("Quote", Icons.Filled.FormatQuote),
    DashboardItem("Thought", Icons.Filled.Psychology),
    DashboardItem("City", Icons.Filled.LocationCity),
    DashboardItem("Word", Icons.Filled.Spellcheck)
)

// --- Composable Functions ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val openDrawer: () -> Unit = { scope.launch { drawerState.open() } }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(modifier = Modifier.fillMaxWidth(0.8f)) {
                DrawerContent(navController, drawerItems) { 
                    scope.launch { drawerState.close() }
                    navController.navigate("logout")
                }
            }
        }
    ) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") { HomeScreen(navController, openDrawer) }
            composable("lecture_schedule") { LectureScheduleScreen(navController, openDrawer) }
            composable("gradebook") { GradebookScreen(navController, openDrawer) }
            composable("datesheet") { DatesheetScreen(navController, openDrawer) }
            composable("academic_calendar") { AcademicCalendarScreen(navController, openDrawer) }
            composable("scheme_of_studies") { SchemeOfStudiesScreen(navController, openDrawer) }
            composable("student_services") { StudentServicesScreen(navController, openDrawer) }
            composable("e_notifications") { ENotificationsScreen(navController, openDrawer) }
            composable("todo_list") { TodoListScreen(navController, openDrawer) }
            composable("personal_diary") { PersonalDiaryScreen(navController, openDrawer) }
            composable("logout") { LogoutScreen() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, openDrawer: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle notification click */ }) {
                        Icon(Icons.Filled.Notifications, contentDescription = "Notifications")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            item { ProfileSection() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { DashboardGrid("School Updates", schoolUpdates) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { DashboardGrid("Academics", academics) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { DashboardGrid("Communication", communication) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { DashboardGrid("Edisapp Today", edisappToday) }
        }
    }
}

@Composable
fun ProfileSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Profile Picture",
                modifier = Modifier.size(64.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text("notyourJIMMY", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("BSIT(5-Sem) | Roll No: 44", fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = { 0.75f },
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}

@Composable
fun DashboardGrid(title: String, items: List<DashboardItem>) {
    Column {
        Text(title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            items.forEach { item ->
                DashboardCard(item)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardCard(item: DashboardItem) {
    Card(
        onClick = { /* Handle card click */ },
        modifier = Modifier.padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(item.icon, contentDescription = item.label, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(item.label, fontSize = 12.sp)
            if (item.count != null) {
                Text(item.count.toString(), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}
