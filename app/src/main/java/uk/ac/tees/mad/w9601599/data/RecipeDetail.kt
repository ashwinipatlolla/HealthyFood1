package uk.ac.tees.mad.w9601599.data

data class RecipeDetail(
    val id: Int,
    val title: String,
    val image: String,
    val servings: Int,
    val readyInMinutes: Int,
    val healthScore: Double,
    val pricePerServing: Double,
    val summary: String,
    val extendedIngredients: List<Ingredient>,

)