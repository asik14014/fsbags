package com.fourseasonsweb.fsbags

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.content_product.*


class ProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        setSupportActionBar(toolbar)

        init(intent.getStringExtra("productId").toInt())
    }

    private fun init(productId: Int) {
        val product = MainActivity.database!!.productDao().getProduct(productId)

        description.setText(product.Description)
        description.isEnabled = false
        try {
            Glide.with(this).load(product.Image).into(findViewById(R.id.backdrop) as ImageView)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        setTitle(product.Name)
    }
}
