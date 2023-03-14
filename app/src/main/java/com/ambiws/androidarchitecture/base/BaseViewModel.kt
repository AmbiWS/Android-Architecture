package com.ambiws.androidarchitecture.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ambiws.androidarchitecture.base.navigation.NavigationCommand
import com.ambiws.androidarchitecture.base.navigation.ViewModelNavigation
import com.ambiws.androidarchitecture.core.network.adapters.ExceptionParser
import com.ambiws.androidarchitecture.core.network.adapters.model.NetworkErrorData
import com.ambiws.androidarchitecture.core.network.adapters.model.exceptions.UnauthorizedServerError
import com.ambiws.androidarchitecture.utils.loge
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), KoinComponent {

    /*
     *  Navigation
     */

    protected val navigation by lazy { ViewModelNavigation() }
    val navigationCommand: LiveData<NavigationCommand> by lazy { navigation.navigationCommand }

    fun navigateBack(hideKeyboard: Boolean = true) = navigation.navigateBack(hideKeyboard)

    /*
     *  Data
     */

    private val exceptionParser: ExceptionParser by inject()
    private val defaultExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        loge(throwable.message, throwable)
        setError(throwable)
    }

    protected val mainContext: CoroutineContext = Dispatchers.Main
    protected val ioContext: CoroutineContext = Dispatchers.IO

    private val _stateLiveEvent: MutableLiveData<UiState> = MutableLiveData()
    val stateLiveEvent: LiveData<UiState> = _stateLiveEvent

    protected fun setError(throwable: Throwable) {
        when (throwable) {
            is UnauthorizedServerError -> {
                // TODO implement logout or authorizer interceptor
                navigation.navigateBackToStart(hideKeyboard = true)
            }
            else -> {
                _stateLiveEvent.value = UiState.Error(exceptionParser.parseError(throwable))
            }
        }
    }

    /*
     *  Core
     */

    protected fun launch(
        coroutineContext: CoroutineContext = mainContext,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        // TODO check behaviour on collect (endless loading?)
        return viewModelScope.launch(context = coroutineContext + defaultExceptionHandler) {
            _stateLiveEvent.value = UiState.Loading
            this.block()
            _stateLiveEvent.value = UiState.Success
        }
    }
}

sealed class UiState {
    object Loading : UiState()
    object Success : UiState()
    data class Error(val error: NetworkErrorData) : UiState()
}
