package com.maxssoft.mytappoints.presentation.screen.query

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maxssoft.mytappoints.domain.interactor.PointInteractor
import com.maxssoft.mytappoints.presentation.screen.query.model.PointsQueryInputState
import com.maxssoft.mytappoints.presentation.screen.query.model.PointsQueryViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel для экрана запроса точек
 *
 * @author Сидоров Максим on 30.10.2022
 */
class PointsQueryViewModel(
    private val pointInteractor: PointInteractor
): ViewModel() {

    private val _viewState = MutableStateFlow<PointsQueryViewState>(PointsQueryViewState.InputQuery)
    private val _inputCountState = MutableStateFlow<PointsQueryInputState>(PointsQueryInputState.None)

    /**
     * Флоу состояний экрана [PointsQueryViewState]
     */
    val viewState: StateFlow<PointsQueryViewState> = _viewState

    /**
     * Флоу состояния ввод количества точек и доступности кнопки запуска запроса
     */
    val inputCountState: StateFlow<PointsQueryInputState> = _inputCountState

    /**
     * Событие измененгия текста поля ввода количества точек
     */
    fun onInputCountChange(countText: CharSequence?) {
        Log.d("PointsQueryViewModel", "onInputCountChange(countText = $countText)")
        _inputCountState.value = when(countText?.toInt()) {
            null -> PointsQueryInputState.ErrorEmpty
            !in 1 .. MAX_QUERY_COUNT -> PointsQueryInputState.ErrorInterval
            else -> PointsQueryInputState.Success
        }
    }

    /**
     * Событие нажатия на кнопку запуска запроса
     */
    fun onRunButtonClick(countText: CharSequence?) {
        countText?.toInt()?.let { count -> _viewState.value = PointsQueryViewState.RunQuery(count) }
    }

    /**
     * Вызывается, когда происходит переход на другой экран
     * сбрасывает состояние вьюшки на начальное значение [PointsQueryViewState.InputQuery]
     */
    fun onLeaveScreen() {
        _viewState.value = PointsQueryViewState.InputQuery
        _inputCountState.value = PointsQueryInputState.None
    }

    private fun CharSequence.toInt(): Int? = runCatching { Integer.parseInt(this.toString()) }.getOrDefault( null)

    class Factory(private val pointInteractor: PointInteractor) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            PointsQueryViewModel(pointInteractor) as T
    }
}

private const val MAX_QUERY_COUNT = 1000
