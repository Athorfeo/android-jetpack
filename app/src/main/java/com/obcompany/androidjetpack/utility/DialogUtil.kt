package com.obcompany.androidjetpack.utility

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.obcompany.androidjetpack.R

class DialogUtil {
    companion object {
        fun progress(activity: Activity): AlertDialog{
            val builder = AlertDialog.Builder(activity)
            val inflater= activity.layoutInflater
            builder.setView(inflater.inflate(R.layout.dialog_loading, activity.findViewById(android.R.id.content), false))
            return builder.create()
        }
    }
}