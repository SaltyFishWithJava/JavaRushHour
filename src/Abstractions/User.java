package Abstractions;

public class User {
	private String name;
	private String password;
	private String id;
	
	public User(){
		
	}
	
	public User(String _name, String _password,String _id){
		name = _name;
		password = _password;
		id = _id;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
}
