package com.armandev.mileprix

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class OrderlistView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orderlist_view)
        supportActionBar!!.hide()

        val active_order = findViewById<Button>(R.id.activeOrder)

        active_order.setOnClickListener {
//            val active_order_fragment  = findViewById<>(R.id.activeOrderFragment)
        }
    }
}