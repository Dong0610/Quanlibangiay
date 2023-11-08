package btl.n01.quanlibangiay.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import btl.n01.quanlibangiay.adapter.OrderAdapter;
import btl.n01.quanlibangiay.base.BaseFragment;
import btl.n01.quanlibangiay.databinding.FragmentOrderBinding;
import btl.n01.quanlibangiay.interfaces.FectchDataListener;
import btl.n01.quanlibangiay.model.Order;
import btl.n01.quanlibangiay.utility.Constant;

public class OderFragment extends BaseFragment<FragmentOrderBinding> {
    private OrderAdapter orderAdapter;
    @Override
    public FragmentOrderBinding getBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentOrderBinding.inflate(inflater);
    }

    @Override
    public void initView() {
        binding.icBack.setOnClickListener(v -> {
            closeFragment(this);
        });
        getOrder(new FectchDataListener<Order>() {
            @Override
            public void OnSuccess(ArrayList<Order> datas) {
                setDataToView(datas);
            }

            @Override
            public void OnFlaid(String mess) {

            }
        });
    }

    private void setDataToView(ArrayList<Order> datas) {
        orderAdapter = new OrderAdapter(order -> {

        });
        binding.rcvListOrder.setAdapter(orderAdapter);
        orderAdapter.setItems(datas);
    }

    public void getOrder(FectchDataListener<Order> callback) {
        DatabaseReference databaseReference = database.getReference(Constant.KEY_ORDER);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Order> orders = new ArrayList<>();
                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    String orderID = orderSnapshot.getKey();
                    String shopId = orderSnapshot.child(Constant.ORDER_SHOP_ID).getValue(String.class);
                    String userId = orderSnapshot.child(Constant.ORDER_USER_ID).getValue(String.class);
                    String usName = orderSnapshot.child(Constant.ORDER_USER_NAME).getValue(String.class);
                    String userMail = orderSnapshot.child(Constant.ORDER_USER_EMAIL).getValue(String.class);
                    String userAddress = orderSnapshot.child(Constant.ORDER_USER_ADDRESS).getValue(String.class);
                    String orderStatus = orderSnapshot.child(Constant.ORDER_STATUS).getValue(String.class);
                    String productId = orderSnapshot.child(Constant.ORDER_PR_ID).getValue(String.class);
                    String productName = orderSnapshot.child(Constant.ORDER_PR_NAME).getValue(String.class);
                    int productCount = orderSnapshot.child(Constant.ORDER_PR_COUNT).getValue(Integer.class);
                    float productPrice = orderSnapshot.child(Constant.ORDER_PR_PRICE).getValue(Float.class);
                    String productImg = orderSnapshot.child(Constant.ORDRT_PR_IMG).getValue(String.class);
                    String orderTime =formatDateString(orderSnapshot.child(Constant.ORDER_TIME).getValue(Date.class).toString());
                    int orderPrSize = orderSnapshot.child(Constant.ORDER_PR_SIZE).getValue(Integer.class);
                    Order order = new Order(orderID, shopId, userId, usName, userMail, userAddress, orderStatus, productId, productName, productCount, productPrice, productImg, orderTime, orderPrSize);
                    if (order.getUserId().equals(preference.getString(Constant.USER_ID, ""))) {
                        orders.add(order);
                    }
                }
                callback.OnSuccess(orders);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.OnFlaid(error.getMessage());
            }
        });
    }

}
