package uk.ac.tees.mad.w9601599

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uk.ac.tees.mad.w9601599.data.Recipe

class RecipeAdapter(private var recipes: List<Recipe>, private val onClick: (Int) -> Unit) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    fun setRecipes(recipes: List<Recipe>) {
        this.recipes = recipes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
        holder.itemView.setOnClickListener { onClick(recipe.id) }
    }

    override fun getItemCount() = recipes.size

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewRecipe)
        private val textView: TextView = itemView.findViewById(R.id.textViewTitle)

        fun bind(recipe: Recipe) {
            textView.text = recipe.title
            Glide.with(itemView.context).load(recipe.image).into(imageView)
        }
    }
}
