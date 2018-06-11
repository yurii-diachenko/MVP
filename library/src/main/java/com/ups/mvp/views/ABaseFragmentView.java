package com.ups.mvp.views;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ups.mvp.R;
import com.ups.mvp.presenters.IPresenter;

/**
 * Created by test on 23.09.2015.
 */
public abstract class ABaseFragmentView<T extends IPresenter> extends Fragment implements IView {

    protected T presenter;
    protected View root;
    private ProgressDialog progressDialog;

    protected abstract T createPresenter();

    protected abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(getLayoutId(), container, false);
            onViewCreated(savedInstanceState);
            presenter = createPresenter();
            presenter.init();
        }

        return root;
    }

    /**
     * just for the case if we need to get something from intent
     *
     * @param savedInstanceState
     */
    protected void onViewCreated(Bundle savedInstanceState) {
    }

    @Override
    public void showProgress() {
        try {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hideProgress() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getErrorTitle(Throwable exception) {
        return getString(R.string.app_name);
    }

    @Override
    public void showErrorMessage(String title, @StringRes int stringRes) {
        showErrorMessage(title, getString(stringRes));
    }

    @Override
    public void showErrorMessage(String title, String body) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(title != null ? title : getString(R.string.app_name));
            builder.setMessage(body);
            builder.setNeutralButton(R.string.btn_ok, null);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.paused();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resumed();
    }
}
