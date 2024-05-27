package com.example.test.ui.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R

class BasketAdapter: RecyclerView.Adapter<BasketAdapter.ViewHolder>() {

    private val name = arrayOf(
        "Xiaomi Redmi Note 6",
        "Xiaomi Redmi Note 7",
        "Xiaomi Redmi Note 8",
        "Xiaomi Redmi Note 9",
        "Xiaomi Redmi Note 10",
        "Xiaomi Redmi Note 11"
    )

    private val price = arrayOf(
        "6990.00₽",
        "8990.00₽",
        "10990.00₽",
        "12990.00₽",
        "14990.00₽",
        "16990.00₽"
    )

    private val imageResource = intArrayOf(
        R.drawable.smart_1,
        R.drawable.smart_2,
        R.drawable.smart_3,
        R.drawable.smart_4,
        R.drawable.smart_5,
        R.drawable.smart_6
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var textName: TextView
        var textPrice: TextView

        init {
            image = itemView.findViewById(R.id.imageViewBasket)
            textName = itemView.findViewById(R.id.nameTextView)
            textPrice = itemView.findViewById(R.id.priceTextView)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_basket, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return name.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textName.text = name [position]
        holder.textPrice.text = price [position]
        holder.image.setImageResource(imageResource[position])

        holder.itemView.setOnClickListener {v: View ->
            Toast.makeText(v.context,"Clickced on the item", Toast.LENGTH_SHORT).show()
        }
    }
}