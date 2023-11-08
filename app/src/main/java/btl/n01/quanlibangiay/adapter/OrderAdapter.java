package btl.n01.quanlibangiay.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import btl.n01.quanlibangiay.base.BaseAdapter;
import btl.n01.quanlibangiay.databinding.ItemListOrderBinding;
import btl.n01.quanlibangiay.model.Order;

public class OrderAdapter extends BaseAdapter<Order, ItemListOrderBinding> {
    private final OnOrderDetailListener detail;

    public OrderAdapter(OnOrderDetailListener detail) {

        this.detail = detail;
    }

    @Override
    protected ItemListOrderBinding createBinding(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return ItemListOrderBinding.inflate(inflater, parent, false);
    }

    @Override
    protected void bind(ItemListOrderBinding binding, Order item, int position) {
        binding.txtIdOrder.setText(item.getOrderID());
        binding.txtTime.setText(item.getOrderTime());
        binding.txtTotalPrice.setText(formatToCurrency(item.getProductCount() * item.getProductPrice()));
        binding.txtStatus.setText(item.getOrderStatus());
        binding.txtShipingCount.setText(item.getProductCount() + " sản phẩm");
        binding.txtDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail.onDetail(item);
            }
        });
    }
    public interface OnOrderDetailListener {
        void onDetail(Order order);
    }
}
