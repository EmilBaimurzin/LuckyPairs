package com.lucky.game.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lucky.game.R
import com.lucky.game.core.library.ViewBindingDialog
import com.lucky.game.databinding.DialogWinnerBinding

class DialogWinner: ViewBindingDialog<DialogWinnerBinding>(DialogWinnerBinding::inflate) {
    private val args: DialogWinnerArgs by navArgs()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.Dialog_No_Border)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.setCancelable(false)
        dialog!!.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                findNavController().popBackStack(R.id.fragmentMain, false, false)
                true
            } else {
                false
            }
        }

        val minutes = (args.time % 3600) / 60;
        val seconds = args.time % 60;

        binding.time.text = String.format("%02d:%02d", minutes, seconds)

        binding.replay.setOnClickListener {
            findNavController().popBackStack(R.id.fragmentMain, false, false)
            findNavController().navigate(R.id.action_fragmentMain_to_fragmentPairs)
        }

        binding.close.setOnClickListener {
            findNavController().popBackStack(R.id.fragmentMain, false, false)
        }
    }
}