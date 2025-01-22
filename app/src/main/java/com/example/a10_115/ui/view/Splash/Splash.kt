package com.example.a10_115.ui.view.Splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a10_115.ui.Navigation.DestinasiNavigasi

object DestinasiSplash : DestinasiNavigasi {
    override val route = "Splash"
    override val titleRes = "Splash"
}

@Composable
fun Splash(
    onDosenClick: () -> Unit,
    onMataKuliahClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // State for animation control
    var isVisible by remember { mutableStateOf(false) }

    // Trigger animation when the view is loaded
    LaunchedEffect(Unit) {
        isVisible = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFC0C0C0)) // Background color changed to silver
    ) {
        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Buttons with animations
            AnimatedButton(text = "Pekerja", icon = Icons.Filled.Person, onClick = onDosenClick)
            AnimatedButton(text = "Tanaman", icon = Icons.Filled.Edit, onClick = onMataKuliahClick)
        }
    }
}

@Composable
fun AnimatedButton(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    var isPressed by remember { mutableStateOf(false) }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFB0B0B0), // Silver-like button color
            contentColor = Color.Black // Black text and icon color
        ),
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .size(100.dp) // Set width and height to create a square button
            .clickable { isPressed = !isPressed }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Icon with increased size
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .padding(bottom = 8.dp) // Padding between icon and text
                    .size(50.dp), // Increased icon size
                tint = Color.Black // Black icon color
            )
            // Text
            Text(
                text = text,
                fontSize = 16.sp, // Adjust font size to fit inside the square
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .animateScale(isPressed) // Adding animation to text
            )
        }
    }
}

@Composable
fun Modifier.animateScale(isPressed: Boolean): Modifier {
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.1f else 1.0f,
        animationSpec = tween(durationMillis = 300)
    )
    return this.then(Modifier.scale(scale))
}