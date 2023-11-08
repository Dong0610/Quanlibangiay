package btl.n01.quanlibangiay.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import btl.n01.quanlibangiay.R;
import btl.n01.quanlibangiay.adapter.CartAdapter;
import btl.n01.quanlibangiay.base.BaseFragment;
import btl.n01.quanlibangiay.databinding.FragmentCartBinding;
import btl.n01.quanlibangiay.interfaces.FectchDataListener;
import btl.n01.quanlibangiay.model.Cart;
import btl.n01.quanlibangiay.utility.Constant;

public class CartFragment extends BaseFragment<FragmentCartBinding> {
    @Override
    protected FragmentCartBinding getBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentCartBinding.inflate(inflater);
    }

    private ArrayList<Cart> carts;

    @Override
    protected void initView() {
        loadData();
        binding.btnContinue.setOnClickListener(view -> {
            confirmPayment();
        });
        setBehaveButton(false);
    }

    private void loadData() {
        getCartProduct(new FectchDataListener<Cart>() {
            @Override
            public void OnSuccess(ArrayList<Cart> carts) {
                CartFragment.this.carts= carts;
                binding.llNoProduct.setVisibility(carts.size()==0 ? View.VISIBLE : View.GONE);
                setDataToView(carts);
            }

            @Override
            public void OnFlaid(String mess) {

            }
        });
    }

    private CartAdapter adapter;
    private void setDataToView(ArrayList<Cart> carts) {
        adapter= new CartAdapter(new CartAdapter.OnCardEvent() {
            @Override
            public void onLove(Cart product) {

            }

            @Override
            public void onDelete(Cart product) {

            }

            @Override
            public void onUpdateCount(int count, String id) {

            }
            @Override
            public void onSelect(String idProduct) {
                viewDetails(idProduct);
            }

            @Override
            public void onBuy(Cart product) {
                addDataToBuy(product);
            }
        });
        binding.recyclerView.setAdapter(adapter);
        adapter.setItems(carts);
    }

    private void viewDetails(String idProduct) {

    }

    private ArrayList<Cart> listProductBuy = new ArrayList<>();

    private void confirmPayment() {
       //loadding.show();
        AtomicInteger count = new AtomicInteger(0);
        int expectedConfirmations = listProductBuy.size();
        for (Cart cart : listProductBuy) {
            HashMap<String, Object> hashMap = getHasMap(cart);
            database.getReference(Constant.KEY_ORDER)
                    .push()
                    .setValue(hashMap)
                    .addOnCompleteListener(task -> {
                        if (count.incrementAndGet() == expectedConfirmations) {
                            for (Cart product : listProductBuy) {
                                database.getReference(Constant.KEY_CART)
                                        .child(preference.getString(Constant.USER_ID, ""))
                                        .child(product.getProductID()).removeValue();
                            }
                            new Handler(Looper.myLooper()).postDelayed(() -> {

                                loadData();
                                 loadding.dismiss();
                                showToast("Đơn hàng của bạn đã tạo thành công!");
                            }, 1500);
                        }
                    })
                    .addOnFailureListener(e -> {
                        showToast(e.getMessage());
                    });
        }
    }

    private HashMap<String, Object> getHasMap(Cart cart) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Constant.ORDER_SHOP_ID, cart.getProductShopID());
        hashMap.put(Constant.ORDER_USER_ID, preference.getString(Constant.USER_ID, ""));
        hashMap.put(Constant.ORDER_USER_NAME, preference.getString(Constant.USER_NAME, ""));
        hashMap.put(Constant.ORDER_USER_EMAIL, preference.getString(Constant.USER_EMAIL, ""));
        hashMap.put(Constant.ORDER_USER_ADDRESS, preference.getString(Constant.USER_ADDRESS, ""));
        hashMap.put(Constant.ORDER_TIME, new Date());
        hashMap.put(Constant.ORDER_PR_COUNT, cart.getNunCount());
        hashMap.put(Constant.ORDER_PR_NAME, cart.getProductName());
        hashMap.put(Constant.ORDER_PR_ID, cart.getProductID());
        hashMap.put(Constant.ORDER_PR_PRICE, cart.getPrice());
        hashMap.put(Constant.ORDRT_PR_IMG, cart.getPrductImg());
        hashMap.put(Constant.ORDER_PR_SIZE, cart.getSize());
        hashMap.put(Constant.ORDER_STATUS, "WAIT_CONFIRM");
        return hashMap;
    }

    private void getCartProduct(FectchDataListener<Cart> loadCartData) {
        loadding.show();
        DatabaseReference cartReference = database.getReference(Constant.KEY_CART)
                .child(preference.getString(Constant.USER_ID, ""));

        cartReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Cart> carts = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String productID = dataSnapshot.getKey();
                    String productName = dataSnapshot.child(Constant.CART_PRODUCT_NAME).getValue(String.class);
                    String productImg = dataSnapshot.child(Constant.CART_PRODUCT_IMG).getValue(String.class);
                    int productSize = dataSnapshot.child(Constant.CART_PRODUCT_SIZE).getValue(Integer.class);
                    String productShopID = dataSnapshot.child(Constant.CART_PRODUCT_SHOP_ID).getValue(String.class);
                    float productPrice = dataSnapshot.child(Constant.CART_PRODUCT_PRICE).getValue(Float.class);
                    int productCount = dataSnapshot.child(Constant.CART_PRODUCT_COUNT).getValue(Integer.class);

                    Cart cartProduct = new Cart(
                            false,
                            productCount,
                            productID,
                            productShopID,
                            productName,
                            productImg,
                            productPrice,
                            productSize

                    );
                    carts.add(cartProduct);
                }
                loadding.dismiss();
                loadCartData.OnSuccess(carts);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadCartData.OnFlaid(error.getMessage());
            }
        });
    }

    private void initTotal(List<Cart> listProduct) {
        binding.llSumPay.setVisibility(View.VISIBLE);
        binding.itemCount.setText(String.valueOf(listProduct.size()));
        float totalPrice = 0;
        for (Cart item : listProduct) {
            totalPrice += (item.getNunCount() * item.getPrice());
        }
        binding.txtSumCount.setText(formatToCurrency(totalPrice));
    }

    private void addDataToBuy(Cart product) {
        if (listProductBuy.size() == 0) {
            listProductBuy.add(product);
            setBehaveButton(true);
        } else {
            int indexPr = listProductBuy.indexOf(product);
            if (indexPr == -1) {
                listProductBuy.add(product);
            } else {
                listProductBuy.remove(indexPr);
            }

            if (listProductBuy.size() == 0) {
                setBehaveButton(false);
            } else {
                setBehaveButton(true);
            }
        }

        initTotal(listProductBuy);
    }

    @SuppressLint("ResourceAsColor")
    private void setBehaveButton(boolean value) {
        if (value) {
            binding.btnContinue.setEnabled(true);
            binding.btnContinue.setTextColor(ContextCompat.getColor(CartFragment.this.requireContext(), R.color.white));
            binding.btnContinue.setBackgroundResource(R.drawable.bg_btn_account_end);
        } else {
            binding.btnContinue.setEnabled(false);
            binding.btnContinue.setTextColor(ContextCompat.getColor(CartFragment.this.requireContext(), R.color.textcolor2));
            binding.btnContinue.setBackgroundResource(R.drawable.bg_edt_account_dis);
        }
    }


}
