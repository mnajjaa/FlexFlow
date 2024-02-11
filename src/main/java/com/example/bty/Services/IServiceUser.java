package com.example.bty.Services;

import com.example.bty.Entities.User;

public interface IServiceUser {
    public void register(User user);
    public boolean emailExists(String email);
    public boolean Authentification(String email,String password);

}
