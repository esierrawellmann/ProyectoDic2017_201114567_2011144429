package com.edd.player;

import com.edd.player.DTO.DTOLogin;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HttpHelper {
    static final String serverURl = "http://localhost:8081/";
    private static final String USER_AGENT = "Mozilla/5.0";



    public  static DTOLogin logIn(String user, String password) throws Exception{

        URL obj = new URL(serverURl+"login");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Content-Type", "application/json");
        String urlParameters = "{\"user\":\""+user+"\",\"password\":\""+password+"\"}";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + serverURl);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        String rsp = response.toString();
        DTOLogin loginResponse = objectMapper.readValue(rsp, DTOLogin.class);
        return loginResponse ;


    }
    public static String getMatrix() throws Exception
    {

        URL obj = new URL(serverURl+"print_matrix");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + serverURl);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        //objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        return response.toString() ;


    }
    public static String getBTree(String year , String genere) throws Exception
    {
        String urlStr= "show_artist_tree/"+year+"/"+genere;

        URL obj = new URL(serverURl+ URLEncoder.encode(urlStr,"UTF-8"));
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + serverURl);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        //objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        return response.toString() ;


    }
    public static String getABBTree(String year , String genere,String artist) throws Exception
    {

        URL obj = new URL(serverURl+"show_artist_bbtree/"+ URLEncoder.encode(year,"UTF-8")+"/"+URLEncoder.encode(genere,"UTF-8")+"/"+URLEncoder.encode(artist,"UTF-8"));
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + serverURl);


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        //objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        return response.toString() ;


    }

    public static String getSongList(String year , String genere,String artist,String disk) throws Exception
    {

        URL obj = new URL(serverURl+"show_artist_disks/"+ URLEncoder.encode(year,"UTF-8")+"/"+URLEncoder.encode(genere,"UTF-8")+"/"+URLEncoder.encode(artist,"UTF-8")+"/"+URLEncoder.encode(disk,"UTF-8"));
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + serverURl);


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        //objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        return response.toString() ;


    }

    public static String getUserList() throws Exception
    {

        URL obj = new URL(serverURl+"print_users");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + serverURl);


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        //objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        return response.toString() ;


    }
    public static String deleteUser(String usrName) throws Exception
    {

        URL obj = new URL(serverURl+"eliminar/"+ URLEncoder.encode(usrName,"UTF-8"));
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + serverURl);


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        //objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        return response.toString() ;


    }



    public static String deleteGenere(String year, String genere) throws Exception
    {

        URL obj = new URL(serverURl+"eliminar_genero/"+ URLEncoder.encode(genere,"UTF-8")+"/"+URLEncoder.encode(year,"UTF-8"));
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + serverURl);


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        //objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        return response.toString() ;


    }



    public static String getLibrary() throws Exception
    {

        URL obj = new URL(serverURl+"library");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + serverURl);


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        //objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        return response.toString() ;


    }

    public static String deleteSong(String year, String genere,String artist,String album,String song) throws Exception
    {

        URL obj = new URL(serverURl+"delete_song/"+ URLEncoder.encode(year,"UTF-8")+"/"+URLEncoder.encode(genere,"UTF-8")+"/"+URLEncoder.encode(artist,"UTF-8")+"/"+URLEncoder.encode(album,"UTF-8")+"/"+URLEncoder.encode(song,"UTF-8"));
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + serverURl);


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        //objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        return response.toString() ;


    }

    public static String deleteArtist(String year, String genere,String artist) throws Exception
    {

        URL obj = new URL(serverURl+"delete_artist/"+ URLEncoder.encode(year,"UTF-8")+"/"+URLEncoder.encode(genere,"UTF-8")+"/"+URLEncoder.encode(artist,"UTF-8"));
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + serverURl);


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        //objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        return response.toString() ;


    }
}
