package com.example.searchapplication.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchapplication.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = searchText.combine(_users) { text, users ->
        if (text.isBlank()) {
            users
        } else {
            users.filter { it.doesMatchSearchQuery(text) }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    init {
        fetchUsers("")
    }

    fun onSearchTextChange(text: String?) {
        if (text != null) {
            _searchText.value = text
            fetchUsers(text)
        }

    }

    private fun fetchUsers(query: String) {
        viewModelScope.launch {
            try {
                _isSearching.value = true
                val response = RetrofitInstance.api.searchUsers(query)
                _users.value = response.items
            } catch (e: Exception) {
                // Handle the error (e.g., show a message to the user)
            } finally {
                _isSearching.value = false
            }
        }
    }
}
