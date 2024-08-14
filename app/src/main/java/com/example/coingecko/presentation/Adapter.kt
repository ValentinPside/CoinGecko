package com.example.coingecko.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coingecko.R
import com.example.coingecko.databinding.CoinItemBinding
import com.example.coingecko.domain.Coin

class Adapter (private val onClick: (title: String, id: String) -> Unit) :
    ListAdapter<Coin, Adapter.ViewHolder>(DiffUtilItem()) {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CoinItemBinding.bind(view)

        fun bind(coin: Coin) {
            binding.name.text = coin.name
            binding.symbol.text = coin.symbol
            binding.price.text = coin.price.toString()
            binding.changePercent.text = coin.changePercent.toString().plus("%")
            Glide.with(binding.imageView)
                .load(coin.image)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .circleCrop()
                .into(binding.imageView)
            binding.root.setOnClickListener{
                coin.name?.let { it1 -> coin.id?.let { it2 -> onClick.invoke(it1, it2) } }
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

    private class DiffUtilItem: DiffUtil.ItemCallback<Coin>() {

        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem == newItem
        }
    }
}