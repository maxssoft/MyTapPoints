package com.maxssoft.mytappoints.presentation.screen.query.model

/**
 * Состояния экрана запроса точек
 *
 * @author Сидоров Максим on 30.10.2022
 */
sealed class PointsQueryViewState {
    /**
     * Начальное состояние экрана (ввод количества и кнопка запуска запроса)
     */
    object InputQuery: PointsQueryViewState()

    /**
     * Запуск запроса на выполнение
     * @param queryCount количество записей в запросе
     */
    data class RunQuery(val queryCount: Int): PointsQueryViewState()
}