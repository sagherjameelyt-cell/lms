package com.example.template_lms

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.template_lms.ui.login.LoginActivity
import com.example.template_lms.ui.theme.TemplateLMSTheme
import kotlinx.coroutines.delay
import kotlin.random.Random

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemplateLMSTheme {
                SplashScreen()
            }
        }
    }
}

data class Particle(
    var x: Float,
    var y: Float,
    var alpha: Float,
    var scale: Float,
    var velocityX: Float,
    var velocityY: Float
)

@Composable
fun ParticleSystem(modifier: Modifier = Modifier) {
    val particles = remember { mutableStateOf(listOf<Particle>()) }

    LaunchedEffect(Unit) {
        particles.value = (1..100).map {
            Particle(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                alpha = Random.nextFloat() * 0.5f + 0.5f,
                scale = Random.nextFloat() * 0.5f + 0.2f,
                velocityX = (Random.nextFloat() - 0.5f) * 0.0005f,
                velocityY = (Random.nextFloat() - 0.5f) * 0.0005f
            )
        }

        while (true) {
            delay(16)
            particles.value = particles.value.map {
                it.x += it.velocityX
                it.y += it.velocityY
                if (it.x < 0 || it.x > 1 || it.y < 0 || it.y > 1) {
                    it.x = Random.nextFloat()
                    it.y = Random.nextFloat()
                }
                it
            }
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        particles.value.forEach { particle ->
            drawCircle(
                color = Color.White.copy(alpha = particle.alpha),
                radius = particle.scale * 5f,
                center = Offset(particle.x * size.width, particle.y * size.height)
            )
        }
    }
}

@Composable
fun SplashScreen() {
    val context = LocalContext.current
    val uosFont = FontFamily(Font(R.font.uos))
    val lmsFont = FontFamily(Font(R.font.lms))

    val logoAlpha = remember { Animatable(0f) }
    val logoScale = remember { Animatable(0.5f) }
    val rotation = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        logoAlpha.animateTo(1f, animationSpec = tween(500))
        logoScale.animateTo(1f, animationSpec = tween(500))
        rotation.animateTo(360f, animationSpec = tween(1000, easing = LinearEasing))

        delay(2000)
        context.startActivity(Intent(context, LoginActivity::class.java))
        (context as? ComponentActivity)?.finish()
    }

    Box(
        modifier = Modifier.fillMaxSize().background(
            Color(0xFF0A254E) // A Lighter Blue
        ),
        contentAlignment = Alignment.Center
    ) {
        ParticleSystem()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .alpha(logoAlpha.value)
                    .scale(logoScale.value)
                    .rotate(rotation.value),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "University of Sahiwal",
                color = Color.White,
                fontSize = 26.sp,
                fontFamily = uosFont
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Login Management System",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 20.sp,
                fontFamily = lmsFont
            )
        }
    }
}