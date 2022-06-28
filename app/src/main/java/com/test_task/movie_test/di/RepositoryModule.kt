package com.test_task.movie_test.di
import com.test_task.movie_test.data.network.AppApi
import com.test_task.movie_test.data.repos.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideCharRepository(
        api: AppApi
    ): MovieRepository {
        return MovieRepositoryImpl(api)
    }

}

//val repositoryModule = module {
//    fun provideCharRepository(
//        api: AppApi
//    ): MovieRepository {
//        return MovieRepositoryImpl(api)
//    }
//
//    single {
//        provideCharRepository(get())
//    }
//}