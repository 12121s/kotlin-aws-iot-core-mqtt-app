package com.illis.awsiotcoremqtt.presentation.viewmodel.mqtt

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.illis.awsiotcoremqtt.domain.usecase.PublishMqttUseCase
import com.illis.awsiotcoremqtt.domain.usecase.SubscribeMqttUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

enum class MqttState {
    MQTT_COMPLETE,
    MQTT_FAIL
}

class MqttViewModel(
    app : Application,
    private val subscribeMqttUseCase: SubscribeMqttUseCase,
    private val publishMqttUseCase: PublishMqttUseCase
) : AndroidViewModel(app) {
    val mqttStatus : MutableLiveData<MqttState> = MutableLiveData()

    fun publish(msg : String, type: PublishMqttUseCase.MqttType, delay: Int) = viewModelScope.launch(Dispatchers.IO) {
        publishMqttUseCase.publish(msg, type, delay).get()
        subscribe()
    }

    private fun subscribe() = viewModelScope.launch(Dispatchers.IO) {
        val subscribe = subscribeMqttUseCase.subscribeResponse()
        try {
            val result = subscribe.get(7, TimeUnit.SECONDS)
            if (result == "ok")
                mqttStatus.postValue(MqttState.MQTT_COMPLETE)
            else {
                mqttStatus.postValue(MqttState.MQTT_FAIL)
            }
        } catch (e: Exception) {
            mqttStatus.postValue(MqttState.MQTT_FAIL)
        }
    }

}