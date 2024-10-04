package org.rekhta.ui.profile.feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import com.kafka.base.domain.InvokeSuccess
import com.kafka.base.extensions.stateInDefault
import org.kafka.common.ObservableLoadingCounter
import org.kafka.common.asUiMessage
import org.kafka.common.collectStatus
import org.kafka.common.snackbar.SnackbarManager
import com.kafka.domain.interactors.UpdateFeedback
import com.kafka.domain.observers.ObserveUser
import org.kafka.navigation.Navigator
import javax.inject.Inject

class FeedbackViewModel @Inject constructor(
    private val updateFeedback: UpdateFeedback,
    private val snackbarManager: SnackbarManager,
    private val navigator: Navigator,
    observeUser: ObserveUser,
) : ViewModel() {
    private val loadingCounter = ObservableLoadingCounter()

    val state: StateFlow<FeedbackViewState> = combine(
        observeUser.flow.map { it?.email },
        loadingCounter.observable,
        ::FeedbackViewState
    ).stateInDefault(scope = viewModelScope, initialValue = FeedbackViewState())

    fun onBackPressed() {
        navigator.goBack()
    }

    fun sendFeedback(text: String, email: String) {
        viewModelScope.launch {
            loadingCounter.addLoader()
            updateFeedback(UpdateFeedback.Params(text, email))
                .collectStatus(loadingCounter, snackbarManager) { status ->
                    if (status == InvokeSuccess) {
                        snackbarManager.addMessage("Thank you for your feedback!".asUiMessage())
                        navigator.goBack()
                    }
                }
        }
    }
}

data class FeedbackViewState(val email: String? = null, val isLoading: Boolean = false)
