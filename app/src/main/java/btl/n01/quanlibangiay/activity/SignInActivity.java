package btl.n01.quanlibangiay.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import btl.n01.quanlibangiay.base.BaseActivity;
import btl.n01.quanlibangiay.databinding.ActivitySignInBinding;
import btl.n01.quanlibangiay.interfaces.FectchDataListener;
import btl.n01.quanlibangiay.model.Users;
import btl.n01.quanlibangiay.utility.Constant;

public class SignInActivity extends BaseActivity<ActivitySignInBinding> {
    @Override
    protected ActivitySignInBinding getViewBinding() {
        return ActivitySignInBinding.inflate(getLayoutInflater());
    }

    private String email, pass;

    @Override
    protected void createView() {
        binding.btnSingIn.setOnClickListener(view -> {
            email = binding.edtMail.getText().toString();
            pass = binding.edtPass.getText().toString();
            signIn();
        });

        binding.txtSignup.setOnClickListener(view -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            finish();
        });
    }

    ArrayList<Users> users = new ArrayList<>();

    private void signIn() {
        loadding.show();
        GetAllUsers(new FectchDataListener<Users>() {
            @Override
            public void OnSuccess(ArrayList<Users> datas) {
                users = datas;
                checkAccount(datas);
            }

            @Override
            public void OnFlaid(String mess) {
                showToast(mess);
            }
        });
    }

    private void checkAccount(ArrayList<Users> datas) {
        Users foundUser = null;
        for (Users user : datas) {
            if (user.getEmail().equals(email)) {
                foundUser = user;
                break;
            }
        }
        if (foundUser != null) {
            if (foundUser.getPass().equals(pass)) {
                saveUserPreferences(foundUser);
            } else {
                loadding.dismiss();
                binding.txtErrorPass.setText("Sai mật khẩu!");
            }
        } else {
            binding.txtErrorEmail.setText("Tài khoản không tồn tại");
        }
    }

    private void saveUserPreferences(Users user) {
        preference.edit(editor -> {
            editor.putBoolean(Constant.KEY_LOGIN, true);
            editor.putString(Constant.USER_ID, user.getId());
            editor.putString(Constant.USER_NAME, user.getName());
            editor.putString(Constant.USER_EMAIL, user.getEmail());
            editor.putString(Constant.USER_PASS, user.getPass());
            editor.putString(Constant.USER_ADDRESS, user.getAddress());
        });

        new Handler(Looper.myLooper()).postDelayed(() -> {
            loadding.dismiss();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }, 1200);
    }


    private void GetAllUsers(FectchDataListener<Users> fetch) {
        DatabaseReference cartReference = database.getReference(Constant.KEY_USER);

        cartReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Users> users = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String usId = dataSnapshot.getKey();
                    String userName = dataSnapshot.child(Constant.USER_NAME).getValue(String.class);
                    String useEmail = dataSnapshot.child(Constant.USER_EMAIL).getValue(String.class);
                    String userPass = dataSnapshot.child(Constant.USER_PASS).getValue(String.class);
                    String userAddress = dataSnapshot.child(Constant.USER_ADDRESS).getValue(String.class);
                    users.add(new Users(usId, userName, useEmail, userPass, userAddress));

                }
                loadding.dismiss();
                fetch.OnSuccess(users);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                fetch.OnFlaid(error.getMessage());
            }
        });
    }
}
