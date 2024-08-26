package com.example.searchapplication.network
import com.example.searchapplication.data.User


import retrofit2.http.GET
import retrofit2.http.Query

// Define a data class to match the API response
data class UserResponse(
    val items: List<User>
)

// Retrofit API interface
interface GitHubApiService {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int = 1
    ): UserResponse
}
