package dev.sierov.api

data class BaseUrl(val url: String) {
    override fun toString() = url

    companion object {
        val Default = BaseUrl(url = "https://raw.githubusercontent.com/jumbo-tech-campus/AndroidAssignment/main")
        val Local = BaseUrl(url = "http://localhost:8080")
    }
}