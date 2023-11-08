package btl.n01.quanlibangiay.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import btl.n01.quanlibangiay.activity.SignUpActivity;
import btl.n01.quanlibangiay.base.BaseFragment;
import btl.n01.quanlibangiay.databinding.FragmentAccountBinding;
import btl.n01.quanlibangiay.utility.Constant;

public class AccountFragment extends BaseFragment<FragmentAccountBinding> {
    @Override
    protected FragmentAccountBinding getBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentAccountBinding.inflate(inflater);
    }

    @Override
    protected void initView() {
        binding.txtLogout.setOnClickListener(view -> {
            loadding.show();
            new Handler(Looper.myLooper()).postDelayed(() -> {
                preference.putBoolean(Constant.KEY_LOGIN, false);
                preference.clear();
                startActivity(new Intent(AccountFragment.this.requireContext(), SignUpActivity.class));
                requireActivity().finish();
            },2000);
        });
        binding.txtName.setText(preference.getString(Constant.USER_NAME, ""));
        binding.txtEmail.setText(preference.getString(Constant.USER_EMAIL, ""));
        binding.txtAddress.setText(preference.getString(Constant.USER_ADDRESS, ""));

        binding.icEditAddess.setOnClickListener(view -> {
            editAddress();
        });
        binding.icEditPass.setOnClickListener(view -> {
            editPass();
        });
    }

    private void editPass() {
        binding.llUpdatePass.setVisibility(View.VISIBLE);
        binding.btnCancelPass.setOnClickListener(view -> binding.llUpdatePass.setVisibility(View.GONE));
        binding.btnSavePass.setOnClickListener(v -> {
            String oldPass = binding.edtOldPass.getText().toString();
            String newPass = binding.edtNewAddress.getText().toString();
            if (oldPass.equals(preference.getString(Constant.USER_PASS, ""))) {
                loadding.show();
                preference.putString(Constant.USER_PASS, newPass);
                HashMap<String, Object> updatePass = new HashMap<>();
                updatePass.put(Constant.USER_PASS, newPass);
                database.getReference(Constant.KEY_USER)
                        .child(preference.getString(Constant.USER_ID, ""))
                        .updateChildren(updatePass)
                        .addOnCompleteListener(task -> {

                            new Handler(Looper.myLooper()).postDelayed(() -> {
                                binding.llUpdatePass.setVisibility(View.GONE);
                                showToast("Cập nhật dữ liệu thành công!");
                                loadding.dismiss();
                            }, 1500);

                        }).addOnFailureListener(e -> {

                            new Handler(Looper.myLooper()).postDelayed(() -> {
                                binding.llUpdatePass.setVisibility(View.GONE);
                                showToast(e.getMessage());
                                loadding.dismiss();
                            }, 1500);

                        });
            } else {
                showToast("Mật khẩu cũ không đúng");
            }
        });
    }

    private void editAddress() {
        binding.llUpdateAddress.setVisibility(View.VISIBLE);
        binding.btnCancelAddress.setOnClickListener(view -> binding.llUpdateAddress.setVisibility(View.GONE));
        binding.btnSaveAddress.setOnClickListener(v -> {
            loadding.show();
            preference.putString(Constant.USER_ADDRESS, binding.edtNewAddress.getText().toString());
            HashMap<String, Object> updateAddress = new HashMap<>();
            updateAddress.put(Constant.USER_ADDRESS, binding.edtNewAddress.getText().toString());
            database.getReference(Constant.KEY_USER)
                    .child(preference.getString(Constant.USER_ID, ""))
                    .updateChildren(updateAddress)
                    .addOnCompleteListener(task -> {
                        new Handler(Looper.myLooper()).postDelayed(() -> {
                            binding.llUpdateAddress.setVisibility(View.GONE);
                            binding.txtAddress.setText(preference.getString(Constant.USER_ADDRESS, ""));
                            showToast("Cập nhật dữ liệu thành công!");
                            loadding.dismiss();
                        }, 1500);

                    }).addOnFailureListener(e -> {
                        new Handler(Looper.myLooper()).postDelayed(() -> {
                            binding.llUpdateAddress.setVisibility(View.GONE);
                            showToast(e.getMessage());
                            loadding.dismiss();
                        }, 1500);

                    });
        });
    }
}
