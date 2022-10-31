package com.maxssoft.mytappoints.presentation.screen.pointlist.adapter

import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter
import com.maxssoft.mytappoints.databinding.PointItemBinding
import com.maxssoft.mytappoints.domain.model.Point

/**
 * Адаптер для точки (список точек)
 *
 * @author Сидоров Максим on 31.10.2022
 */
class PointsDelegateAdapter(): ViewBindingDelegateAdapter<Point, PointItemBinding>(PointItemBinding::inflate) {

    override fun isForViewType(item: Any): Boolean = item is Point

    override fun Point.getItemId(): Any = this

    override fun PointItemBinding.onBind(item: Point) {
        xText.text = "X = ${item.x}"
        yText.text = "Y = ${item.y}"
    }
}