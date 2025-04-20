package com.example.healiohealthapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight.Companion.Bold

private val ButtonPink = Color(0xFFFFCDD2)
private val BorderPink = Color(0xFFEF9A9A)

@Composable
fun CircleButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(130.dp)
            .shadow(elevation = 8.dp, shape = CircleShape)
            .border(width = 2.dp, color = BorderPink, shape = CircleShape)
            .background(color = ButtonPink, shape = CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Black,
                fontSize = 24.sp,
                //fontWeight = Bold
            ),
            textAlign = TextAlign.Center
        )
    }
}
