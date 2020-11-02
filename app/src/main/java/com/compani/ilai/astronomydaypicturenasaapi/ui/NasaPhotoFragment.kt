package com.compani.ilai.astronomydaypicturenasaapi.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.compani.ilai.astronomydaypicturenasaapi.R
import com.compani.ilai.astronomydaypicturenasaapi.glide.GlideApp
import com.compani.ilai.astronomydaypicturenasaapi.utils.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_nasa_photo.*
import kotlinx.android.synthetic.main.layout_loading.*

@AndroidEntryPoint
class NasaPhotoFragment : Fragment(R.layout.fragment_nasa_photo) {

    private val viewModel by viewModels<NasaPhotoViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.nasaPhoto.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    showLoading(false)
                    tv_title.text = it.data?.title
                    GlideApp.with(iv_photo)
                        .load(it.data?.hdurl)
                        .into(iv_photo)

                    tv_date.text = it.data?.date
                    tv_explanation.text = it.data?.explanation
                }
                Status.ERROR -> {
                    showLoading(false)
                    Snackbar.make(requireView(), it.message!!, Snackbar.LENGTH_SHORT).show()
                }
                Status.LOADING -> showLoading(true)
            }
        })

    }

    private fun showLoading(isShow: Boolean) {
        if(isShow) {
            loading_container.visibility = View.VISIBLE
        } else {
            loading_container.visibility = View.GONE
        }
    }
}