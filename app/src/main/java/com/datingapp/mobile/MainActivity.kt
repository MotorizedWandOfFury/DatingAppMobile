package com.datingapp.mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.datingapp.mobile.components.User
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import com.facebook.soloader.SoLoader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lithoContext = ComponentContext(this)
        val component = User.create(lithoContext).build()
        val lithoView = LithoView.create(lithoContext, component)

        setContentView(lithoView)
    }
}
