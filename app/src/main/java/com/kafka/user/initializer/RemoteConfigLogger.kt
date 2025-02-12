package com.kafka.user.initializer

import com.kafka.remote.config.RECOMMENDATION_ROW_ENABLED
import com.kafka.remote.config.RemoteConfig
import com.kafka.remote.config.isRecommendationRowEnabled
import com.kafka.analytics.logger.Analytics
import com.kafka.base.AppInitializer
import com.kafka.base.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class RemoteConfigLogger @Inject constructor(
    private val remoteConfig: RemoteConfig,
    private val analytics: Analytics,
) : AppInitializer {
    override fun init() {
        analytics.log {
            remoteConfigValue(RECOMMENDATION_ROW_ENABLED, remoteConfig.isRecommendationRowEnabled())
        }
    }
}
