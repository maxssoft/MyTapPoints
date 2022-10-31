package com.maxssoft.mytappoints.domain.repository

import com.maxssoft.mytappoints.domain.model.Point

/**
 * Репозиторий для получения списка точек
 *
 * @author Сидоров Максим on 30.10.2022
 */
interface PointRepository {

    /**
     * Запрос точек, возвращает список точек [Point]
     * @param maxCount максимальное количество точек
     */
    suspend fun getPoints(maxCount: Int): Result<List<Point>>
}