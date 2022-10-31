package com.maxssoft.mytappoints.domain.interactor

import com.maxssoft.mytappoints.domain.model.Point
import com.maxssoft.mytappoints.domain.repository.PointRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Имплементация интерактора для роаботы с точками
 *
 * @author Сидоров Максим on 30.10.2022
 */
class PointInteractorImpl(
    private val repository: PointRepository,
) : PointInteractor {

    override suspend fun requestPoints(maxCount: Int): Result<List<Point>> = withContext(Dispatchers.Default) {
        repository.getPoints(maxCount)
    }
}
