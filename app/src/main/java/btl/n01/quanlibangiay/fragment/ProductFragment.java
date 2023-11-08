package btl.n01.quanlibangiay.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import btl.n01.quanlibangiay.R;
import btl.n01.quanlibangiay.adapter.ReviewAdapter;
import btl.n01.quanlibangiay.base.BaseFragment;
import btl.n01.quanlibangiay.base.GenericAdapter;
import btl.n01.quanlibangiay.databinding.FragmentProductBinding;
import btl.n01.quanlibangiay.databinding.ItemListProductSizeBinding;
import btl.n01.quanlibangiay.databinding.ItemSlideImageBinding;
import btl.n01.quanlibangiay.interfaces.FectchDataListener;
import btl.n01.quanlibangiay.interfaces.OnCommentSuccess;
import btl.n01.quanlibangiay.model.Product;
import btl.n01.quanlibangiay.model.Review;
import btl.n01.quanlibangiay.utility.Constant;

public class ProductFragment extends BaseFragment<FragmentProductBinding> {
    @Override
    protected FragmentProductBinding getBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentProductBinding.inflate(inflater);
    }

    private Product product;

    public ProductFragment(Product product) {
        this.product = product;
    }

    int selectSize = 0;

    @Override
    protected void initView() {
        binding.btnAddCard.setOnClickListener(view -> {
            addCart();
        });
        initData();
        binding.icBack.setOnClickListener(v-> closeFragment(this));
        binding.txtSeeMore.setOnClickListener(view -> {
            addFragment(new ReviewFragment(product, product -> {
                this.product=product;
                initData();
            }),0,true);
        });
        loadData();
    }

    private GenericAdapter<String, ItemSlideImageBinding> imageadapter;
    private GenericAdapter<Integer, ItemListProductSizeBinding> sizeadapter;

    private void initData() {
        imageadapter = new GenericAdapter<>(
                product.getAnhGiay(),
                (inflater, parent, attachToParent) -> ItemSlideImageBinding.inflate(inflater, parent, false),
                (itembinding, item, position) -> {
                    Glide.with(this.requireContext()).load(item).into(itembinding.imgView);
                }
        );
        binding.rcvImge.setAdapter(imageadapter);
        sizeadapter = new GenericAdapter<>(product.getKichthuoc(),
                (i, p, v) -> ItemListProductSizeBinding.inflate(i, p, false),
                (itembinding, item, position) -> {
                    itembinding.txtName.setText(item.toString());
                    itembinding.getRoot().setBackgroundResource(selectSize == item ? R.drawable.bg_item_model_category : R.drawable.bg_item_unselect_category);
                    itembinding.getRoot().setOnClickListener(view -> {
                        selectItem(item, position);
                    });
                });
        binding.rcvSize.setAdapter(sizeadapter);

        binding.txtName.setText(product.getTengiay());
        binding.txtPrice.setText(formatToCurrency(product.getGia()));
        binding.txtIntroduce.setText(product.getMota());
        binding.rating.setRating(product.getSosao());
    }

    private void selectItem(Integer item, int position) {
        selectSize=item;
        sizeadapter.setItem(position);
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
        binding.rcvComment.setAdapter(reviewAdapter);
    }
    private void fetchData(FectchDataListener<Review> fetch) {
        DatabaseReference reviewRef = database.getReference(Constant.KEY_REVIEW)
                .child(product.getId());
        ArrayList<Review> data = new ArrayList<>();
        reviewRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int count =0;
                    for (DataSnapshot reviewSnapshot : snapshot.getChildren()) {
                        String reviewId = reviewSnapshot.getKey();
                        String reviewUserId = reviewSnapshot.child(Constant.REVIEW_USER_ID).getValue(String.class);
                        String reviewUserName = reviewSnapshot.child(Constant.REVIEW_USER_NAME).getValue(String.class);
                        String reviewUserImg = reviewSnapshot.child(Constant.REVIEW_USER_IMG).getValue(String.class);
                        float reviewStar = reviewSnapshot.child(Constant.REVIEW_STAR).getValue(Float.class);
                        String reviewComment = reviewSnapshot.child(Constant.REVIEW_COMMENT).getValue(String.class);
                        String reviewTime =formatDateString( reviewSnapshot.child(Constant.REVIEW_TIME).getValue(Date.class).toString());
                        data.add(new Review(reviewId, reviewUserId, reviewUserName, reviewUserImg, reviewStar, reviewComment, reviewTime));
                        count++;
                        if(count>2){
                            break;
                        }
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
    private void addCart() {
        if (selectSize != 0) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put(Constant.CART_PRODUCT_ID, product.getId());
            hashMap.put(Constant.CART_PRODUCT_NAME, product.getTengiay());
            hashMap.put(Constant.CART_PRODUCT_IMG, product.getAnhGiay().get(0));
            hashMap.put(Constant.CART_PRODUCT_SIZE, selectSize);
            hashMap.put(Constant.CART_PRODUCT_SHOP_ID, "");
            hashMap.put(Constant.CART_PRODUCT_PRICE, (product.getGia() - (product.getGia() * product.getGiamgia() / 100)));
            hashMap.put(Constant.CART_PRODUCT_COUNT, 1);
            hashMap.put(Constant.CART_PRODUCT_TIME, new Date());
            database.getReference(Constant.KEY_CART)
                    .child(preference.getString(Constant.USER_ID, ""))
                    .child(product.getId())
                    .setValue(hashMap)
                    .addOnCompleteListener(task -> {
                        showToast("Đã thêm vào giỏ hàng thành công!");
                    })
                    .addOnFailureListener(e -> {

                    });
        } else {
            showToast("Chưa chọn kích  thước!");
        }
    }
}