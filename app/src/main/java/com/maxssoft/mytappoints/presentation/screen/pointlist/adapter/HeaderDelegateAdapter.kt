package com.maxssoft.mytappoints.presentation.screen.pointlist.adapter

import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter
import com.maxssoft.mytappoints.databinding.HeaderItemBinding

/**
 * Адаптер для заголовков секций
 *
 * @author Сидоров Максим on 31.10.2022
 */
class HeaderDelegateAdapter(): ViewBindingDelegateAdapter<String, HeaderItemBinding>(HeaderItemBinding::inflate) {

    override fun isForViewType(item: Any): Boolean = item is String

    override fun String.getItemId(): Any = this

    override fun HeaderItemBinding.onBind(item: String) {
        headerText.text = item
    }
}