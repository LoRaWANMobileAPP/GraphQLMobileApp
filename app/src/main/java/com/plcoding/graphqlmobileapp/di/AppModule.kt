package com.plcoding.graphqlmobileapp.di

import com.apollographql.apollo3.ApolloClient
import com.plcoding.graphqlmobileapp.data.ApolloPointClient
import com.plcoding.graphqlmobileapp.domain.GetPointsUseCase
import com.plcoding.graphqlmobileapp.domain.PointClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://iot.dimensionfour.io/graph")
            .addHttpHeader("x-tenant-id","lo-ra-wan-mobile-app")
            .addHttpHeader("x-tenant-key","553a6492ce6a51194f74c862")
            .build()
    }

    @Provides
    @Singleton
    fun providePointClient(apolloClient: ApolloClient): PointClient {
        return ApolloPointClient(apolloClient)
    }

    @Provides
    @Singleton
    fun providePointsUseCase(pointClient: PointClient): GetPointsUseCase {
        return GetPointsUseCase(pointClient)
    }
}