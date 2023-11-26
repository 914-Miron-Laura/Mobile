package com.example.dressup

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(private  var items:List<Item>,context:Context) : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {


    private val db:ItemDataBaseHelper = ItemDataBaseHelper(context)

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val titleTextView : TextView = itemView.findViewById(R.id.TitleTextView)
        val DescriptionTextView : TextView = itemView.findViewById(R.id.DescriptionTextView)
        val CategoryView : TextView = itemView.findViewById(R.id.CategoryTextView)
        val PriceTextView : TextView = itemView.findViewById(R.id.PriceTextView)
        val SizeTextView : TextView = itemView.findViewById(R.id.SizeTextView)
        val ColorTextView : TextView = itemView.findViewById(R.id.ColorTextView)
        val updateButton : ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton:ImageView = itemView.findViewById(R.id.deleteButton)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.titleTextView.text = item.title
        holder.DescriptionTextView.text = item.description
        holder.CategoryView.text = item.category
        holder.PriceTextView.text = item.price
        holder.SizeTextView.text =item.size
        holder.ColorTextView.text = item.color


        holder.updateButton.setOnClickListener{
            val intent = Intent( holder.itemView.context,UpdateItemActivity::class.java).apply{
                    putExtra("item_id",item.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            db.deteleItem(item.id)
            refreshData(db.getAllItems())
            Toast.makeText(holder.itemView.context,"Item Deleted",Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newItems : List<Item>)
    {
        items = newItems
        notifyDataSetChanged()
    }


}