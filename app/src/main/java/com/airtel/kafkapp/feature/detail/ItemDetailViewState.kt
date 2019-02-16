package com.airtel.kafkapp.feature.detail

import com.airbnb.mvrx.MvRxState
import com.airtel.data.entities.Item
import com.airtel.data.entities.ItemDetail

/**
 * @author Vipul Kumar; dated 27/12/18.
 */
data class ItemDetailViewState(
    val itemId: String,
    val itemDetail: ItemDetail? = null,
    val itemsByCreator: List<Item>? = null,
    val isLoading: Boolean = false
) : MvRxState {
    constructor(args: ItemDetailFragment.Arguments) : this(args.id)
}
