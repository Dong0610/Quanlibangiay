package btl.n01.quanlibangiay.base;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewbinding.ViewBinding;

import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import btl.n01.quanlibangiay.R;
import btl.n01.quanlibangiay.dialog.LoaddingDialog;
import btl.n01.quanlibangiay.lib.shared_preference.SharedPreference;
import btl.n01.quanlibangiay.model.Manufact;

public abstract class BaseFragment<T extends ViewBinding> extends Fragment {

    protected T binding;

    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    private OnBackPressedCallback callback;
    public SharedPreference preference = new SharedPreference();

    public void handlerBackPressed() {
    }

    public LoaddingDialog loadding;

    public void showToast(String mess) {
        Toast.makeText(this.requireContext(), mess, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                handlerBackPressed();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public ArrayList<Manufact> listManufact() {
        ArrayList<Manufact> listPutData = new ArrayList<>();
        listPutData.add(new Manufact("manufacture_id_01", "Nike", R.drawable.shop_img_nike));
        listPutData.add(new Manufact("manufacture_id_02", "Converse", R.drawable.shop_img_converse));
        listPutData.add(new Manufact("manufacture_id_03", "Dior", R.drawable.shop_img_dior));
        listPutData.add(new Manufact("manufacture_id_04", "Domba", R.drawable.shop_img_domba));
        listPutData.add(new Manufact("manufacture_id_05", "Fila", R.drawable.shop_img_fila));
        listPutData.add(new Manufact("manufacture_id_06", "Gucci", R.drawable.shop_img_gucci));
        listPutData.add(new Manufact("manufacture_id_07", "MLB", R.drawable.shop_img_mlb));
        listPutData.add(new Manufact("manufacture_id_08", "New Balance", R.drawable.shop_img_new_balance));
        listPutData.add(new Manufact("manufacture_id_09", "Puma", R.drawable.shop_img_pumas));
        listPutData.add(new Manufact("manufacture_id_10", "Vans", R.drawable.shop_img_vans));
        return listPutData;
    }

    public String formatToCurrency(float value) {
        Locale locale = new Locale("vi", "VN"); // Set the Vietnamese locale
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        return currencyFormat.format(value);
    }

    public void txtFocus(boolean isfocus, ImageView icon, ViewGroup border) {
        if (isfocus) {
            border.setBackgroundResource(R.drawable.bg_edt_account_end);
            int tint = ContextCompat.getColor(requireContext(), R.color.appcolor);
            if (icon != null) icon.setColorFilter(tint);
        } else {
            border.setBackgroundResource(R.drawable.bg_edt_account_dis);
            if (icon != null) icon.setColorFilter(Color.parseColor("#9098B1"));
        }
    }

    public void errorMess(boolean isError, LinearLayout border, ImageView icon, TextView textView, String message) {
        if (isError) {
            border.setBackgroundResource(R.drawable.bg_edt_account_end);
            int tint = ContextCompat.getColor(requireContext(), R.color.red);
            icon.setColorFilter(tint);
            textView.setText(message);
        } else {
            border.setBackgroundResource(R.drawable.bg_edt_account_dis);
            icon.setColorFilter(Color.parseColor("#9098B1"));
            textView.setText("");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        callback.remove();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = getBinding(inflater, container);
        loadding = new LoaddingDialog(this.requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    protected abstract T getBinding(LayoutInflater inflater, ViewGroup container);

    protected abstract void initView();

    public void addFragment(Fragment fragment, int viewId, boolean addtoBackStack) {
        if (viewId == 0) {
            viewId = android.R.id.content;
        }
        ((BaseActivity<?>) requireActivity()).addFragment(fragment, viewId, addtoBackStack);
    }

    public void replaceFullViewFragment(Fragment fragment, int viewId, boolean addToBackStack) {
        if (viewId == 0) {
            viewId = android.R.id.content;
        }
        ((BaseActivity<?>) requireActivity()).replaceFragment(fragment, viewId, addToBackStack);
    }
    public String getTimeNow() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    public static String formatDateString(String inputDate) {
        try {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'Z yyyy", Locale.US);
            Date date = inputDateFormat.parse(inputDate);

            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            return outputDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void replaceFragment(Fragment fragment, int viewId, boolean addtoBackStack) {
        if (viewId == 0) {
            viewId = android.R.id.content;
        }
        ((BaseActivity<?>) requireActivity()).replaceFragment(fragment, viewId, addtoBackStack);
    }

    public void closeFragment(Fragment fragment) {
        ((BaseActivity<?>) requireActivity()).handleBackpress();
    }

    public void addAndRemoveCurrentFragment(Fragment currentFragment, Fragment newFragment, boolean addToBackStack) {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(currentFragment);
        transaction.add(android.R.id.content, newFragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    protected void hideKeyboard() {
        if (getActivity() != null) {
            ((BaseActivity<?>) getActivity()).hideKeyboard();
        }
    }

    protected void showKeyboard(View view) {
        ((BaseActivity<?>) requireActivity()).showKeyboard(view);
    }

    protected void setColorStatusBar(int idColor) {
        if (getActivity() != null) {
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(requireContext(), idColor));
        }
    }

    protected void getResultListener(String requestKey, ResultListener callback) {
        getParentFragmentManager().setFragmentResultListener(requestKey, this, (key, result) -> callback.onResult(key, result));
    }

    protected void setFragmentResult(String requestKey, Bundle resultBundle) {
        requireActivity().getSupportFragmentManager().setFragmentResult(requestKey, resultBundle);
    }

    public static boolean isGoToSetting = false;
    public static boolean isAdsRewardShowing = false;

    public static <F extends Fragment> F newInstance(Class<F> fragment, Bundle args) throws IllegalAccessException, java.lang.InstantiationException {
        F f = fragment.newInstance();
        if (args != null) {
            f.setArguments(args);
        }
        return f;
    }

    interface ResultListener {
        void onResult(String requestKey, Bundle bundle);
    }
}
