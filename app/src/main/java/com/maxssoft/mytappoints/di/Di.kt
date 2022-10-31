package com.maxssoft.mytappoints.di

import com.maxssoft.mytappoints.data.api.BASE_URL
import com.maxssoft.mytappoints.data.api.PointApi
import com.maxssoft.mytappoints.data.repository.PointRepositoryImpl
import com.maxssoft.mytappoints.domain.interactor.PointInteractor
import com.maxssoft.mytappoints.domain.interactor.PointInteractorImpl
import com.maxssoft.mytappoints.domain.repository.PointRepository
import com.maxssoft.mytappoints.presentation.screen.pointlist.PointsViewModel
import com.maxssoft.mytappoints.presentation.screen.query.PointsQueryViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * TODO Не хотелось тащить сюда даггер, поэтому сделал простой синглтон с ленивыми провайдерами
 *
 * @author Сидоров Максим on 30.10.2022
 */
object Di {

    /**
     * Клиент [Retrofit]
     */
    private val retrofitClient: Retrofit by lazy(LazyThreadSafetyMode.NONE) {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Retrofit cервис для запросов точек из сети
     */
    private val pointApi: PointApi by lazy(LazyThreadSafetyMode.NONE) { retrofitClient.create(PointApi::class.java) }

    /**
     * Репозиторий для запроса точек с сервера
     */
    private val pointRepository: PointRepository by lazy(LazyThreadSafetyMode.NONE) {
        PointRepositoryImpl(pointApi)
    }

    /**
     * Интерактор для работы с точками
     */
    private val pointInteractor: PointInteractor by lazy(LazyThreadSafetyMode.NONE) {
        PointInteractorImpl(repository = pointRepository)
    }

    object ViewModels {
        /**
         * Фабрика для создания вьюмодели экрана запроса точек [PointsQueryViewModel]
         */
        val pointsQueryViewModelFactory by lazy(LazyThreadSafetyMode.NONE) {
            PointsQueryViewModel.Factory(pointInteractor)
        }

        /**
         * Фабрика для создания вьюмодели экрана списка точек [PointsViewModel]
         */
        val pointsViewModelFactory by lazy(LazyThreadSafetyMode.NONE) {
            PointsViewModel.Factory(pointInteractor)
        }
    }
}