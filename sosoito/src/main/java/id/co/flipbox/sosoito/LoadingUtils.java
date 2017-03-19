package id.co.flipbox.sosoito;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.view.View;

public class LoadingUtils
{
    public static void showLoading(boolean show, View screenView, View loadingView) {
        screenView.setVisibility(show ? View.GONE : View.VISIBLE);
        loadingView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static void showLoadingAnimated(final boolean show, final Context context
            , final View screenView, final View loadingView)
    {
        int shortAnimTime = context.getResources().getInteger(android.R.integer.config_shortAnimTime);
        screenView.setVisibility(show ? View.GONE : View.VISIBLE);
        screenView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                screenView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });
        loadingView.setVisibility(show ? View.VISIBLE : View.GONE);
        loadingView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                loadingView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    public static ProgressDialog getNotCancelableProgressDialog(Context context)
    {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }
}
