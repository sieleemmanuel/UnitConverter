package com.siele.unitconverter.ui

import androidx.lifecycle.Observer
import java.util.concurrent.TimeoutException



import androidx.lifecycle.LiveData
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

fun <T> LiveData<T>.getOrAwaitValue(
    time:Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T{
    var data:T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(t: T) {
            data = t
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }

    }
    this.observeForever(observer)

    if (!latch.await(time, timeUnit)){
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}