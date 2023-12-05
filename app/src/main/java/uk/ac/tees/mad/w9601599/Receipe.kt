package uk.ac.tees.mad.w9601599

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.ac.tees.mad.w9601599.data.Recipe
import uk.ac.tees.mad.w9601599.data.RecipeSearchResponse
import uk.ac.tees.mad.w9601599.retro.SpoonacularApiService

class Receipe : AppCompatActivity() {

    private lateinit var searchEditText: TextInputEditText
    private lateinit var searchButton: Button
    private lateinit var recipesRecyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter
    private lateinit var reciep : List<Recipe>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipe)

        reciep = ArrayList<Recipe>()

        searchEditText = findViewById(R.id.searchEditText)
        searchButton = findViewById(R.id.searchButton)
        recipesRecyclerView = findViewById(R.id.recipesRecyclerView)

        adapter = RecipeAdapter(reciep) { recipeId ->
            val intent = Intent(this, ViewReciepDetailsActivity::class.java)
            intent.putExtra("RECIPE_ID", recipeId)
            startActivity(intent)
        }
        recipesRecyclerView.adapter = adapter
        recipesRecyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(SpoonacularApiService::class.java)

        searchButton.setOnClickListener {
            val query = searchEditText.text.toString()
            if (query.isNotEmpty()) {
                apiService.searchRecipes(query).enqueue(object : retrofit2.Callback<RecipeSearchResponse> {
                    override fun onResponse(call: retrofit2.Call<RecipeSearchResponse>, response: retrofit2.Response<RecipeSearchResponse>) {
                        if (response.isSuccessful) {
                            adapter.setRecipes(response.body()?.results ?: emptyList())
                        } else {
                            println(response)
                            Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<RecipeSearchResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, "Failure: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}