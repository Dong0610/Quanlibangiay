package btl.n01.quanlibangiay.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewbinding.ViewBinding;

import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import btl.n01.quanlibangiay.R;
import btl.n01.quanlibangiay.dialog.LoaddingDialog;
import btl.n01.quanlibangiay.lib.shared_preference.SharedPreference;


public abstract class BaseActivity<V extends ViewBinding> extends AppCompatActivity {

    public FirebaseDatabase database = FirebaseDatabase.getInstance();

    private static final String TAG = BaseActivity.class.getName();
    public SharedPreference preference = new SharedPreference();
    protected V binding;
    private boolean onFullscreen = false;
    private View decorView;
    public LoaddingDialog loadding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewBinding();

        setContentView(binding.getRoot());
        loadding = new LoaddingDialog(this);

        decorView = getWindow().getDecorView();
        createView();
    }

    public String formatToCurrency(float value) {
        Locale locale = new Locale("vi", "VN"); // Set the Vietnamese locale
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        return currencyFormat.format(value);
    }

    public String formatTimes(Object timestamp) {
        if (timestamp instanceof Long) {
            long timestampLong = (Long) timestamp;
            Date date = new Date(timestampLong);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            return sdf.format(date);
        }
        return "";
    }

    public String getTimeNow() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void showToast(String mess) {
        Toast.makeText(this, mess, Toast.LENGTH_LONG).show();
    }

    protected abstract V getViewBinding();

    protected abstract void createView();

    public void txtFocus(boolean isfocus, ImageView icon, LinearLayout border) {
        if (isfocus) {
            border.setBackgroundResource(R.drawable.bg_edt_account_end);
            int tint = ContextCompat.getColor(this, R.color.appcolor);
            icon.setColorFilter(tint);
        } else {
            border.setBackgroundResource(R.drawable.bg_edt_account_dis);
            icon.setColorFilter(Color.parseColor("#9098B1"));
        }
    }

    private void errorMess(boolean isError, LinearLayout border, ImageView icon, TextView textView, String message) {
        if (isError) {
            border.setBackgroundResource(R.drawable.bg_edt_account_end);
            int tint = ContextCompat.getColor(this, R.color.red);
            icon.setColorFilter(tint);
            textView.setText(message);
        } else {
            border.setBackgroundResource(R.drawable.bg_edt_account_dis);
            icon.setColorFilter(Color.parseColor("#9098B1"));
            textView.setText("");
        }
    }

    protected int hideSystemBars() {
        return (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    protected void setFullscreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
            WindowInsetsController windowInsetsController = getWindow().getInsetsController();
            if (windowInsetsController != null) {
                windowInsetsController.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
                windowInsetsController.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            }
        } else {
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    protected void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    protected void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
        }
    }

    protected void handleBackpress() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        }

        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        }
    }

    protected void addFragment(Fragment fragment, int viewId, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out);
        transaction.add(viewId, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    protected void addFragment(Fragment fragment, int viewId, boolean addToBackStack, boolean hideBottomBar) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out);
        if (hideBottomBar) {
            findViewById(viewId).setVisibility(View.GONE);
        }
        transaction.add(viewId, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    protected void replaceFragment(Fragment fragment, int viewId, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(viewId, fragment);
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out);
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        transaction.commit();
    }

    protected void showFullscreen(boolean on) {
        onFullscreen = on;
        if (on) {
            setFullscreen();
        }
    }
}
