package com.example.dressup
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.SyncStateContract.Columns

class ItemDataBaseHelper(context:Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,
    DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allitems"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CATEGORY = "category"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_SIZE = "size"
        private const val COLUMN_PRICE = "price"
        private const val COLUMN_COLOR = "color"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_TITLE TEXT,$COLUMN_DESCRIPTION TEXT,$COLUMN_CATEGORY TEXT, $COLUMN_COLOR TEXT,$COLUMN_PRICE INTEGER,$COLUMN_SIZE TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertItem(item: Item) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, item.title)
            put(COLUMN_CATEGORY, item.category)
            put(COLUMN_DESCRIPTION, item.description)
            put(COLUMN_SIZE, item.size)
            put(COLUMN_PRICE, item.price)
            put(COLUMN_COLOR, item.color)

        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllItems(): List<Item> {
        val itemslists = mutableListOf<Item>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
            val category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
            val price = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE))
            val size = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SIZE))
            val color = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COLOR))


            val item = Item(id, title, description, category, price, size, color)
            itemslists.add(item)
        }
        cursor.close()
        db.close()
        return itemslists

    }

    fun updateItem(item: Item) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, item.title)
            put(COLUMN_DESCRIPTION, item.description)
            put(COLUMN_CATEGORY, item.category)
            put(COLUMN_PRICE, item.price)
            put(COLUMN_SIZE, item.size)
            put(COLUMN_COLOR, item.color)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(item.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()

    }


    fun getItemById(itemId: Int): Item {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $itemId"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
        val category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
        val price = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE))
        val size = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SIZE))
        val color = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COLOR))

        cursor.close()
        db.close()
        return Item(id, title, description, category, price, size, color)

    }

    fun deteleItem(itemId: Int)
    {
        val db=writableDatabase
        val whereClause = "$COLUMN_ID =?"
        val whereArgs = arrayOf(itemId.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }

}


