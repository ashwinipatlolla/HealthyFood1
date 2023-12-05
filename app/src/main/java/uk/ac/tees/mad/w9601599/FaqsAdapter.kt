package uk.ac.tees.mad.w9601599

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uk.ac.tees.mad.w9601599.data.FAQ

class FaqsAdapter(private val faqs: List<FAQ>) : RecyclerView.Adapter<FaqsAdapter.FaqViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_community, parent, false)
        return FaqViewHolder(view)
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        val faq = faqs[position]
        holder.bind(faq)
    }

    override fun getItemCount() = faqs.size

    class FaqViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val questionTextView: TextView = itemView.findViewById(R.id.questionTextView)
        private val answerTextView: TextView = itemView.findViewById(R.id.answerTextView)

        fun bind(faq: FAQ) {
            questionTextView.text = faq.question
            answerTextView.text = faq.answer
        }
    }
}
