package io.armcha.arch;

/**
 */

public interface BaseLoadingContract {

    interface View extends BaseMVPContract.View {

        void showLoading();

        void hideLoading();

        void showError(String errorMessage);
    }

    interface Presenter extends BaseMVPContract.Presenter<View> {

    }

}
