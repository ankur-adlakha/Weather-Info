package com.ankuradlakha.weather.di

import com.ankuradlakha.weather.data.AppCache
import com.ankuradlakha.weather.data.AppDatabase
import com.ankuradlakha.weather.data.repositories.LocationRepository
import com.ankuradlakha.weather.network.API
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    internal fun providesLocationRepository(
        appDatabase: AppDatabase, api: API,appCache: AppCache
    ) =
        LocationRepository(appDatabase, api,appCache)
}