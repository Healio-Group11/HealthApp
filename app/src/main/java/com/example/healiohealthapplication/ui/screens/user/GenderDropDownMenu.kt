package com.example.healiohealthapplication.ui.screens.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.healiohealthapplication.R

@Composable
fun GenderDropdown(
    selectedGender: String,
    expanded: Boolean,
    genderOptions: List<String>,
    onExpandChanged: (Boolean) -> Unit,
    onGenderSelected: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedGender,
            onValueChange = {},
            label = { Text(stringResource(R.string.edit_user_screen_gender_dropdown_component_label)) },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable { onExpandChanged(true) },
            trailingIcon = {
                IconButton(onClick = { onExpandChanged(true) }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = stringResource(R.string.edit_user_screen_gender_dropdown_component_icon_description))
                }
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandChanged(false) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            genderOptions.forEach { gender ->
                DropdownMenuItem(
                    text = { Text(gender) },
                    onClick = {
                        onGenderSelected(gender)
                        onExpandChanged(false)
                    }
                )
            }
        }
    }
}