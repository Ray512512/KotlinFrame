package com.ray.frame.presentation.simple;


import io.reactivex.subscribers.DisposableSubscriber;

public abstract class SimpleSubscriber<T> extends DisposableSubscriber<T> {
    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable e) {
    }
}
