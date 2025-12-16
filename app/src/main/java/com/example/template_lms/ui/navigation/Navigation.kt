package com.example.template_lms.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.template_lms.HomeScreen
import com.example.template_lms.ui.lecture_schedule.LectureScheduleScreen
import com.example.template_lms.ui.schemeofstudies.SchemeOfStudiesScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") { HomeScreen(navController = navController, openDrawer = {}) }
        composable("lecture_schedule") { LectureScheduleScreen(navController = navController, openDrawer = {}) }
        composable("scheme_of_studies") { SchemeOfStudiesScreen(navController = navController, openDrawer = {}) }
    }
}
