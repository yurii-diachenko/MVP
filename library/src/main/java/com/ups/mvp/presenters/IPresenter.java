package com.ups.mvp.presenters;


import com.ups.mvp.views.IView;

public abstract class IPresenter<T extends IView> {
    protected T view;
    public IPresenter(T view) {
        this.view = view;
    }

    public abstract void init();

    public void resumed() {
    }

    public void paused() {
    }

    public void started() {
    }

    public void stopped() {
    }
}
