package dev.triumphteam.reaper

import dev.triumphteam.horizon.app
import dev.triumphteam.horizon.component.functional.component
import dev.triumphteam.horizon.html.FlowContent
import dev.triumphteam.horizon.html.button
import dev.triumphteam.horizon.html.div
import dev.triumphteam.horizon.html.img
import dev.triumphteam.horizon.router.Route
import dev.triumphteam.horizon.state.mutableStateOf
import dev.triumphteam.reaper.components.simpleButton
import dev.triumphteam.reaper.constant.SoulReaper
import dev.triumphteam.reaper.state.localStorageSetStateOf

private const val PANE_CLASSES = "bg-dark-background/40 backdrop-blur-xl border border-white/5 rounded-2xl"

public fun main() {
    app {
        // Github Pages doesn't serve it to index, so this is a workaround.
        route("/random-reaper", FlowContent::page)
    }
}

private fun FlowContent.page(route: Route) {
    val selectedState = localStorageSetStateOf(key = "reaper_preference")

    div(className = "relative min-h-screen w-full flex items-center justify-center p-6 select-none") {
    div(className = "absolute inset-0 z-[0] bg-background-surface bg-[radial-gradient(#ffffff33_1px,var(--color-darker-background)_1px)] bg-[size:20px_20px]") {}

    div(className = "w-full max-w-5xl z-10 flex flex-col gap-6 py-8") {
            div(className = "text-center space-y-2") {
                div(className = "text-5xl font-bold bg-primary bg-clip-text text-transparent") {
                    text("Reaper Task Randomizer")
                }
                div(className = "text-dark-text-secondary text-lg") {
                    text("Select your preferred bosses and get a random one!")
                }
            }

            div(className = "flex flex-col gap-6") {
                val chosenState = mutableStateOf<SoulReaper?>(null)

                div(className = "$PANE_CLASSES flex flex-col items-center justify-center p-8 min-h-[24rem]") {
                    component {
                        val chosen by remember(chosenState)

                        render {
                            if (chosen == null) {
                                div(className = "flex flex-col items-center gap-6") {
                                    div(className = "w-32 h-32 rounded-full bg-primary/10 flex items-center justify-center") {
                                        div(className = "text-6xl") {
                                            text("?")
                                        }
                                    }
                                }
                                return@render
                            }

                            div(className = "flex flex-col items-center gap-8 w-full") {
                                div(className = "relative") {
                                    div(className = "absolute inset-0 bg-primary/20 blur-3xl rounded-full") {}
                                    img(
                                        src = "/assets/bosses/${chosen?.image ?: chosen?.id}.png",
                                        alt = chosen?.fullName ?: "Error",
                                        className = "relative max-w-lg max-h-80 object-contain drop-shadow-2xl"
                                    )
                                }

                                div(className = "text-center space-y-2") {
                                    div(className = "text-5xl font-bold text-dark-text-primary") {
                                        text(chosen?.fullName ?: "Error")
                                    }
                                }
                            }
                        }
                    }
                }
                div(className = "flex items-center justify-center") {
                    button(className = "px-12 py-5 bg-primary cursor-pointer text-white font-bold text-xl rounded-lg transition-all duration-300 transform hover:scale-105 active:scale-95") {
                        text("Choose")

                        onClick = {
                            chosenState.set(selectedState.minus(chosenState.get()).random())
                        }
                    }
                }
            }

            div(className = "$PANE_CLASSES flex py-6 px-8 flex-col gap-6") {
                div(className = "flex justify-between items-center") {
                    div(className = "text-dark-text-primary font-semibold text-lg") {
                        text("Choose Your Favorite")
                    }
                    div(className = "flex gap-2") {
                        simpleButton {
                            onClick = { selectedState.addAll(SoulReaper.entries) }
                            text("Select All")
                        }

                        simpleButton {
                            onClick = { selectedState.clear() }
                            text("Clear")
                        }
                    }
                }

                div(className = "grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3") {
                    component {
                        val list = remember(selectedState)

                        render {
                            SoulReaper.entries.forEach { boss ->
                                val isSelected = boss in list
                                val selectedClasses =
                                    if (isSelected) "border-primary bg-primary/5 shadow-lg shadow-primary/20" else "border-white/10 hover:border-white/20"

                                button(className = "relative p-4 bg-dark-surface/30 backdrop-blur-sm border-2 $selectedClasses rounded-xl transition-all duration-200 transform hover:-translate-y-1 hover:shadow-xl text-sm font-medium") {
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
            }
        }
    }
}
