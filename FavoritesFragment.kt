package com.example.finalminiproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoritesFragment : Fragment() {

    private lateinit var favoritesRecyclerView: RecyclerView
    private lateinit var emptyFavoritesText: TextView
    private var propertyAdapter: PropertyAdapter? = null // Make adapter nullable

    private val propertyViewModel: PropertyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        favoritesRecyclerView = view.findViewById(R.id.favoritesRecyclerView)
        emptyFavoritesText = view.findViewById(R.id.emptyFavoritesText)
        setupRecyclerView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        propertyViewModel.favoriteProperties.observe(viewLifecycleOwner) { favoriteProperties ->
            if (favoriteProperties.isNullOrEmpty()) {
                emptyFavoritesText.visibility = View.VISIBLE
                favoritesRecyclerView.visibility = View.GONE
            } else {
                emptyFavoritesText.visibility = View.GONE
                favoritesRecyclerView.visibility = View.VISIBLE
            }

            // If the adapter hasn't been created yet, create it.
            if (propertyAdapter == null) {
                // CORRECTED: Call the updated PropertyAdapter constructor
                propertyAdapter = PropertyAdapter(favoriteProperties.toMutableList(), propertyViewModel)
                favoritesRecyclerView.adapter = propertyAdapter
            } else {
                // If it already exists, just update its data.
                propertyAdapter?.updateProperties(favoriteProperties)
            }
        }
    }

    private fun setupRecyclerView() {
        favoritesRecyclerView.layoutManager = LinearLayoutManager(context)
    }
}