package com.example.finalminiproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ImageSliderAdapter(private val images: List<String>) :
    RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.slider_image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image_slider, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        // Here you would use a library like Glide or Picasso to load real URLs.
        // For our mock data, we'll just set a placeholder color.
        val colors = listOf(android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_purple)
        holder.imageView.setBackgroundResource(colors[position % colors.size])
    }

    override fun getItemCount(): Int = images.size
}