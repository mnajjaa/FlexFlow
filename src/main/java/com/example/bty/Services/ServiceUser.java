package com.example.bty.Services;

import com.example.bty.Entities.Role;
import com.example.bty.Entities.User;
import com.example.bty.Utils.ConnexionDB;
import com.example.bty.Utils.Session;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceUser implements IServiceUser{
    private PreparedStatement pste;

    Connection cnx = ConnexionDB.getInstance().getConnexion();
    @Override
    public void register(User u) {
        String req = "INSERT INTO `user` (`name`,`email`, `password`,`role`,`telephone`) VALUE (?,?,?,?,?)";
        try {
            pste = cnx.prepareStatement(req);
            pste.setString(1, u.getName());
            pste.setString(2, u.getEmail());
            pste.setString(3, BCrypt.hashpw(u.getPassword(), BCrypt.gensalt()));
            pste.setString(4,u.getRole().toString());
            pste.setString(5,u.getTelephone());
            pste.executeUpdate();
            System.out.println("utilisateur créée");
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    @Override
    public boolean emailExists(String email) {
        try {
            String query = "SELECT COUNT(*) FROM user WHERE email = ?";
            pste = cnx.prepareStatement(query);
            pste.setString(1, email);
            ResultSet resultSet = pste.executeQuery();
            if (resultSet.next()) {

                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la vérification de l'email : " + e.getMessage());
        }
        return false;
    }
    @Override
    public boolean Authentification(String email,String password) {
        boolean status = false;
        try {
            String req = "select * from user where email=? ";

            pste = cnx.prepareStatement(req);
            pste.setString(1, email);

            ResultSet rs = pste.executeQuery();

            while (rs.next()) {
                //User u = this.findByEmail(email);
                Role userRole = Role.ADMIN;
                User u =new User(rs.getInt("id"),rs.getString("name"),rs.getString("email"),rs.getString("password"),rs.getString("telephone"),userRole);
                Session s=Session.getInstance();
                s.setLoggedInUser(u);
                status = BCrypt.checkpw(password, rs.getString("password"));

            }
        } catch (Exception ex) {
        }
        return status;
    }
    public User findByEmail(String email) throws SQLException {
        User U = new User();
        String req = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement pste=cnx.prepareStatement(req)) {
            pste.setString(1, email);


            pste.setString(1,email);
            ResultSet rs = pste.executeQuery(req);

            while (rs.next()) {

                U.setId(rs.getInt("id"));
                U.setName(rs.getString("nom"));
                U.setEmail(rs.getString("email"));
                U.setPassword(rs.getString("password"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return U;
    }
}
