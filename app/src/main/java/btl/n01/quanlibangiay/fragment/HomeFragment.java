package btl.n01.quanlibangiay.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import btl.n01.quanlibangiay.adapter.ManufactAdapter;
import btl.n01.quanlibangiay.adapter.ProductAdapter;
import btl.n01.quanlibangiay.base.BaseFragment;
import btl.n01.quanlibangiay.databinding.DialogChooseSizeBinding;
import btl.n01.quanlibangiay.databinding.FragmentHomeBinding;
import btl.n01.quanlibangiay.dialog.ChoseSizeDialog;
import btl.n01.quanlibangiay.interfaces.FectchDataListener;
import btl.n01.quanlibangiay.interfaces.HomeSearchManu;
import btl.n01.quanlibangiay.model.Product;
import btl.n01.quanlibangiay.utility.Constant;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {
    @Override
    protected FragmentHomeBinding getBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentHomeBinding.inflate(getLayoutInflater());
    }

    final HomeSearchManu searchManu;

    public HomeFragment(HomeSearchManu searchManu) {

        this.searchManu = searchManu;
    }

    private ArrayList<Product> products = new ArrayList<>();

    @Override
    protected void initView() {
        loadding.show();
        getListproduct(new FectchDataListener<Product>() {
            @Override
            public void OnSuccess(ArrayList<Product> products) {
                searchManu.onGetProduct(products);
                products.addAll(products);
                loadProductView(products);
            }

            @Override
            public void OnFlaid(String mess) {
                showToast(mess);
                loadding.dismiss();
            }
        });
        setManuFact();
    }

    private void loadProductView(ArrayList<Product> products) {
        ProductAdapter productAdapter= new ProductAdapter(new ProductAdapter.OnProductListener() {
            @Override
            public void OnSelect(Product product) {
                addFragment(new ProductFragment(product),0,true);
            }

            @Override
            public void OnAddCart(Product product) {
                new ChoseSizeDialog(product.getKichthuoc(), HomeFragment.this.requireContext(), new ChoseSizeDialog.ChoseSizeEvent() {
                    @Override
                    public void onConfirm(int size) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put(Constant.CART_PRODUCT_ID, product.getId());
                        hashMap.put(Constant.CART_PRODUCT_NAME, product.getTengiay());
                        hashMap.put(Constant.CART_PRODUCT_IMG, product.getAnhGiay().get(0));
                        hashMap.put(Constant.CART_PRODUCT_SIZE, size);
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
                                    showToast(e.getMessage());
                                });
                    }
                }).show();
            }
        });
        binding.rcvListProduct.setAdapter(productAdapter);
        productAdapter.setItems(products);
        loadding.dismiss();
    }


    private void getListproduct(FectchDataListener fectchDataListener) {
        database.getReference(Constant.KEY_PRODUCT)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<Product> listProduct = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String productId = dataSnapshot.getKey();
                            String productName = dataSnapshot.child(Constant.PR_TENSP).getValue(String.class);
                            int productCount = dataSnapshot.child(Constant.PR_GIA).getValue(Integer.class);
                            String productManuId = dataSnapshot.child(Constant.PR_MANUID).getValue(String.class);
                            String productManuName = dataSnapshot.child(Constant.PR_MANUNAME).getValue(String.class);
                            String productDescription = dataSnapshot.child(Constant.PR_MOTA).getValue(String.class);
                            float productPrice = dataSnapshot.child(Constant.PR_GIA).getValue(Float.class);
                            float productDiscount = dataSnapshot.child(Constant.PR_GIAMGIA).getValue(Float.class);
                            float productRating = dataSnapshot.child(Constant.PR_SAO).getValue(Float.class);
                            int productPurchaseCount = dataSnapshot.child(Constant.PR_LUOTMUA).getValue(Integer.class);
                            int productReviewCount = dataSnapshot.child(Constant.PR_LUOTDANHGIA).getValue(Integer.class);
                            List<Integer> productSizes = new ArrayList<>();
                            DataSnapshot sizesSnapshot = dataSnapshot.child(Constant.PR_KICHTHUOC);
                            for (DataSnapshot sizeSnapshot : sizesSnapshot.getChildren()) {
                                int size = sizeSnapshot.getValue(Integer.class);
                                productSizes.add(size);
                            }
                            List<String> productImages = new ArrayList<>();
                            DataSnapshot imagesSnapshot = dataSnapshot.child(Constant.PR_ANHSP);
                            for (DataSnapshot imageSnapshot : imagesSnapshot.getChildren()) {
                                String imageUrl = imageSnapshot.getValue(String.class);
                                productImages.add(imageUrl);
                            }
                            Product product = new Product(productId, productName, productCount, productManuId, productManuName,
                                    productDescription, productPrice, productDiscount, productRating, productPurchaseCount,
                                    productReviewCount, productSizes, productImages);

                            listProduct.add(product);
                        }
                        fectchDataListener.OnSuccess(listProduct);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        fectchDataListener.OnFlaid(error.getMessage());
                    }
                });

    }

    private void setManuFact() {
        ManufactAdapter manufactAdapter = new ManufactAdapter(manufact -> {
            searchManu.onSearch(manufact);
        });
        binding.rcvCategory.setAdapter(manufactAdapter);
        manufactAdapter.setItems(listManufact());

    }
}
