/*
 * Copyright (C) 2021, Alashov Berkeli
 * All rights reserved.
 */
package tm.alashow.datmusic.downloader

import com.kafka.downloader.R
import tm.alashow.datmusic.downloader.DownloaderEvent.ChooseDownloadsLocation.message

sealed class DownloaderEvent : UiMessageConvertable {
    data object ChooseDownloadsLocation : DownloaderEvent() {
        val message = DownloadMessage.Resource(R.string.downloader_enqueue_downloadsLocationNotSelected)
    }

    data class DownloaderFetchError(val error: Throwable) : DownloaderEvent()

    override fun toUiMessage() = when (this) {
        is ChooseDownloadsLocation -> message
        is DownloaderFetchError -> DownloadMessage.Error(this.error)
    }
}
