package com.example.finalminiproject

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.text.NumberFormat
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val toolbar: Toolbar = findViewById(R.id.detail_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Find all views
        val viewPager: ViewPager2 = findViewById(R.id.image_slider)
        val tabLayout: TabLayout = findViewById(R.id.image_slider_indicator)
        val nameTextView: TextView = findViewById(R.id.detail_name)
        val priceTextView: TextView = findViewById(R.id.detail_price)
        val addressTextView: TextView = findViewById(R.id.detail_address)
        val locationTextView: TextView = findViewById(R.id.detail_location)
        val specsTextView: TextView = findViewById(R.id.detail_specs)
        val fabShare: FloatingActionButton = findViewById(R.id.fab_share)
        val contactAgentButton: Button = findViewById(R.id.contact_agent_button)

        val property = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("PROPERTY_EXTRA", Property::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("PROPERTY_EXTRA") as? Property
        }

        property?.let { prop ->
            supportActionBar?.title = prop.name

            // Setup the ViewPager2 Adapter
            val imageAdapter = ImageSliderAdapter(prop.imageUrls)
            viewPager.adapter = imageAdapter

            // Link the TabLayout indicator to the ViewPager2
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                // This is needed to create the dots, but we don't need to customize them
            }.attach()

            val indianLocale = Locale("en", "IN")
            val priceFormatter = NumberFormat.getCurrencyInstance(indianLocale).apply {
                maximumFractionDigits = 0
            }
            val areaFormatter = NumberFormat.getNumberInstance(indianLocale)

            val priceText = priceFormatter.format(prop.price)
            val specsText = "• ${prop.type}\n• ${prop.bedrooms} Bedrooms\n• ${prop.bathrooms} Bathrooms\n• ${areaFormatter.format(prop.areaSqFt)} sq. ft."

            nameTextView.text = prop.name
            priceTextView.text = priceText
            addressTextView.text = prop.address
            locationTextView.text = prop.location
            specsTextView.text = specsText

            fabShare.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"

                val shareText = "Check out this property!\n\n${prop.name}\n${prop.address}\nPrice: $priceText"
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Property Listing: ${prop.name}")

                startActivity(Intent.createChooser(shareIntent, "Share Property via"))
            }

            contactAgentButton.setOnClickListener {
                // Replace with your phone number
                val phoneNumber = "9999988888"

                // Create the implicit intent to open the dialer
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$phoneNumber")
                startActivity(intent)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}