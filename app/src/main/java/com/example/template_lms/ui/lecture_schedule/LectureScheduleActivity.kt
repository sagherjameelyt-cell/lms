package com.example.template_lms.ui.lecture_schedule

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import com.example.template_lms.ui.theme.TemplateLMSTheme

class LectureScheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemplateLMSTheme {
                LectureScheduleScreen(navController = rememberNavController(), openDrawer = {})
            }
        }
    }
}
