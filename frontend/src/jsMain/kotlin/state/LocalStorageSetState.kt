package dev.triumphteam.reaper.state

import dev.triumphteam.horizon.state.AbstractState
import dev.triumphteam.reaper.constant.SoulReaper
import kotlinx.browser.localStorage
import org.w3c.dom.get

public class LocalStorageSetState(
    private val key: String,
    private val backing: MutableSet<SoulReaper>,
) : AbstractState<MutableSet<SoulReaper>>(), MutableSet<SoulReaper> by backing {

    init {
        loadLocal()
    }

    override fun get(): MutableSet<SoulReaper> {
        return this
    }

    override fun add(element: SoulReaper): Boolean {
        return backing.add(element).also { saveAndUpdate() }
    }

    override fun remove(element: SoulReaper): Boolean {
        return backing.remove(element).also { saveAndUpdate() }
    }

    override fun clear() {
        backing.clear().also { saveAndUpdate() }
    }

    override fun addAll(elements: Collection<SoulReaper>): Boolean {
        return backing.addAll(elements).also { saveAndUpdate() }
    }

    private fun saveAndUpdate() {
        localStorage.setItem(key, backing.joinToString(separator = ","))
        update()
    }

    private fun loadLocal() {
        val stored = localStorage[key]?.trim()?.split(",")?.filter { it.isNotBlank() } ?: return
        backing.addAll(stored.map(SoulReaper::valueOf))
    }
}

public fun localStorageSetStateOf(
    key: String,
    original: MutableSet<SoulReaper> = mutableSetOf(),
): LocalStorageSetState {
    return LocalStorageSetState(key, original)
}
