package com.example.test.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val itemNames = arrayOf(
        "Xiaomi Redmi Note 6",
        "Xiaomi Redmi Note 7",
        "Xiaomi Redmi Note 8",
        "Xiaomi Redmi Note 9",
        "Xiaomi Redmi Note 10",
        "Xiaomi Redmi Note 11")

    private val itemPrices = arrayOf(
        "6990.00₽",
        "8990.00₽",
        "10990.00₽",
        "12990.00₽",
        "14990.00₽",
        "16990.00₽")

    private val itemImages = intArrayOf(
        R.drawable.smart_1,
        R.drawable.smart_2,
        R.drawable.smart_3,
        R.drawable.smart_4,
        R.drawable.smart_5,
        R.drawable.smart_6
    )


    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        var image: ImageView
        var textName: TextView
        var textPrice: TextView

        init {
            image =itemView.findViewById(R.id.item_image)
            textName = itemView.findViewById(R.id.name_text_view)
            textPrice = itemView.findViewById(R.id.price_text_view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         val v = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_model, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return itemNames.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textName.text = itemNames [position]
        holder.textPrice.text = itemPrices [position]
        holder.image.setImageResource(itemImages[position])

        holder.itemView.setOnClickListener {v: View ->

        }
    }
}