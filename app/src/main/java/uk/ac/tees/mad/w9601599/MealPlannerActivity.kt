package uk.ac.tees.mad.w9601599

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.ac.tees.mad.w9601599.data.MealPlanResponse
import uk.ac.tees.mad.w9601599.retro.SpoonacularApiService

class MealPlannerActivity : AppCompatActivity() {
    private lateinit var mealsRecyclerView: RecyclerView
    private lateinit var nutrientsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_planner)

        mealsRecyclerView = findViewById(R.id.mealsRecyclerView)
        nutrientsTextView = findViewById(R.id.nutrientsTextView)

        fetchMealPlan()
    }

    private fun fetchMealPlan() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(SpoonacularApiService::class.java)

        apiService.getDailyMealPlan().enqueue(object : Callback<MealPlanResponse> {
            override fun onResponse(call: Call<MealPlanResponse>, response: Response<MealPlanResponse>) {
                if (response.isSuccessful) {
                    val mealPlan = response.body()
                    updateUI(mealPlan)
                }
            }

            override fun onFailure(call: Call<MealPlanResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun updateUI(mealPlan: MealPlanResponse?) {
        mealPlan?.let {
            mealsRecyclerView.adapter = MealAdapter(it.meals)

            mealsRecyclerView.layoutManager = LinearLayoutManager(this)
            nutrientsTextView.text = "Total Nutrients: \n Calories - ${it.nutrients.calories}\n Carbs - ${it.nutrients.carbohydrates}\n Fat - ${it.nutrients.fat}\n Protein - ${it.nutrients.protein}"
        }
    }
}
