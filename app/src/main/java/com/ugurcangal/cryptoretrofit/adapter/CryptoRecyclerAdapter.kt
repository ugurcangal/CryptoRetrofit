package com.ugurcangal.cryptoretrofit.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ugurcangal.cryptoretrofit.databinding.RowLayoutBinding
import com.ugurcangal.cryptoretrofit.model.CryptoModel

class CryptoRecyclerAdapter(
    private val cryptoList: ArrayList<CryptoModel>,
    private val listener: Listener
) :
    RecyclerView.Adapter<CryptoRecyclerAdapter.CryptoHolder>() {

    interface Listener {
        fun onItemClick(cryptoModel: CryptoModel)
    }

     private val colors: Array<String> =
        arrayOf("#FBDB2D", "#E3B329", "#FAB33A", "#E38629", "#FF7226")


    class CryptoHolder(private val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            cryptoModel: CryptoModel,
            colors: Array<String>,
            position: Int,
            listener: Listener
        ) {
            itemView.setOnClickListener {
                listener.onItemClick(cryptoModel)
            }
            itemView.setBackgroundColor(Color.parseColor(colors[position % 5]))
            binding.textCurrency.text = cryptoModel.currency
            binding.textPrice.text = cryptoModel.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptoHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoHolder, position: Int) {
        holder.bind(cryptoList[position], colors, position, listener)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }
}