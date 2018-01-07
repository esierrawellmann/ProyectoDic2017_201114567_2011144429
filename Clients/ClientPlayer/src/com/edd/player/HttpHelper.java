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


        //add reuqest header
        con.setRequestMethod("GET");
//        con.setRequestProperty("User-Agent", USER_AGENT);
//       // con.setRequestProperty("Content-Type", "application/json");
//        String urlParameters = "";
//
//        // Send post request
//        con.setDoOutput(true);
//        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//        wr.writeBytes(urlParameters);
//        wr.flush();
//        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + serverURl);
        System.out.println("Post parameters : " + serverURl+"print_matrix");
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
        //objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        return response.toString() ;


    }
}
