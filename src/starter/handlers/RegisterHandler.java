package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import request.RegisterRequest;
import responses.RegisterResponse;
import services.RegisterService;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.Objects;

public class RegisterHandler {
    public Object handleRequest(Request req, Response res) throws SQLException, DataAccessException {
        //Get JSON data
        String reqData = req.body();

        // Use Gson to deserialize the JSON data into a LoginRequest object
        Gson gson = new Gson();
        RegisterRequest request = (RegisterRequest)gson.fromJson(reqData, RegisterRequest.class);

        // Call the LoginService to perform the login
        RegisterService service = new RegisterService();
        RegisterResponse result = service.register(request);

        //return response

        if (Objects.equals(result.getMessage(), "Error: bad request")) {
            res.status(400);
        } else if (Objects.equals(result.getMessage(), "Error: already taken")) {
            res.status(403);
        } else {
            res.status(200);
        }

        return gson.toJson(result);
    }
}
