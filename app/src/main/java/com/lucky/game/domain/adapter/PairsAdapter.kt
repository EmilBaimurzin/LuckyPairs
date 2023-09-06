package com.lucky.game.domain.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.lucky.game.R
import com.lucky.game.databinding.ItemPairBinding
import com.lucky.game.domain.other.PairsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PairsAdapter(private var onItemClick: ((Int) -> Unit)? = null) : RecyclerView.Adapter<PairsViewHolder>() {
    var items = mutableListOf<PairsItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PairsViewHolder {
        return PairsViewHolder(
            ItemPairBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PairsViewHolder, position: Int) {
        holder.bind(items[position])
        holder.onItemClick = onItemClick
    }
}

class PairsViewHolder(private val binding: ItemPairBinding) : RecyclerView.ViewHolder(binding.root) {
    var onItemClick: ((Int) -> Unit)? = null
    fun bind(item: PairsItem) {
        binding.apply {
            val img = when (item.value) {
                1 -> R.drawable.symbol01
                2 -> R.drawable.symbol02
                3 -> R.drawable.symbol03
                4 -> R.drawable.symbol04
                5 -> R.drawable.symbol05
                6 -> R.drawable.symbol06
                7 -> R.drawable.symbol07
                8 -> R.drawable.symbol08
                9 -> R.drawable.symbol09
                10 -> R.drawable.symbol10
                11-> R.drawable.symbol11
                else -> R.drawable.symbol12
            }
            if (item.isOpened) {
                itemImg.setImageResource(img)
            } else {
                itemImg.setImageDrawable(null)
            }
            if (item.openAnimation) {
                flipImage(binding.root, img)
            }

            if (item.closeAnimation) {
                flipImage(binding.root, null)
            }

            binding.root.setOnClickListener {
                if (!item.openAnimation && !item.closeAnimation && !item.isOpened) {
                    onItemClick?.invoke(adapterPosition)
                }
            }
        }
    }

    private fun flipImage(
        view: View,
        @DrawableRes img: Int?,
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(200)
            if (img != null) {
                binding.itemImg.setImageResource(img)
            } else {
                binding.itemImg.setImageDrawable(null)
            }
        }
        val animatorSet = AnimatorSet()
        val rotateAnimator = ObjectAnimator.ofFloat(view, "rotationY", 0f, 180f)
        rotateAnimator.duration = 400

        val scaleXAnimator = ValueAnimator.ofFloat(1f, -1f)
        scaleXAnimator.addUpdateListener { animator ->
            val scale = animator.animatedValue as Float
            view.scaleX = scale
        }
        scaleXAnimator.duration = 400

        animatorSet.playTogether(rotateAnimator, scaleXAnimator)
        animatorSet.start()
    }

}