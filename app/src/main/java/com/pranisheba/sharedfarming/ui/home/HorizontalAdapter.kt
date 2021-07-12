package com.pranisheba.sharedfarming.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pranisheba.sharedfarming.R
import com.pranisheba.sharedfarming.model.FundOpportunity
import com.squareup.picasso.Picasso


class HorizontalAdapter(
  private val list: List<FundOpportunity>,
  private val onItemClicked: (position: Int) -> Unit
) :
  RecyclerView.Adapter<HorizontalAdapter.MyViewHolder>() {

  class MyViewHolder(view: View, private val onItemClicked: (position: Int) -> Unit) :
    RecyclerView.ViewHolder(view), View.OnClickListener {

    init {
      view.setOnClickListener(this)
    }

    var image: ImageView = view.findViewById(R.id.image)
    var name: TextView = view.findViewById(R.id.name)
    var price: TextView = view.findViewById(R.id.price)

    override fun onClick(v: View?) {
      onItemClicked(adapterPosition)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val itemView: View = LayoutInflater
      .from(parent.context)
      .inflate(
        R.layout.recycler_item,
        parent,
        false
      )
    return MyViewHolder(itemView, onItemClicked)
  }

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val listData = list[position].image

    //Loading Image into view
    Picasso.get().load(listData).placeholder(R.mipmap.ic_launcher).into(holder.image)
    holder.name.text = list[position].name
    holder.price.text = String.format("%.2f/cow", list[position].amount)
  }

  override fun getItemCount(): Int {
    return list.size
  }
}