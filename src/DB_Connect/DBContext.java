/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB_Connect;

import Model.ScoreModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBContext {

    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public Connection getConnection() throws Exception {
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        String url = "jdbc:sqlserver://LAPTOP-9038L4U8\\HOANGTU:1433;databaseName=Game2048;user=sa;password=sa";
//        return DriverManager.getConnection(url);

//        mysql
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/game2048";
        String user = "root";
        String pass = "tu0764";

        return DriverManager.getConnection(url, user, pass);
    }

    public List<ScoreModel> listScore(int top) throws SQLException {
        List<ScoreModel> scoreModels = new ArrayList<>();
//        String sql = "SELECT TOP (?) * FROM SCORE ORDER BY SCORE DESC";
//        mysql
        String sql = "SELECT * FROM SCORE ORDER BY SCORE DESC LIMIT ?";
        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, top);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                scoreModels.add(new ScoreModel(rs.getString("username"), rs.getInt("score")));
            }
            return scoreModels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        con.close();
        return null;
    }

    public int minTop(int top) throws SQLException {
        int minTop = 0;
        String sql = "SELECT MIN(sc.SCORE) MIN FROM (SELECT * FROM SCORE ORDER BY SCORE DESC LIMIT ?) sc";
        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, top);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                minTop = rs.getInt("MIN");
            }
            return minTop;
        } catch (Exception e) {
            e.printStackTrace();
        }
        con.close();
        return 0;
    }

    public void insert(ScoreModel scoreModel) throws Exception {
        String sql = "INSERT INTO SCORE(USERNAME, SCORE) VALUES(?, ?)";
        try {
            con = getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);
            ps.setString(1, scoreModel.getUsername());
            ps.setInt(2, scoreModel.getScore());
            ps.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
        }
    }

}
