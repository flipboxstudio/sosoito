package id.co.flipbox.sosoito;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoadingLayout extends RelativeLayout
{
    public static final long DEFAULT_ANIMATION_DURATION = 2500L;

    private static final int LOADING        = 0;
    private static final int CUSTOM_LOADING = 1;
    private static final int EMPTY          = 2;

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

    public LoadingLayout (Context context)
    {
        super(context);
        init();
    }

    public LoadingLayout (Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LoadingLayout (Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi (api = Build.VERSION_CODES.LOLLIPOP)
    public LoadingLayout (Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init ()
    {
        inflate(getContext(), R.layout.progressbar, this);
        inflate(getContext(), R.layout.empty_view, this);
        inflate(getContext(), R.layout.customprogressbar, this);
        reset();
    }

    public void reset ()
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
        customLoadingIcon.setImageResource(R.drawable.cpb_default_icon);
        customLoadingMessage.setText(R.string.text_loading_message);
        defaultAnimatorSetup();
    }

    public void setProgressDialog (ProgressDialog progressDialog)
    {
        this.progressDialog = progressDialog;
    }

    public void showProgressDialog ()
    {
        showProgressDialog("");
    }

    public void showProgressDialog (String message)
    {
        if (progressDialog != null && !progressDialog.isShowing())
        {
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    public void setProgressDialogMessage (String message)
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

    public void showLoading (final boolean show)
    {
        showLoading(show, null);
    }

    public void showLoading (final boolean show, @Nullable final String message)
    {
        setViewState(show, LOADING);

        if (message != null)
        {
            loadingMessage.setText(message);
            loadingMessage.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @TargetApi (Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showLoadingAnimated (final boolean show, final View screenView)
    {
        int shortAnimTime = getContext().getResources().getInteger(android.R.integer.config_shortAnimTime);
        screenView.setVisibility(show ? View.GONE : View.VISIBLE);
        screenView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd (Animator animation)
            {
                screenView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });
        loadingView.setVisibility(show ? View.VISIBLE : View.GONE);
        loadingView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd (Animator animation)
            {
                loadingView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    public void showCustomLoading (final boolean show)
    {
        showCustomLoading(show, R.drawable.cpb_default_icon, null);
    }

    public void showCustomLoading (final boolean show, final String message)
    {
        showCustomLoading(show, R.drawable.cpb_default_icon, message);
    }

    public void showCustomLoading (final boolean show, int iconResId)
    {
        showCustomLoading(show, iconResId, null);
    }

    public void showCustomLoading (final boolean show, int iconResId, @Nullable final String message)
    {
        setViewState(show, CUSTOM_LOADING);

        customLoadingIcon.setImageResource(iconResId);
        if (message != null)
        {
            customLoadingMessage.setText(message);
            customLoadingMessage.setVisibility(show ? VISIBLE : GONE);
        }

        if (show)
        {
            animatorSet.start();
        }
        else
        {
            animatorSet.end();
        }
    }

    public void showEmptyView (final boolean show)
    {
        showEmptyView(show, R.drawable.icon_problem, null);
    }

    public void showEmptyView (final boolean show, String message)
    {
        showEmptyView(show, R.drawable.icon_problem, message);
    }

    public void showEmptyView (final boolean show, int iconResId)
    {
        showEmptyView(show, iconResId, null);
    }

    public void showEmptyView (final boolean show, int iconResId, @Nullable String message)
    {
        setViewState(show, EMPTY);

        emptyIcon.setImageResource(iconResId);
        if (message != null)
        {
            emptyMessage.setText(message);
        }
    }

    private void defaultAnimatorSetup ()
    {
        setCLVAnimator(R.animator.flip, DEFAULT_ANIMATION_DURATION);
    }

    public void setCLVAnimator (int animatorResId)
    {
        setCLVAnimator(animatorResId, DEFAULT_ANIMATION_DURATION);
    }

    public void setCLVAnimator (int animatorResId, long duration)
    {
        AnimatorSet cLIAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), animatorResId);
        cLIAnimatorSet.setTarget(customLoadingIcon);
        animatorSet = new AnimatorSet();
        animatorSet.play(cLIAnimatorSet);
        animatorSet.setDuration(duration);
    }

    private void setViewState (boolean show, int state)
    {
        for (int i = 0; i < this.getChildCount(); i++)
        {
            this.getChildAt(i).setVisibility(show ? INVISIBLE : VISIBLE);
        }

        if (state == LOADING)
        {
            emptyView.setVisibility(View.GONE);
            customLoadingView.setVisibility(View.GONE);
            loadingView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
        else if (state == CUSTOM_LOADING)
        {
            emptyView.setVisibility(GONE);
            loadingView.setVisibility(GONE);
            customLoadingView.setVisibility(show ? VISIBLE : GONE);
        }
        else if (state == EMPTY)
        {
            loadingView.setVisibility(GONE);
            customLoadingView.setVisibility(GONE);
            emptyView.setVisibility(show ? VISIBLE : GONE);
        }
        else
        {
            loadingView.setVisibility(GONE);
            customLoadingView.setVisibility(GONE);
            emptyView.setVisibility(GONE);
        }
    }
}