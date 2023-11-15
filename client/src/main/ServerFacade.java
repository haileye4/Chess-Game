import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import request.CreateGameRequest;
import request.RegisterRequest;
import request.LoginRequest;
import request.*;
import responses.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

public class ServerFacade {
    //each of 7 methods

    //make register one first
    public RegisterResponse Register(RegisterRequest req) throws IOException, URISyntaxException {
        //HANDLE EXCEPTION IF USERNAME IS ALREADY TAKEN
        URL url = new URL("http://localhost:8080/user");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        connection.connect();

        try(OutputStream requestBody = connection.getOutputStream();) {
            // Write request body to OutputStream ...
            var jsonBody = new Gson().toJson(req);
            requestBody.write(jsonBody.getBytes());
        }

        RegisterResponse response = null;

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

            InputStream responseBody = connection.getInputStream();
            // Read response body from InputStream ...
            InputStreamReader inputStreamReader = new InputStreamReader(responseBody);
            response = new Gson().fromJson(inputStreamReader, RegisterResponse.class);
        }
        else {
            // SERVER RETURNED AN HTTP ERROR
            System.out.println("Error: Username already taken! Cannot duplicate users...");
            System.out.println("\n");
            throw new RuntimeException("HTTP error");
        }
        connection.disconnect();
        return response;
    }

    public LoginResponse Login(LoginRequest req) throws IOException, URISyntaxException {
        //make sure to handle
        URL url = new URL("http://localhost:8080/session");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        connection.connect();

        try(OutputStream requestBody = connection.getOutputStream();) {
            // Write request body to OutputStream ...
            var jsonBody = new Gson().toJson(req);
            requestBody.write(jsonBody.getBytes());
        }

        LoginResponse response = null;

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Get HTTP response headers, if necessary
            // Map<String, List<String>> headers = connection.getHeaderFields();

            // OR

            //connection.getHeaderField("Content-Length");

            InputStream responseBody = connection.getInputStream();
            // Read response body from InputStream ...
            InputStreamReader inputStreamReader = new InputStreamReader(responseBody);
            response = new Gson().fromJson(inputStreamReader, LoginResponse.class);
        }
        else {
            // SERVER RETURNED AN HTTP ERROR
            System.out.println("Error: User not registered!");
            System.out.println("\n");
            throw new RuntimeException("HTTP Error");
        }
        connection.disconnect();
        return response;
    }

    public LogoutResponse Logout(String authToken) throws IOException, URISyntaxException, SQLException {
        URL url = new URL("http://localhost:8080/session");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("DELETE");
        connection.setDoOutput(true);

        connection.addRequestProperty("authorization", authToken);

        connection.connect();

        LogoutResponse response = null;

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Get HTTP response headers, if necessary
            // Map<String, List<String>> headers = connection.getHeaderFields();

            // OR

            //connection.getHeaderField("Content-Length");

            InputStream responseBody = connection.getInputStream();
            // Read response body from InputStream ...
            InputStreamReader inputStreamReader = new InputStreamReader(responseBody);
            response = new Gson().fromJson(inputStreamReader, LogoutResponse.class);
        }
        else {
            // SERVER RETURNED AN HTTP ERROR
            throw new RuntimeException("HTTP error");
        }
        connection.disconnect();
        return response;
    }

    public CreateGameResponse CreateGame(CreateGameRequest req) throws IOException, URISyntaxException {
        URL url = new URL("http://localhost:8080/game");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // Set HTTP request headers, if necessary
        //will have to set authToken
        // connection.addRequestProperty(""authorization"", PASS AUTHTOKEN );

        connection.connect();

        try(OutputStream requestBody = connection.getOutputStream();) {
            // Write request body to OutputStream ...
            var jsonBody = new Gson().toJson(req);
            requestBody.write(jsonBody.getBytes());
        }
        
        CreateGameResponse response = null; 

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Get HTTP response headers, if necessary
            // Map<String, List<String>> headers = connection.getHeaderFields();

            // OR

            //connection.getHeaderField("Content-Length");

            InputStream responseBody = connection.getInputStream();
            // Read response body from InputStream ...
            InputStreamReader inputStreamReader = new InputStreamReader(responseBody);
            response = new Gson().fromJson(inputStreamReader, CreateGameResponse.class);
        }
        else {
            // SERVER RETURNED AN HTTP ERROR
            throw new RuntimeException("HTTP error");
        }
        return response;
    }
}
