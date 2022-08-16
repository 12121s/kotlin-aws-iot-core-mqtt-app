package com.illis.awsiotcoremqtt

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.illis.awsiotcoremqtt.domain.usecase.PublishMqttUseCase
import com.illis.awsiotcoremqtt.presentation.viewmodel.mqtt.MqttState
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
        mqttViewModel.publish("hello", PublishMqttUseCase.MqttType.HELLO, 1)
        mqttViewModel.mqttStatus.observe(this) { response ->
            when (response) {
                MqttState.MQTT_COMPLETE ->
                    Toast.makeText(baseContext, "success!", Toast.LENGTH_SHORT).show()
                MqttState.MQTT_FAIL -> {
                    Toast.makeText(baseContext, "fail!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}