package com.jshvarts.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_splash.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import android.content.Intent

class SplashActivity : AppCompatActivity() {
    private val itemCount = 50
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        loadingProgressBar.max = itemCount

        observeProgress()
                .concatMap { Observable.just(it).delay(50, TimeUnit.MILLISECONDS) }
                .doOnComplete {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                .subscribe ( { loadingProgressBar.progress++ }, Timber::e)
    }

    private fun observeProgress(): Observable<Int> {
        return Observable.range(1, itemCount)
    }
}