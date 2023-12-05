package uk.ac.tees.mad.w9601599

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.ac.tees.mad.w9601599.data.Ingredient
import uk.ac.tees.mad.w9601599.data.RecipeDetail
import uk.ac.tees.mad.w9601599.retro.SpoonacularApiService

class ViewReciepDetailsActivity : AppCompatActivity() {


    private lateinit var recipeImageView: ImageView
    private lateinit var recipeTitleTextView: TextView
    private lateinit var recipeServingsTextView: TextView
    private lateinit var recipeReadyInMinutesTextView: TextView
    private lateinit var recipeHealthScoreTextView: TextView
    private lateinit var recipePricePerServingTextView: TextView
    private lateinit var recipeSummaryTextView: TextView
    private lateinit var recipeIngredientsTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reciep_details)

        recipeImageView = findViewById(R.id.recipeImageView)
        recipeTitleTextView = findViewById(R.id.recipeTitleTextView)
        recipeServingsTextView = findViewById<TextView>(R.id.recipeServingsTextView)
        recipeReadyInMinutesTextView = findViewById<TextView>(R.id.recipeReadyInMinutesTextView)
        recipeHealthScoreTextView = findViewById<TextView>(R.id.recipeHealthScoreTextView)
        recipePricePerServingTextView = findViewById<TextView>(R.id.recipePricePerServingTextView)
        recipeSummaryTextView = findViewById<TextView>(R.id.recipeSummaryTextView)
        recipeIngredientsTextView = findViewById<TextView>(R.id.recipeIngredientsTextView)


        val recipeId = intent.getIntExtra("RECIPE_ID", -1)
        if (recipeId != -1) {
            fetchRecipeDetails(recipeId)
        } else {
            Toast.makeText(this, "Recipe ID not found", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun fetchRecipeDetails(recipeId: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(SpoonacularApiService::class.java)

        apiService.getRecipeInformation(recipeId, "b9be65b94d994b5fa702cf79bcc6bfa7").enqueue(object : Callback<RecipeDetail> {
            override fun onResponse(call: Call<RecipeDetail>, response: Response<RecipeDetail>) {
                if (response.isSuccessful) {
                    val recipeDetail = response.body()
                    updateUI(recipeDetail)
                } else {
                    print(response.message())
                    Toast.makeText(applicationContext, response.message().toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RecipeDetail>, t: Throwable) {
                Toast.makeText(applicationContext, "Failure: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUI(recipeDetail: RecipeDetail?) {
        recipeDetail?.let { detail ->
            // Set title
            recipeTitleTextView.text = detail.title

            // Load image
            Glide.with(this).load(detail.image).into(recipeImageView)

            // Set servings
            val servingsText = "Servings: ${detail.servings}"
            recipeServingsTextView.text = servingsText

            // Set ready in minutes
            val readyInMinutesText = "Ready in Minutes: ${detail.readyInMinutes}"
            recipeReadyInMinutesTextView.text = readyInMinutesText

            // Set health score
            val healthScoreText = "Health Score: ${detail.healthScore}"
            recipeHealthScoreTextView.text = healthScoreText

            // Set price per serving
            val pricePerServingText = "Price Per Serving: ${detail.pricePerServing}"
            recipePricePerServingTextView.text = pricePerServingText

            // Set summary
            // Note: HTML tags in summary need to be parsed
            val summarySpanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(detail.summary, Html.FROM_HTML_MODE_COMPACT)
            } else {
                @Suppress("DEPRECATION")
                Html.fromHtml(detail.summary)
            }
            recipeSummaryTextView.text = summarySpanned


            recipeIngredientsTextView.text = formatIngredients(detail.extendedIngredients)

        }
    }

    private fun formatIngredients(ingredients: List<Ingredient>): String {
        print(ingredients)
        return ingredients.joinToString(separator = "\n") { ingredient ->
            "${ingredient.name}: ${ingredient.amount} ${ingredient.unit}"
        }
    }

}