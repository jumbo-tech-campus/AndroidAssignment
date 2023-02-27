package com.abhinash.jumboandroidassignment.extentions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

fun <T> Fragment.observe(liveData: LiveData<T>, action: (T) -> Unit) =
    liveData.observe(viewLifecycleOwner) { action(it) }