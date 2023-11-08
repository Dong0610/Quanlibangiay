package btl.n01.quanlibangiay.model;

import java.util.List;
import java.util.Arrays;
import java.util.List;

public class Product {
    private String id;
    private String tengiay;
    private int soluong;

    private String manuID;

    public Product(String id) {
        this.id = id;
    }

    public Product(String id, String tengiay, int soluong, String manuID, String manuName, String mota, float gia, float giamgia, float sosao, int solanmua, int luotdanhgia, List<Integer> kichthuoc, List<String> anhGiay) {
        this.id = id;
        this.tengiay = tengiay;
        this.soluong = soluong;
        this.manuID = manuID;
        this.manuName = manuName;
        this.mota = mota;
        this.gia = gia;
        this.giamgia = giamgia;
        this.sosao = sosao;
        this.solanmua = solanmua;
        this.luotdanhgia = luotdanhgia;
        this.kichthuoc = kichthuoc;
        this.anhGiay = anhGiay;
    }

    private String manuName;

    private String mota;
    private float gia,giamgia,sosao;
    private int solanmua,luotdanhgia;
    private List<Integer> kichthuoc;
    private List<String> anhGiay;

    public Product() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTengiay() {
        return tengiay;
    }
    public String getManuID() {
        return manuID;
    }

    public void setManuID(String manuID) {
        this.manuID = manuID;
    }

    public String getManuName() {
        return manuName;
    }

    public void setManuName(String manuName) {
        this.manuName = manuName;
    }
    public void setTengiay(String tengiay) {
        this.tengiay = tengiay;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }

    public float getGiamgia() {
        return giamgia;
    }

    public void setGiamgia(float giamgia) {
        this.giamgia = giamgia;
    }

    public float getSosao() {
        return sosao;
    }

    public void setSosao(float sosao) {
        this.sosao = sosao;
    }

    public int getSolanmua() {
        return solanmua;
    }

    public void setSolanmua(int solanmua) {
        this.solanmua = solanmua;
    }

    public int getLuotdanhgia() {
        return luotdanhgia;
    }

    public void setLuotdanhgia(int luotdanhgia) {
        this.luotdanhgia = luotdanhgia;
    }

    public List<Integer> getKichthuoc() {
        return kichthuoc;
    }

    public void setKichthuoc(List<Integer> kichthuoc) {
        this.kichthuoc = kichthuoc;
    }

    public List<String> getAnhGiay() {
        return anhGiay;
    }

    public void setAnhGiay(List<String> anhGiay) {
        this.anhGiay = anhGiay;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", tengiay='" + tengiay + '\'' +
                ", soluong='" + soluong + '\'' +
                ", mota='" + mota + '\'' +
                ", gia=" + gia +
                ", giamgia=" + giamgia +
                ", sosao=" + sosao +
                ", solanmua=" + solanmua +
                ", luotdanhgia=" + luotdanhgia +
                ", kichthuoc=" + kichthuoc +
                ", anhGiay=" + anhGiay +
                '}';
    }
}
