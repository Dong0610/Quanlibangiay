package btl.n01.quanlibangiay.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import btl.n01.quanlibangiay.base.BaseAdapter;
import btl.n01.quanlibangiay.databinding.ItemListProductBinding;
import btl.n01.quanlibangiay.model.Product;

public class ProductAdapter extends BaseAdapter<Product, ItemListProductBinding> {
    @Override
    protected ItemListProductBinding createBinding(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return ItemListProductBinding.inflate(inflater, parent, false);
    }

    public interface OnProductListener {
        void OnSelect(Product product);

        void OnAddCart(Product product);
    }

    private OnProductListener productListener;

    public ProductAdapter(OnProductListener product) {
        this.productListener = product;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bind(ItemListProductBinding binding, Product item, int position) {
        Glide.with(binding.getRoot()).load(item.getAnhGiay().get(0)).into(binding.imgProduct);
        binding.txtName.setText(item.getTengiay());
        binding.rating.setRating(item.getSosao());
        binding.txtPrice.setText(formatToCurrency(item.getGia()));
        binding.txtDiscount.setText("" + item.getGiamgia() + "%");
        binding.getRoot().setOnClickListener(v -> {
            productListener.OnSelect(item);
        });
        binding.btnAddCart.setOnClickListener(view -> {
            productListener.OnAddCart(item);
        });
    }
}
