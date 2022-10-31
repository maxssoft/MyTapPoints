package com.maxssoft.mytappoints.data.repository

import com.maxssoft.mytappoints.data.api.PointApi
import com.maxssoft.mytappoints.data.model.PointData
import com.maxssoft.mytappoints.data.model.PointsResponse
import com.maxssoft.mytappoints.domain.model.Point
import com.maxssoft.mytappoints.domain.repository.PointRepository

/**
 * Имплементация репозитория для запроса точек [PointRepository]
 *
 * @author Сидоров Максим on 30.10.2022
 */
class PointRepositoryImpl(
    private val pointApi: PointApi
): PointRepository {

    override suspend fun getPoints(maxCount: Int): Result<List<Point>> {
        return try {
            val response = pointApi.getPoints(maxCount)
            return if (response.isSuccessful) {
                Result.success(
                    response.body().toList()
                        .map { it.toPoint() }
                        .sortedBy { it.x }
                )
            } else {
                Result.failure(
                    Exception("errorCode = ${response.code()}, " +
                        "error message = ${response.errorBody()?.string() ?: "Unknown request error"}"
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun PointsResponse?.toList() = this?.points ?: emptyList()
    private fun PointData.toPoint(): Point = Point(x = this.x, y = this.y)
}