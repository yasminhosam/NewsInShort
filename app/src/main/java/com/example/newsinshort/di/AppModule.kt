package com.example.newsinshort.di

import com.example.newsinshort.data.AppConstants
import com.example.newsinshort.data.api.ApiService
import com.example.newsinshort.data.datasource.NewsDataSource
import com.example.newsinshort.data.datasource.NewsDataSourceImpl
import com.example.newsinshort.data.repository.NewsRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import com.example.newsinshort.BuildConfig

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient():OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor{ chain ->
                val originalRequest=chain.request()
                val newUrl=originalRequest.url.newBuilder()
                    .addQueryParameter("apiKey",BuildConfig.API_KEY)
                    .build()
                val newRequest = originalRequest.newBuilder().url(newUrl).build()
                chain.proceed(newRequest)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory()) // ✅ makes Moshi understand Kotlin classes
            .build()
    }
    @Provides
    @Singleton
    fun provideRetrofit (okHttpClient: OkHttpClient,moshi: Moshi):Retrofit{
        return Retrofit.Builder()
            .baseUrl(AppConstants.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit):ApiService{
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsDataSource(apiService: ApiService):NewsDataSource{
        return NewsDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsDataSource: NewsDataSource): NewsRepository {
        return NewsRepository(newsDataSource)
    }
}