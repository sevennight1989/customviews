package com.android.custview.utils

import android.util.ArrayMap
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.CoroutineContext

open class Qualifier<D> {
    private var key: D? = null
    private fun getKey() = key
    fun setKeyName(key: D) {
        this.key = key
    }

    override fun hashCode(): Int {
        return 31 * getKey().hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Qualifier<*>) {
            other.key == this.key
        } else {
            false
        }
    }

    override fun toString(): String {
        return "Qualifier[key:${key}]"
    }

    class StringQuaQualifier : Qualifier<String>() {

    }
}

private val entrySingle by lazy { ConcurrentHashMap<String, Any>() }

private val entryFactory by lazy { ConcurrentHashMap<String, () -> Any?>() }

fun startInit(component: Components.() -> Unit) {
    component.invoke(Components.get())
}

class Components {

    companion object {
        private val entry = ArrayMap<String, Any?>()
        private val instance by lazy { Components() }
        fun get() = instance
        fun getEntry() = entry
    }

    inline fun <reified T> single(single: () -> T) {
        val name = T::class.java.name
        getEntry()[name] = single()
    }

}

inline fun <reified T> get(name: String = T::class.java.name): T {
    return Components.getEntry()[name] as T
}

inline fun <reified T> inject(name: String = T::class.java.name): Lazy<T> {

    return lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { Components.getEntry()[name] as T }
}


