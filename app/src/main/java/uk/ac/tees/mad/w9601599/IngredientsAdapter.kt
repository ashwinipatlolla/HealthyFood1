package uk.ac.tees.mad.w9601599

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uk.ac.tees.mad.w9601599.data.Ingredient

class IngredientsAdapter(
    private val ingredients: List<Ingredient>,
    private val onAddClick: (Ingredient) -> Unit
) : RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ingrident, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.bind(ingredient, onAddClick)
    }

    override fun getItemCount(): Int = ingredients.size

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewIngredient)
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewIngredientName)
        private val addButton: Button = itemView.findViewById(R.id.buttonAddToList)

        fun bind(ingredient: Ingredient, onAddClick: (Ingredient) -> Unit) {
            nameTextView.text = ingredient.name
            Glide.with(itemView.context).load("https://spoonacular.com/cdn/ingredients_100x100/${ingredient.image}").into(imageView)
            addButton.setOnClickListener { onAddClick(ingredient) }
        }
    }
}
