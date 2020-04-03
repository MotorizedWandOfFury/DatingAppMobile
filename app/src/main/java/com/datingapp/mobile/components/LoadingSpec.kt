package com.datingapp.mobile.components

import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.Row
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.widget.Text

@LayoutSpec
class LoadingSpec {
    companion object {
        @JvmStatic
        @OnCreateLayout
        fun onCreateLayout(context: ComponentContext): Component {
            return Row.create(context)
                .child(
                    Text.create(context)
                        .text("Loading...")
                        .textSizeDip(50F)
                ).build()
        }
    }
}