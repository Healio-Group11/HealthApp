package com.example.healiohealthapplication.ui.screens.medicine

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.healiohealthapplication.ui.components.BottomNavBar
import com.example.healiohealthapplication.ui.components.TopNavBar
import com.example.healiohealthapplication.data.models.Medicine

@Composable
fun EditMedicineScreen(
    medicineId: String,
    medicineName: String,
    description: String,
    schedule: String,
    amount: String,
    duration: String,
    navController: NavController,
    viewModel: MedicineViewModel = hiltViewModel()
) {
    // States to hold updated values
    var newMedicineName by remember { mutableStateOf(medicineName) }
    var newDescription by remember { mutableStateOf(description) }
    var newSchedule by remember { mutableStateOf(schedule) }
    var newAmount by remember { mutableStateOf(amount) }
    var newDuration by remember { mutableStateOf(duration) }

    // Scaffold to wrap the screen with TopBar, BottomBar, and content
    Scaffold(
        topBar = { TopNavBar("Edit Medicine", navController) }, // TopNavBar with title
        bottomBar = { BottomNavBar(navController) } // BottomNavBar with navigation
    ) { innerPadding ->
        // Main content of the screen
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding) // Apply inner padding from Scaffold
                .padding(16.dp) // Additional padding around the content
        ) {
            // Edit Fields using the renamed MedicineInputField
            EditMedicineInputField(
                label = "Medicine Name",
                value = newMedicineName,
                onValueChange = { newMedicineName = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            EditMedicineInputField(
                label = "Description",
                value = newDescription,
                onValueChange = { newDescription = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            EditMedicineInputField(
                label = "Schedule",
                value = newSchedule,
                onValueChange = { newSchedule = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            EditMedicineInputField(
                label = "Amount",
                value = newAmount,
                onValueChange = { newAmount = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            EditMedicineInputField(
                label = "Duration",
                value = newDuration,
                onValueChange = { newDuration = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Save Button
            Button(
                onClick = {
                    // Use the medicineId to update the medicine with the new details
                    val updatedMedicine = Medicine(
                        id = medicineId,  // Ensure that the medicineId is included
                        name = newMedicineName,
                        description = newDescription,
                        schedule = newSchedule,
                        amount = newAmount,
                        duration = newDuration
                    )

                    // Call the ViewModel to update the medicine in the repository
                    viewModel.updateMedicine(updatedMedicine) { success ->
                        if (success) {
                            Toast.makeText(
                                navController.context,
                                "Medicine details updated!",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.navigateUp()  // Navigate back after updating
                        } else {
                            Toast.makeText(
                                navController.context,
                                "Failed to update medicine.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Save Changes", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Cancel Button
            OutlinedButton(
                onClick = {
                    // Navigate back without saving
                    navController.navigateUp()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Cancel", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun EditMedicineInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label)
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = label) },
            singleLine = true
        )
    }
}
