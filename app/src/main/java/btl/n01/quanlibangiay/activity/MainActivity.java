package btl.n01.quanlibangiay.activity;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import btl.n01.quanlibangiay.R;
import btl.n01.quanlibangiay.base.BaseActivity;
import btl.n01.quanlibangiay.databinding.ActivityMainBinding;
import btl.n01.quanlibangiay.fragment.AccountFragment;
import btl.n01.quanlibangiay.fragment.CartFragment;
import btl.n01.quanlibangiay.fragment.HomeFragment;
import btl.n01.quanlibangiay.fragment.OderFragment;
import btl.n01.quanlibangiay.fragment.SearchFragment;
import btl.n01.quanlibangiay.interfaces.HomeSearchManu;
import btl.n01.quanlibangiay.model.Manufact;
import btl.n01.quanlibangiay.model.Product;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    private ArrayList<Product> products;

    @Override
    protected void createView() {
        products = new ArrayList<>();
        binding.bottomNavigation.setOnNavigationItemSelectedListener(OnItemSelectedBottomBar);
        openFragment(new HomeFragment(new HomeSearchManu() {
            @Override
            public void onSearch(Manufact manufact) {
                openFragment(new SearchFragment(manufact,null), false);
            }

            @Override
            public void onGetProduct(ArrayList<Product> list) {
                MainActivity.this.products = list;
            }
        }), true);

        binding.icSearch.setOnClickListener(view -> {
            openFragment(new SearchFragment(null,binding.edtSearch.getText().toString()), false);
        });

    }


    public void openFragment(Fragment fragment, boolean value) {
        if (value) {
            binding.frameView1.setVisibility(View.VISIBLE);
            binding.frameView2.setVisibility(View.GONE);
            replaceFragment(fragment, R.id.frame_view1, true);
        } else {
            binding.frameView1.setVisibility(View.GONE);
            binding.frameView2.setVisibility(View.VISIBLE);
            replaceFragment(fragment, R.id.frame_view2, true);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener OnItemSelectedBottomBar = item -> {
        switch (item.getItemId()) {
            case R.id.iconhome:
                hideKeyboard();
                openFragment(new HomeFragment(new HomeSearchManu() {
                    @Override
                    public void onSearch(Manufact manufact) {
                        openFragment(new SearchFragment(manufact,null), false);
                    }
                    @Override
                    public void onGetProduct(ArrayList<Product> list) {
                        MainActivity.this.products = list;
                    }
                }), true);

                return true;
            case R.id.ic_search:
                hideKeyboard();
                openFragment(new SearchFragment(null,null), false);
                return true;
            case R.id.ic_card:
                hideKeyboard();
                openFragment(new CartFragment(), false);
                return true;
            case R.id.ic_discount:
                hideKeyboard();
                openFragment(new OderFragment(), false);
                return true;
            case R.id.ic_account:
                hideKeyboard();
                openFragment(new AccountFragment(), false);
                return true;
        }
        return false;
    };

}