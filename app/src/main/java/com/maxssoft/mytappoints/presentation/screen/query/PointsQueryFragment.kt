package com.maxssoft.mytappoints.presentation.screen.query

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.maxssoft.mytappoints.R
import com.maxssoft.mytappoints.databinding.FragmentPointsQueryBinding
import com.maxssoft.mytappoints.di.Di
import com.maxssoft.mytappoints.presentation.screen.pointlist.PointsFragment
import com.maxssoft.mytappoints.presentation.screen.query.model.PointsQueryInputState
import com.maxssoft.mytappoints.presentation.screen.query.model.PointsQueryViewState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Фрагмент для запроса точек
 */
class PointsQueryFragment : Fragment() {

    private lateinit var binding: FragmentPointsQueryBinding
    private val viewModel by viewModels<PointsQueryViewModel> { Di.ViewModels.pointsQueryViewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPointsQueryBinding.inflate(inflater, container, false)
        binding.initView()
        return binding.root
    }

    private fun FragmentPointsQueryBinding.initView() {
        countEdit.addTextChangedListener(
            object : TextWatcher  {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) { viewModel.onInputCountChange(s) }
            }
        )

        runButton.setOnClickListener { viewModel.onRunButtonClick(binding.countEdit.editableText) }

        viewModel.viewState
            .onEach(::handleViewState)
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.inputCountState
            .onEach(::handleInputState)
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleViewState(viewState: PointsQueryViewState) {
        Log.d("PointsQueryFragment", "handleViewState(viewState = $viewState)")
        when(viewState) {
            PointsQueryViewState.InputQuery -> {
                binding.inputCountLayout.isVisible = true
                binding.runButton.isVisible = true
                binding.progressBar.isVisible = false
            }
            is PointsQueryViewState.RunQuery -> {
                val bundle = bundleOf(PointsFragment.QUERY_COUNT_ARG to viewState.queryCount)
                findNavController().navigate(R.id.action_points, bundle)
                viewModel.onLeaveScreen()
            }
        }
    }

    private fun handleInputState(inputState: PointsQueryInputState) {
        Log.d("PointsQueryFragment", "handleInputState(inputState = $inputState)")
        binding.runButton.isEnabled = inputState.runEnabled
        binding.inputCountLayout.error = when(inputState) {
            PointsQueryInputState.ErrorEmpty -> getString(R.string.error_query_count_empty)
            PointsQueryInputState.ErrorInterval -> getString(R.string.error_query_count_interval)
            PointsQueryInputState.None -> null
            PointsQueryInputState.Success -> null
        }
    }
}