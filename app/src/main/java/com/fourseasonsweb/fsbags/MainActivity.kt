package com.fourseasonsweb.fsbags

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import com.fourseasonsweb.fsbags.adapter.ProductsAdapter
import com.fourseasonsweb.fsbags.data.Product
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var adapter: ProductsAdapter? = null
    private var productList: MutableList<Product> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val intent = Intent(this, ShoppingCartActivity::class.java)
            startActivity(intent)
        }

        init()
    }

    private fun init() {
        recyclerView = findViewById(R.id.recycler_view) as RecyclerView

        productList = ArrayList()
        adapter = ProductsAdapter(this, productList)

        val mLayoutManager = GridLayoutManager(this, 2)
        recyclerView!!.layoutManager = mLayoutManager
        recyclerView!!.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(10), true))
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.adapter = adapter

        prepareProducts()

        try {
            //Glide.with(this).load(R.drawable.cover).into(findViewById(R.id.backdrop) as ImageView)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun dpToPx(dp: Int): Int {
        val r = resources
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics))
    }

    private fun prepareProducts() {
        /*
        val covers = intArrayOf(
            R.drawable.album1,
            R.drawable.album2,
            R.drawable.album3,
            R.drawable.album4,
            R.drawable.album5,
            R.drawable.album6,
            R.drawable.album7,
            R.drawable.album8,
            R.drawable.album9,
            R.drawable.album10,
            R.drawable.album11
        )*/
        val covers = intArrayOf(1,2,3,4,5,6,7,8,9,10,11)

        var a = Product(0, "True Romance", "", covers[0])
        productList.add(a)

        a = Product(1, "Xscpae", "", covers[1])
        productList.add(a)

        a = Product(2, "Maroon 5", "", covers[2])
        productList.add(a)

        a = Product(3, "Born to Die", "", covers[3])
        productList.add(a)

        a = Product(4, "Honeymoon", "", covers[4])
        productList.add(a)

        a = Product(5, "I Need a Doctor", "", covers[5])
        productList.add(a)

        a = Product(6, "Loud", "", covers[6])
        productList.add(a)

        a = Product(7, "Legend", "", covers[7])
        productList.add(a)

        a = Product(8, "Hello", "", covers[8])
        productList.add(a)

        a = Product(9, "Greatest Hits", "", covers[9])
        productList.add(a)

        adapter!!.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                //val intent = Intent(this, SettingsActivity::class.java)
                //startActivity(intent)
                true
            }
            R.id.action_logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
