package com.obcompany.androidjetpack.app.ui.example

import android.os.Bundle
import com.obcompany.androidjetpack.databinding.FragmentExampleBinding
import com.obcompany.androidjetpack.utility.DialogUtil
import android.view.*
import com.obcompany.androidjetpack.app.ui.BaseFragment

class ExampleFragment: BaseFragment(), View.OnClickListener{
    private lateinit var binding: FragmentExampleBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentExampleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadingDialog = DialogUtil.progress(activity!!)
    }

    override fun onClick(v: View?) {
        when(v?.id){
        }
    }
}