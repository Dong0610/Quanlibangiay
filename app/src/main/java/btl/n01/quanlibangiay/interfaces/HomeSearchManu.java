package btl.n01.quanlibangiay.interfaces;

import java.util.ArrayList;

import btl.n01.quanlibangiay.model.Manufact;
import btl.n01.quanlibangiay.model.Product;

public interface HomeSearchManu {
    void onSearch(Manufact manufact);
    void onGetProduct(ArrayList<Product> list);
}
