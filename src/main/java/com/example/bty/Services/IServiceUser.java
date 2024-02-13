package com.example.bty.Services;

import com.example.bty.Entities.User;

public interface IServiceUser {
    public void register(User user);
    public boolean emailExists(String email);
    public int Authentification(String email,String password);
    public void ActiverOrDesactiver(int id);
public void update(User user);


}
