package com.baimsg.qstool.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baimsg.qstool.data.domain.repositories.CookieDataResource
import com.baimsg.qstool.data.models.entities.CookieRecord
import com.baimsg.qstool.utils.extensions.stateInDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Create by Baimsg on 2023/4/1
 *
 **/
@HiltViewModel
class HomeViewModel @Inject constructor(private val cookieDataResource: CookieDataResource) :
    ViewModel() {

    private val pendingAction: MutableSharedFlow<HomeAction> = MutableSharedFlow()

    private val _showCookieRecord = MutableStateFlow(false)

    private val _cookieRecords = MutableStateFlow<List<CookieRecord>>(emptyList())

    val viewState: StateFlow<HomeViewState> = combine(
        _showCookieRecord, _cookieRecords, ::HomeViewState
    ).stateInDefault(viewModelScope, HomeViewState.EMPTY)

    init {
        viewModelScope.launch {
            loadCookieRecords()

            pendingAction.collectLatest { action ->
                when (action) {
                    is HomeAction.RefreshCookieRecord -> {
                        loadCookieRecords()
                    }
                }
            }
        }
    }

    private suspend fun loadCookieRecords() {
        _cookieRecords.value = cookieDataResource.cookies()
    }

    fun submitAction(action: HomeAction) {
        viewModelScope.launch {
            pendingAction.emit(action)
        }
    }
}