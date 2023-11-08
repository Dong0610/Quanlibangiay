package btl.n01.quanlibangiay.interfaces;

import java.util.ArrayList;

import btl.n01.quanlibangiay.model.Product;

public interface FectchDataListener<T> {
    void OnSuccess(ArrayList<T> datas);
    void OnFlaid(String mess);
}
