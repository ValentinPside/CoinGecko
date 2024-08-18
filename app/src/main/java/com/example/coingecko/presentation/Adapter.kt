package com.example.coingecko.presentation

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coingecko.R
import com.example.coingecko.databinding.CoinItemBinding
import com.example.coingecko.domain.models.Coin
import com.example.coingecko.utils.CurrentCurrency

class Adapter(private val onClick: (title: String, id: String) -> Unit) :
    ListAdapter<Coin, Adapter.ViewHolder>(DiffUtilItem()) {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CoinItemBinding.bind(view)

        @SuppressLint("ResourceAsColor")
        fun bind(coin: Coin) {
            binding.name.text = coin.name
            binding.symbol.text = coin.symbol
            binding.price.text = CurrentCurrency.currentCurrency.plus(coin.price)
            binding.changePercent.text = coin.changePercent
            try {
                val b: Int = coin.color
                if (b == R.color.red) {
                    binding.changePercent.setTextColor(Color.RED)
                }
                if (b == R.color.green) {
                    binding.changePercent.setTextColor(R.color.green)
                }
            } catch (nfe: NumberFormatException) {
                println("Could not parse $nfe")
            }

            Glide.with(binding.imageView)
                .load(coin.image)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(binding.imageView)
            binding.root.setOnClickListener {
                onClick.invoke(coin.name, coin.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coin_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = currentList[position]
        holder.bind(item)
    }

    private class DiffUtilItem : DiffUtil.ItemCallback<Coin>() {

        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem == newItem
        }
    }
}