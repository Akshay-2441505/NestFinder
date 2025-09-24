package com.example.finalminiproject

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

// An Enum to represent our different sorting options in a clear, safe way.
enum class SortOrder {
    PRICE_ASC, PRICE_DESC, AREA_DESC
}

class PropertyViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PropertyRepository
    private val prefsManager = SharedPreferencesManager(application)

    private val _allProperties = MutableLiveData<List<Property>>()

    private val _filteredProperties = MutableLiveData<List<Property>>()
    val filteredProperties: LiveData<List<Property>> = _filteredProperties

    private val _favoriteProperties = MutableLiveData<List<Property>>()
    val favoriteProperties: LiveData<List<Property>> = _favoriteProperties

    init {
        val properties = repository.getMockProperties()
        _allProperties.value = properties
        _filteredProperties.value = properties
        updateFavorites()
    }

    fun searchProperties(query: String) {
        val listToFilter = _allProperties.value
        if (query.isBlank()) {
            _filteredProperties.value = listToFilter
        } else {
            _filteredProperties.value = listToFilter?.filter {
                it.name.contains(query, ignoreCase = true) || it.location.contains(query, ignoreCase = true)
            }
        }
    }

    // ✅ REPLACED the old sort function with this new, more powerful one.
    fun sortProperties(order: SortOrder) {
        val currentList = _filteredProperties.value
        val sortedList = when (order) {
            SortOrder.PRICE_ASC -> currentList?.sortedBy { it.price }
            SortOrder.PRICE_DESC -> currentList?.sortedByDescending { it.price }
            SortOrder.AREA_DESC -> currentList?.sortedByDescending { it.areaSqFt }
        }
        _filteredProperties.value = sortedList
    }

    // ... the rest of the ViewModel (favorites logic) remains the same ...
    fun isFavorite(propertyId: String): Boolean {
        return prefsManager.isFavorite(propertyId)
    }
    fun addFavorite(propertyId: String) {
        prefsManager.addFavorite(propertyId)
        updateFavorites()
    }
    fun removeFavorite(propertyId: String) {
        prefsManager.removeFavorite(propertyId)
        updateFavorites()
    }
    private fun updateFavorites() {
        val favoriteIds = prefsManager.getFavorites()
        val favorites = _allProperties.value?.filter { property ->
            favoriteIds.contains(property.id)
        }
        _favoriteProperties.value = favorites
    }
}