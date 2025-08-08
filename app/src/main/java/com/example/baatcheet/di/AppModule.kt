package com.example.baatcheet.di

import com.example.baatcheet.data.network.ApiServices
import com.example.baatcheet.data.repository.AuthRepository
import com.example.baatcheet.data.repository.AuthRepositoryImpl
import com.example.baatcheet.data.repository.ChatRepository
import com.example.baatcheet.data.repository.ChatRepositoryImpl
import com.example.baatcheet.data.repository.ProfileRepository
import com.example.baatcheet.data.repository.ProfileRepositoryImpl
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://baatcheet-backend-umxj.onrender.com/"

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(apiServices: ApiServices): AuthRepository {
        return AuthRepositoryImpl(apiServices)
    }
    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()


    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()


    @Provides
    @Singleton
    fun provideProfileRepository(
        storage: FirebaseStorage,
        fireStore: FirebaseFirestore,
        realtimeDb: FirebaseDatabase,
    ): ProfileRepository {
        return ProfileRepositoryImpl(storage, fireStore,realtimeDb)
    }

    @Provides
    @Singleton
    fun provideChatRepository(
        fireStore: FirebaseFirestore
    ): ChatRepository {
        return ChatRepositoryImpl(fireStore)
    }
}