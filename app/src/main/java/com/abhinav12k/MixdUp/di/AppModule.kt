package com.abhinav12k.MixdUp.di

import android.content.Context
import com.abhinav12k.MixdUp.common.Constants
import com.abhinav12k.MixdUp.data.local.MixdUpDatabase
import com.abhinav12k.MixdUp.data.local.dao.DrinkCardDao
import com.abhinav12k.MixdUp.data.remote.CocktailApi
import com.abhinav12k.MixdUp.data.repository.DrinkRepositoryImpl
import com.abhinav12k.MixdUp.domain.repository.DrinkRepository
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCocktailApi(@ApplicationContext appContext: Context): CocktailApi {
        val client = OkHttpClient.Builder()
            .addInterceptor(
                ChuckerInterceptor.Builder(appContext)
                    .collector(ChuckerCollector(appContext))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CocktailApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDrinkRepository(api: CocktailApi, drinkCardDao: DrinkCardDao): DrinkRepository {
        return DrinkRepositoryImpl(api, drinkCardDao)
    }

    @Provides
    @Singleton
    fun provideIODispatcher() = Dispatchers.IO

    @Provides
    @Singleton
    fun provideDrinkCardDao(database: MixdUpDatabase): DrinkCardDao {
        return database.drinkCardDao()
    }

    @Provides
    @Singleton
    fun provideMixdUpDatabase(@ApplicationContext context: Context): MixdUpDatabase {
        return MixdUpDatabase.getInstance(context)
    }
}