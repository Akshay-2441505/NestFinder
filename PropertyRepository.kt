package com.example.finalminiproject

object PropertyRepository {

    fun getMockProperties(): List<Property> {
        return listOf(
            Property("p1", "Prestige Shantiniketan", "For Sale", 2_50_00_000, "Tower C, Whitefield Main Road", "Whitefield", 3, 3, 1800, listOf("drawable1", "drawable2", "drawable3")),
            Property("p2", "Sobha Dream Acres", "For Rent", 35_000, "Balagere Panathur Road", "Panathur", 2, 2, 1200, listOf("drawable4", "drawable5")),
            Property("p3", "Phoenix One Bangalore West", "For Sale", 6_00_00_000, "Dr. Rajkumar Road, Rajajinagar", "Rajajinagar", 4, 4, 2800, listOf("drawable1", "drawable3", "drawable5", "drawable2")),
            Property("p4", "Salarpuria Sattva Magnificia", "For Sale", 1_80_00_000, "Old Madras Road, Duravani Nagar", "KR Puram", 3, 2, 1650, listOf("drawable1", "drawable2")),
            Property("p5", "Embassy Pristine", "For Rent", 75_000, "Outer Ring Road, Bellandur", "Bellandur", 3, 3, 2200, listOf("drawable3", "drawable4", "drawable5")),
            Property("p6", "Brigade Exotica", "For Sale", 2_20_00_000, "Old Madras Road, Aavalahalli", "Budigere Cross", 3, 3, 2100, listOf("drawable2", "drawable4"))
        )
    }
}