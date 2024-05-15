package com.solutionplus.altasherat.common.presentation

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding

@Suppress("UNCHECKED_CAST")
fun <B : ViewBinding> Class<B>.bindView(lifecycleOwner: LifecycleOwner, container: ViewGroup? = null): B {
    val inflateMethod = this.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.javaPrimitiveType
    )
    val layoutInflater = when (lifecycleOwner) {
        is Activity -> lifecycleOwner.layoutInflater
        is Fragment -> lifecycleOwner.layoutInflater
        else -> throw IllegalArgumentException("Unsupported LifecycleOwner: $lifecycleOwner")
    }
    val invokeLayout = inflateMethod.invoke(null, layoutInflater, container, false) as B
    if (lifecycleOwner is Activity) {
        lifecycleOwner.setContentView(invokeLayout.root)
    }
    return invokeLayout
}


@Suppress("UNCHECKED_CAST")
private fun <T : Any> Any.getClass(): Class<T> {
    return this::class.java as Class<T>
}