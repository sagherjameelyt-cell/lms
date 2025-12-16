package com.example.template_lms.ui.password_reset

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.template_lms.ui.theme.TemplateLMSTheme

class ResetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemplateLMSTheme {
                ResetPasswordScreen()
            }
        }
    }
}
