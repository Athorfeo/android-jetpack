package com.obcompany.androidjetpack.app.ui.about

import android.os.Bundle
import android.view.*
import com.obcompany.androidjetpack.databinding.FragmentAboutBinding
import com.obcompany.androidjetpack.BuildConfig
import com.obcompany.androidjetpack.app.ui.SimpleFragment


class AboutFragment: SimpleFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentAboutBinding.inflate(inflater, container, false)

        binding.textVersion.text = BuildConfig.VERSION_NAME
        binding.textThanks.text = "The Movie Database - https://www.themoviedb.org"
        binding.textAppYear.text = "2019"
        binding.textAuthorName.text = "Juan D. Ortiz"
        binding.textAuthorGithubNickname.text = "@Athorfeo"

        return binding.root
    }

}