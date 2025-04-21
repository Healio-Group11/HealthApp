package com.example.healiohealthapplication.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.healiohealthapplication.ui.theme.NavBarTextWhite

@Composable
fun TopNavBarIconButton(
    contentDescription: Int,
    icon: ImageVector,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(contentDescription),
            tint = NavBarTextWhite.copy(alpha = 0.8f)
        )
    }
}