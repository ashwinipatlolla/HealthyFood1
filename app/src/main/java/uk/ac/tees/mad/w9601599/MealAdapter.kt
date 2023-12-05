package uk.ac.tees.mad.w9601599

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uk.ac.tees.mad.w9601599.data.Meal

class MealAdapter(private val meals: List<Meal>) : RecyclerView.Adapter<MealViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(meals[position])
    }


    override fun getItemCount() = meals.size

}
class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
    private val imageView: ImageView = itemView.findViewById(R.id.imageViewMeal)

    fun bind(meal: Meal) {
        titleTextView.text = meal.title

        Glide.with(itemView.context).load(meal.getImageUrl()).into(imageView)
    }
}
