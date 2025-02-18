package com.abadzheva.contentprovidersextraapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //==============================================

        thread {
            val cursor =
                contentResolver.query(
                    "content://com.abadzheva.shoppinglist/shop_items/".toUri(),
                    null,
                    null,
                    null,
                    null,
                    null,
                )
            while (cursor?.moveToNext() == true) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val count = cursor.getInt(cursor.getColumnIndexOrThrow("count"))
                val enabled = cursor.getInt(cursor.getColumnIndexOrThrow("enabled")) > 0
                val shopItem =
                    ShopItem(
                        id = id,
                        name = name,
                        count = count,
                        enabled = enabled,
                    )
                Log.d("MainActivity", shopItem.toString())
            }
            cursor?.close()
        }
    }
}