package btl.n01.quanlibangiay.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import btl.n01.quanlibangiay.base.BaseAdapter;
import btl.n01.quanlibangiay.databinding.ItemListCategoryBinding;
import btl.n01.quanlibangiay.interfaces.ManufactListener;
import btl.n01.quanlibangiay.model.Manufact;

public class ManufactAdapter extends BaseAdapter<Manufact, ItemListCategoryBinding> {

    final ManufactListener listener;

    public ManufactAdapter(ManufactListener listener) {
        this.listener = listener;
    }

    @Override
    protected ItemListCategoryBinding createBinding(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return ItemListCategoryBinding.inflate(inflater, parent, false);
    }

    @Override
    protected void bind(ItemListCategoryBinding binding, Manufact item, int position) {
        if (item != null) {
            binding.imgView.setImageResource((Integer) item.getImgae());
            binding.txtName.setText(item.getName());
            binding.getRoot().setOnClickListener(view -> {
                listener.OnManuSeclect(item);
            });
        }
    }
}
