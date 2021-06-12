package com.mvp.mvi.views;

public interface ILoginView<T> {
    void onStateChanged(T state);
}
