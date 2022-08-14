package com.illis.awsiotcoremqtt.domain.usecase

import android.app.Application
import com.illis.awsiotcoremqtt.presentation.viewmodel.mqtt.MqttViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {
    @Singleton
    @Provides
    fun provideMqttViewModelFactory(
        application: Application,
        subscribeMqttUseCase: SubscribeMqttUseCase,
        publishMqttUseCase: PublishMqttUseCase
    ): MqttViewModelFactory {
        return MqttViewModelFactory(
            application,
            subscribeMqttUseCase,
            publishMqttUseCase
        )
    }
}