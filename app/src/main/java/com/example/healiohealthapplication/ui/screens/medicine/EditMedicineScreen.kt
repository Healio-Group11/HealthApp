package com.example.healiohealthapplication.ui.screens.medicine

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.healiohealthapplication.ui.components.BottomNavBar
import com.example.healiohealthapplication.ui.components.TopNavBar
import com.example.healiohealthapplication.data.models.Medicine

@Composable
fun EditMedicineScreen(
    medicineId: String,
    navController: NavController,
    viewModel: MedicineViewModel = hiltViewModel()
) {
    // Fetch the medicine from ViewModel
    viewModel.getMedicineById(medicineId)

    // Get the medicine details from the ViewModel state
    val medicine = viewModel.medicine.collectAsState().value

    // Show loading indicator while medicine is being fetched
    if (medicine == null) {
        // This is just a placeholder UI when medicine is being fetched
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        // States to hold updated values (initialized with existing medicine details)
        var newMedicineName by remember { mutableStateOf(medicine.name) }
        var newDescription by remember { mutableStateOf(medicine.description) }
        var newSchedule by remember { mutableStateOf(medicine.schedule) }
        var newAmount by remember { mutableStateOf(medicine.amount) }
        var newDuration by remember { mutableStateOf(medicine.duration) }

        // Scaffold to wrap the screen with TopBar, BottomBar, and content
        Scaffold(
            topBar = { TopNavBar("Edit Medicine", navController) },
            bottomBar = { BottomNavBar(navController) }
        ) { innerPadding ->
            // Main content of the screen
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(16.dp)
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
                        val updatedMedicine = medicine.copy(
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

