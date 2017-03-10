package thesis;

import java.io.Serializable;

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static int nextid = 1;
	final int id; 
	String name;
	String description;
	
	public Product(String name, String description){
		this.id = Product.nextid;
		Product.nextid++;
		this.name = name;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
	
}
