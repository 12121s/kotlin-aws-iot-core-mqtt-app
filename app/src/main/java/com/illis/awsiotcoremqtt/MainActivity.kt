package com.illis.awsiotcoremqtt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.illis.awsiotcoremqtt.presentation.viewmodel.mqtt.MqttViewModel
import com.illis.awsiotcoremqtt.presentation.viewmodel.mqtt.MqttViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var mqttViewModelFactory: MqttViewModelFactory
    lateinit var mqttViewModel: MqttViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mqttViewModel = ViewModelProvider(this, mqttViewModelFactory)[MqttViewModel::class.java]
    }
}