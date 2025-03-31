package com.example.healiohealthapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.healiohealthapplication.ui.theme.CircleButtonPink
import com.example.healiohealthapplication.ui.theme.Green142

@Composable
fun CircleButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .size(120.dp)
            .background(
                color = CircleButtonPink, // same light pink
                shape = CircleShape
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}