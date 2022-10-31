package com.maxssoft.mytappoints.presentation.screen.pointlist

import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import com.maxssoft.mytappoints.R
import com.maxssoft.mytappoints.databinding.FragmentPointsBinding
import com.maxssoft.mytappoints.di.Di
import com.maxssoft.mytappoints.presentation.screen.error.PointsErrorFragment
import com.maxssoft.mytappoints.presentation.screen.pointlist.adapter.ChartDelegateAdapter
import com.maxssoft.mytappoints.presentation.screen.pointlist.adapter.HeaderDelegateAdapter
import com.maxssoft.mytappoints.presentation.screen.pointlist.adapter.PointsDelegateAdapter
import com.maxssoft.mytappoints.presentation.screen.pointlist.model.Chart
import com.maxssoft.mytappoints.presentation.screen.pointlist.model.PointsViewState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Фрагмент, отображающий точки
 */
class PointsFragment : Fragment() {

    private lateinit var binding: FragmentPointsBinding
    private val viewModel by viewModels<PointsViewModel> { Di.ViewModels.pointsViewModelFactory }

    private val adapter = createAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate(arguments?.getInt(QUERY_COUNT_ARG) ?: DEFAULT_COUNT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentPointsBinding.inflate(inflater, container, false)
        binding.initView()
        return binding.root
    }

    private fun FragmentPointsBinding.initView() {
        viewModel.viewState
            .onEach(::handleViewState)
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .launchIn(viewLifecycleOwner.lifecycleScope)

        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = adapter
    }

    private fun handleViewState(viewState: PointsViewState) {
        Log.d("PointsFragment", "handleViewState(viewState = $viewState)")
        when(viewState) {
            is PointsViewState.LoadError -> {
                val bundle = bundleOf(PointsErrorFragment.ERROR_TEXT_ARG to viewState.error)
                findNavController().popBackStack()
                findNavController().navigate(R.id.action_points_loading_error, bundle)
            }
            is PointsViewState.LoadSuccess -> {
                // формируем список для [CompositeDelegateAdapter] вида:
                // Заголовк, [Chart], Загловок, [Point], [Point], [Point]
                val items = listOf(
                    getString(R.string.header_points_chart),
                    Chart(viewState.points),
                    getString(R.string.header_points_list),
                ).plus(viewState.points)
                adapter.swapData(items)

                binding.progressBar.isVisible = false
                binding.rv.isVisible = true
            }
            PointsViewState.Loading -> {
                binding.progressBar.isVisible = true
                binding.rv.isVisible = false

            }
            PointsViewState.None -> Unit
        }
    }

    private fun createAdapter() =
        CompositeDelegateAdapter(
            HeaderDelegateAdapter(),
            PointsDelegateAdapter(),
            ChartDelegateAdapter()
        )

    companion object {
        private const val DEFAULT_COUNT = 100
        const val QUERY_COUNT_ARG = "queryCount"
    }
}