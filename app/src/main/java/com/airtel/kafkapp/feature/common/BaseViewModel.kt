package com.airtel.kafkapp.feature.common

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.airtel.data.data.config.kodeinInstance
import com.airtel.data.data.config.logging.Logger
import com.airtel.data.model.data.Resource
import com.airtel.kafkapp.BuildConfig
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.kodein.di.generic.instance

/**
 * @author Vipul Kumar; dated 22/10/18.
 *
 * ViewModel which exposes a [CompositeDisposable] and [Job]
 * which are automatically cleared/stopped when the ViewModel is cleared.
 *
 * Ideally, a code that triggers a change in viewState looks like
 *
 * flowable.toObservable()
 * .subscribeOn(schedulers.io)
 * .execute { state }
 *
 */
open class BaseViewModel<S : MvRxState>(
    initialState: S
) : BaseMvRxViewModel<S>(initialState, debugMode = BuildConfig.DEBUG), IBaseViewModel {

    init {
        logStateChanges()
    }

    private val job = Job()

    protected val logger: Logger by kodeinInstance.instance()
    override val disposables = CompositeDisposable()
    override val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
        job.cancel()
    }
}
