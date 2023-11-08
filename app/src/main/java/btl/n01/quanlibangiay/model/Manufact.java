package btl.n01.quanlibangiay.model;

public class Manufact {

    private String iD, name;
    private Object imgae;

    public Manufact(String iD, String name, Object imgae) {
        this.iD = iD;
        this.name = name;
        this.imgae = imgae;
    }

    public Manufact() {
    }

    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getImgae() {
        return imgae;
    }

    public void setImgae(Object imgae) {
        this.imgae = imgae;
    }


}
