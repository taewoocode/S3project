package com.example.s3test.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private String userID;
    private String userPW;
    private String userEmail;
    private String userName;

    private Connection con;
    private ResultSet rs;

    public UserDao() {
        try {
            String dbURL = "jdbc:mysql://localhost:3306/vector?characterEncoding=UTF-8&serverTimezone=UTC";
            String dbID = "root";
            String dbPwd = "cuha";
            Class.forName("org.mariadb.jdbc.Driver");
            con = DriverManager.getConnection(dbURL,dbID,dbPwd);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    // -2: id 없음 / -1: 서버 오류 / 0: 비밀번호 틀림 / 1: 성공
    public int login(String userID, String UserPW) {
        try {
            PreparedStatement pst = con.prepareStatement("SELECT userpw FROM tomcat WHERE userid = ?");
            pst.setString(1, userID);
            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString(1).equals(UserPW) ? 1 : 0;
            } else {
                return -2;
            }
        } catch (Exception e) { e.printStackTrace(); return -1; }
    }

    public boolean ID_Check(String userID) {
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM tomcat WHERE userid = ?");
            pst.setString(1, userID);
            rs = pst.executeQuery();
            if(rs.next()) {
                return false;
            }else {
                return true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;

    }
    // -1 : 서버 오류 / 0: 이미 존재 하는 id / 1: 성공
    public int join(UserDao userDAO) {
        if(!ID_Check(userDAO.getUserID())) return 0;
        try {
            PreparedStatement pst = con.prepareStatement("INSERT INTO tomcat VALUES (?,?,?,?)");
            pst.setString(1, userDAO.getUserID());
            pst.setString(2, userDAO.getUserPW());
            pst.setString(3, userDAO.getUserEmail());
            pst.setString(4, userDAO.getUserName());
            return pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public UserDao getUser(String userID) {
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM tomcat userid = ?");
            pst.setString(1, userID);
            rs = pst.executeQuery();
            if(rs.next()) {
                UserDao userDAO = new UserDao();
                userDAO.setUserID(rs.getString(1));
                userDAO.setUserPW(rs.getString(2));
                userDAO.setUserEmail(rs.getString(3));
                userDAO.setUserName(rs.getString(4));
                return userDAO;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getUserPW() {
        return userPW;
    }
    public void setUserPW(String userPW) {
        this.userPW = userPW;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

}
