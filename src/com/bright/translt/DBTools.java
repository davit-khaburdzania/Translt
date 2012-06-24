package com.bright.translt;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.client.am.PreparedStatement40;
import sun.security.util.Password;

/**
 *
 * @author bright
 * email: dato7077@gmail.com
 */
public class DBTools {
    Connection con;
    private String db_user = "bright";
    private String db_password = "abracadabra";
    public static String local_address;
    public void connect(){
        try {
            local_address = InetAddress.getLocalHost().getHostAddress();
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/translate", db_user, db_password );
        } catch (SQLException ex) {
            System.out.println("can't esablishe connection");
            System.out.println(ex.getMessage());
        }catch (Exception ex){
             System.out.println("general exception");
             ex.printStackTrace();
        }
    } 
    
    public void setDBInfo(String user, String password){
        this.db_user = user;
        this.db_password = password;
    }
    
    public void insert(String w, String ip){
        try {
            if(con == null) connect();
            String sql = "insert into HISTORY(ip, word, request_time) " +
                                      "VALUES('" + ip + "', '" + w + "', CURRENT_TIME)";
            PreparedStatement st = con.prepareStatement(sql);
            st.execute();
        } catch (SQLException ex) {
            System.out.println("sql exception:");
            ex.printStackTrace();
        }
    }
    
    public  ResultSet getResultSet(){
        try {
            if(con == null) connect();
            String sql = "select * from HISTORY where IP='" + local_address + "'";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet result = st.executeQuery();
            return result;
        } catch (SQLException ex) {
            System.out.println("sql exception:");
            ex.printStackTrace();
        }
        return null;
    }
}
