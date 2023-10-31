package dataAccess;

import models.AuthToken;

import java.util.ArrayList;

/**
 * stores authTokens in the database
 */
public class AuthDAO {
    /**
     * list that holds authTokens (for now)
     */
    static ArrayList<AuthToken> tokens = new ArrayList<AuthToken>();

    /**
     * create a new authentication token
     * @param token
     * @throws DataAccessException
     */
    public void create(AuthToken token){
        tokens.add(token);
    }

    /**
     * read all authetication tokens
     * @return list of authTokens
     * @throws DataAccessException
     */
    public ArrayList<AuthToken> read() {
        return tokens;
    }

    /**
     * update an authToken
     * @param token
     * @throws DataAccessException
     */
    public void update(AuthToken token) throws DataAccessException {

    }

    /**
     * delete an authentication token from our database
     * @param token
     * @throws DataAccessException
     */
    public void delete(AuthToken token) /*throws DataAccessException*/ {
        tokens.remove(token);
    }

    public void clear() {
        tokens.clear();
    }
}
