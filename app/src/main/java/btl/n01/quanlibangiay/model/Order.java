package btl.n01.quanlibangiay.model;

import java.io.Serializable;

public class Order implements Serializable {
    private String orderID;
    private String shopId;
    private String userId;
    private String usName;
    private String userMail, userAddress, orderStatus;
    private String productId;
    private String productName;
    private int productCount;
    private float productPrice;
    private String productImg;
    private String orderTime;
    private int orderPrSize;

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Order(String orderID, String shopId, String userId, String usName, String userMail, String userAddress, String orderStatus, String productId, String productName, int productCount, float productPrice, String productImg, String orderTime, int orderPrSize) {
        this.orderID = orderID;
        this.shopId = shopId;
        this.userId = userId;
        this.usName = usName;
        this.userMail = userMail;
        this.userAddress = userAddress;
        this.orderStatus = orderStatus;
        this.productId = productId;
        this.productName = productName;
        this.productCount = productCount;
        this.productPrice = productPrice;
        this.productImg = productImg;
        this.orderTime = orderTime;
        this.orderPrSize = orderPrSize;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public int getOrderPrSize() {
        return orderPrSize;
    }

    public void setOrderPrSize(int orderPrSize) {
        this.orderPrSize = orderPrSize;
    }

    public String getUsName() {
        return usName;
    }

    public void setUsName(String usName) {
        this.usName = usName;
    }
}

