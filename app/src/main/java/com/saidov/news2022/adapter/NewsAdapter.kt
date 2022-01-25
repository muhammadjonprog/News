package com.saidov.news2022.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.saidov.news2022.model.Article

import com.saidov.news2022.R
import com.saidov.news2022.view.DetailFragment
/**
 * Created by MUHAMMADJON SAIDOV on 22,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */
class NewsAdapter : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    private var newsResponse = ArrayList<Article>()

    private var callBack : NewsAdapterCallBack? = null

  inner  class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

       private val itemTitle : TextView = itemView.findViewById<TextView>(R.id.titleNews)
       private val itemDescription : TextView = itemView.findViewById<TextView>(R.id.descriptionNews)
       private val itemImage : ImageView = itemView.findViewById<ImageView>(R.id.imageNews)
       private val cardView : CardView = itemView.findViewById<CardView>(R.id.cardView)


        fun bind(newsResponse: Article){
            itemTitle.text = newsResponse.title
            itemDescription.text = newsResponse.description

            Glide.with(itemView.context)
                .load(newsResponse.urlToImage)
                .into(itemImage)

            cardView.setOnClickListener {
                callBack?.itemOnClickListener(newsResponse)

            }

            cardView.setOnLongClickListener {
                callBack?.itemOnLongClickListener(newsResponse,it)
                true
            }
        }
    }

    fun setCallBack(callBack: NewsAdapterCallBack){
        this.callBack = callBack
    }

    fun updatedData(newsResponse : ArrayList<Article>){
        this.newsResponse = newsResponse
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(com.saidov.news2022.R.layout.news_row,parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: NewsAdapter.MyViewHolder, position: Int) {
        holder.bind(newsResponse[position])
    }

    override fun getItemCount(): Int {
        return newsResponse.size
    }

}
