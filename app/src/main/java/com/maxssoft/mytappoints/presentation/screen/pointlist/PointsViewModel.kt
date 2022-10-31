package com.maxssoft.mytappoints.presentation.screen.pointlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.maxssoft.mytappoints.domain.interactor.PointInteractor
import com.maxssoft.mytappoints.presentation.screen.pointlist.model.PointsViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel для экрана, отображающего список точек
 *
 * @author Сидоров Максим on 30.10.2022
 */
class PointsViewModel(
    private val pointInteractor: PointInteractor
): ViewModel() {

    private val _viewState = MutableStateFlow<PointsViewState>(PointsViewState.None)

    /**
     * Флоу состояний экрана [PointsViewState]
     */
    val viewState: StateFlow<PointsViewState> = _viewState

    /**
     * При создании view вызывает загрузку данных
     * @param maxCount максимальное количество точек в ответе сервера
     */
    fun onCreate(maxCount: Int) {
        Log.d("PointsViewModel", "onCreate(maxCount = $maxCount)")
        // Вызывает загрузку данных только один раз при первом создании вьюшки
        if (_viewState.value == PointsViewState.None) {
            viewModelScope.launch {
                _viewState.value = PointsViewState.Loading

                val result = pointInteractor.requestPoints(maxCount = maxCount)
                if (result.isSuccess) {
                    _viewState.value = PointsViewState.LoadSuccess(result.getOrDefault(emptyList()))
                } else {
                    _viewState.value = PointsViewState.LoadError(
                        error = result.exceptionOrNull()?.localizedMessage ?: UNKNOWN_INTERNAL_ERROR
                    )
                }
            }
        }
    }

    class Factory(private val pointInteractor: PointInteractor) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            PointsViewModel(pointInteractor) as T
    }
}

private const val UNKNOWN_INTERNAL_ERROR = "Unknown internal error"
