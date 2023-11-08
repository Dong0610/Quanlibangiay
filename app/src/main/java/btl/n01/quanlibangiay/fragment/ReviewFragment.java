package btl.n01.quanlibangiay.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Date;

import btl.n01.quanlibangiay.adapter.CartAdapter;
import btl.n01.quanlibangiay.adapter.ReviewAdapter;
import btl.n01.quanlibangiay.base.BaseFragment;
import btl.n01.quanlibangiay.databinding.FragmentReviewBinding;
import btl.n01.quanlibangiay.interfaces.FectchDataListener;
import btl.n01.quanlibangiay.interfaces.OnCommentSuccess;
import btl.n01.quanlibangiay.model.Cart;
import btl.n01.quanlibangiay.model.Product;
import btl.n01.quanlibangiay.model.Review;
import btl.n01.quanlibangiay.utility.Constant;

public class ReviewFragment extends BaseFragment<FragmentReviewBinding> {
    @Override
    protected FragmentReviewBinding getBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentReviewBinding.inflate(inflater);
    }

    private Product product;
    final OnCommentSuccess commentSuccess;

    public ReviewFragment(Product product, OnCommentSuccess commentSuccess) {
        this.product = product;
        this.commentSuccess = commentSuccess;
    }

    @Override
    protected void initView() {
        binding.icBack.setOnClickListener(v -> {
            closeFragment(ReviewFragment.this);
        });
        binding.btnWiteComment.setOnClickListener(view -> {
            addFragment(new WriteReviewFragment(product, product -> {
                this.product = product;
                loadData();
            }), 0, true);
        });
        loadData();
    }

    private void loadData() {
        fetchData(new FectchDataListener<Review>() {
            @Override
            public void OnSuccess(ArrayList<Review> datas) {
                if(datas!=null){
                    initToView(datas);
                }
            }
            @Override
            public void OnFlaid(String mess) {
                showToast(mess);
            }
        });
    }

    private void initToView(ArrayList<Review> datas) {
        ReviewAdapter reviewAdapter= new ReviewAdapter();
        reviewAdapter.setItems(datas);
        binding.rcvListComment.setAdapter(reviewAdapter);
    }

    private void fetchData(FectchDataListener<Review> fetch) {
        DatabaseReference reviewRef = database.getReference(Constant.KEY_REVIEW)
                .child(product.getId());
        ArrayList<Review> data = new ArrayList<>();
        reviewRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot reviewSnapshot : snapshot.getChildren()) {
                        String reviewId = reviewSnapshot.getKey();
                        String reviewUserId = reviewSnapshot.child(Constant.REVIEW_USER_ID).getValue(String.class);
                        String reviewUserName = reviewSnapshot.child(Constant.REVIEW_USER_NAME).getValue(String.class);
                        String reviewUserImg = reviewSnapshot.child(Constant.REVIEW_USER_IMG).getValue(String.class);
                        float reviewStar = reviewSnapshot.child(Constant.REVIEW_STAR).getValue(Float.class);
                        String reviewComment = reviewSnapshot.child(Constant.REVIEW_COMMENT).getValue(String.class);
                        String reviewTime =formatDateString( reviewSnapshot.child(Constant.REVIEW_TIME).getValue(Date.class).toString());

                        data.add(new Review(reviewId, reviewUserId, reviewUserName, reviewUserImg, reviewStar, reviewComment, reviewTime));
                    }
                    fetch.OnSuccess(data);
                } else {
                  fetch.OnSuccess(null);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                fetch.OnFlaid(error.getMessage());
            }
        });
    }

}
