package com.saidov.news2022.modules.main.home.newsdetails

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.saidov.news2022.R
import com.saidov.news2022.core.fragment.BaseFragment
import com.saidov.news2022.modules.main.ui.vm.SharedViewModel
import com.saidov.news2022.other.Constants.Companion.DESCRIPTION
import com.saidov.news2022.other.Constants.Companion.PUBLISHED_AT
import com.saidov.news2022.other.Constants.Companion.TITLE
import com.saidov.news2022.other.Constants.Companion.URL
import com.saidov.news2022.other.Constants.Companion.URL_TO_IMAGE
import kotlin.properties.Delegates


class DetailFragment() : BaseFragment(R.layout.fragment_detail) {
    private var imageNews: ImageView by Delegates.notNull()
    private var titleNews: TextView by Delegates.notNull()
    private var descriptionNews: TextView by Delegates.notNull()
    private var urlNews: TextView by Delegates.notNull()
    private var publishNews: TextView by Delegates.notNull()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        imageNews = findViewByID(R.id.imageViewDetail)
        titleNews = findViewByID(R.id.titleDetail)
        descriptionNews = findViewByID(R.id.descriptionDetail)
        urlNews = findViewByID(R.id.urlNewsDetail)
        publishNews = findViewByID(R.id.publishDetail)
        sharedViewModel.setToolBarBackVisibility(true)
    }

    private fun initData() {
        val data = arguments
        val title = data?.getString(TITLE)
        val description = data?.getString(DESCRIPTION)
        val urlToImage = data?.getString(URL_TO_IMAGE)
        val url = data?.getString(URL)
        val publish = data?.get(PUBLISHED_AT)
        Glide.with(requireActivity()).load(urlToImage).into(imageNews)
        titleNews.text = title
        descriptionNews.text = description
        urlNews.text = url
        publishNews.text = "${getString(R.string.published)} $publish"
    }
}
