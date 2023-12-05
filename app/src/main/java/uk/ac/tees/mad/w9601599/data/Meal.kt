package uk.ac.tees.mad.w9601599.data

data class Meal(
    val id: Int,
    val title: String,
    val imageType: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String
) {
    fun getImageUrl(): String {
        return "$sourceUrl.$imageType"
    }
}
