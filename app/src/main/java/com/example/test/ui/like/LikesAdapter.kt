package com.example.test.ui.like

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R

class LikesAdapter: RecyclerView.Adapter<LikesAdapter.ViewHolder>() {

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

    private val itemRating = arrayOf(
        4.9,
        4.5,
        4.2,
        4.4,
        4.6,
        4.7)

    private val ItemKodTovara = arrayOf(
        "0000000001",
        "0000000002",
        "0000000003",
        "0000000004",
        "0000000005",
        "0000000006"
    )


    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        var image: ImageView
        var textName: TextView
        var textPrice: TextView
        var textRating: TextView
        var textKodTovara: TextView
        var Rating: RatingBar

        init {
            image =itemView.findViewById(R.id.imageLike)
            textName = itemView.findViewById(R.id.nameLikes)
            textPrice = itemView.findViewById(R.id.priceLikes)
            textRating = itemView.findViewById(R.id.rating_TV)
            textKodTovara = itemView.findViewById(R.id.kod_tovara)
            Rating = itemView.findViewById(R.id.rating)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.likes_item_recycler_view, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return itemNames.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textName.text = itemNames [position]
        holder.textPrice.text = itemPrices [position]
        holder.image.setImageResource(itemImages[position])
        holder.textRating.text = itemRating[position].toString()
        holder.Rating.rating = itemRating[position].toFloat()
        holder.textKodTovara.text = ItemKodTovara[position]

        holder.itemView.setOnClickListener {v: View ->

        }
    }
}