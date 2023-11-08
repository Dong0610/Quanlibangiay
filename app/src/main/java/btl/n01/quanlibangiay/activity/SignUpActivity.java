package btl.n01.quanlibangiay.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import btl.n01.quanlibangiay.base.BaseActivity;
import btl.n01.quanlibangiay.databinding.ActivitySignUpBinding;
import btl.n01.quanlibangiay.utility.Constant;

public class SignUpActivity extends BaseActivity<ActivitySignUpBinding> {

    private String name, email, pass, repass;

    @Override
    protected ActivitySignUpBinding getViewBinding() {
        return ActivitySignUpBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void createView() {
        if(preference.getBoolean(Constant.KEY_LOGIN,false)==true){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }

        clickEvent();
        binding.txtSignin.setOnClickListener(view -> {
            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            finish();
        });
    }

    private void clickEvent() {
        binding.btnSignup.setOnClickListener(view -> {
            hideKeyboard();
            signUp();
        });
    }

    private void signUp() {
        name = binding.edtusname.getText().toString();
        email = binding.edtemail.getText().toString();
        pass = binding.edtpass.getText().toString();
        repass = binding.edtpassagain.getText().toString();
        if (checkData(name, email, pass, repass)) {
            loadding.show();
            DatabaseReference userReference = database.getReference(Constant.KEY_USER);

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(Constant.USER_NAME, name);
            hashMap.put(Constant.USER_EMAIL, email);
            hashMap.put(Constant.USER_PASS, pass);
            hashMap.put(Constant.USER_ADDRESS, "");

            DatabaseReference newChildRef = userReference.push();
            newChildRef.setValue(hashMap)
                    .addOnCompleteListener(task -> {

                        String generatedKey = newChildRef.getKey();
                        preference.edit(editor -> {
                            editor.putBoolean(Constant.KEY_LOGIN, true);
                            editor.putString(Constant.USER_ID, generatedKey);
                            editor.putString(Constant.USER_NAME, name);
                            editor.putString(Constant.USER_EMAIL, email);
                            editor.putString(Constant.USER_PASS, pass);
                            editor.putString(Constant.USER_ADDRESS, "");
                        });
                        new Handler(Looper.myLooper()).postDelayed(() -> {
                            loadding.dismiss();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }, 1200);

                    })
                    .addOnFailureListener(e -> {
                        showToast(e.getMessage());
                    });
        }
    }

    private boolean checkData(String name, String email, String pass, String repass) {
        boolean isValid = true;
        if (name == null || name.length() < 6) {
            binding.txtErrorFulname.setText("Tên yêu cầu 6 kí tự *");
            isValid = false; // Name is too short
        } else {
            binding.txtErrorFulname.setText("");
        }
        if (!isValidEmail(email)) {
            binding.txtErrorEmail.setText("Email không đúng");
            isValid = false; // Invalid email
        } else {
            binding.txtErrorEmail.setText("");
        }
        if (pass == null || pass.length() < 6) {
            binding.txtErrorPass.setText("Mật khẩu yêu cầu lớn hơn 6 kí tự");
            isValid = false; // Password is too short
        } else {
            binding.txtErrorPass.setText("");
        }
        if (!pass.equals(repass)) {
            binding.txtErrorPassAgain.setText("Nhập lại mật khẩu sai");
            isValid = false; // Passwords do not match
        } else {
            binding.txtErrorPassAgain.setText("");
        }
        return isValid; // Return the overall validation result
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
        return email != null && email.matches(emailPattern);
    }

}