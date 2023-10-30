package com.example.showtimecompose.di

import com.example.showtimecompose.repository.api.APIUrls
import com.example.showtimecompose.repository.api.ApiService
import com.example.showtimecompose.repository.api.ApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

//KTOR
@Singleton
@Provides
fun provideHttpClient():HttpClient{
    return HttpClient(Android){
        install(Logging){
            level=LogLevel.ALL
        }
        install(DefaultRequest){
            url(APIUrls.BASE_URL)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
        install(ContentNegotiation){
            json(Json)
        }
    }
}

    @Singleton
    @Provides
    fun provideApiService(httpClient: HttpClient):ApiService=ApiServiceImpl(httpClient)

    @Provides
    fun provideDispatcher():CoroutineDispatcher= Dispatchers.Default

}
