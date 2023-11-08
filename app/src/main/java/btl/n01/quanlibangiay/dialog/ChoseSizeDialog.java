package btl.n01.quanlibangiay.dialog;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import btl.n01.quanlibangiay.R;
import btl.n01.quanlibangiay.base.BaseDialog;
import btl.n01.quanlibangiay.base.GenericAdapter;
import btl.n01.quanlibangiay.databinding.DialogAppWaringBinding;
import btl.n01.quanlibangiay.databinding.DialogChooseSizeBinding;
import btl.n01.quanlibangiay.databinding.ItemListProductSizeBinding;

public class ChoseSizeDialog extends BaseDialog {
    private GenericAdapter<Integer, ItemListProductSizeBinding> sizeadapter;
    private int selectSize=0;
    public ChoseSizeDialog(List<Integer> listSize, Context context, ChoseSizeDialog.ChoseSizeEvent listener) {
        super(context);
        DialogChooseSizeBinding binding = DialogChooseSizeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setCancelable(false);
        sizeadapter = new GenericAdapter<>(listSize,
                (i, p, v) -> ItemListProductSizeBinding.inflate(i, p, false),
                (itembinding, item, position) -> {
                    itembinding.txtName.setText(item.toString());
                    itembinding.getRoot().setBackgroundResource(selectSize == item ? R.drawable.bg_item_model_category : R.drawable.bg_item_unselect_category);
                    itembinding.getRoot().setOnClickListener(view -> {
                        selectItem(item, position);
                    });
                });
        binding.rcvSize.setAdapter(sizeadapter);
        binding.btnSave.setOnClickListener(v -> {
            listener.onConfirm(selectSize);
            dismiss();
        });
        binding.btnCancel.setOnClickListener(v -> dismiss());
    }
    private void selectItem(Integer item, int position) {
        selectSize=item;
        sizeadapter.setItem(position);
    }
    public interface ChoseSizeEvent {
        void onConfirm(int size);
    }
}
