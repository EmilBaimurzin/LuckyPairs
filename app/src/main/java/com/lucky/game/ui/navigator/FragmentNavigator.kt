package com.lucky.game.ui.navigator

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.lucky.game.R
import com.lucky.game.core.library.GameFragment
import com.lucky.game.databinding.FragmentNavigatorBinding

class FragmentNavigator : GameFragment<FragmentNavigatorBinding>(FragmentNavigatorBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            play.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentMain_to_fragmentPairs)
            }
            exit.setOnClickListener {
                requireActivity().finish()
            }
        }

        binding.privacyText.setOnClickListener {
            requireActivity().startActivity(
                Intent(
                    ACTION_VIEW,
                    Uri.parse("https://www.google.com")
                )
            )
        }
    }
}