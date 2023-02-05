package best.project.shop.model;

public enum Roles {
	
	ADMIN("Admin"),
    CLIENT("Client");
    
	public final String roleName;

    private Roles(String roleName) {
        this.roleName = roleName;
    }
}
