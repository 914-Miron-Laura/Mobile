package com.example.dressup

import AddItemActivty
import com.example.dressup.databinding.ActivityMainBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dressup.Item
import com.example.dressup.ItemDataBaseHelper
import com.example.dressup.ItemsAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: ItemDataBaseHelper
    private lateinit var itemsAdapter: ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ItemDataBaseHelper(this)

        val allItems = db.getAllItems()
        if (validateItems(allItems)) {
            itemsAdapter = ItemsAdapter(allItems, this)

            binding.itemsRecyclerView.layoutManager = LinearLayoutManager(this)
            binding.itemsRecyclerView.adapter = itemsAdapter

        } else {
            // Handle the case where the items are not valid (e.g., show an error message)
        }

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddItemActivty::class.java)
            startActivity(intent)
        }
    }

    private fun validateItems(items: List<Item>): Boolean {
        if (items.isEmpty()) {
            Toast.makeText(this, "No items available", Toast.LENGTH_SHORT).show()
            return false
        }

        // Add more specific validation rules if needed
        // For example, check if each item has a valid title
        for (item in items) {
            if (item.title.isEmpty()) {
                Toast.makeText(this, "Invalid item: Title cannot be empty", Toast.LENGTH_SHORT).show()
                return false
            }
            // Add more checks as necessary
        }

        return true
    }


    override fun onResume() {
        super.onResume()
        // Additional logic for onResume if needed
    }
}