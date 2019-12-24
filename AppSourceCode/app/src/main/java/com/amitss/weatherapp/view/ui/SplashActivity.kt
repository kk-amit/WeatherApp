package com.amitss.weatherapp.view.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.amitss.weatherapp.R
import com.amitss.weatherapp.view.util.SPLASH_DELAY
import timber.log.Timber

class SplashActivity : BaseActivity() {

    private var mDelayHandler: Handler? = null

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d(getString(R.string.str_on_create))
        setContentView(R.layout.activity_splash)

        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)


    }

    public override fun onDestroy() {
        Timber.d(getString(R.string.str_on_destroy))
        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
        super.onDestroy()
    }
}
