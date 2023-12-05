package uk.ac.tees.mad.w9601599

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uk.ac.tees.mad.w9601599.data.FAQ

class Community : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        val sampleFaqs = listOf(
            FAQ("What is healthy eating?", "Healthy eating is about maintaining a balanced diet."),
            FAQ("How much water should I drink daily?", "It's recommended to drink about 8 glasses of water a day."),
            FAQ("What are good sources of protein?", "Lean meat, poultry, fish, beans, peas, and nuts."),
            FAQ("How can I improve my sleep?", "Maintain a regular sleep schedule and create a comfortable sleep environment."),
            FAQ("What are the benefits of regular exercise?", "Improved mood, increased energy levels, and better physical health."),
            FAQ("How to reduce stress?", "Try relaxation techniques, such as deep breathing, meditation, or yoga."),
            FAQ("What are whole grains?", "Whole grains contain the entire grain kernel."),
            FAQ("Why are fruits and vegetables important?", "They are rich in vitamins, minerals, and fiber."),
            FAQ("What is the importance of vitamin D?", "It helps with calcium absorption and bone health."),
            FAQ("Are fats bad for health?", "Not all fats are bad; healthy fats are essential for body functions.")
        )


        val recyclerView: RecyclerView = findViewById(R.id.faqsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FaqsAdapter(sampleFaqs)
    }
}