package com.saidov.news2022.modules.main.home.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saidov.news2022.R
import com.saidov.news2022.modules.main.ui.model.ArticleModel

/**
 * Created by MUHAMMADJON SAIDOV on 22,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 */

class NewsAdapter(
    val onClickListener: View.OnClickListener, val onLongClickListener: View.OnLongClickListener
) : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val itemTitle: TextView = itemView.findViewById(R.id.titleNews)
        private val itemDescription: TextView = itemView.findViewById(R.id.descriptionNews)
        private val itemImage: ImageView = itemView.findViewById(R.id.imageNews)

        fun bind() {
            val article = differ.currentList[adapterPosition]
            itemTitle.text = article.title
            itemDescription.text = article.description
            Glide.with(itemView.context).load(article.urlToImage).into(itemImage)
            itemView.setOnClickListener(onClickListener)
            itemView.setOnLongClickListener(onLongClickListener)
            itemView.tag = article
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<ArticleModel>() {
        override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.news_row, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}
