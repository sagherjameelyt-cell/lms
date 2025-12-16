package com.example.template_lms.ui.registration

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.template_lms.ui.theme.TemplateLMSTheme

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemplateLMSTheme {
                RegistrationScreen()
            }
        }
    }
}
