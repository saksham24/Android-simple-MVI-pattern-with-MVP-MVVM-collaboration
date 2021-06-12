package com.mvp.mvi.views

interface ILoginView<T> {
    fun onStateChanged(state: T)
}
