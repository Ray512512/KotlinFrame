package io.armcha.arch;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Bundle;

/**
 */

public interface BaseMVPContract {

    interface View {

    }

    interface Presenter<V extends BaseMVPContract.View> {

        Bundle getStateBundle();

        void attachLifecycle(Lifecycle lifecycle);

        void detachLifecycle(Lifecycle lifecycle);

        void attachView(V view);

        void detachView();

        V getView();

        boolean isViewAttached();

        void onPresenterCreate();

        void onPresenterDestroy();

        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        void onLifecycleChanged(LifecycleOwner owner, Lifecycle.Event event);
    }
}
