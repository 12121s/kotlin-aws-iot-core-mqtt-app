package com.illis.awsiotcoremqtt.presentation.viewmodel.mqtt

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.illis.awsiotcoremqtt.domain.usecase.PublishMqttUseCase
import com.illis.awsiotcoremqtt.domain.usecase.SubscribeMqttUseCase
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class MqttState(
    val isLoading: Boolean = false,
    val completedTask: String = ""
)

class MqttViewModel(
    app : Application,
    private val subscribeMqttUseCase: SubscribeMqttUseCase,
    private val publishMqttUseCase: PublishMqttUseCase
) : AndroidViewModel(app) {

    fun publish(msg : String, type: PublishMqttUseCase.MqttType) = viewModelScope.launch(Dispatchers.IO) {
        publishMqttUseCase.publish1s(msg, type).thenAcceptAsync {
            Logger.d(subscribeMqttUseCase.subscribeResponse().get())
        }
    }

    private fun isNetworkAvailable(context: Context?) : Boolean {
        val connectivityManager = (context as Context).getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            var res = false
            connectivityManager.let {
                it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                    res = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }}}
            return res
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

}