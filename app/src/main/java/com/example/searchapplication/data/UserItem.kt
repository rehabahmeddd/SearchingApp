package com.example.searchapplication.data

data class User(
    val login: String?,
    val id: String,
    val avatar_url: String
) {
    fun doesMatchSearchQuery(query: String?): Boolean {
        // Check if either username or query is null and return false if so
        if (login.isNullOrEmpty() || query.isNullOrEmpty()) {
            return false
        }

        val matchingList = listOf(login)

        return matchingList.any {
            it.contains(query, ignoreCase = true)
        }
    }
}
