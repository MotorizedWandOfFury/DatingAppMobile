package com.datingapp.mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.datingapp.mobile.components.Loading
import com.datingapp.mobile.network.Client
import com.datingapp.mobile.render.LithoDatingAppRenderer
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val c = ComponentContext(this)
        val lithoView = LithoView.create(c, Loading.create(c).build())
        val renderer = LithoDatingAppRenderer()

        setContentView(lithoView)

        Client.get().exceptionally { ex ->
            Log.e(TAG, "Failed to render view", ex)
            return@exceptionally null
        }.thenAcceptAsync { model ->
            if (model == null) {
                return@thenAcceptAsync
            }

            val component = renderer.renderView(model, c)
            lithoView.setComponentAsync(component)
        }
    }
}
