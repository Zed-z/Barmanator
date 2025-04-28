package pl.poznan.put.barmanator.utils

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TimerViewModel(initialTime: Int) : ViewModel() {
    var initialTime = initialTime

    private val _time: MutableStateFlow<Int> = MutableStateFlow(initialTime)
    val time: StateFlow<Int> = _time

    fun setTime(time: Int, overrideInitialTime: Boolean = false) {
        _time.value = time
        if (overrideInitialTime) {
            initialTime = time
        }
    }

    fun resetTime() {
        _time.value = initialTime;
    }

    private val _running: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val running: StateFlow<Boolean> = _running

    fun setRunning(running: Boolean) {
        _running.value = running;
    }

}