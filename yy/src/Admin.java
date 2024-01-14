public class Admin extends User {
	ProductManager productManager = new ProductManager();

	public Admin() {

	}

	public Admin(String ID, String name, String email, String password) {
		super(ID, name, email, password);
	}

	public void setAdminInfo(String ID, String name, String email, String password) {
		this.ID = ID;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	void listProductsFullInfo() {
		productManager.listProductsForAdmin();
	}

	void addNewProduct() {

		productManager.addNewProduct();
	}

	void updateProductInfo() {
		productManager.updateProductInfo();
	}
	public void deleteProduct() {
		productManager.deleteProduct();
	}

	public String toString() {
		return "-----------------------------------------------------"+
				"\n- <Admin Account>.\n" +
				"- ID = " + this.ID + "\n" +
				"- Name = " + this.name + "\n" +
				"- Email = " + this.email + "\n" +
				"- Password = " + this.password + "\n";
	}

}
