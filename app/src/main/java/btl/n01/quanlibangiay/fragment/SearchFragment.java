package btl.n01.quanlibangiay.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import btl.n01.quanlibangiay.adapter.ManufactAdapter;
import btl.n01.quanlibangiay.adapter.ProductAdapter;
import btl.n01.quanlibangiay.base.BaseFragment;
import btl.n01.quanlibangiay.databinding.FragmentSearchBinding;
import btl.n01.quanlibangiay.interfaces.FectchDataListener;
import btl.n01.quanlibangiay.model.Manufact;
import btl.n01.quanlibangiay.model.Product;
import btl.n01.quanlibangiay.utility.Constant;

public class SearchFragment extends BaseFragment<FragmentSearchBinding> {
    @Override
    protected FragmentSearchBinding getBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentSearchBinding.inflate(inflater);
    }

    private Manufact manufact;
    private String key;
    private ArrayList<Product> products= new ArrayList<>();

    public SearchFragment(Manufact manufact, String key) {
        this.manufact = manufact;
        this.key = key;
    }

    @Override
    protected void initView() {
        getListproduct(new FectchDataListener<Product>() {
            @Override
            public void OnSuccess(ArrayList<Product> products) {
                SearchFragment.this.products= products;
            }
            @Override
            public void OnFlaid(String mess) {
                showToast(mess);
            }
        });
        if(key!=null){
            searchByKey();
        }
        if (manufact!=null){
            sarchByManu();
        }
        setManuFact();

        binding.icBack.setOnClickListener(view -> {
            binding.llManufact.setVisibility(View.VISIBLE);
            binding.llResult.setVisibility(View.GONE);
            loadProductView(new ArrayList<>());
        });
        binding.icSearch.setOnClickListener(v->{
            key= binding.edtSearchData.getText().toString();
            searchByKey();
        });
    }

    private void setManuFact() {
        ManufactAdapter manufactAdapter = new ManufactAdapter(manufact -> {
           SearchFragment.this.manufact=manufact;
           sarchByManu();
        });
        binding.rcvManufact.setAdapter(manufactAdapter);
        manufactAdapter.setItems(listManufact());

    }

    private void sarchByManu() {
        binding.txtResult.setText("Kết quả cho \'"+manufact.getName()+"\'");
        binding.llResult.setVisibility(View.VISIBLE);
        binding.llManufact.setVisibility(View.GONE);
        if (products.size()!=0){
            ArrayList<Product> searchList= new ArrayList<>();
            for (Product product : products){
                if(product.getManuID().equals(manufact.getiD())){
                    searchList.add(product);
                }
            }
            loadProductView(searchList);
        }
        else {
            getListproduct(new FectchDataListener<Product>() {
                @Override
                public void OnSuccess(ArrayList<Product> datas) {
                    ArrayList<Product> searchList= new ArrayList<>();
                    products=datas;
                    for (Product product : products){
                        if(product.getManuID().equals(manufact.getiD())){
                            searchList.add(product);
                        }
                    }
                    loadProductView(searchList);
                }
                @Override
                public void OnFlaid(String mess) {

                }
            });
        }
    }

    private void loadProductView(ArrayList<Product> products) {
        ProductAdapter productAdapter= new ProductAdapter(new ProductAdapter.OnProductListener() {
            @Override
            public void OnSelect(Product product) {

            }

            @Override
            public void OnAddCart(Product product) {

            }
        });
        binding.rcvResultSreach.setAdapter(productAdapter);
        productAdapter.setItems(products);
        loadding.dismiss();
    }
    private void searchByKey() {
        binding.txtResult.setText("Kết quả cho \'"+key+"\'");
        binding.llResult.setVisibility(View.VISIBLE);
        binding.llManufact.setVisibility(View.GONE);
        if (products.size()!=0){
            ArrayList<Product> searchList= new ArrayList<>();
            for (Product product : products){
                if(product.getTengiay().toLowerCase().contains(key.toLowerCase())){
                    searchList.add(product);
                }
            }
            loadProductView(searchList);
        }
        else {
            getListproduct(new FectchDataListener<Product>() {
                @Override
                public void OnSuccess(ArrayList<Product> datas) {
                    ArrayList<Product> searchList= new ArrayList<>();
                    products=datas;
                    for (Product product : products){
                        if(product.getTengiay().toLowerCase().contains(key.toLowerCase())){
                            searchList.add(product);
                        }
                    }
                    loadProductView(searchList);
                }
                @Override
                public void OnFlaid(String mess) {

                }
            });
        }
    }


    private void getListproduct(FectchDataListener<Product> fectchDataListener) {
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

}
