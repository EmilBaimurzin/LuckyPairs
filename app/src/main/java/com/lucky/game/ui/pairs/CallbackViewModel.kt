package com.lucky.game.ui.pairs

import androidx.lifecycle.ViewModel

class CallbackViewModel : ViewModel() {
    var callback: (() -> Unit)? = null
}