package com.baimsg.qstool.ui.change

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baimsg.qstool.ROUTER_KEY_UIN
import com.baimsg.qstool.data.db.daos.CookieRecordDao
import com.baimsg.qstool.data.domain.repositories.AccountsDataResource
import com.baimsg.qstool.data.models.CheckRisk
import com.baimsg.qstool.data.models.entities.CookieRecord
import com.baimsg.qstool.utils.extensions.stateInDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Create by Baimsg on 2023/4/3
 *
 **/
@HiltViewModel
class ChangeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val cookieRecordDao: CookieRecordDao,
    private val accountsDataResource: AccountsDataResource,
) : ViewModel() {

    private val _uin = savedStateHandle.getStateFlow(ROUTER_KEY_UIN, 0L)

    private val _test = MutableStateFlow("")

    private val _checkRisk = MutableStateFlow(CheckRisk())

    private val _cookieRecord = MutableStateFlow<CookieRecord?>(null)

    private val pendingAction = MutableSharedFlow<ChangeAction>()

    val viewState =
        combine(_uin, _cookieRecord, _checkRisk, _test, ::ChangeViewState).stateInDefault(
            viewModelScope, ChangeViewState.Empty
        )

    init {
        viewModelScope.launch {
            _cookieRecord.value = cookieRecordDao.observeEntriesById("${_uin.value}").firstOrNull()

            _cookieRecord.collectLatest {
                it?.cookies?.let { cookies ->
                    _checkRisk.value =
                        accountsDataResource.chkRisk(cookies).getOrDefault(CheckRisk())

                    _checkRisk.collectLatest { risk ->
                        _test.value = accountsDataResource.queryVerifyMethod(risk.ticket, cookies)
                            .getOrDefault("fail")
                    }
                }
            }

            pendingAction.collectLatest { action ->
//                when (action) {
//
//                }
            }
        }
    }

    fun submitAction(action: ChangeAction) {
        viewModelScope.launch {
            pendingAction.emit(action)
        }
    }
}