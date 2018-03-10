package com.alp.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.alp.conn.HiveConnection;
import com.alp.conn.MySQLConnection;
import com.alp.dto.ProductCountDTO;
import com.alp.dto.TrendTweetDTO;

public class HiveProdCount {
    private ResultSet res;
    private Statement stmt;
    private PreparedStatement preparedStmt;
    private static Connection hiveConn = HiveConnection.conn();
    private static Connection mysqlConn = MySQLConnection.conn();

    public List<ProductCountDTO> getProdCount() {
        List<ProductCountDTO> productCountList = new ArrayList<ProductCountDTO>();
        try {
            stmt = hiveConn.createStatement();
            stmt.execute("msck repair table tweets");
            res = stmt.executeQuery("select * from product_count");
            while (res.next()) {
                ProductCountDTO productCountDTO = new ProductCountDTO();
                productCountDTO.setHashTags(res.getString(1));
                productCountDTO.setTotalCounts(res.getInt(2));
                productCountList.add(productCountDTO);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
        return productCountList;
    }

    public void insertProductCount() {
        try {
            String query = "insert into product_counts (hashtags, total_counts) values (?, ?)";
            stmt = mysqlConn.createStatement();
            stmt.execute("truncate table product_counts");
            preparedStmt = mysqlConn.prepareStatement(query);
            List<ProductCountDTO> prodCountList = getProdCount();
            for (ProductCountDTO productCountDTO : prodCountList) {
                preparedStmt.setString(1, productCountDTO.getHashTags());
                preparedStmt.setInt(2, productCountDTO.getTotalCounts());
                preparedStmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
    }

    public List<TrendTweetDTO> getTrendTweets() {
        List<TrendTweetDTO> trendTweetList = new ArrayList<TrendTweetDTO>();
        try {
            stmt = hiveConn.createStatement();
            res = stmt.executeQuery("select * from trending_tweets");
            while (res.next()) {
                TrendTweetDTO trendTweetDTO = new TrendTweetDTO();
                trendTweetDTO.setHashTags(res.getString(1));
                trendTweetDTO.setWord(res.getString(2));
                trendTweetDTO.setCnt(res.getInt(3));
                trendTweetList.add(trendTweetDTO);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
        return trendTweetList;
    }
    
    public void insertTrendTweets() {
        try {
            String query = "insert into trending_tweets (hashtags, word, cnt) values (?, ?, ?)";
            stmt = mysqlConn.createStatement();
            stmt.execute("truncate table trending_tweets");
            preparedStmt = mysqlConn.prepareStatement(query);
            List<TrendTweetDTO> trendTweetList = getTrendTweets();
            for (TrendTweetDTO trendTweetDTO : trendTweetList) {
                preparedStmt.setString(1, trendTweetDTO.getHashTags());
                preparedStmt.setString(2, trendTweetDTO.getWord());
                preparedStmt.setInt(3, trendTweetDTO.getCnt());
                preparedStmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
    }

    public static void main(String[] args) {
        try {
        HiveProdCount hpc = new HiveProdCount();
        hpc.insertProductCount();
        hpc.insertTrendTweets();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (hiveConn != null) {
                try {
                    hiveConn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (mysqlConn != null) {
                try {
                    mysqlConn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
