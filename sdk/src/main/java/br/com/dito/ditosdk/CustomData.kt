package br.com.dito.ditosdk

class CustomData {

    val params: MutableMap<String, Any> = LinkedHashMap()

    fun add(key: String, value: String) {
        params[key] = value
    }

    fun add(key: String, value: Int) {
        params[key] = value
    }

    fun add(key: String, value: Double) {
        params[key] = value
    }

    fun add(key: String, value: Boolean) {
        params[key] = value
    }
}