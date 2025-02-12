package com.kafka.data.injection

import android.app.Application
import android.os.Debug
import androidx.room.Room
import com.kafka.data.db.KafkaDatabase
import com.kafka.data.db.KafkaRoomDatabase
import me.tatarka.inject.annotations.Provides
import com.kafka.base.ApplicationScope

const val databaseName = "kafka.db"

interface DatabaseModule {
    @Provides
    @ApplicationScope
    fun provideDatabase(context: Application): KafkaRoomDatabase {
        val builder = Room.databaseBuilder(
            context,
            KafkaRoomDatabase::class.java,
            databaseName,
        ).fallbackToDestructiveMigration()
        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }
        return builder.build()
    }

    @Provides
    @ApplicationScope
    fun provideRoomDatabase(bind: KafkaRoomDatabase): KafkaDatabase = bind

    @Provides
    @ApplicationScope
    fun provideItemDao(db: KafkaRoomDatabase) = db.itemDao()

    @Provides
    @ApplicationScope
    fun provideItemDetailDao(db: KafkaRoomDatabase) = db.itemDetailDao()

    @Provides
    @ApplicationScope
    fun provideFileDao(db: KafkaRoomDatabase) = db.fileDao()

    @Provides
    @ApplicationScope
    fun provideSearchConfigurationDao(db: KafkaRoomDatabase) = db.recentSearchDao()

    @Provides
    @ApplicationScope
    fun provideRecentTextDao(db: KafkaRoomDatabase) = db.recentTextDao()

    @Provides
    @ApplicationScope
    fun provideRecentAudioDao(db: KafkaRoomDatabase) = db.recentAudioDao()

    @Provides
    @ApplicationScope
    fun provideDownloadRequestsDao(db: KafkaRoomDatabase) = db.downloadRequestsDao()
}
