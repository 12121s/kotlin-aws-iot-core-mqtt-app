package com.illis.awsiotcoremqtt.presentation.di;

import android.content.Context
import dagger.Module;
import dagger.Provides
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent;
import software.amazon.awssdk.crt.CRT
import software.amazon.awssdk.crt.mqtt.MqttClientConnection
import software.amazon.awssdk.crt.mqtt.MqttClientConnectionEvents
import software.amazon.awssdk.iot.AwsIotMqttConnectionBuilder
import java.io.FileOutputStream
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MqttModule {
    val endPoint = "amazonaws.com"

    private val rootCa = "Amazon.pem"
    private val cert = "certificate.pem.crt"
    private val privateKey = "private.pem.key"

    @Singleton
    @Provides
    fun provideMqttClient(builder: AwsIotMqttConnectionBuilder) : MqttClientConnection {
        val connection = builder.build()
        val connected = connection.connect()
        try {
            val sessionPresent = connected.get()
            println("Connected to ${if (sessionPresent) "new" else "existing"} session!")
        } catch (ex: Exception) {
            println("Exception occurred during connect  $ex")
        }

        return connection
    }

    @Singleton
    @Provides
    fun provideResourceMap(@ApplicationContext context: Context) : HashMap<String, String> {
        val resourceNames = listOf(rootCa, cert, privateKey)
        val resourceMap = HashMap<String, String>()
        for (resourceName in resourceNames) {
            context.resources.assets.open(resourceName).use { res ->
                    val cachedName = "${context.externalCacheDir}/${resourceName}"
                FileOutputStream(cachedName).use { cachedRes ->
                        res.copyTo(cachedRes)
                }
                resourceMap[resourceName] = cachedName
            }
        }
        return resourceMap
    }

    @Singleton
    @Provides
    fun provideMqttConnectionCallback(): MqttClientConnectionEvents {
        return object : MqttClientConnectionEvents {
            override fun onConnectionInterrupted(errorCode: Int) {
                if (errorCode != 0) {
                    println("Connection interrupted: $errorCode: " + CRT.awsErrorString(errorCode))
                }
            }

            override fun onConnectionResumed(sessionPresent: Boolean) {
                println("Connection resumed: " + if (sessionPresent) "existing session" else "clean session")
            }
        }
    }

    @Singleton
    @Provides
    fun provideAwsIotConnectionBuilder(resourceMap: HashMap<String, String>, callbacks : MqttClientConnectionEvents) : AwsIotMqttConnectionBuilder {
        val builder = AwsIotMqttConnectionBuilder.newMtlsBuilderFromPath(resourceMap[cert], resourceMap[privateKey])

        builder
                .withConnectionEventCallbacks(callbacks)
                .withClientId("client")
                .withEndpoint(endPoint)
                .withPort(8883)
                .withCleanSession(true)
                .withProtocolOperationTimeoutMs(60000)

        return builder
    }
}
