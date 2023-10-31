import com.google.gson.Gson;
import handlers.*;
import spark.*;
import java.util.*;

public class Server {

    public static void main(String[] args) {
        new Server().run();
    }

    private void run() {
        // Specify the port you want the server to listen on
        Spark.port(8080);

        // Register a directory for hosting static files
        Spark.externalStaticFileLocation("/Users/haileyjohnson/Desktop/Chess Game/web");

        Spark.post("/user", (req, res) ->
                (new RegisterHandler()).handleRequest(req, res));

        Spark.post("/session", (req, res) ->
                (new LoginHandler()).handleRequest(req, res));

        Spark.delete("/session", (req, res) ->
                (new LogoutHandler()).handleRequest(req, res));

        Spark.get("/game", (req, res) ->
                (new ListGamesHandler()).handleRequest(req, res));

        Spark.post("/game", (req, res) ->
                (new CreateGameHandler()).handleRequest(req, res));

        Spark.put("/game", (req, res) ->
                (new JoinGameHandler()).handleRequest(req, res));

        Spark.delete("/db", (req, res) ->
                (new ClearApplicationHandler()).handleRequest(req, res));
    }
}