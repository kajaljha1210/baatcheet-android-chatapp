package com.example.baatcheet.ui.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieCompositionFactory
import com.example.baatcheet.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val _lottieComposition = MutableStateFlow<LottieComposition?>(null)
    val lottieComposition: StateFlow<LottieComposition?> = _lottieComposition

    private val _isLottieReady = MutableStateFlow(false)
    val isLottieReady: StateFlow<Boolean> = _isLottieReady

    init {
        preloadLottie(application.applicationContext)
    }

    private fun preloadLottie(context: Context) {
        val result = LottieCompositionFactory.fromRawRes(context, R.raw.intro)

        result.addListener { composition ->
            _lottieComposition.value = composition
            _isLottieReady.value = true
            Log.d("Lottie", "Loaded successfully!")
        }

        result.addFailureListener {
            Log.e("Lottie", "Failed to load animation", it)
        }
    }
}

