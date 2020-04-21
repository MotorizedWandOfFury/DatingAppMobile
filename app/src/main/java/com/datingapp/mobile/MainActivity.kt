package com.datingapp.mobile

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.datingapp.mobile.components.Loading
import com.datingapp.mobile.network.Client
import com.datingapp.mobile.render.LithoDatingAppRenderer
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    companion object {
        const val TAG = "MainActivity"
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + exceptionHandler

    private lateinit var job: Job
    private lateinit var exceptionHandler: CoroutineExceptionHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = SupervisorJob()
        exceptionHandler = CoroutineExceptionHandler { _, exception ->
            Log.e(TAG, "Error occurred", exception);
        }

        val c = ComponentContext(this)
        val lithoView = LithoView.create(c, Loading.create(c).build())
        val renderer = LithoDatingAppRenderer()

        setContentView(lithoView)

        launch {
            Client.get()?.run {
                val component = renderer.renderView(this, c)
                lithoView.setComponentAsync(component)
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
