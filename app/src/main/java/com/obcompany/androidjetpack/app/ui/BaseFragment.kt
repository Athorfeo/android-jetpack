package com.obcompany.androidjetpack.app.ui

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {
    lateinit var loadingDialog: AlertDialog
}