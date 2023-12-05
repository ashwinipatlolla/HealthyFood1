package uk.ac.tees.mad.w9601599

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uk.ac.tees.mad.w9601599.data.Ingredient

class SavedIngredientsActivity : AppCompatActivity() {
    private lateinit var savedIngredientsAdapter: IngredientsAdapter
    private lateinit var totalCaloriesTextView: TextView
    private lateinit var totalFatTextView: TextView
    private lateinit var totalCholesterolTextView: TextView
    private lateinit var totalSugarTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_ingredients)

        totalCaloriesTextView = findViewById(R.id.totalCaloriesTextView)
        totalFatTextView = findViewById(R.id.totalFatTextView)
        totalCholesterolTextView = findViewById(R.id.totalCholesterolTextView)
        totalSugarTextView = findViewById(R.id.totalSugarTextView)

        val clearAllButton: Button = findViewById(R.id.clearAllButton)
        clearAllButton.setOnClickListener { clearAllIngredients() }

        loadSavedIngredients()
    }

    private fun loadSavedIngredients() {
        val ingredientPreferences = IngredientPreferences(this)
        val savedIngredients = ingredientPreferences.getSavedIngredientList()

        displayNutritionalSummary(savedIngredients)

        savedIngredientsAdapter = IngredientsAdapter(savedIngredients) { /* Handle item click if needed */ }

        findViewById<RecyclerView>(R.id.savedIngredientsRecyclerView).apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = savedIngredientsAdapter
        }
    }

    private fun displayNutritionalSummary(ingredients: List<Ingredient>) {
        val totalCalories = ingredients.sumOf { it.nutrients.find { nutrient -> nutrient.name == "Calories" }?.amount ?: 0.0 }
        val totalFat = ingredients.sumOf { it.nutrients.find { nutrient -> nutrient.name == "Fat" }?.amount ?: 0.0 }
        val totalCholesterol = ingredients.sumOf { it.nutrients.find { nutrient -> nutrient.name == "Cholesterol" }?.amount ?: 0.0 }
        val totalSugar = ingredients.sumOf { it.nutrients.find { nutrient -> nutrient.name == "Sugar" }?.amount ?: 0.0 }

        totalCaloriesTextView.text = "Total Calories: $totalCalories"
        totalFatTextView.text = "Total Fat: $totalFat"
        totalCholesterolTextView.text = "Total Cholesterol: $totalCholesterol"
        totalSugarTextView.text = "Total Sugar: $totalSugar"
    }

    private fun clearAllIngredients() {
        val ingredientPreferences = IngredientPreferences(this)
        ingredientPreferences.saveIngredientList(emptyList())
        loadSavedIngredients()
    }
}
