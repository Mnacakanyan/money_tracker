package com.money.tracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.money.tracker.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {


    val greetingCompleted: StateFlow<Boolean?> = userPreferencesRepository.greetingCompletedFlow
        .map { it as Boolean? }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun setGreetingCompleted(completed: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setGreetingCompleted(completed)
        }
    }
}
