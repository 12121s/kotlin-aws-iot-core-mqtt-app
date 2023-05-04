package com.illis.awsiotcoremqtt.presentation.viewmodel.mqtt

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.illis.awsiotcoremqtt.domain.usecase.PublishMqttUseCase
import com.illis.awsiotcoremqtt.domain.usecase.SubscribeMqttUseCase

class MqttViewModelFactory(
    private val app: Application,
    /*private val subscribeMqttUseCase: SubscribeMqttUseCase,
    private val publishMqttUseCase: PublishMqttUseCase*/
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MqttViewModel(
            app
            /*subscribeMqttUseCase,
            publishMqttUseCase*/
        ) as T
    }
}