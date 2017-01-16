package com.ups.mvp.views;

import android.support.annotation.StringRes;

public interface IView {

    void showProgress();
    void hideProgress();
    void showErrorMessage(String title, String body);
    void showErrorMessage(String title, @StringRes int stringRes);
    String getErrorTitle(Throwable exception);
}
