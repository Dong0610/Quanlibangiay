package btl.n01.quanlibangiay.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import btl.n01.quanlibangiay.R;
import btl.n01.quanlibangiay.base.BaseAdapter;
import btl.n01.quanlibangiay.databinding.ItemListCartBinding;
import btl.n01.quanlibangiay.lib.view.CheckBoxImageView;
import btl.n01.quanlibangiay.model.Cart;

public class CartAdapter extends BaseAdapter<Cart, ItemListCartBinding> {
    private OnCardEvent onCardEvent;

    public CartAdapter(OnCardEvent onCardEvent) {
        this.onCardEvent = onCardEvent;
    }

    @Override
    protected ItemListCartBinding createBinding(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return ItemListCartBinding.inflate(inflater, parent, false);
    }

    @Override
    protected void bind(ItemListCartBinding binding, Cart item, int position) {
        binding.iclove.setImageResource(item.islove() ? R.drawable.ic_love_card : R.drawable.ic_love_app);
        binding.txtName.setText(item.getProductName());
        binding.txtPrice.setText(formatToCurrency(item.getPrice()));

        Glide.with(binding.getRoot()).load(item.getPrductImg()).into(binding.imgProduct);

        binding.icPlus.setOnClickListener(v -> {
            item.setNunCount(item.getNunCount() + 1);
            binding.txtNumCount.setText(String.valueOf(item.getNunCount()));
            double totalPrice = item.getNunCount() * item.getPrice();
            binding.txtPrice.setText(formatToCurrency((float) totalPrice));
            onCardEvent.onUpdateCount(item.getNunCount(), item.getProductID());
        });

        binding.icDiscount.setOnClickListener(v -> {
            item.setNunCount(item.getNunCount() - 1);
            if (item.getNunCount() < 1) {
                item.setNunCount(1);
            }
            binding.txtNumCount.setText(String.valueOf(item.getNunCount()));
            double totalPrice = item.getNunCount() * item.getPrice();
            binding.txtPrice.setText(formatToCurrency((float) totalPrice));
            onCardEvent.onUpdateCount(item.getNunCount(), item.getProductID());
        });

        binding.iclove.setOnClickListener(v -> onCardEvent.onLove(item));
        binding.icdelete.setOnClickListener(v -> onCardEvent.onDelete(item));
        binding.getRoot().setOnClickListener(v -> onCardEvent.onSelect(item.getProductID()));

        binding.checkboxBuy.setOnCheckedChangeListener((CheckBoxImageView.OnCheckedChangeListener) (buttonView, isChecked) -> onCardEvent.onBuy(item));
    }

    public interface OnCardEvent {
        void onLove(Cart product);
        void onDelete(Cart product);
        void onUpdateCount(int count, String id);
        void onSelect(String idProduct);
        void onBuy(Cart product);
    }
}

