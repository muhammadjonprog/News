package com.saidov.news2022.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saidov.news2022.R
import com.saidov.news2022.model.Article

/**
 * Created by MUHAMMADJON SAIDOV on 15,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 *
 */
class NewsAdapter (private val context: Context?, private val newsList: MutableList<Article>):
    RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.imageNews)
        val titleNews: TextView = itemView.findViewById(R.id.titleNews)
        val descriptionNews: TextView = itemView.findViewById(R.id.descriptionNews)


        fun bind(listItem: Article) {
            image.setOnClickListener {
//                Toast.makeText(it.context, "нажал на ${itemView.image}", Toast.LENGTH_SHORT)
//                    .show()
            }
            itemView.setOnClickListener {
               /// Toast.makeText(it.context, "нажал на ${itemView.txt_name.text}", Toast.LENGTH_SHORT).show()
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listItem = newsList[position]
        holder.bind(listItem)

        Glide.with(holder.itemView.context)
            .load(newsList[position].urlToImage)
            .into(holder.image)
        holder.titleNews.text = newsList[position].title
        holder.descriptionNews.text = newsList[position].description

    }

    override fun getItemCount() = newsList.size
}