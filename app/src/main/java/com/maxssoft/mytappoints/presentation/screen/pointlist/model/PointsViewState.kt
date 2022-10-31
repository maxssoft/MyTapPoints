package com.maxssoft.mytappoints.presentation.screen.pointlist.model

import com.maxssoft.mytappoints.domain.model.Point

/**
 * Состояния экрана со списком точек
 *
 * @author Сидоров Максим on 30.10.2022
 */
sealed class PointsViewState {
    /**
     * Начальное состояние вьюшки
     */
    object None: PointsViewState()

    /**
     * Состояние процесса загрузки данных
     */
    object Loading: PointsViewState()

    /**
     * Данные загружены успешно
     * @property points список загруженных точек
     */
    data class LoadSuccess(val points: List<Point>): PointsViewState()

    /**
     * Ошибка загрузки данных
     * @property error текст ошибки сервера
     */
    data class LoadError(val error: String): PointsViewState()
}