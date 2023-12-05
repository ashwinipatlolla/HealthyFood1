package uk.ac.tees.mad.w9601599.data

data class Ingredient(
    val id: Int,
    val name: String,
    val amount: Double,
    val unit: String,
    val image: String,
    var nutrients: List<Nutrient>
)