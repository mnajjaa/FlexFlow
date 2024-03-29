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

    //** Register a new user
    @Override
    public void register(User u) {
        String req = "INSERT INTO `user` (`nom`,`email`, `password`,`telephone`,`role`) VALUE (?,?,?,?,?)";
        try {
            pste = cnx.prepareStatement(req);
            pste.setString(1, u.getName());
            pste.setString(2, u.getEmail());
            pste.setString(3, BCrypt.hashpw(u.getPassword(), BCrypt.gensalt()));
            pste.setString(4,u.getTelephone());
            pste.setString(5,u.getRole().toString());


            pste.executeUpdate();
            System.out.println("utilisateur créée");
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //** Check if an email already exists
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

    //** Authenticate a user
    @Override
    public int Authentification(String email,String password) {
        int status = 0;
        try {
            String req = "select * from user where email=? ";

            pste = cnx.prepareStatement(req);
            pste.setString(1, email);

            ResultSet rs = pste.executeQuery();

            while (rs.next()) //l9a une ligne fi wosset lbase de donnee
            {
                //User u = this.findByEmail(email);
                if(rs.getString("etat").equals("0"))
                {

                    return 2 ;
                }



               if( BCrypt.checkpw(password, rs.getString("password"))) {
                   //if logged in successfully yemchy yasnaalou session w y7ottou fiha les informations mte3ou

                   //explain : f session bch y7ott le vrai role du user connecté khater 9bal ken y7ott role.ADMIN ou role.COACH meme si
                   // user connecté est un coach ou un membre
                   Role userRole = Role.valueOf(rs.getString("role"));
                   User u =new User(rs.getInt("id"),rs.getString("nom"),rs.getString("email"),rs.getString("password"),rs.getString("telephone"),userRole);

                   Session s=Session.getInstance();
                   s.setLoggedInUser(u);
                   System.out.println("The connected is "+s.getLoggedInUser().getRole());
                   return 1;
               }

            }
        } catch (Exception ex) {
        }
        return status;
    }

    //** Activer ou bdesactiver un membre
    @Override
    public void ActiverOrDesactiver(int id) {

       //verifier si l'utilisateur connecté est un admin
        Session s=Session.getInstance();
        User u = s.getLoggedInUser();
        if(u.getRole() != Role.ADMIN)
        {
            System.out.println("You are not allowed to perform this action");
            return;
        }


        String req = "UPDATE user SET etat =!etat  WHERE id = ?";
        try {
            pste = cnx.prepareStatement(req);
            pste.setInt(1, id);
            pste.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //** Update user information
    @Override
    public void update(User u) {
        String req = "UPDATE user SET nom = ?, email = ?, password = ?, telephone = ? WHERE id = ?";
        try {
            pste = cnx.prepareStatement(req);
            pste.setString(1, u.getName());
            pste.setString(2, u.getEmail());
            pste.setString(3, BCrypt.hashpw(u.getPassword(), BCrypt.gensalt()));
            pste.setString(4, u.getTelephone());
            pste.setInt(5, u.getId());
            int rowsUpdated = pste.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing user was updated successfully!");
            } else {
                System.out.println("No user found with this id!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //** Delete a user
    @Override
    public void delete(int id) {
        //verifier si l'utilisateur connecté est un admin
        Session s=Session.getInstance();
        User u = s.getLoggedInUser();
        if(u.getRole() != Role.ADMIN)
        {
            System.out.println("You are not allowed to perform this action");
            return;
        }

        String req = "DELETE FROM user WHERE id = ?";
        try {
            pste = cnx.prepareStatement(req);
            pste.setInt(1, id);
            int rowsDeleted = pste.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A user was deleted successfully!");
            } else {
                System.out.println("No user found with this id!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    // Find a user by email
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
