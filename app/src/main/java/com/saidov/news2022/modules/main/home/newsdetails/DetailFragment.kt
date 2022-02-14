package com.saidov.news2022.modules.main.home.newsdetails

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.saidov.news2022.R
import com.saidov.news2022.core.callback.OnToolBarChangedListener
import com.saidov.news2022.core.fragment.BaseFragment


class DetailFragment() :
    BaseFragment(R.layout.fragment_detail) {
    lateinit var imageNews: ImageView
    lateinit var titleNews: TextView
    lateinit var descriptionNews: TextView
    lateinit var urlNews: TextView
    lateinit var publishNews: TextView
    var listener: OnToolBarChangedListener? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageNews = view.findViewById(R.id.imageViewDetail)
        titleNews = view.findViewById(R.id.titleDetail)
        descriptionNews = view.findViewById(R.id.descriptionDetail)
        urlNews = view.findViewById(R.id.urlNewsDetail)
        publishNews = view.findViewById(R.id.publishDetail)

        listener?.setToolBarBackVisibility(true)

        val data = this.arguments
        val title = data?.get("title")
        val description = data?.get("description")
        val urlToImage = data?.get("urlToImage")
        val url = data?.get("url")
        val publish = data?.get("publishedAt")

        Glide.with(requireActivity())
            .load(urlToImage)
            .into(imageNews)

        listener?.setToolbarName(title = title as String)
        titleNews.text = title as CharSequence?
        descriptionNews.text = description as CharSequence?
        urlNews.text = url as CharSequence?
        publishNews.text = "Опубликовано: $publish" as CharSequence?
    }

    companion object{
        fun newInstance(listener: OnToolBarChangedListener?):DetailFragment{
            val fragment:DetailFragment = DetailFragment()
            fragment.listener=listener
            return fragment
        }

    }


}