package id.co.flipbox.sosoito;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoadingLayout extends RelativeLayout {
    public static final long DEFAULT_ANIMATION_DURATION = 2500L;
    private View           loadingView;
    private TextView       loadingMessage;
    private View           emptyView;
    private ImageView      emptyIcon;
    private TextView       emptyMessage;
    private ProgressDialog progressDialog;
    private View           customLoadingView;
    private ImageView      customLoadingIcon;
    private TextView       customLoadingMessage;
    private AnimatorSet    animatorSet;

    public LoadingLayout(Context context) {
        super(context);
        init();
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.progressbar, this);
        inflate(getContext(), R.layout.empty_view, this);
        inflate(getContext(), R.layout.customprogressbar, this);
        reset();
    }

    public void reset()
    {
        loadingView = findViewById(R.id.progressbar);
        loadingMessage = (TextView) findViewById(R.id.progressmessage);

        emptyView = findViewById(R.id.empty_view);
        emptyIcon = (ImageView) findViewById(R.id.empty_icon);
        emptyMessage = (TextView) findViewById(R.id.empty_message);
        emptyIcon.setImageResource(R.drawable.icon_problem);
        emptyMessage.setText(R.string.text_empty_message);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        customLoadingView = findViewById(R.id.customprogressbar);
        customLoadingIcon = (ImageView) findViewById(R.id.cpb_icon);
        customLoadingMessage = (TextView) findViewById(R.id.cpb_message);
        emptyIcon.setImageResource(R.drawable.cpb_default_icon);
        emptyMessage.setText(R.string.text_loading_message);
        defaultAnimatorSetup();
    }

    private void defaultAnimatorSetup()
    {
        AnimatorSet cLIAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.flip);
        cLIAnimatorSet.setTarget(customLoadingIcon);
        animatorSet = new AnimatorSet();
        animatorSet.play(cLIAnimatorSet);
        animatorSet.setDuration(DEFAULT_ANIMATION_DURATION);
    }

    public void setProgressDialog(ProgressDialog progressDialog)
    {
        this.progressDialog = progressDialog;
    }

    public void showProgressDialog ()
    {
        if (progressDialog != null && !progressDialog.isShowing())
        {
            progressDialog.show();
        }
    }

    public void showProgressDialog (String message)
    {
        if (progressDialog != null && !progressDialog.isShowing())
        {
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    public void setProgressDialogMessage(String message)
    {
        progressDialog.setMessage(message);
    }

    public void hideProgressDialog ()
    {
        if (progressDialog != null && progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
    }

    public void showLoading(final boolean show) {
        for (int i=0; i < this.getChildCount(); i++)
        {
            this.getChildAt(i).setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        }
        emptyView.setVisibility(View.GONE);
        customLoadingView.setVisibility(View.GONE);
        loadingView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void showLoading(final boolean show, final String message) {
        for (int i=0; i < this.getChildCount(); i++)
        {
            this.getChildAt(i).setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        }
        emptyView.setVisibility(View.GONE);
        customLoadingView.setVisibility(View.GONE);
        loadingView.setVisibility(show ? View.VISIBLE : View.GONE);
        loadingMessage.setText(message);
        loadingMessage.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showLoadingAnimated(final boolean show, final View screenView)
    {
        int shortAnimTime = getContext().getResources().getInteger(android.R.integer.config_shortAnimTime);
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

    public void showEmptyView(final boolean show)
    {
        emptyView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void showEmptyView(final boolean show, String message)
    {
        emptyMessage.setText(message);
        showEmptyView(show);
    }

    public void showEmptyView(final boolean show, int iconResId)
    {
        emptyIcon.setImageResource(iconResId);
        showEmptyView(show);
    }

    public void showEmptyView(final boolean show, int iconResId, String message)
    {
        emptyIcon.setImageResource(iconResId);
        emptyMessage.setText(message);
        showEmptyView(show);
    }

    public void showCustomLoading(final boolean show) {
        for (int i=0; i < this.getChildCount(); i++)
        {
            this.getChildAt(i).setVisibility(show ? INVISIBLE : VISIBLE);
        }
        emptyView.setVisibility(GONE);
        loadingView.setVisibility(GONE);
        customLoadingView.setVisibility(show ? VISIBLE : GONE);
        if (show)
            animatorSet.start();
        else
            animatorSet.end();
    }

    public void showCustomLoading(final boolean show, final String message) {
        for (int i=0; i < this.getChildCount(); i++)
        {
            this.getChildAt(i).setVisibility(show ? INVISIBLE : VISIBLE);
        }
        emptyView.setVisibility(GONE);
        loadingView.setVisibility(GONE);
        customLoadingView.setVisibility(show ? VISIBLE : GONE);
        customLoadingMessage.setText(message);
        customLoadingMessage.setVisibility(show ? VISIBLE : GONE);
        if (show)
            animatorSet.start();
        else
            animatorSet.end();
    }

    public void showCustomLoading(final boolean show, int iconResId) {
        for (int i=0; i < this.getChildCount(); i++)
        {
            this.getChildAt(i).setVisibility(show ? INVISIBLE : VISIBLE);
        }
        emptyView.setVisibility(GONE);
        loadingView.setVisibility(GONE);
        customLoadingView.setVisibility(show ? VISIBLE : GONE);
        customLoadingIcon.setImageResource(iconResId);
        if (show)
            animatorSet.start();
        else
            animatorSet.end();
    }

    public void showCustomLoading(final boolean show, int iconResId, final String message) {
        for (int i=0; i < this.getChildCount(); i++)
        {
            this.getChildAt(i).setVisibility(show ? INVISIBLE : VISIBLE);
        }
        emptyView.setVisibility(GONE);
        loadingView.setVisibility(GONE);
        customLoadingView.setVisibility(show ? VISIBLE : GONE);
        customLoadingIcon.setImageResource(iconResId);
        customLoadingMessage.setText(message);
        customLoadingMessage.setVisibility(show ? VISIBLE : GONE);
        if (show)
            animatorSet.start();
        else
            animatorSet.end();
    }

    public void setCLVAnimator(int animatorResId)
    {
        AnimatorSet cLIAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), animatorResId);
        cLIAnimatorSet.setTarget(customLoadingIcon);
        animatorSet = new AnimatorSet();
        animatorSet.play(cLIAnimatorSet);
        animatorSet.setDuration(DEFAULT_ANIMATION_DURATION);
    }

    public void setCLVAnimator(int animatorResId, long duration)
    {
        AnimatorSet cLIAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), animatorResId);
        cLIAnimatorSet.setTarget(customLoadingIcon);
        animatorSet = new AnimatorSet();
        animatorSet.play(cLIAnimatorSet);
        animatorSet.setDuration(duration);
    }
}