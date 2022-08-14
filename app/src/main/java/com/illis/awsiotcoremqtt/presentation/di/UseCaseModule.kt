package com.illis.awsiotcoremqtt.presentation.di

import com.illis.awsiotcoremqtt.domain.usecase.PublishMqttUseCase
import com.illis.awsiotcoremqtt.domain.usecase.SubscribeMqttUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import software.amazon.awssdk.crt.mqtt.MqttClientConnection
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun providePublishMqttUseCase(
        mqttClient: MqttClientConnection
    ) : PublishMqttUseCase {
        return PublishMqttUseCase(mqttClient)
    }

    @Singleton
    @Provides
    fun provideSubscribeMqttUseCase(
        mqttClient: MqttClientConnection
    ) : SubscribeMqttUseCase {
        return SubscribeMqttUseCase(mqttClient)
    }
}