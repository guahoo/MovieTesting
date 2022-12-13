package com.test_task.movie_test.di


import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test_task.movie_test.BuildConfig
import com.test_task.movie_test.data.network.AppApi
import com.test_task.movie_test.extensions.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.internal.Util
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
      @Module
      @InstallIn(SingletonComponent::class)
      class NetworkModule {
          @Provides
          @Singleton
          fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
              HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

          @Provides
          @Singleton
          fun provideOkHttpClient(
              @ApplicationContext context: Context,
              httpLoggingInterceptor: HttpLoggingInterceptor
          ): OkHttpClient =
              OkHttpClient.Builder().apply {

                  readTimeout(60, TimeUnit.SECONDS)
                  connectTimeout(60, TimeUnit.SECONDS)
                  addInterceptor(httpLoggingInterceptor)


                  addInterceptor(Interceptor { chain ->
                      val request: Request.Builder = chain.request().newBuilder()
                      val url = chain.request().url().newBuilder().addQueryParameter(
                          "api-key", BuildConfig.API_KEY
                      ).build()
                      request.url(url)
                      return@Interceptor chain.proceed(request.build())
                  })
                  protocols(Util.immutableList(Protocol.HTTP_1_1))
                  interceptors().add(httpLoggingInterceptor)
                  interceptors().add(NetworkConnectionInterceptor(context))
              }.build()

          @Provides
          @Singleton
          fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
              Retrofit.Builder().apply {
                  baseUrl(BuildConfig.BASE_URL)
                  addConverterFactory(GsonConverterFactory.create(gson))
                  client(okHttpClient)
              }.build()

          @Singleton
          @Provides
          fun provideAppApi(retrofit: Retrofit): AppApi =
              retrofit.create(AppApi::class.java)


          @Provides
          @Singleton
          fun provideGson(): Gson =
              GsonBuilder()
                  .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                  .serializeNulls()
                  .setLenient()
                  .create()

      }


//        val httpLogging = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//
//        }
//
//
//
//        val gson = GsonBuilder()
//            .setLenient()
//            .serializeNulls()
//            .setLongSerializationPolicy(LongSerializationPolicy.STRING)
//            .create()
//
//        val okHttpClient =
//            OkHttpClient.Builder()
//
//                .readTimeout(60, TimeUnit.SECONDS)
//                .connectTimeout(60, TimeUnit.SECONDS)
//                .addInterceptor(httpLogging)
//
//
//                .addInterceptor(Interceptor { chain ->
//                    val request: Request.Builder = chain.request().newBuilder()
//                    val url =  chain.request().url().newBuilder().addQueryParameter(
//                        "api-key", BuildConfig.API_KEY
//                    ).build()
//                    request.url(url)
//                    return@Interceptor chain.proceed(request.build())
//                })
//                .protocols(Util.immutableList(Protocol.HTTP_1_1))
//                .build()
//
//
//        val appApi = Retrofit.Builder()
//
//            .addConverterFactory(ScalarsConverterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .client(okHttpClient)
//            .baseUrl(BuildConfig.BASE_URL)
//            .build().create(AppApi::class.java)
//
//
//    factory<Gson> { gson }
//    factory { httpLogging }
//    factory<OkHttpClient> { okHttpClient }
//    factory<AppApi> { appApi }
//
//





