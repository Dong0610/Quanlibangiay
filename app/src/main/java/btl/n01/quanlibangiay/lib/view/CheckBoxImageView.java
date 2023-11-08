package btl.n01.quanlibangiay.lib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import java.text.NumberFormat;
import java.util.Locale;

import btl.n01.quanlibangiay.R;

public class CheckBoxImageView extends AppCompatImageView {
    private boolean checked;
    private int defImageRes;
    private int checkedImageRes;
    private OnCheckedChangeListener onCheckedChangeListener;

    public CheckBoxImageView(Context context) {
        super(context);
        init(null, 0);
        setOnClickListener(this::onClick);
    }

    public CheckBoxImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
        setOnClickListener(this::onClick);
    }

    public CheckBoxImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
        setOnClickListener(this::onClick);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        setImageResource(isChecked() ? checkedImageRes : defImageRes);
    }

    @SuppressLint("ResourceType")
    private void init(AttributeSet attributeSet, int defStyle) {
        TypedArray a = getContext().obtainStyledAttributes(attributeSet, R.styleable.CheckBoxImageView, defStyle, 0);
        defImageRes = a.getResourceId(R.styleable.CheckBoxImageView_default_img, 0);
        checkedImageRes = a.getResourceId(R.styleable.CheckBoxImageView_checked_img, 0);
        checked = a.getBoolean(R.styleable.CheckBoxImageView_checked, false);
        setImageResource(isChecked() ? checkedImageRes : defImageRes);
        a.recycle();
    }

    private void onClick(View v) {
        checked = !checked;
        setChecked(checked);
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedChanged(this, checked);
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(View buttonView, boolean isChecked);
    }
}
