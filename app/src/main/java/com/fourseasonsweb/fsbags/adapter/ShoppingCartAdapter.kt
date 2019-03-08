package com.fourseasonsweb.fsbags.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.fourseasonsweb.fsbags.R
import com.fourseasonsweb.fsbags.data.Product
import kotlinx.android.synthetic.main.product_item.view.*

class ShoppingCartAdapter(context: Context, private var productList: ArrayList<Product>)
    : BaseAdapter() {

    private var context: Context? = context

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val product = productList[position]
        val inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val productView = inflator.inflate(R.layout.product_item, null)

        //set Info
        productView.tv_name.text = product.getName()
        productView.tv_price.text = "Цена: ${product.getPrice()}"

        return productView
    }

    override fun getItem(position: Int): Any {
        return  productList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return productList.size
    }
}