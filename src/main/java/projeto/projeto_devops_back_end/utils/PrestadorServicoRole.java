package projeto.projeto_devops_back_end.utils;

public enum PrestadorServicoRole {

    ADMIN("admin"),
    USER("user");

    private String role;

    PrestadorServicoRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }

}
