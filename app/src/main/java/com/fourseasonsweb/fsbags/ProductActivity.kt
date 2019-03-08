package com.fourseasonsweb.fsbags

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.fourseasonsweb.fsbags.adapter.ShoppingCartAdapter
import com.fourseasonsweb.fsbags.data.Product
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : AppCompatActivity() {

    private val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
    private var adapter: ShoppingCartAdapter? = null
    private var recyclerView: RecyclerView? =null
    private var productList: ArrayList<Product> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        setSupportActionBar(toolbar)

        initRecyclerView()

        fillProductList()
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_cart) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        productList = ArrayList()
        adapter = ShoppingCartAdapter(this, productList)
        //recyclerView!!.adapter = adapter
        recyclerView!!.itemAnimator = DefaultItemAnimator()
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
}
