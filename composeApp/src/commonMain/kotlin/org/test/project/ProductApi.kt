package org.test.project

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import org.test.project.Constants.BASE_URL

class ProductApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    fun fetchProducts(limit: Int): Flow<RequestState> {
        return flow {
            emit(RequestState.Loading)
            delay(2000)
            try {
                // Fetch and deserialize response into Products
                val products: List<Product> = httpClient.get("${BASE_URL}products?limit=$limit").body()

                // Emit Success with deserialized products
                emit(RequestState.Success(data = products))
            }catch (e: Exception) {
                println("ProductsApi Error: ${e.message}") // simple logging
                emit(RequestState.Error(message = "Error while fetching of the data"))
            }
        }
    }
}


