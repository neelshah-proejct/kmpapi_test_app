package org.test.project

import kotlinx.serialization.Serializable

@Serializable
sealed class RequestState {
    @Serializable
    data object Idle: RequestState()

    @Serializable
    data object Loading: RequestState()

    @Serializable
    data class Success(val data: List<Product>): RequestState()

    @Serializable
    data class Error(val message: String): RequestState()

    fun isLoading(): Boolean = this is Loading
    fun isSuccess(): Boolean = this is Success
    fun isError(): Boolean = this is Error

    fun getProducts(): List<Product> = when (this) {
        is Success -> this.data
        else -> throw IllegalStateException("No products available in this state")
    }

    fun getErrorMessage(): String = when (this) {
        is Error -> this.message
        else -> throw IllegalStateException("No error message available in this state")
    }
}