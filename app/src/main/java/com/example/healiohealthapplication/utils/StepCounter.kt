package com.example.healiohealthapplication.utils

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.MutableStateFlow

// tracks steps using the accelerometer

class StepCounter(context: Context) : SensorEventListener { // implements the sensor event listener (context is the way the sensor is accessed)
    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var stepSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

    val stepCount = MutableStateFlow(0)

    fun startListening() {
        stepSensor?.let { // only listens if there is a step detector in the phone
            // "this" refers to the current stepCounter instance. this is where updates of listening are sent to
            // "it" refers to the stepSensor which will be sending the updates
            // "SensorManager.SENSOR_DELAY_UI determines how fast the updates are given
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) { // triggers whenever there is a new sensor event
        if (event?.sensor?.type == Sensor.TYPE_STEP_DETECTOR) { // if an event from specifically the step detector is noticed then
            stepCount.value += 1 // add a step (one event = one step)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // the "SensorEventListener" requires this function
    }
}