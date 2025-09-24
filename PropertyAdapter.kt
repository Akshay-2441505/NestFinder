package com.example.finalminiproject

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

// CORRECTED CONSTRUCTOR: Only takes a MutableList and the ViewModel
class PropertyAdapter(
    private var properties: MutableList<Property>,
    private val viewModel: PropertyViewModel
) : RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>() {

    class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val propertyImage: ImageView = itemView.findViewById(R.id.propertyImage)
        val propertyName: TextView = itemView.findViewById(R.id.propertyName)
        val propertyPrice: TextView = itemView.findViewById(R.id.propertyPrice)
        val propertyAddress: TextView = itemView.findViewById(R.id.propertyAddress)
        val propertyBeds: TextView = itemView.findViewById(R.id.propertyBeds)
        val propertyBaths: TextView = itemView.findViewById(R.id.propertyBaths)
        val propertyArea: TextView = itemView.findViewById(R.id.propertyArea)
        val favoriteIcon: ImageView = itemView.findViewById(R.id.favoriteIcon)
    }

    // CORRECTED METHOD: The updateProperties function
    fun updateProperties(newProperties: List<Property>) {
        properties.clear()
        properties.addAll(newProperties)
        notifyDataSetChanged() // Refresh the RecyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_property_card, parent, false)
        return PropertyViewHolder(view)
    }

    override fun getItemCount() = properties.size

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val property = properties[position]

        val indianLocale = Locale("en", "IN")
        val priceFormatter = NumberFormat.getCurrencyInstance(indianLocale).apply {
            maximumFractionDigits = 0
        }
        val areaFormatter = NumberFormat.getNumberInstance(indianLocale)

        holder.propertyName.text = property.name
        holder.propertyPrice.text = priceFormatter.format(property.price)
        holder.propertyAddress.text = property.address
        holder.propertyBeds.text = "${property.bedrooms} Beds"
        holder.propertyBaths.text = "${property.bathrooms} Baths"
        holder.propertyArea.text = "${areaFormatter.format(property.areaSqFt)} sqft"

        updateFavoriteIcon(holder.favoriteIcon, viewModel.isFavorite(property.id))

        holder.favoriteIcon.setOnClickListener {
            val isCurrentlyFavorite = viewModel.isFavorite(property.id)
            if (isCurrentlyFavorite) {
                viewModel.removeFavorite(property.id)
                updateFavoriteIcon(holder.favoriteIcon, false)
            } else {
                viewModel.addFavorite(property.id)
                updateFavoriteIcon(holder.favoriteIcon, true)
            }
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("PROPERTY_EXTRA", property)
            context.startActivity(intent)
        }
    }

    private fun updateFavoriteIcon(icon: ImageView, isFavorite: Boolean) {
        if (isFavorite) {
            icon.imageTintList = ColorStateList.valueOf(Color.RED)
        } else {
            icon.imageTintList = ColorStateList.valueOf(Color.GRAY)
        }
    }
}