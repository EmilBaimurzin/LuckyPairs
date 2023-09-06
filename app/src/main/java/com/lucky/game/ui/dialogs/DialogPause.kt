package com.lucky.game.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.lucky.game.R
import com.lucky.game.core.library.ViewBindingDialog
import com.lucky.game.databinding.DialogPauseBinding
import com.lucky.game.ui.pairs.CallbackViewModel

class DialogPause: ViewBindingDialog<DialogPauseBinding>(DialogPauseBinding::inflate) {
    private val viewModel: CallbackViewModel by activityViewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.Dialog_No_Border)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.setCancelable(false)
        dialog!!.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                viewModel.callback?.invoke()
                findNavController().popBackStack()
                true
            } else {
                false
            }
        }

        binding.play.setOnClickListener {
            viewModel.callback?.invoke()
            findNavController().popBackStack()
        }
    }
}