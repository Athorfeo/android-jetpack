package com.obcompany.androidjetpack.app.ui.about

import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.fragment.app.Fragment
import com.obcompany.androidjetpack.databinding.FragmentAboutBinding
import com.obcompany.androidjetpack.BuildConfig
import com.obcompany.androidjetpack.R
import kotlinx.android.synthetic.main.activity_main.*


class AboutFragment: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentAboutBinding.inflate(inflater, container, false)

        binding.textVersion.text = BuildConfig.VERSION_NAME
        binding.textThanks.text = "The Movie Database - https://www.themoviedb.org"
        binding.textAppYear.text = "2019"
        binding.textAuthorName.text = "Juan D. Ortiz"
        binding.textAuthorGithubNickname.text = "@Athorfeo"

        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }
}