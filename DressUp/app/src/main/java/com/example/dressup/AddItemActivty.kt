import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dressup.Item
import com.example.dressup.ItemDataBaseHelper
import com.example.dressup.databinding.ActivityAddItemActivtyBinding

class AddItemActivty : AppCompatActivity() {

    private lateinit var binding: ActivityAddItemActivtyBinding
    private lateinit var db: ItemDataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ItemDataBaseHelper(this)

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString().trim()
            val description = binding.DescriptionEditText.text.toString().trim()
            val category = binding.CategoryEditText.text.toString().trim()
            val price = binding.PriceEditText.text.toString().trim()
            val size = binding.SizeEditText.text.toString().trim()
            val color = binding.ColorEditText.text.toString().trim()

            if (validateInput(title, description, category, price, size, color)) {
                val item = Item(0, title, description, category, price, size, color)
                db.insertItem(item)
                finish()
                Toast.makeText(this, "Item Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInput(
        title: String,
        description: String,
        category: String,
        price: String,
        size: String,
        color: String
    ): Boolean {
        if (title.isEmpty() || description.isEmpty() || category.isEmpty() ||
            price.isEmpty() || size.isEmpty() || color.isEmpty()
        ) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}
