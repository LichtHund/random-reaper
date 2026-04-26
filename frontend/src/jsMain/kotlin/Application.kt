package dev.triumphteam.reaper

import dev.triumphteam.horizon.app
import dev.triumphteam.horizon.component.functional.component
import dev.triumphteam.horizon.html.FlowContent
import dev.triumphteam.horizon.html.button
import dev.triumphteam.horizon.html.div
import dev.triumphteam.horizon.html.img
import dev.triumphteam.horizon.router.Route
import dev.triumphteam.horizon.state.mutableStateOf
import dev.triumphteam.reaper.constant.SoulReaper
import dev.triumphteam.reaper.state.localStorageSetStateOf

private const val PANE_CLASSES = "bg-dark-background border border-white/10 rounded-lg"

public fun main() {
    app {
        index(FlowContent::index)
    }
}

private fun FlowContent.index(route: Route) {
    val selectedState = localStorageSetStateOf(key = "reaper_preference")

    div(className = "w-screen h-screen flex items-center justify-center") {
        div(className = "w-4/5 h-4/5 flex gap-4") {
            div(className = "flex-2 flex flex-col gap-4") {
                val chosenState = mutableStateOf<SoulReaper?>(null)

                div(className = "grow $PANE_CLASSES flex flex-col items-center justify-center p-8") {
                    component {
                        val chosen by remember(chosenState)

                        render {
                            if (chosen == null) {
                                div(className = "text-dark-text-secondary/50 text-lg") {
                                    text("Select a Soul Reaper and click Randomize!")
                                }
                                return@render
                            }

                            div(className = "flex flex-col items-center gap-6") {
                                img(
                                    src = "/assets/bosses/${chosen?.image ?: chosen?.id}.png",
                                    className = "max-w-md max-h-96 object-contain"
                                )

                                div(className = "text-center") {
                                    div(className = "text-3xl font-semibold text-dark-text-primary") {
                                        text(chosen?.fullName ?: "Error")
                                    }
                                }
                            }
                        }
                    }
                }
                div(className = "h-32 $PANE_CLASSES flex items-center justify-center") {
                    button(className = "px-4 py-2 bg-primary rounded-lg") {
                        text("Randomize")

                        onClick = onClick@{
                            chosenState.set(selectedState.minus(chosenState.get()).random())
                        }
                    }
                }
            }

            div(className = "flex-1 $PANE_CLASSES flex py-4 flex-col gap-4") {
                div(className = "flex-1 overflow-y-auto p-4 rounded-lg space-y-2 flex flex-col gap-1") {
                    component {
                        val list = remember(selectedState)

                        render {
                            SoulReaper.entries.forEach { boss ->
                                val selected = if (boss in list) "border-primary" else "border-white/10"

                                button(className = "p-3 bg-dark-surface border-2 $selected hover:bg-dark-surface/80 rounded-md transition-all cursor-pointer") {
                                    onClick = onClick@{
                                        if (boss in list) {
                                            list.remove(boss)
                                            return@onClick
                                        }

                                        list.add(boss)
                                    }
                                    text(boss.fullName)
                                }
                            }
                        }
                    }
                }
                div(className = "flex justify-center items-center gap-4") {
                    button(className = "px-4 py-2 bg-dark-surface border border-white/10 hover:border-primary/50 rounded-md transition-all cursor-pointer") {
                        onClick = onClick@{
                            selectedState.addAll(SoulReaper.entries)
                        }
                        text("Select All")
                    }

                    button(className = "px-4 py-2 bg-dark-surface border border-white/10 hover:border-primary/50 rounded-md transition-all cursor-pointer") {
                        onClick = onClick@{
                            selectedState.clear()
                        }
                        text("Deselect All")
                    }
                }
            }
        }
    }
}
