package uk.ac.tees.mad.w9601599.retro

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import uk.ac.tees.mad.w9601599.data.IngredientInformation
import uk.ac.tees.mad.w9601599.data.IngredientsSearchResponse
import uk.ac.tees.mad.w9601599.data.MealPlanResponse
import uk.ac.tees.mad.w9601599.data.RecipeDetail
import uk.ac.tees.mad.w9601599.data.RecipeSearchResponse

interface SpoonacularApiService {
    @Headers(
        "X-RapidAPI-Key: b9be65b94d994b5fa702cf79bcc6bfa7",
        "X-RapidAPI-Host: spoonacular-recipe-food-nutrition-v1.p.rapidapi.com"
    )
    @GET("recipes/complexSearch")
    fun searchRecipes(@Query("query") query: String,@Query("apiKey") apiKey: String = "b9be65b94d994b5fa702cf79bcc6bfa7"): Call<RecipeSearchResponse>

    @Headers(
        "X-RapidAPI-Key: b9be65b94d994b5fa702cf79bcc6bfa7",
        "X-RapidAPI-Host: spoonacular-recipe-food-nutrition-v1.p.rapidapi.com"
    )
    @GET("recipes/{id}/information")
    fun getRecipeInformation(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String = "b9be65b94d994b5fa702cf79bcc6bfa7"
    ): Call<RecipeDetail>

    @Headers(
        "X-RapidAPI-Key: b9be65b94d994b5fa702cf79bcc6bfa7",
        "X-RapidAPI-Host: spoonacular-recipe-food-nutrition-v1.p.rapidapi.com"
    )
    @GET("food/ingredients/search")
    fun searchIngredients(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String = "b9be65b94d994b5fa702cf79bcc6bfa7",
        @Query("number") number: String = "20"
    ): Call<IngredientsSearchResponse>

    @GET("food/ingredients/{id}/information")
    fun getIngredientInformation(
        @Path("id") id: Int,
        @Query("amount") amount: String = "1",
        @Query("apiKey") apiKey: String = "b9be65b94d994b5fa702cf79bcc6bfa7"
    ): Call<IngredientInformation>

    @GET("mealplanner/generate")
    fun getDailyMealPlan(
        @Query("timeFrame") timeFrame: String = "day",
        @Query("apiKey") apiKey: String = "b9be65b94d994b5fa702cf79bcc6bfa7"
    ): Call<MealPlanResponse>
}