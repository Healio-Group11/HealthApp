package com.example.healiohealthapplication.utils

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.sqrt

// tracks steps using the accelerometer or android step counter sensor depending on what the phone has
class StepCounter(context: Context) : SensorEventListener { // implements the sensor event listener (context is the way the sensor is accessed)
    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var stepDetector: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) // use Sensor.TYPE_STEP_COUNTER instead (or both)?
    private var accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    val stepCount = MutableStateFlow(0)
    private val currentlyUsedSensor = MutableStateFlow(0) // 1 = step detector, 2 = accelerometer

    private var previousMagnitude = 0f
    private var stepThreshold = 11f
    private var stepCooldown = 0L
    private val stepDelay = 300L // milliseconds between valid steps

    fun startListening() {
        if (stepDetector == null) {
            Log.e("StepCounter", "No sensor of type step detector found!") // this gets called
            if (accelerometer != null) {
                accelerometer?.let {
                    currentlyUsedSensor.value = 2
                    sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
                }
            } else {
                Log.e("StepCounter", "No sensor of type accelerometer found!")
                // some message in UI that steps cannot be used
            }
        } else {
            Log.d("StepCounter", "Sensor of type step detector found. Step sensor listening started")
            currentlyUsedSensor.value = 1
            stepDetector?.let { // only listens if there is a step detector in the phone
                // "this" refers to the current stepCounter instance. this is where updates of listening are sent to
                // "it" refers to the stepSensor which will be sending the updates
                // "SensorManager.SENSOR_DELAY_UI determines how fast the updates are given
                sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
            }
        }
    }

    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) { // triggers whenever there is a new sensor event
        if (currentlyUsedSensor.value == 1) {
            if (event?.sensor?.type == Sensor.TYPE_STEP_DETECTOR) { // if an event from specifically the step detector is noticed then
                val newStepValue = stepCount.value + 1
                stepCount.value = newStepValue // add a step (one event = one step)
                Log.d("StepCounter", "Step count steps counted: ${stepCount.value}")
            }
        } else if (currentlyUsedSensor.value == 2) {
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                val magnitude = sqrt(x * x + y * y + z * z)

                val currentTime = System.currentTimeMillis()
                if (magnitude > stepThreshold && previousMagnitude <= stepThreshold &&
                    currentTime - stepCooldown > stepDelay
                ) {
                    stepCount.value += 1
                    stepCooldown = currentTime
                    Log.d("StepCounter", "Accelerometer steps counted: ${stepCount.value}")
                }

                previousMagnitude = magnitude
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // the "SensorEventListener" requires this function
    }
}