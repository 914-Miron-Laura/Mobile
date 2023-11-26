package com.example.dressup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dressup.databinding.ActivityUpdateItemBinding

class UpdateItemActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityUpdateItemBinding
    private lateinit var  db: ItemDataBaseHelper
    private var itemId = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= ItemDataBaseHelper(this)

        itemId = intent.getIntExtra("item_id",-1)
        if(itemId == -1)
        {
            finish()
            return
        }
        val item = db.getItemById(itemId)
        binding.UpdatetitleEditText.setText(item.title)
        binding.UpdateDescriptionEditText.setText(item.description)
        binding.UpdateCategoryEditText.setText(item.category)
        binding.UpdatePriceEditText.setText(item.price)
        binding.UpdateSizeEditText.setText(item.size)
        binding.UpdateColorEditText.setText(item.color)


        binding.updateButton.setOnClickListener{
            val newTitle =binding.UpdatetitleEditText.text.toString()
            val newDescription =binding.UpdateDescriptionEditText.text.toString()
            val newCategory =binding.UpdateCategoryEditText.text.toString()
            val newSize =binding.UpdateSizeEditText.text.toString()
            val newPrice =binding.UpdatePriceEditText.text.toString()
            val newColor =binding.UpdateColorEditText.text.toString()
            val updatedItem = Item(itemId,newTitle,newDescription,newCategory,newPrice,newSize,newColor)

            db.updateItem(updatedItem)
            finish()
            Toast.makeText(this,"NEW INFO SAVED!",Toast.LENGTH_SHORT).show()


        }

    }
}