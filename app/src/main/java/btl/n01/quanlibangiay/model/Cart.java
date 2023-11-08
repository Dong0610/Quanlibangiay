package btl.n01.quanlibangiay.model;


import java.io.Serializable;

public class Cart {
    private boolean islove;
    private int nunCount;
    private String productID;
    private String productShopID;
    private String productName;
    private String prductImg;
    private float price;
    private int size;

    public Cart(
            boolean islove,
            int nunCount,
            String productID,
            String productShopID,
            String productName,
            String prductImg,
            float price,
            int size
    ) {
        this.islove = islove;
        this.nunCount = nunCount;
        this.productID = productID;
        this.productShopID = productShopID;
        this.productName = productName;
        this.prductImg = prductImg;
        this.price = price;
        this.size = size;
    }

    public boolean islove() {
        return islove;
    }

    public void setIslove(boolean islove) {
        this.islove = islove;
    }

    public int getNunCount() {
        return nunCount;
    }

    public void setNunCount(int nunCount) {
        this.nunCount = nunCount;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductShopID() {
        return productShopID;
    }

    public void setProductShopID(String productShopID) {
        this.productShopID = productShopID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrductImg() {
        return prductImg;
    }

    public void setPrductImg(String prductImg) {
        this.prductImg = prductImg;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

