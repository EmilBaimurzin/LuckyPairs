package com.lucky.game.ui.pairs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucky.game.domain.other.PairsItem
import com.lucky.game.domain.other.PairsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PairsViewModel: ViewModel() {
    private val repository = PairsRepository()
    private var foundPairs = mutableListOf<Int>()
    private var gameScope = CoroutineScope(Dispatchers.Default)
    var gameState = true
    var winCallback: (() -> Unit)? = null
    var pauseState = false
    private val _list = MutableLiveData(
        repository.generateList(24)
    )
    val list: LiveData<List<PairsItem>> = _list

    private val _timer = MutableLiveData(0)
    val timer: LiveData<Int> = _timer

    fun startTimer() {
        gameScope = CoroutineScope(Dispatchers.Default)
        gameScope.launch {
            while (true) {
                delay(1000)
                _timer.postValue(_timer.value!! + 1)
            }
        }
    }

    fun stopTimer() {
        gameScope.cancel()
    }

    fun openItem(index: Int) {
        viewModelScope.launch {
            val newList = _list.value!!
            newList[index].openAnimation = true
            _list.postValue(newList)
            delay(410)
            newList[index].openAnimation = false
            newList[index].isOpened = true
            val filteredList = newList.filter { it.isOpened && !foundPairs.contains(it.value)}
            if (filteredList.size == 2) {
                val isSame = filteredList.distinct()
                if (isSame.size == 1) {
                    foundPairs.add(newList[index].value)
                    if (foundPairs.size == 24 / 2) {
                        winCallback?.invoke()
                    }
                } else {
                    val newFilteredList = newList.filter { it.isOpened && !foundPairs.contains(it.value)}
                    newFilteredList.forEach {
                        newList[newList.indexOf(it)].closeAnimation = true
                    }
                    _list.postValue(newList)
                    delay(410)
                    newFilteredList.map { it.closeAnimation = true }
                    newFilteredList.forEach {
                        newList[newList.indexOf(it)].closeAnimation = false
                        newList[newList.indexOf(it)].isOpened = false
                    }
                    _list.postValue(newList)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        gameScope.cancel()
    }
}