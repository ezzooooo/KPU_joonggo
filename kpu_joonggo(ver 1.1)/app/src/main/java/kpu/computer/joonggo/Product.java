package kpu.computer.joonggo;

public class Product {

    Integer productNumber;
    String name;
    Integer price;
    String stat;
    String imagepath1, imagepath2, imagepath3;
    String seller;
    String exp;
    String category;

    public Product(Integer productNumber, String seller, String name, String exp, String category, Integer price, String stat, String imagepath1, String imagepath2, String imagepath3) {
        this.productNumber = productNumber;
        this.seller = seller;
        this.name = name;
        this.exp = exp;
        this.category = category;
        this.price = price;
        this.stat = stat;
        this.imagepath1 = imagepath1;
        this.imagepath2 = imagepath2;
        this.imagepath3 = imagepath3;
    }



    public String getImagepath2() {
        return imagepath2;
    }

    public void setImagepath2(String imagepath2) {
        this.imagepath2 = imagepath2;
    }

    public String getImagepath3() {
        return imagepath3;
    }

    public void setImagepath3(String imagepath3) {
        this.imagepath3 = imagepath3;
    }

    public String getImagepath1() {
        return imagepath1;
    }

    public void setImagepath1(String imagepath1) {
        this.imagepath1 = imagepath1;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(Integer productNumber) {
        this.productNumber = productNumber;
    }

    public String getName() {  return name;    }

    public void setName(String name) {        this.name = name;    }

    public Integer getPrice() {        return price;    }

    public void setPrice(Integer price) {        this.price = price;    }

    public String getStat() {        return stat;    }

    public void setStat(String stat) {        this.stat = stat;    }

}
