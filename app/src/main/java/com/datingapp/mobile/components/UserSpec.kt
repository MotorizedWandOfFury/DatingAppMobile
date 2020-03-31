package com.datingapp.mobile.components

import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.widget.Text

@LayoutSpec
class UserSpec {
    companion object {
        @OnCreateLayout
        @JvmStatic
        fun onCreateLayout(context: ComponentContext): Component {
            return Column.create(context)
                .child(
                    Text.create(context).text("Hi").textSizeDip(50F)
                ).build()
        }
    }
}