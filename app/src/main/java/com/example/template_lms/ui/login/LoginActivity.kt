package com.example.template_lms.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.template_lms.ui.theme.TemplateLMSTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemplateLMSTheme {
                LoginScreen()
            }
        }
    }
}
