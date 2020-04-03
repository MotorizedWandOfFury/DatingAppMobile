package com.datingapp.mobile.render

import com.datingapp.mobile.components.User
import com.datingapp.mobile.models.DatingAppJsonModel
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import java.util.Locale

class LithoDatingAppRenderer {

    fun renderView(model: DatingAppJsonModel, c: ComponentContext): Component {
        val uiBuilder = render(model, c) ?: throw Error("Unable to render UI")
        return uiBuilder.build()
    }

//Create renderer for each possible resource (User, MatchQueue, etc)
    private fun render(model: DatingAppJsonModel, c: ComponentContext): Component.Builder<*>? =
        when(model.type.toLowerCase(Locale.ROOT)) {
            "user" -> renderUser(model, c)
            else -> null // Anything we don't understand we don't handle
        }


    private fun renderUser(model: DatingAppJsonModel, c: ComponentContext): User.Builder {
        if(model.type.toLowerCase(Locale.ROOT) != "user") {
            throw Error("Expected 'User', got '${model.type}' instead")
        }

        return User.create(c)
    }
}