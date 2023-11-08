package btl.n01.quanlibangiay.fragment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import btl.n01.quanlibangiay.base.BaseFragment;
import btl.n01.quanlibangiay.base.GenericAdapter;
import btl.n01.quanlibangiay.databinding.FragmentWriteReviewBinding;
import btl.n01.quanlibangiay.databinding.ItemListImageCommentBinding;
import btl.n01.quanlibangiay.interfaces.OnCommentSuccess;
import btl.n01.quanlibangiay.model.Product;
import btl.n01.quanlibangiay.utility.Constant;

public class WriteReviewFragment extends BaseFragment<FragmentWriteReviewBinding> {

    private Product product;
    private List<Uri> listImagePR = new ArrayList<>();
    private GenericAdapter<Uri, ItemListImageCommentBinding> imageAdapter;
    private final OnCommentSuccess commentSuccess;

    public WriteReviewFragment(Product product,OnCommentSuccess commentSuccess) {
        this.product = product;
        this.commentSuccess=commentSuccess;
    }
    @Override
    public FragmentWriteReviewBinding getBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentWriteReviewBinding.inflate(inflater);
    }

    @Override
    public void initView() {
        binding.icBack.setOnClickListener(view -> closeFragment(WriteReviewFragment.this));
        listImagePR.add(null);
        binding.btnSaveReview.setOnClickListener(view -> saveComment());

        binding.rating.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            float sumRatting = product.getSosao() * product.getLuotdanhgia();
            float ratingValue = (sumRatting + rating) / (product.getLuotdanhgia() + 1);
            HashMap<String, Object> hasmap = new HashMap<>();
            hasmap.put(Constant.PR_LUOTDANHGIA, product.getLuotdanhgia() + 1);
            hasmap.put(Constant.PR_SAO, ratingValue);
            product.setLuotdanhgia(product.getLuotdanhgia() + 1);
            product.setSosao(ratingValue);

        });

    }


    @SuppressLint("SuspiciousIndentation")
    private void saveComment() {
        HashMap<String, Object> hashMap = new HashMap<>();
        loadding.show();
        if (listImagePR.size() == 1) {
            hashMap.put(Constant.REVIEW_COMMENT, binding.edtComment.getText().toString());
            hashMap.put(Constant.REVIEW_TIME, new Date());
            hashMap.put(Constant.REVIEW_STAR, binding.rating.getRating());
            hashMap.put(Constant.REVIEW_USER_ID, preference.getString(Constant.USER_ID, ""));
            hashMap.put(Constant.REVIEW_USER_NAME, preference.getString(Constant.USER_NAME, ""));

            database.getReference(Constant.KEY_REVIEW)
                    .child(product.getId())
                    .push()
                    .setValue(hashMap)
                    .addOnCompleteListener(task -> {
                        HashMap<String, Object> update = new HashMap<>();
                        update.put(Constant.PR_SAO, product.getSosao());
                        update.put(Constant.PR_LUOTDANHGIA, product.getLuotdanhgia());
                        database.getReference(Constant.KEY_PRODUCT)
                                .child(product.getId())
                                .updateChildren(update);
                        showToast("Đã lưu lại đánh giá sản phẩm");
                        loadding.dismiss();
                        commentSuccess.OnSuccess(product);
                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            closeFragment(this);
                        }, 1500);
                    })
                    .addOnFailureListener(e -> {
                        showToast(e.getMessage());
                    });
        }
    }
}

