package com.moodsmap.waterlogging.presentation.rxutil;


import io.reactivex.subscribers.DisposableSubscriber;

public abstract class SimpleSubscriber<T> extends DisposableSubscriber<T> {
    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable e) {
    }
}
