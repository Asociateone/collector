package com.example.collecter.ui.composables.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.neonFontFamily
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun NeonLetter(
    letter: String,
    isOn: Boolean,
    isBroken: Boolean = false,
    modifier: Modifier = Modifier
) {
    // Broken letter flicker animation
    val infiniteTransition = rememberInfiniteTransition(label = "broken_flicker")
    val brokenFlicker by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 800
                0f at 0
                1f at 50
                0.3f at 100
                1f at 150
                0f at 200
                0.8f at 350
                1f at 400
                1f at 800
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "broken_flicker"
    )

    val alpha = if (!isOn) 0f else if (isBroken) brokenFlicker else 1f
    val glowIntensity = if (isBroken) 25f else 35f

    Box(modifier = modifier) {
        // Purple outer glow
        Text(
            text = letter,
            style = TextStyle(
                fontSize = 72.sp,
                fontFamily = neonFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color(0xFFB026FF).copy(alpha = alpha * 0.4f),
                shadow = Shadow(
                    color = Color(0xFFB026FF),
                    offset = Offset(0f, 0f),
                    blurRadius = glowIntensity * 2.5f
                ),
                letterSpacing = 2.sp
            )
        )
        // Cyan glow layer
        Text(
            text = letter,
            style = TextStyle(
                fontSize = 72.sp,
                fontFamily = neonFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF00FFFF).copy(alpha = alpha * 0.5f),
                shadow = Shadow(
                    color = Color(0xFF00FFFF),
                    offset = Offset(0f, 0f),
                    blurRadius = glowIntensity * 1.5f
                ),
                letterSpacing = 2.sp
            )
        )
        // Hot pink main layer
        Text(
            text = letter,
            style = TextStyle(
                fontSize = 72.sp,
                fontFamily = neonFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color(0xFFFF1493).copy(alpha = alpha),
                shadow = Shadow(
                    color = Color(0xFFFF1493),
                    offset = Offset(0f, 0f),
                    blurRadius = glowIntensity
                ),
                letterSpacing = 2.sp
            )
        )
    }
}

@Composable
fun SplashScreen(
    onTimeout: () -> Unit
) {
    val letters = listOf("C", "O", "L", "L", "E", "C", "T", "E", "R")
    var activeLetters by remember { mutableStateOf(0) }

    // Startup sequence - turn on letters one by one
    LaunchedEffect(Unit) {
        for (i in 0..letters.size) {
            activeLetters = i
            delay(150) // 150ms between each letter lighting up
        }
        delay(1500) // Keep the sign on for 1.5 seconds
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A0A0A),
                        Color(0xFF1A0033),
                        Color(0xFF2D0052),
                        Color(0xFF1A0033),
                        Color(0xFF0A0A0A)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Grid pattern background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    val gridColor = Color(0xFF9D00FF).copy(alpha = 0.2f)
                    val lineSpacing = 50.dp.toPx()

                    // Horizontal lines
                    var y = 0f
                    while (y < size.height) {
                        drawLine(
                            color = gridColor,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = 2f
                        )
                        y += lineSpacing
                    }

                    // Vertical lines
                    var x = 0f
                    while (x < size.width) {
                        drawLine(
                            color = gridColor,
                            start = Offset(x, 0f),
                            end = Offset(x, size.height),
                            strokeWidth = 2f
                        )
                        x += lineSpacing
                    }
                }
        )

        // Neon Sign Letters - Startup Sequence
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            letters.forEachIndexed { index, letter ->
                NeonLetter(
                    letter = letter,
                    isOn = index < activeLetters,
                    isBroken = index == 4 && activeLetters > 4 // Middle 'E' is broken
                )
            }
        }
    }
}
