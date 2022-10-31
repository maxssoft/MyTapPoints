package com.maxssoft.mytappoints.presentation.screen.error

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.maxssoft.mytappoints.databinding.FragmentPointsErrorBinding

/**
 * Фрагмент, отображающий ошибку загрузки точек
 */
class PointsErrorFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentPointsErrorBinding.inflate(inflater, container, false).initView().root
    }

    private fun FragmentPointsErrorBinding.initView(): FragmentPointsErrorBinding {
        detailsButton.setOnClickListener { errorText.isVisible = true }
        errorText.text = arguments?.getString(ERROR_TEXT_ARG)
        return this
    }

    companion object {
        const val ERROR_TEXT_ARG = "errorText"
    }
}