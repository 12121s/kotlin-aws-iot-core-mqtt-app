package com.illis.awsiotcoremqtt.domain.usecase

import dagger.hilt.android.qualifiers.ApplicationContext
import software.amazon.awssdk.crt.mqtt.MqttClientConnection
import software.amazon.awssdk.crt.mqtt.QualityOfService
import java.nio.charset.StandardCharsets
import java.util.concurrent.CompletableFuture

class SubscribeMqttUseCase(val context: ApplicationContext,
                           private val mqttClient: MqttClientConnection) {

    fun subscribeTVCommon() : CompletableFuture<String> {
        val future = CompletableFuture<String>()
        mqttClient.subscribe("topic", QualityOfService.AT_LEAST_ONCE) { message ->
            val msg = String(message.payload, StandardCharsets.UTF_8)
            future.complete(msg)
        }
        return future
    }

    fun subscribeResponse() : CompletableFuture<String> {
        val future = CompletableFuture<String>()
        mqttClient.subscribe("topic", QualityOfService.AT_LEAST_ONCE) { message ->
            val msg = String(message.payload, StandardCharsets.UTF_8)
            future.complete(msg)
        }
        return future
    }
}