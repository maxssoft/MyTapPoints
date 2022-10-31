package com.maxssoft.mytappoints.data.model

/**
 * Модель ответа сервера для запроса точек
 *
 * @author Сидоров Максим on 30.10.2022
 */
data class PointsResponse(val points: List<PointData>)

data class PointData(val x: Double, val y: Double)

