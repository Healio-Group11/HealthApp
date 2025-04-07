package com.example.healiohealthapplication.ui.screens.steps

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.healiohealthapplication.ui.theme.ErrorRedDarker
import com.example.healiohealthapplication.ui.theme.ErrorRedLighter

@Composable
fun ErrorCard(errorMessage: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = ErrorRedLighter),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = errorMessage,
                color = ErrorRedDarker, // Darker red for the text
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}