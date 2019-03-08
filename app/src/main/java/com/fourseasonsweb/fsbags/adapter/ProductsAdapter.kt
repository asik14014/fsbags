package com.fourseasonsweb.fsbags.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import com.fourseasonsweb.fsbags.R
import com.fourseasonsweb.fsbags.ShoppingCartActivity
import com.fourseasonsweb.fsbags.data.Product

class ProductsAdapter(private val mContext: Context, private val productList: List<Product>) :
    RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var count: TextView
        var thumbnail: ImageView
        var overflow: ImageView

        init {
            title = view.findViewById(R.id.title)
            count = view.findViewById(R.id.count)
            thumbnail = view.findViewById(R.id.thumbnail) as ImageView
            overflow = view.findViewById(R.id.overflow) as ImageView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_product, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = productList[position]
        holder.title.setText(product.getName())
        holder.count.setText(product.getDescription())

        //раскоментировать
        //Glide.with(mContext).load(product.getThumbnail()).into(holder.thumbnail)

        holder.overflow.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                showPopupMenu(holder.overflow)
            }
        })
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private fun showPopupMenu(view: View) {
        // inflate menu
        val popup = PopupMenu(mContext, view)
        val inflater = popup.getMenuInflater()
        inflater.inflate(R.menu.menu_product, popup.getMenu())
        popup.setOnMenuItemClickListener(MyMenuItemClickListener())
        popup.show()
    }

    /**
     * Click listener for popup menu items
     */
    internal inner class MyMenuItemClickListener : PopupMenu.OnMenuItemClickListener {

        override fun onMenuItemClick(menuItem: MenuItem): Boolean {
            when (menuItem.getItemId()) {
                R.id.action_add_favourite -> {
                    Toast.makeText(mContext, "Added to shopping cart", Toast.LENGTH_SHORT).show()
                    //добавить в базу
                    return true
                }
                R.id.action_play_next -> {
                    Toast.makeText(mContext, "Open product", Toast.LENGTH_SHORT).show()

                    val intent = Intent(mContext, ShoppingCartActivity::class.java)
                    mContext.startActivity(intent)

                    return true
                }
            }
            return false
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}