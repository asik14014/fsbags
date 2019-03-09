package com.fourseasonsweb.fsbags

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.widget.Toast
import com.fourseasonsweb.fsbags.adapter.ShoppingCartAdapter
import com.fourseasonsweb.fsbags.data.Product
import com.fourseasonsweb.fsbags.data.room.models.OrderEntity
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import kotlinx.android.synthetic.main.content_shopping_cart.*
import java.util.*

class ShoppingCartActivity : AppCompatActivity() {

    private val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
    private var adapter: ShoppingCartAdapter? = null
    private var recyclerView: RecyclerView? =null
    private var productList: ArrayList<Product> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
        setSupportActionBar(toolbar)

        initRecyclerView()
        fillProductList()

        create_order_button.setOnClickListener { createOrder() }
    }


    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_cart)
        val mLayoutManager = GridLayoutManager(this, 1)
        productList = ArrayList()
        adapter = ShoppingCartAdapter(this, productList)

        recyclerView!!.layoutManager = mLayoutManager
        recyclerView!!.addItemDecoration(GridSpacingItemDecoration(1, dpToPx(10), true))
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.adapter = adapter
    }

    private fun fillProductList(){
        productList.clear()
        var userName = ""
        if (MainActivity.userName != null) userName = MainActivity.userName!!
        val cartList = MainActivity.database!!.cartDao().getAllByUser(userName)

        for (item in cartList)
        {
            val product = MainActivity.database!!.productDao().getProduct(item.ProductId)
            productList.add(Product(product.Id, product.Name, product.Description, product.Image, product.price))
        }

        //now adding the adapter to recyclerview
        adapter!!.notifyDataSetChanged()
    }

    private fun dpToPx(dp: Int): Int {
        val r = resources
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics))
    }

    private fun createOrder() {
        val userName = MainActivity.userName!!
        val cartList = MainActivity.database!!.cartDao().getAllByUser(userName)

        for (item in cartList) {
            MainActivity.database!!.orderDao().insert(OrderEntity(0, item.ProductId, userName, Date()))
            MainActivity.database!!.cartDao().delete(item)
        }
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
        Toast.makeText(this, "Заказ успешно оформлен", Toast.LENGTH_SHORT).show()

    }
}
