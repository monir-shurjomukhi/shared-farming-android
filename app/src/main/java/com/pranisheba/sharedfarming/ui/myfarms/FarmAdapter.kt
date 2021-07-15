package com.pranisheba.sharedfarming.ui.myfarms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pranisheba.sharedfarming.R
import com.pranisheba.sharedfarming.model.Invoice
import com.squareup.picasso.Picasso


class FarmAdapter(
  private val list: List<Invoice>,
  private val onItemClicked: (position: Int) -> Unit
) :
  RecyclerView.Adapter<FarmAdapter.MyViewHolder>() {

  class MyViewHolder(view: View, private val onItemClicked: (position: Int) -> Unit) :
    RecyclerView.ViewHolder(view), View.OnClickListener {

    init {
      view.setOnClickListener(this)
    }

    var image: ImageView = view.findViewById(R.id.image)
    var name: TextView = view.findViewById(R.id.name)
    var price: TextView = view.findViewById(R.id.price)
    var unit: TextView = view.findViewById(R.id.unit)

    override fun onClick(v: View?) {
      onItemClicked(adapterPosition)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val itemView: View = LayoutInflater
      .from(parent.context)
      .inflate(
        R.layout.recycler_item_farm,
        parent,
        false
      )
    return MyViewHolder(itemView, onItemClicked)
  }

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val listData = list[position]

    //Loading Image into view
    Picasso.get().load(listData.product?.image).placeholder(R.mipmap.ic_launcher).into(holder.image)
    holder.name.text = listData.product?.name
    holder.price.text = String.format("Amount: %s", listData.amount)
    holder.unit.text = String.format("Unit: %d", listData.unit)
  }

  override fun getItemCount(): Int {
    return list.size
  }
}