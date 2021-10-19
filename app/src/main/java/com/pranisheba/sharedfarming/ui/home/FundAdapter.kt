package com.pranisheba.sharedfarming.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.pranisheba.sharedfarming.R
import com.pranisheba.sharedfarming.model.FundOpportunity
import com.squareup.picasso.Picasso


class FundAdapter(
  private val list: List<FundOpportunity>,
  private val onItemClicked: (position: Int) -> Unit
) :
  RecyclerView.Adapter<FundAdapter.MyViewHolder>() {

  class MyViewHolder(view: View, private val onItemClicked: (position: Int) -> Unit) :
    RecyclerView.ViewHolder(view), View.OnClickListener {

    private val fundCard: CardView = view.findViewById(R.id.fundCardView)
    val image: ImageView = view.findViewById(R.id.image)
    val name: TextView = view.findViewById(R.id.name)
    val price: TextView = view.findViewById(R.id.price)

    init {
      fundCard.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
      onItemClicked(adapterPosition)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val itemView: View = LayoutInflater
      .from(parent.context)
      .inflate(
        R.layout.recycler_item_fund,
        parent,
        false
      )
    return MyViewHolder(itemView, onItemClicked)
  }

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val listData = list[position]

    //Loading Image into view
    Picasso.get().load(listData.image).placeholder(R.drawable.ic_baseline_image_24)
      .error(R.drawable.ic_baseline_broken_image_24).into(holder.image)
    holder.name.text = listData.name
    holder.price.text = String.format("%.2f/cow", listData.amount)
  }

  override fun getItemCount(): Int {
    return list.size
  }
}