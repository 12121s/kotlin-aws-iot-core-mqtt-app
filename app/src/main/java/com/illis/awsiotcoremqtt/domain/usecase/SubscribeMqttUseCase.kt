package com.illis.awsiotcoremqtt.domain.usecase

import software.amazon.awssdk.crt.mqtt.MqttClientConnection
import software.amazon.awssdk.crt.mqtt.QualityOfService
import java.nio.charset.StandardCharsets
import java.util.concurrent.CompletableFuture

class SubscribeMqttUseCase(private val mqttClient: MqttClientConnection) {

    fun subscribeResponse() : CompletableFuture<String> {
        val future = CompletableFuture<String>()
        mqttClient.subscribe("topic", QualityOfService.AT_LEAST_ONCE) { message ->
            val msg = String(message.payload, StandardCharsets.UTF_8)
            future.complete(msg)
        }
        return future
    }
}