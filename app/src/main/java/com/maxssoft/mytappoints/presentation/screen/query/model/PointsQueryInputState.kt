package com.maxssoft.mytappoints.presentation.screen.query.model

/**
 * Стейты поля ввода количества точек
 *
 * @author Сидоров Максим on 30.10.2022
 */
sealed class PointsQueryInputState(val runEnabled: Boolean) {

    object None: PointsQueryInputState(runEnabled = false)

    object Success: PointsQueryInputState(runEnabled = true)

    object ErrorInterval: PointsQueryInputState(runEnabled = false)

    object ErrorEmpty: PointsQueryInputState(runEnabled = false)
}
