package model;

public class CartItemJSON {
	private String title;
	private int quantity;
	private int price;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "CartItemJSON [title=" + title + ", quantity=" + quantity
				+ ", price=" + price + "]";
	}
	public CartItemJSON(String title, int quantity, int price) {
		this.title = title;
		this.quantity = quantity;
		this.price = price;
	}
	

}
