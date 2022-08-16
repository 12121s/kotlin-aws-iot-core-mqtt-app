package com.illis.awsiotcoremqtt.domain.usecase

import software.amazon.awssdk.crt.mqtt.MqttClientConnection
import software.amazon.awssdk.crt.mqtt.MqttMessage
import software.amazon.awssdk.crt.mqtt.QualityOfService
import java.util.concurrent.CompletableFuture

class PublishMqttUseCase(private val mqttClient: MqttClientConnection) {

    enum class MqttType(
        val typeName: String
    ) {
        HELLO("hello")
    }

    fun publish(msg : String, type: MqttType, delay: Int) : CompletableFuture<Int> {

        val mqttMessage = MqttMessage("topic", msg.toByteArray(), QualityOfService.AT_LEAST_ONCE, false)
        return mqttClient.publish(mqttMessage)
    }
}