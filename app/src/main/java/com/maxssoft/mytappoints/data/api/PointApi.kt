package com.maxssoft.mytappoints.data.api

import com.maxssoft.mytappoints.data.model.PointsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Интерфейс api сервера для получения точек
 *
 * @author Сидоров Максим on 30.10.2022
 */
interface PointApi {
    @GET("points")
    suspend fun getPoints(@Query("count") count: Int): Response<PointsResponse>
}

