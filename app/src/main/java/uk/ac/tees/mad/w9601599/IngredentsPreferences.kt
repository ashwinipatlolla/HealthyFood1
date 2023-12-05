package uk.ac.tees.mad.w9601599

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uk.ac.tees.mad.w9601599.data.Ingredient

class IngredientPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("ingredient_pre", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveIngredientList(ingredients: List<Ingredient>) {
        val jsonString = gson.toJson(ingredients)
        prefs.edit().putString("saved_ingredients", jsonString).apply()
    }

    fun getSavedIngredientList(): List<Ingredient> {
        val jsonString = prefs.getString("saved_ingredients", null) ?: return emptyList()
        val type = object : TypeToken<List<Ingredient>>() {}.type
        return gson.fromJson(jsonString, type)
    }
}
