package com.maxssoft.mytappoints.presentation.screen.pointlist.adapter

import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter
import com.maxssoft.mytappoints.databinding.GraphItemBinding
import com.maxssoft.mytappoints.presentation.screen.pointlist.model.Chart

/**
 * Адаптер для графика точек
 *
 * @author Сидоров Максим on 31.10.2022
 */
class ChartDelegateAdapter(): ViewBindingDelegateAdapter<Chart, GraphItemBinding>(GraphItemBinding::inflate) {

    override fun isForViewType(item: Any): Boolean = item is Chart

    override fun Chart.getItemId(): Any = this

    override fun GraphItemBinding.onBind(item: Chart) {
        chartView.addSeries(
            LineGraphSeries(
                item.points.map { DataPoint(it.x, it.y) }.toTypedArray()
            )
        )

        // set manual X bounds
        chartView.viewport.isXAxisBoundsManual = true
        if (item.points.isNotEmpty()) {
            chartView.viewport.setMinX(0.0)
            chartView.viewport.setMaxX(10.0)
        }
        // enable scrolling
        chartView.viewport.isScrollable = true
    }
}