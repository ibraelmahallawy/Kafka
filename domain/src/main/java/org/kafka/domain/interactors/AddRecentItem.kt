package org.kafka.domain.interactors

import com.kafka.data.dao.ItemDetailDao
import com.kafka.data.entities.RecentItem
import kotlinx.coroutines.withContext
import org.kafka.base.AppCoroutineDispatchers
import org.kafka.base.domain.Interactor
import javax.inject.Inject

class AddRecentItem @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    private val itemDetailDao: ItemDetailDao,
    private val updateRecentItem: UpdateRecentItem
) : Interactor<AddRecentItem.Params>() {

    override suspend fun doWork(params: Params) {
        withContext(dispatchers.io) {
            val item = itemDetailDao.get(params.itemId)
            updateRecentItem.execute(RecentItem.fromItem(item))
        }
    }

    data class Params(val itemId: String)
}
