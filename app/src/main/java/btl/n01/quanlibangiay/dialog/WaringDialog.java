package btl.n01.quanlibangiay.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.Nullable;

import btl.n01.quanlibangiay.base.BaseDialog;
import btl.n01.quanlibangiay.databinding.DialogAppWaringBinding;

public class WaringDialog extends BaseDialog {
    public WaringDialog(@Nullable String mess, Context context, WaringListener listener) {
        super(context);

        DialogAppWaringBinding binding = DialogAppWaringBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setCancelable(false);

        if (mess != null && !mess.equals("")) {
            binding.txtMess.setVisibility(View.VISIBLE);
            binding.txtMess.setText(mess);
        } else {
            binding.txtMess.setVisibility(View.GONE);
        }

        binding.btnBackOrder.setOnClickListener(v -> {
            listener.onConfirm();
            dismiss();
        });
        binding.btnBackOrder2.setOnClickListener(v -> dismiss());
    }

    public interface WaringListener {
        void onConfirm();
    }
}
