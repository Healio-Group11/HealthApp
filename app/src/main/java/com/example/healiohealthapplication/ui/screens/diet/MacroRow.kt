package com.example.healiohealthapplication.ui.screens.diet

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.healiohealthapplication.ui.theme.Green142

@Composable
fun MacroRow(label: String, value: String, unit: String) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFF7F7F7),
        tonalElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .border(
                width = 2.dp,
                color = Green142,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
            )
            Text(
                text = "$value $unit",
                style = MaterialTheme.typography.titleMedium.copy(color = Color.Black)
            )
        }
    }
}