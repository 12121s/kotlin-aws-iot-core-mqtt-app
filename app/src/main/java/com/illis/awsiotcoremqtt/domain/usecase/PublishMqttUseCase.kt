package com.illis.awsiotcoremqtt.domain.usecase

import dagger.hilt.android.qualifiers.ApplicationContext
import software.amazon.awssdk.crt.mqtt.MqttClientConnection
import software.amazon.awssdk.crt.mqtt.MqttMessage
import software.amazon.awssdk.crt.mqtt.QualityOfService
import java.util.concurrent.CompletableFuture

class PublishMqttUseCase(val context: ApplicationContext,
                         private val mqttClient: MqttClientConnection) {

    enum class MqttType(
        val typeName: String
    ) {
        KEYCODE("keycode"),
        KEYPAD("keypad"),
        HOTKEY("hotkey")
    }
    
    fun publish1s(msg: String, type: MqttType) : CompletableFuture<Int> {
        return publish(msg, type, delay = 1)
    }

    fun publish4s(msg: String, type: MqttType) : CompletableFuture<Int> {
        return publish(msg, type, delay = 4)
    }

    private fun publish(msg : String, type: MqttType, delay: Int) : CompletableFuture<Int> {

        val mqttMessage = MqttMessage("topic", msg.toByteArray(), QualityOfService.AT_LEAST_ONCE, false)
        return mqttClient.publish(mqttMessage)
    }
}