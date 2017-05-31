package id.co.flipbox.sosoito;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
    private View           loadingView;
    private TextView       loadingMessage;
    private View           emptyView;
    private ImageView      emptyIcon;
    private TextView       emptyMessage;
    private ProgressDialog progressDialog;

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
        loadingView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void showLoading(final boolean show, final String message) {
        for (int i=0; i < this.getChildCount(); i++)
        {
            this.getChildAt(i).setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        }
        emptyView.setVisibility(View.GONE);
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
}