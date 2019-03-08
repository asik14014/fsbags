package com.fourseasonsweb.fsbags

import android.arch.persistence.room.Room
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
import com.fourseasonsweb.fsbags.data.room.BagsDatabase
import com.fourseasonsweb.fsbags.data.room.models.ProductEntity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        var database: BagsDatabase? = null
        var userName: String? = ""
    }

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
        MainActivity.database =  Room.databaseBuilder(this,BagsDatabase::class.java,"bagsDatabase")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

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
        val covers = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)

        MainActivity.database!!.productDao().insert(ProductEntity(1, "True Romance", "", 10.0, covers[0]))
        MainActivity.database!!.productDao().insert(ProductEntity(2, "Xscpae", "", 10.0, covers[1]))
        MainActivity.database!!.productDao().insert(ProductEntity(3, "Maroon 5", "", 10.0, covers[2]))
        MainActivity.database!!.productDao().insert(ProductEntity(4, "Born to Die", "", 10.0, covers[3]))
        MainActivity.database!!.productDao().insert(ProductEntity(5, "Honeymoon", "", 10.0, covers[4]))
        MainActivity.database!!.productDao().insert(ProductEntity(6, "I Need a Doctor", "", 10.0, covers[5]))
        MainActivity.database!!.productDao().insert(ProductEntity(7, "Loud", "", 10.0, covers[6]))
        MainActivity.database!!.productDao().insert(ProductEntity(8, "Legend", "", 10.0, covers[7]))
        MainActivity.database!!.productDao().insert(ProductEntity(9, "Hello", "", 10.0, covers[8]))
        MainActivity.database!!.productDao().insert(ProductEntity(10, "Greatest Hits", "", 10.0, covers[9]))
        MainActivity.database!!.productDao().insert(ProductEntity(11, "True Romance", "", 10.0, covers[10]))
        MainActivity.database!!.productDao().insert(ProductEntity(12, "True Romance", "", 10.0, covers[11]))

        for (item in MainActivity.database!!.productDao().getAllProducts())
        {
            productList.add(Product(item.Id, item.Name, item.Description, item.Image, item.price))
        }

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
                LoginActivity.mGoogleSignInClient!!.signOut()
                    .addOnCompleteListener(this) {
                        val intent = Intent(this, LoginActivity::class.java)
                        finish()
                        startActivity(intent)
                    }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
