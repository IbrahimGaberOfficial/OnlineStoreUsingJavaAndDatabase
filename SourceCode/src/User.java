public class User extends Visitor {
	String ID;
	String name;
	String email;
	String password;

	public User() {
	}

	public User(String ID, String name, String email, String password) {
		this.ID = ID;
		this.name = name;
		this.email = email;
		this.password = password;
	}
}
