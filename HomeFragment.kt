package com.example.finalminiproject

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    private var propertyAdapter: PropertyAdapter? = null
    private val propertyViewModel: PropertyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.propertiesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        setHasOptionsMenu(true)

        propertyViewModel.filteredProperties.observe(viewLifecycleOwner) { filteredProperties ->
            if (propertyAdapter == null) {
                propertyAdapter = PropertyAdapter(filteredProperties.toMutableList(), propertyViewModel)
                recyclerView.adapter = propertyAdapter
            } else {
                propertyAdapter?.updateProperties(filteredProperties)
            }
        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_toolbar_menu, menu)

        val searchItem = menu.findItem(R.id.menu_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                propertyViewModel.searchProperties(newText.orEmpty())
                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    // ✅ UPDATED this method to show the dialog
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_sort -> {
                showSortDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // ✅ ADDED this new function to create and show the AlertDialog
    private fun showSortDialog() {
        val sortOptions = arrayOf("Price: Low to High", "Price: High to Low", "Area: Largest First")

        AlertDialog.Builder(requireContext())
            .setTitle("Sort By")
            .setItems(sortOptions) { dialog, which ->
                // 'which' is the index of the item that was clicked
                val selectedOrder = when (which) {
                    0 -> SortOrder.PRICE_ASC
                    1 -> SortOrder.PRICE_DESC
                    2 -> SortOrder.AREA_DESC
                    else -> SortOrder.PRICE_ASC // Default case
                }
                // Call the ViewModel with the user's choice
                propertyViewModel.sortProperties(selectedOrder)
            }
            .show()
    }
}