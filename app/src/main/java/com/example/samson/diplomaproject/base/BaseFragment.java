package com.example.samson.diplomaproject.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment<A extends Activity> extends Fragment {

    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getLayoutResId() == 0)
            throw new RuntimeException("Forgot set layout!!!");

        mRootView = inflater.inflate(getLayoutResId(), container, false);
        return mRootView;
    }

    @SuppressWarnings("unchecked")
    protected A getHostActivity() {
        return (A) getActivity();
    }

    @LayoutRes
    protected abstract int getLayoutResId();

    @SuppressWarnings("unchecked")
    protected <T extends View> T $(@IdRes int _resId) {
        return (T) mRootView.findViewById(_resId);
    }

    protected void setClickListeners(View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }
}
