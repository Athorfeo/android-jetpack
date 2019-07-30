package com.obcompany.androidjetpack.utility.base

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {
    lateinit var loadingDialog: AlertDialog
}