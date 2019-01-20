package udacity.lsaippa.cooking.utils;


import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("ALL")
public class IdleResource implements IdlingResource {

    @Nullable
    private volatile ResourceCallback mCallback;
    private AtomicBoolean isIdle = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdle.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback = callback;
    }


    public void setIdleState(boolean isIdleNow) {
        isIdle.set(isIdleNow);
        if (isIdleNow && mCallback != null) {
            //noinspection ConstantConditions
            mCallback.onTransitionToIdle();
        }
    }

}
