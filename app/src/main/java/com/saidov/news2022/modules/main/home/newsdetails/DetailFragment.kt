package com.saidov.news2022.modules.main.home.newsdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.saidov.news2022.R


class DetailFragment : Fragment() {
    lateinit var imageNews : ImageView
    lateinit var titleNews : TextView
    lateinit var descriptionNews : TextView
    lateinit var urlNews : TextView
    lateinit var publishNews : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        imageNews = view.findViewById(R.id.imageViewDetail)
        titleNews = view.findViewById(R.id.titleDetail)
        descriptionNews = view.findViewById(R.id.descriptionDetail)
        urlNews = view.findViewById(R.id.urlNewsDetail)
        publishNews = view.findViewById(R.id.publishDetail)


        val data = this.arguments
        val title = data?.get("title",)
        val description = data?.get("description",)
        val urlToImage = data?.get("urlToImage",)
        val url = data?.get("url",)
        val publish = data?.get("publishedAt",)

        Glide.with(requireActivity())
            .load(urlToImage)
            .into(imageNews)

        titleNews.text = title as CharSequence?
        descriptionNews.text = description as CharSequence?
        urlNews.text = url as CharSequence?
        publishNews.text ="Опубликовано: $publish" as CharSequence?

        return view
    }

}