package uk.ac.tees.mad.w9601599


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.ac.tees.mad.w9601599.data.Ingredient
import uk.ac.tees.mad.w9601599.data.IngredientInformation
import uk.ac.tees.mad.w9601599.data.IngredientsSearchResponse
import uk.ac.tees.mad.w9601599.retro.SpoonacularApiService

class DietCalculator : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var ingredientsRecyclerView: RecyclerView
    private lateinit var ingredientsAdapter: IngredientsAdapter
    private var ingredientsList: List<Ingredient> = listOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_calculator)

        searchEditText = findViewById(R.id.searchIngredientEditText)
        searchButton = findViewById(R.id.searchButton)
        ingredientsRecyclerView = findViewById(R.id.ingredientsRecyclerView)

        ingredientsRecyclerView.layoutManager = LinearLayoutManager(this)
        ingredientsAdapter = IngredientsAdapter(ingredientsList) { ingredient ->
            // Handle "Add to List" button click
            addToSavedList(ingredient)
        }
        ingredientsRecyclerView.adapter = ingredientsAdapter

        searchButton.setOnClickListener {
            performSearch(searchEditText.text.toString())
        }

        findViewById<Button>(R.id.viewSavedListButton).setOnClickListener{
            startActivity(Intent(applicationContext,SavedIngredientsActivity::class.java))
        }
    }

    private fun performSearch(query: String) {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val apiService = retrofit.create(SpoonacularApiService::class.java)



        apiService.searchIngredients(query, "b9be65b94d994b5fa702cf79bcc6bfa7","20").enqueue(object : Callback<IngredientsSearchResponse> {
            override fun onResponse(call: Call<IngredientsSearchResponse>, response: Response<IngredientsSearchResponse>) {
                if (response.isSuccessful) {
                    ingredientsList = response.body()?.results ?: emptyList()
                    ingredientsAdapter = IngredientsAdapter(ingredientsList) { ingredient ->
                        addToSavedList(ingredient)
                    }
                    ingredientsRecyclerView.adapter = ingredientsAdapter
                } else {
                    print(response)
                    Toast.makeText(applicationContext, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<IngredientsSearchResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Failure: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addToSavedList(ingredient: Ingredient) {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(SpoonacularApiService::class.java)

        apiService.getIngredientInformation(ingredient.id).enqueue(object : Callback<IngredientInformation> {
            override fun onResponse(call: Call<IngredientInformation>, response: Response<IngredientInformation>) {
                if (response.isSuccessful) {
                    val ingredientInfo = response.body()

                    var nutri = ingredientInfo?.nutrition?.nutrients
                    if (nutri != null) {
                        ingredient.nutrients = nutri
                    }


                    val ingredientPreferences = IngredientPreferences(applicationContext)
                    val savedIngredients = ingredientPreferences.getSavedIngredientList().toMutableList()

                    savedIngredients.add(ingredient)

                    ingredientPreferences.saveIngredientList(savedIngredients)

                    Toast.makeText(applicationContext, "${ingredient.name} added to list", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Error fetching ingredient info", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<IngredientInformation>, t: Throwable) {
                Toast.makeText(applicationContext, "API call failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


}