package com.app.idtexam.utils

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class DualClickListener : View.OnClickListener {
    private var lastClickTime: Long = 0
    private val DOUBLE_CLICK_TIME_DELTA: Long = 300
    private var job: Job? = null

    override fun onClick(v: View) {
        val clickTime = System.currentTimeMillis()
        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
            job?.cancel()
            onDoubleClick(v)
        } else {
            job = CoroutineScope(Dispatchers.Main).launch {
                delay(DOUBLE_CLICK_TIME_DELTA)
                onSingleClick(v)
            }
        }
        lastClickTime = clickTime
    }
    abstract fun onDoubleClick(v: View)
    abstract fun onSingleClick(v: View)
}
