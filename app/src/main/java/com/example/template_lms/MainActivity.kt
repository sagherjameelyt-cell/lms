package com.example.template_lms

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.template_lms.ui.login.LoginActivity
import com.example.template_lms.ui.theme.TemplateLMSTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemplateLMSTheme {
                StudentPortalScreen()
            }
        }
    }
}

// --- Data Structures ---
data class DrawerItem(val icon: ImageVector, val text: String)
data class DashboardItem(val label: String, val icon: ImageVector, val count: Int? = null)

// --- Data Sources ---
val portalItems = listOf(
    DrawerItem(Icons.Filled.Dashboard, "Dashboard"),
    DrawerItem(Icons.Filled.Schedule, "Lecture Schedule"),
    DrawerItem(Icons.Filled.Star, "Grade Book"),
    DrawerItem(Icons.Filled.DateRange, "Datesheet"),
    DrawerItem(Icons.Filled.CalendarToday, "Academic Calendar"),
    DrawerItem(Icons.Filled.MiscellaneousServices, "Student Services"),
    DrawerItem(Icons.Filled.MenuBook, "Scheme of Study"),
    DrawerItem(Icons.Filled.Notifications, "E-Notifications"),
    DrawerItem(Icons.Filled.Checklist, "Todo List"),
    DrawerItem(Icons.Filled.Book, "Personal Diary"),
    DrawerItem(Icons.Filled.NotificationsActive, "Notice Board")
)

val schoolUpdates = listOf(
    DashboardItem("News", Icons.Filled.Article, 3),
    DashboardItem("Events", Icons.Filled.Email, 5),
    DashboardItem("Bulletin", Icons.Filled.Campaign, 11)
)

val academics = listOf(
    DashboardItem("Assignment", Icons.Filled.Assignment, 3),
    DashboardItem("Events", Icons.Filled.LocalCafe, 5),
    DashboardItem("Attendance", Icons.Filled.Person, 3),
    DashboardItem("Fee", Icons.Filled.Payment, 5)
)

val communication = listOf(
    DashboardItem("Chat", Icons.Filled.Chat, 3),
    DashboardItem("SMS", Icons.Filled.Sms, 5)
)

val edisappToday = listOf(
    DashboardItem("Edu-news", Icons.Filled.Newspaper),
    DashboardItem("Quote", Icons.Filled.FormatQuote),
    DashboardItem("Thought", Icons.Filled.Psychology),
    DashboardItem("City", Icons.Filled.LocationCity),
    DashboardItem("Word", Icons.Filled.Spellcheck)
)


// --- Main Screen Structure ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentPortalScreen() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent { // onLogout lambda
                val intent = Intent(context, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Home") },
                    navigationIcon = { IconButton(onClick = { scope.launch { drawerState.open() } }) { Icon(Icons.Default.Menu, "Menu") } },
                    actions = {
                        IconButton(onClick = { /* TODO */ }) { Icon(Icons.Default.Notifications, "Notifications") }
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "User 1",
                            modifier = Modifier.size(32.dp).clip(CircleShape).padding(end = 4.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "User 2",
                            modifier = Modifier.size(32.dp).clip(CircleShape).padding(end = 8.dp)
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF3F7FF))
                )
            },
            bottomBar = { BottomNavBar() },
        ) { paddingValues ->
            HomePageContent(paddingValues = paddingValues)
        }
    }
}

// --- Home Page Sections ---
@Composable
fun HomePageContent(paddingValues: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color(0xFFF3F7FF))
    ) {
        item { StudentInfoCard() }
        item { DashboardSection("School Updates", schoolUpdates) }
        item { DashboardSection("Academics", academics) }
        item { DashboardSection("Communication", communication) }
        item { DashboardSection("Edisapp Today", edisappToday) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
fun StudentInfoCard() {
    val attendanceProgress = 0.59f
    val feeProgress = 0.88f
    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth()) {
        Card(
            modifier = Modifier.padding(top = 32.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF4A80F0)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                Text("Matthew McConaughey", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                Text("IX A", color = Color.White.copy(alpha = 0.8f), fontSize = 16.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))

                // Progress Section
                Column {
                    Row(Modifier.fillMaxWidth()) {
                        Text("Attendance", color = Color.White.copy(alpha = 0.8f))
                        Spacer(Modifier.width(4.dp))
                        Text("${(attendanceProgress * 100).toInt()}%", color = Color.White, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.weight(1f))
                        Text("Fee", color = Color.White.copy(alpha = 0.8f))
                        Spacer(Modifier.width(4.dp))
                        Text("${(feeProgress * 100).toInt()}%", color = Color.White, fontWeight = FontWeight.SemiBold)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = feeProgress, // This progress bar shows the fee progress
                        modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                        color = Color.White,
                        backgroundColor = Color.White.copy(alpha = 0.3f)
                    )
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
fun DashboardSection(title: String, items: List<DashboardItem>) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(items) { item ->
                DashboardItemCard(item)
            }
        }
    }
}

// --- Reusable Cards & Components ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardItemCard(item: DashboardItem) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.width(110.dp).clickable(remember { MutableInteractionSource() }, onClick = {})
    ) {
        Box(modifier = Modifier.padding(12.dp)) {
            if (item.count != null) {
                Text(
                    text = item.count.toString(),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.TopStart)
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(item.icon, contentDescription = item.label, tint = Color(0xFF4A80F0), modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(item.label, color = Color.Black.copy(alpha = 0.7f), fontWeight = FontWeight.SemiBold, fontSize = 14.sp, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun BottomNavBar() {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(selected = true, onClick = { }, icon = { Icon(Icons.Default.Home, "Home")}, label = { Text("Home") })
        NavigationBarItem(selected = false, onClick = { }, icon = { Icon(Icons.Default.Assignment, "Assignment")}, label = { Text("Assignment") })
        NavigationBarItem(selected = false, onClick = { }, icon = { Icon(Icons.Default.MenuBook, "Homework")}, label = { Text("Homework") })
        NavigationBarItem(selected = false, onClick = { }, icon = { Icon(Icons.Default.Payment, "Pay Fee")}, label = { Text("Pay Fee") })
    }
}

// --- Navigation Drawer ---
@Composable
fun DrawerContent(onLogout: () -> Unit) {
    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFEFEF))
                .systemBarsPadding()
        ) {
            // Drawer Header
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Student Portal", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Drawer Items
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(portalItems) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = { /* TODO: Handle navigation */ })
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(item.icon, contentDescription = null, tint = Color.Gray)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(item.text, color = Color.Gray, fontSize = 16.sp)
                    }
                }
            }

            // Logout Button
            Box(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = onLogout,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A80F0))
                ) {
                    Text("Logout", color = Color.White)
                }
            }
        }
    }
}
