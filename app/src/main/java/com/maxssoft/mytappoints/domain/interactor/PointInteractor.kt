package com.maxssoft.mytappoints.domain.interactor

import com.maxssoft.mytappoints.domain.model.Point

/**
 * Интерактор для работы с точками
 *
 * @author Сидоров Максим on 30.10.2022
 */
interface PointInteractor {

    /**
     * Запрашивает точки с сервера
     * @param maxCount максимальное количество точек в ответе
     */
    suspend fun requestPoints(maxCount: Int): Result<List<Point>>
}