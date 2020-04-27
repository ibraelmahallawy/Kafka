package com.kafka.ui.player

import com.kafka.data.entities.ItemDetail
import com.kafka.data.model.item.File
import com.kafka.ui.BaseViewState

/**
 * @author Vipul Kumar; dated 27/12/18.
 */
data class PlayerViewState(
    val isLoading: Boolean = false,
    val playerData: PlayerData? = null,
    val itemDetail: ItemDetail? = null
) : BaseViewState

data class PlayerData(
    val contentId: String,
    val title: String? = "aah ko chaahiye ik umr",
    val subtitle: String? = "Mirza Ghalib"
)
