package com.alp.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alp.conn.MySQLConnection;
import com.alp.dto.ProductCountDTO;
import com.alp.dto.ProductCountResponse;
import com.alp.dto.TrendTweetResponse;
import com.alp.dto.TweetDTO;
import com.alp.dto.WordDTO;
import com.google.gson.Gson;

@RestController
public class AlpController {

    @RequestMapping(value = "/alp/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getTestOP() {
        return "TestALPService";
    }

    @RequestMapping(value = "/alp/prodcount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getProdCount() {
        Statement stmt;
        ResultSet res;
        String jsonInString = null;
        Connection mysqlConn = MySQLConnection.conn();
        ProductCountResponse productCountResponse = new ProductCountResponse();
        List<ProductCountDTO> productCountList = new ArrayList<ProductCountDTO>();
        try {
            stmt = mysqlConn.createStatement();
            res = stmt.executeQuery("select * from product_counts");
            while (res.next()) {
                ProductCountDTO productCountDTO = new ProductCountDTO();
                productCountDTO.setHashTags(res.getString(1));
                productCountDTO.setTotalCounts(res.getInt(2));
                productCountList.add(productCountDTO);
            }
            productCountResponse.setProductCountList(productCountList);
            Gson gson = new Gson();
            jsonInString = gson.toJson(productCountList);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (mysqlConn != null) {
                try {
                    mysqlConn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonInString;
    }

    @RequestMapping(value = "/alp/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getTrendTweets(@PathVariable String name) {
        Statement stmt;
        ResultSet res;
        String jsonInString = null;
        Connection mysqlConn = MySQLConnection.conn();
        List<TweetDTO> tweetDTOList = new ArrayList<TweetDTO>();
        try {
            stmt = mysqlConn.createStatement();
            res = stmt.executeQuery("select * from trending_tweets where hashtags='" + name + "'");
            while (res.next()) {
                TweetDTO tweetDTO = new TweetDTO();
                tweetDTO.setName(res.getString(2));
                List<WordDTO> wordDTOList = new ArrayList<WordDTO>();
                WordDTO wordDTO = new WordDTO();
                wordDTO.setName(res.getString(2));
                wordDTO.setSize(res.getInt(3));
                wordDTOList.add(wordDTO);
                tweetDTO.setChildren(wordDTOList);
                tweetDTOList.add(tweetDTO);
            }
            TrendTweetResponse trendTweetResponse = new TrendTweetResponse();
            trendTweetResponse.setName("flare");
            trendTweetResponse.setChildren(tweetDTOList);
            Gson gson = new Gson();
            jsonInString = gson.toJson(trendTweetResponse);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (mysqlConn != null) {
                try {
                    mysqlConn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonInString;
    }

}
