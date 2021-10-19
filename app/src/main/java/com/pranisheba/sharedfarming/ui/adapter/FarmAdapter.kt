package com.pranisheba.sharedfarming.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
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

    private val farmCard: CardView = view.findViewById(R.id.farmCardView)
    val image: ImageView = view.findViewById(R.id.image)
    val name: TextView = view.findViewById(R.id.name)
    val price: TextView = view.findViewById(R.id.price)
    val unit: TextView = view.findViewById(R.id.unit)

    init {
      farmCard.setOnClickListener(this)
    }

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
    Picasso.get().load(listData.product?.image).placeholder(R.drawable.ic_baseline_image_24)
      .error(R.drawable.ic_baseline_broken_image_24).into(holder.image)
    holder.name.text = listData.product?.name
    holder.price.text = String.format("Amount: %s", listData.amount)
    holder.unit.text = String.format("Unit: %d", listData.unit)
  }

  override fun getItemCount(): Int {
    return list.size
  }
}