package dev.triumphteam.reaper.components

import dev.triumphteam.horizon.html.FlowContent
import dev.triumphteam.horizon.html.button
import dev.triumphteam.horizon.html.element.ButtonTag

public inline fun FlowContent.simpleButton(crossinline block: ButtonTag.() -> Unit = {}) {
    button(className = "px-4 py-2 text-sm bg-dark-surface/50 border border-white/10 hover:border-primary/50 rounded-lg transition-all duration-200") {
        block()
    }
}