package com.ups.mvp.views;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.ups.mvp.R;
import com.ups.mvp.presenters.IPresenter;

import butterknife.ButterKnife;

public abstract class ABaseActivityView<T extends IPresenter> extends FragmentActivity implements IView {
    protected ProgressDialog progressDialog;

    protected T presenter;

    protected abstract T createPresenter();

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        onViewCreated(savedInstanceState);
        presenter = createPresenter();
        presenter.init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(ABaseActivityView.this);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            }
        });
    }

    @Override
    public void hideProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            }
        });
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
    public void showErrorMessage(final String title, final String body) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ABaseActivityView.this);
                builder.setTitle(title);
                builder.setMessage(body);
                builder.setNeutralButton(R.string.btn_ok, null);
                builder.show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.paused();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.resumed();
    }
}