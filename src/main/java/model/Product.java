package model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by fish on 15/5/25.
 */
@XmlRootElement
public class Product {

    private String title, description, imageUrl, secret;

    @NotEmpty
    private String tag;

    private double price;

    private long productId;

    private int server, farm;


    public Product() {
    }


    public Product(long productId, String title, String tag, String secret, String description, String img, double price,
                   int server, int farm) {
        this.productId = productId;
        this.title = title;
        this.tag = tag;
        this.secret = secret;
        this.description = description;
        this.imageUrl = img;
        this.price = price;
        this.server = server;
        this.farm = farm;
    }

    // Getter and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getServer() {
        return server;
    }

    public void setServer(int server) {
        this.server = server;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", secret='" + secret + '\'' +
                ", tag='" + tag + '\'' +
                ", price=" + price +
                ", productId=" + productId +
                ", server=" + server +
                ", farm=" + farm +
                '}';
    }
}
