package com.datingapp.mobile.network

import android.util.Log
import com.datingapp.mobile.BuildConfig
import com.datingapp.mobile.models.DatingAppJsonModel
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

object Client {
    private const val TAG = "Client"
    private const val DATING_APP_MEDIA_TYPE = "application/vnd.datingapp+json"

    private val okHttpClient = OkHttpClient.Builder()
        .followRedirects(true)
        .connectTimeout(2, TimeUnit.SECONDS)
        .build()
    private val gson = Gson()

    fun get(path: String = ""): CompletableFuture<DatingAppJsonModel> {
        val future = CompletableFuture<DatingAppJsonModel>()
        val uri = "http://${BuildConfig.SERVER_ADDRESS}/${path}"

        val request = Request.Builder()
            .get()
            .url(uri)
            .header("Accept", DATING_APP_MEDIA_TYPE)
            .build()

        Log.v(TAG, "Making request with uri: $uri")
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Failed request", e)
                future.completeExceptionally(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonString = response.body?.string()
                if (jsonString != null) {
                    try {
                        val model = gson.fromJson(jsonString, DatingAppJsonModel::class.java)
                        future.complete(model)
                    } catch (e: Exception) {
                        future.completeExceptionally(Exception("Could not parse response body as json", e))
                    }
                } else {
                    future.completeExceptionally(Exception("Body had no response"))
                }
            }
        })

        return future
    }

    //TODO: Add support for json objects
    fun post(url: String) {}

}