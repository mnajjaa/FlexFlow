package com.example.bty.Entities;



public class User {

   private int id;
   private String name;
   private String email;
   private String password ;
   private String telephone ;
   private Role role;
   private  boolean etat =false;    // 0 = desactiver , 1 = activer
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public User(int id, String name, String email, String password, String telephone, Role role,String image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.telephone=telephone;
        this.role=role;
        this.etat=etat;
        this.image=image;

    }

    public User() {
    }


    public User(String name, String email, String password,String telephone,Role role,String image) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.role=role;
        this.etat=etat;
        this.image=image;
    }



    public int getId() {
        return id;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", telephone='" + telephone + '\'' +
                ", role=" + role +
                ",image='"+image+'\''+
                '}';
    }

    public Role getRole() {
        return  role;
    }
    public String getTelephone() {
        return telephone;
    }



}
