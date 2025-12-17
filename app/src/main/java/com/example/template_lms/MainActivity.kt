package com.example.template_lms

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.template_lms.ui.academic_calendar.AcademicCalendarScreen
import com.example.template_lms.ui.datesheet.DatesheetScreen
import com.example.template_lms.ui.e_notifications.ENotificationsScreen
import com.example.template_lms.ui.exams.ExamsScreen
import com.example.template_lms.ui.gradebook.GradebookScreen
import com.example.template_lms.ui.home.HomeScreen
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
                DrawerContent(navController = navController, onLogout = {
                    scope.launch { drawerState.close() }
                    navController.navigate("logout")
                })
            }
        }
    ) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") { HomeScreen(navController) }
            composable("exams") { ExamsScreen(navController, openDrawer) }
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
