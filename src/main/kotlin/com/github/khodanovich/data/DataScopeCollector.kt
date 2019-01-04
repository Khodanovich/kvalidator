package com.github.khodanovich.data

class DataScopeCollector {

    private var sources = HashMap<String, () -> Any?>()

    fun addSource(key: String, source: () -> Any?) {
        sources[key]
        if (sources.contains(key).not()) {
            sources[key] = source
        } else {
            throw IllegalArgumentException("key \"$key\" is duplicate")
        }
    }

    fun getData(): HashMap<String, Any?> {
        return collect()
    }

    private fun collect(): HashMap<String, Any?> {
        val s = HashMap<String, Any?>()
        sources.forEach { key, source -> s.set(key, source.invoke()) }
        return s
    }

    fun getBy(key: String): Any? {
        if (sources.containsKey(key)) {
            return sources[key]
        } else {
            throw IllegalArgumentException("There is not data by key = $key")
        }
    }
}