package com.example.healiohealthapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.healiohealthapplication.ui.theme.Green142

@Composable
fun OverlappingCircle(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        // First circle element on the top
        Box(
            modifier = Modifier
                .size(240.dp)
                .align(Alignment.TopStart)
                .offset(x = (-96).dp, y = (-96).dp)
                .background(
                    color = Green142.copy(0.8f),
                    shape = CircleShape
                )
                .alpha(1f)
        )
        // Second circle element on the top
        Box(
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.TopStart)
                .offset(x = 24.dp, y = (-56).dp)
                .background(
                    color = Green142.copy(0.8f),
                    shape = CircleShape
                )
        )
    }
}