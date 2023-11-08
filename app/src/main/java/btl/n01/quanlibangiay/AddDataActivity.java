package btl.n01.quanlibangiay;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import btl.n01.quanlibangiay.databinding.ActivityAddDataBinding;
import btl.n01.quanlibangiay.model.Product;
import btl.n01.quanlibangiay.utility.Constant;

public class AddDataActivity extends AppCompatActivity {

    ActivityAddDataBinding binding;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnAddPr.setOnClickListener(view -> {
            DatabaseReference ref = database.getReference(Constant.KEY_PRODUCT);
            ArrayList<Product> dssp = ThemSanPham();
            for (Product product : dssp) {
                HashMap<String, Object> hashMap = getHasMap(product);
                ref.child(product.getId())
                        .setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("Add", "\nprId: " + product.getId());
                            }
                        });
            }
        });
    }

    private HashMap<String, Object> getHasMap(Product product) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Constant.PR_TENSP, product.getTengiay());
        hashMap.put(Constant.PR_GIA, product.getGia());
        hashMap.put(Constant.PR_SOLUONG, product.getSoluong());
        hashMap.put(Constant.PR_GIAMGIA, product.getGiamgia());
        hashMap.put(Constant.PR_SAO, product.getSosao());
        hashMap.put(Constant.PR_MANUID, product.getManuID());
        hashMap.put(Constant.PR_MANUNAME, product.getManuName());
        hashMap.put(Constant.PR_MOTA,product.getMota());
        hashMap.put(Constant.PR_LUOTMUA, product.getSolanmua());
        hashMap.put(Constant.PR_LUOTDANHGIA, product.getLuotdanhgia());
        hashMap.put(Constant.PR_KICHTHUOC, product.getKichthuoc());
        hashMap.put(Constant.PR_ANHSP, product.getAnhGiay());
        return hashMap;
    }

    private ArrayList<Product> ThemSanPham() {
        ArrayList<Product> listProduct = new ArrayList();
        listProduct.add(new Product(
                "HJwm1eFo4vu95mwtcrua",
                "Giày Nike Nam",
                10,
                "manufacture_id_01", "Nike",
                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh " +
                        "của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời " +
                        "trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở.", 299000, 0, 0, 0, 0,
                Arrays.asList(38, 39, 10),
                Arrays.asList(
                        "https://supersports.com.vn/cdn/shop/products/DH2987-001-1_900x@2x.jpg?v=1663757542",
                        "https://supersports.com.vn/cdn/shop/products/DH2987-001-6_900x@2x.jpg?v=1663757542"
                )
        ));
        listProduct.add(new
                Product(
                "HJwm1eFo4vu95mwtcrub",
                "Giày Nike Nữ",
                10,
                "manufacture_id_01", "Nike",
                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh " +
                        "của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời " +
                        "trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở.", 299000, 0, 0, 0, 0,
                Arrays.asList(38, 39, 10),
                Arrays.asList(
                        "https://product.hstatic.net/1000366086/product/dd1096-600_0_448acea668564b9aa2b7a878e092280a_master.jpg",
                        "https://product.hstatic.net/1000366086/product/dd1096-600_3_d057defb12af4256b10606490f8dc49e_master.jpg",
                        "https://product.hstatic.net/1000366086/product/dd1096-600_4_2f8534c210d84737bb559d7b65bc4e73_master.jpg"
                )));
        listProduct.add(new
                Product(
                "HJwm1eFo4vu95mwtcruc",
                "Giày Converse Nam",
                10,
                "manufacture_id_02", "Converse",
                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh " +
                        "của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời " +
                        "trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở.", 299000, 0, 0, 0, 0,
                Arrays.asList(38, 39, 10),
                Arrays.asList(
                        "https://supersports.com.vn/cdn/shop/products/M9166C-1_900x@2x.jpg?v=1668400719",
                        "https://supersports.com.vn/cdn/shop/products/M9166C-3_900x@2x.jpg?v=1668400720"
                )));
        listProduct.add(new
                Product(
                "HJwm1eFo4vu95mwtcrud",
                "Giày Converse Nữ",
                10,
                "manufacture_id_02", "Converse",
                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh " +
                        "của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời " +
                        "trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở.", 299000, 0, 0, 0, 0,
                Arrays.asList(38, 39, 10),
                Arrays.asList(
                        "https://product.hstatic.net/1000284478/product/0000_black_563508c_1_c8f81e06b98d4486ac8905fa5fa45354.jpg",
                        "https://product.hstatic.net/1000284478/product/0000_black_563508c_1_c8f81e06b98d4486ac8905fa5fa45354.jpg",
                        "https://product.hstatic.net/1000284478/product/0000_black_563508c_1_c8f81e06b98d4486ac8905fa5fa45354_large.jpg"
                )));
        listProduct.add(new
                Product(
                "HJwm1eFo4vu95mwtcrue",
                "Giày Dior Nam",
                10,
                "manufacture_id_03", "Dior",
                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh " +
                        "của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời " +
                        "trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở.", 299000, 0, 0, 0, 0,
                Arrays.asList(38, 39, 10),
                Arrays.asList(
                        "https://cdn.vuahanghieu.com/unsafe/500x0/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2022/07/giay-gucci-ace-embroidered-sneaker-white-leather-with-bee-mau-trang-size-39-62e39dfe418c9-29072022154446.jpg",
                        "https://cdn.vuahanghieu.com/unsafe/0x500/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2019/10/giay-gucci-men-s-ace-embroidered-sneaker-white-leather-with-bee-5da4465bc071f-14102019165643.jpg"
                )));
        listProduct.add(new
                Product(
                "HJwm1eFo4vu95mwtcruf",
                "Giày Dior Nữ",
                10,
                "manufacture_id_03", "Dior",
                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh " +
                        "của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời " +
                        "trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở.", 299000, 0, 0, 0, 0,
                Arrays.asList(38, 39, 10),
                Arrays.asList(
                        "https://images.hardlyeverwornit.com/plp_thumbnail_jpg/products/144836/Screenshot-2023-08-04-at-15-48-46-64cd0fdd71b3f.png",
                        "https://images.hardlyeverwornit.com/pdp_gallery_list_tablet/products/144836/HARDLY-EVER-WORN-IT139429-64ccdcc331b43.jpg"
                )));
        listProduct.add(new
                Product(
                "HJwm1eFo4vu95mwtcrug",
                "Giày Domba Nam",
                10,
                "manufacture_id_04", "Domba",
                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh " +
                        "của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời " +
                        "trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở.", 299000, 0, 0, 0, 0,
                Arrays.asList(38, 39, 10),
                Arrays.asList(
                        "https://product.hstatic.net/200000581855/product/giay_domba_got_den_h-9111__5__0cfb3eb629124920ba32d1b158056556_grande.jpg",
                        "https://product.hstatic.net/200000581855/product/giay_domba_got_den_h-9111__1__f87381d86fb84d2e9962989e25f2e5bd_grande.jpg"
                )));
        listProduct.add(new
                Product(
                "HJwm1eFo4vu95mwtcruh",
                "Giày Domba Nữ",
                10,
                "manufacture_id_04", "Domba",
                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh " +
                        "của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời " +
                        "trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở.", 299000, 0, 0, 0, 0,
                Arrays.asList(38, 39, 10),
                Arrays.asList(
                        "https://authentic-shoes.com/wp-content/uploads/2023/04/1898197_l_5d29beba94464bf4943fbad53d96e919-768x465.jpg",
                        "https://authentic-shoes.com/wp-content/uploads/2023/04/5d089061638c6-giay-domba-high-point-white-black-h-9111-03_c4c60bc7177441a4ba5fcdd2ef51a57e.jpg"
                )));
        listProduct.add(new
                Product(
                "HJwm1eFo4vu95mwtcrui",
                "Giày Fila Nữ", 10,
                "manufacture_id_05", "Fila",
                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh " +
                        "của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời " +
                        "trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở.", 299000, 0, 0, 0, 0,
                Arrays.asList(38, 39, 10),
                Arrays.asList(
                        "https://supersports.com.vn/cdn/shop/products/1RM02486F-100-1_900x@2x.jpg?v=1679567305",
                        "https://supersports.com.vn/cdn/shop/products/1RM02486F-100-6_900x@2x.jpg?v=1679567305"
                )));
        listProduct.add(new
                Product(
                "HJwm1eFo4vu95mwtcruk",
                "Giày Fila Nam",
                10,
                "manufacture_id_05", "Fila",
                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh " +
                        "của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời " +
                        "trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở.", 299000, 0, 0, 0, 0,
                Arrays.asList(38, 39, 10),
                Arrays.asList(
                        "https://cdn.shopify.com/s/files/1/0456/5070/6581/t/138/assets/9_9FILAvi_png?v=1693911326",
                        "https://cdn.shopify.com/s/files/1/0456/5070/6581/t/138/assets/9_9FILAvi_png?v=1693911326"
                )));
        listProduct.add(new
                Product(
                "HJwm1eFo4vu95mwtcrul",
                "Giày Gucci Nam",
                10,
                "manufacture_id_06", "Gucci",
                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh " +
                        "của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời " +
                        "trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở.", 299000, 0, 0, 0, 0,
                Arrays.asList(38, 39, 10),
                Arrays.asList(
                        "https://cdn.chiaki.vn/unsafe/0x960/left/top/smart/filters:quality(75)/https://chiaki.vn/upload/product/2023/01/giay-gucci-ace-gg-supreme-bees-548950-9n050-8465-63d385db7d594-27012023150547.jpg",
                        "https://cdn.chiaki.vn/unsafe/0x960/left/top/smart/filters:quality(75)/https://chiaki.vn/upload/product/2023/01/giay-gucci-ace-gg-supreme-bees-548950-9n050-8465-63d385db4392b-27012023150547.jpg"
                )));
        listProduct.add(new
                Product(
                "HJwm1eFo4vu95mwtcrum",
                "Giày Gucci Nữ",
                10,
                "manufacture_id_06", "Gucci",
                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh " +
                        "của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời " +
                        "trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở.", 299000, 0, 0, 0, 0,
                Arrays.asList(38, 39, 10),
                Arrays.asList(
                        "https://d3vfig6e0r0snz.cloudfront.net/rcYjnYuenaTH5vyDF/images/products/2c8479b979d548ec0c6f4e0da6d956e3.webp",
                        "https://d3vfig6e0r0snz.cloudfront.net/rcYjnYuenaTH5vyDF/images/products/2c42104e29d7f114a97f0117c9a97bdb.webp"
                )));
        listProduct.add(new
                Product(
                "HJwm1eFo4vu95mwtcruo",
                "Giày MLB Nữ",
                10,
                "manufacture_id_07", "MLB",
                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh " +
                        "của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời " +
                        "trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở.", 299000, 0, 0, 0, 0,
                Arrays.asList(38, 39, 10),
                Arrays.asList(
                        "https://cdn.vuahanghieu.com/unsafe/500x0/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2022/09/632922598969c-20092022091553.jpg",
                        "https://cdn.vuahanghieu.com/unsafe/0x500/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2022/09/632922597e1b8-20092022091553.jpg"
                )));
        listProduct.add(new
                Product(
                "HJwm1eFo4vu95mwtcrup",
                "Giày MLB Nam",
                10,
                "manufacture_id_07", "MLB",
                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh " +
                        "của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời " +
                        "trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở.", 299000, 0, 0, 0, 0,
                Arrays.asList(38, 39, 10),
                Arrays.asList(
                        "https://cdn.vuahanghieu.com/unsafe/0x500/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2023/08/giay-the-thao-mlb-chunky-boston-red-sox-3asxclh3n-43grs-mau-xam-trang-64eebe01032ef-30082023105649.jpg",
                        "https://cdn.vuahanghieu.com/unsafe/0x500/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2023/08/giay-the-thao-mlb-chunky-boston-red-sox-3asxclh3n-43grs-mau-xam-trang-64eebe01032ef-30082023105649.jpg"
                )));
        listProduct.add(new
                Product(
                "HJwm1eFo4vu95mwtcruq",
                "Giày New Balance Nam",
                10,
                "manufacture_id_08", "New Balance",
                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh " +
                        "của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời " +
                        "trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở.", 299000, 0, 0, 0, 0,
                Arrays.asList(38, 39, 10),
                Arrays.asList(
                        "https://app.fuxcdn.de/api/88a8ac53-6277-4ba3-9f52-6c743e914d99/thumbnail/30/30/7f/1660922850/new-balance-numeric-nm1010-tiago-lemos-sea-salt-skateboarding-nm1010wi_3_618x773.jpg",
                        "https://app.fuxcdn.de/api/88a8ac53-6277-4ba3-9f52-6c743e914d99/thumbnail/5e/a9/4c/1660922851/new-balance-numeric-nm1010-tiago-lemos-sea-salt-skateboarding-nm1010wi_4_618x773.jpg"
                )));
        listProduct.add(new
                Product(
                "HJwm1eFo4vu95mwtcrur",
                "Giày New Balance Nữ",
                10,
                "manufacture_id_08", "New Balance",
                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh " +
                        "của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời " +
                        "trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở.", 299000, 0, 0, 0, 0,
                Arrays.asList(38, 39, 10),
                Arrays.asList(
                        "https://d3vfig6e0r0snz.cloudfront.net/rcYjnYuenaTH5vyDF/images/products/7b97c2e505d3bb4f0fe5c56dbe7e1592.webp",
                        "https://d3vfig6e0r0snz.cloudfront.net/rcYjnYuenaTH5vyDF/images/products/d470510f4a4c83c87157e0cbf5fa1e0f.webp"
                )));
        listProduct.add(new
                Product(
                "HJwm1eFo4vu95mwtcrus",
                "Giày Puma Nam",
                10,
                "manufacture_id_09", "Puma",
                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh " +
                        "của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời " +
                        "trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở.", 299000, 0, 0, 0, 0,
                Arrays.asList(38, 39, 10),
                Arrays.asList(
                        "https://sithimy.s3.ap-southeast-1.amazonaws.com/sithimy/media/Z5Ae6rObnpw09fZvACsooNrdpWh8mblTd7KhMtbS.jpg",
                        "https://sithimy.s3.ap-southeast-1.amazonaws.com/sithimy/media/2g2eS4XSn9OhNv2kXzugR6fi0F0Ehyhk00Y8diNd.jpg"
                )));
        listProduct.add(new
                Product(
                "HJwm1eFo4vu95mwtcrut",
                "Giày Puma Nữ",
                10,
                "manufacture_id_09", "Puma",
                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh " +
                        "của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời " +
                        "trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở.", 299000, 0, 0, 0, 0,
                Arrays.asList(38, 39, 10),
                Arrays.asList(
                        "https://product.hstatic.net/200000581855/product/giay_puma_skye_clean_pink_380147-05__3__fa4d30398f1449b6ba35b719cb89da93_grande.jpg",
                        "https://product.hstatic.net/200000581855/product/giay_puma_skye_clean_pink_380147-05__4__fad4e2c81462456abba478557749bd41_grande.jpg"
                )
        ));
        listProduct.add(new
                Product(
                "HJwm1eFo4vu95mwtcruu",
                "Giày Vans Nữ",
                10,
                "manufacture_id_10", "Vans",
                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh " +
                        "của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời " +
                        "trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở.", 299000, 0, 0, 0, 0,
                Arrays.asList(38, 39, 10),
                Arrays.asList(
                        "https://bizweb.dktcdn.net/thumb/small/100/140/774/products/vans-old-skool-black-white-vn000d3hy28-1.jpg?v=1661757331440",
                        "https://bizweb.dktcdn.net/thumb/small/100/140/774/products/vans-old-skool-black-white-vn000d3hy28-3.jpg?v=1661757331440"
                )));
        listProduct.add(new Product(
                "HJwm1eFo4vu95mwtcruv",
                "Giày Vans Nam",
                10,
                "manufacture_id_10", "Vans",
                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh " +
                        "của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời " +
                        "trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở.", 299000, 0, 0, 0, 0,
                Arrays.asList(38, 39, 10),
                Arrays.asList(
                        "https://bizweb.dktcdn.net/thumb/small/100/140/774/products/vans-old-skool-classic-navy-white-vn000d3hnvy-1.jpg?v=1625922070377",
                        "https://bizweb.dktcdn.net/thumb/small/100/140/774/products/vans-old-skool-classic-navy-white-vn000d3hnvy-2.jpg?v=1625922077960"
                )));

        return listProduct;

    }
}