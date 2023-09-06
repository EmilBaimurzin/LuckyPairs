package com.lucky.game.ui.pairs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.lucky.game.R
import com.lucky.game.core.library.GameFragment
import com.lucky.game.databinding.FragmentPairsBinding
import com.lucky.game.domain.adapter.PairsAdapter

class FragmentPairs: GameFragment<FragmentPairsBinding>(FragmentPairsBinding::inflate) {
    private lateinit var pairsAdapter: PairsAdapter
    private val viewModel: PairsViewModel by viewModels()
    private val callbackViewModel: CallbackViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        viewModel.winCallback = {
            end()
        }

        callbackViewModel.callback = {
            viewModel.pauseState = false
            viewModel.startTimer()
        }

        binding.replay.setOnClickListener {
            findNavController().popBackStack()
            findNavController().navigate(R.id.action_fragmentMain_to_fragmentPairs)
        }

        binding.pause.setOnClickListener {
            viewModel.pauseState = true
            viewModel.stopTimer()
            findNavController().navigate(R.id.action_fragmentPairs_to_dialogPause)
        }

        binding.menu.setOnClickListener {
            findNavController().popBackStack(R.id.fragmentMain, false, false)
        }

        viewModel.list.observe(viewLifecycleOwner) {
            pairsAdapter.items = it.toMutableList()
            pairsAdapter.notifyDataSetChanged()
        }
        viewModel.timer.observe(viewLifecycleOwner) { totalSecs ->
            val minutes = (totalSecs % 3600) / 60;
            val seconds = totalSecs % 60;

            binding.time.text = String.format("%02d:%02d", minutes, seconds)
        }

        if (viewModel.gameState && !viewModel.pauseState) {
            viewModel.startTimer()
        }

    }

    private fun end() {
        viewModel.stopTimer()
        viewModel.gameState = false
        findNavController().navigate(
            FragmentPairsDirections.actionFragmentPairsToDialogWinner(viewModel.timer.value!!)
        )
    }

    private fun initAdapter() {
        pairsAdapter = PairsAdapter {
            if (viewModel.list.value!!.find { it.closeAnimation } == null && viewModel.list.value!!.find { it.openAnimation } == null) {
                viewModel.openItem(it)
            }
        }
        with(binding.gameRv) {
            adapter = pairsAdapter
            layoutManager = GridLayoutManager(requireContext(), 4).also {
                it.orientation = GridLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
            itemAnimator = null
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopTimer()
    }
}