package com.example.buensabor.Services.Impl;

import com.example.buensabor.Models.Entity.User;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class Auth0Service {

    @Value("${auth0.domain}")
    private String auth0Domain;

    @Value("${auth0.audience}")
    private String auth0Audience;

    @Value("${auth0.clientId}")
    private String clientId;

    @Value("${auth0.clientSecret}")
    private String clientSecret;

    private String managementApiUrl = "https://dev-a6tntsf5lyicxsfn.us.auth0.com";

    private String GetAccessToken() throws Exception {
        // Get an access token to authenticate with the Management API
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost tokenRequest = new HttpPost(managementApiUrl + "/oauth/token");
        tokenRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        StringEntity tokenRequestBody = new StringEntity(
                "{\"client_id\":\"1tLvX9BZRoDoG0bjPieCW3MAeRQPIyAG\",\"client_secret\":\"rc55GrA-J7_O7OZ5Y6UiWgHNsBbkL1DoA2wDrbo2fM0b7I6cR5rXeKDlmEkIKDrf\",\"audience\":\"https://dev-a6tntsf5lyicxsfn.us.auth0.com/api/v2/\",\"grant_type\":\"client_credentials\"}"
        );

        tokenRequest.setEntity(tokenRequestBody);
        String tokenResponseJson = EntityUtils.toString(httpClient.execute(tokenRequest).getEntity());
        return (new JSONObject(tokenResponseJson)).getString("access_token");
    }

    public void assignRoleToUser(String userId, String roleId) throws Exception {
        try {
            // Get an access token to authenticate with the Management API
            HttpClient httpClient = HttpClients.createDefault();
            String accessToken = this.GetAccessToken();

            // Assign the role to the user using the Management API

            userId = userId.replace("|","%7C");
            HttpPost roleAssignmentRequest = new HttpPost(managementApiUrl + "/api/v2/users/" + userId + "/roles");
            roleAssignmentRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            roleAssignmentRequest.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
            StringEntity roleAssignmentRequestBody = new StringEntity("{\"roles\": [\"" + roleId + "\"]}");
            roleAssignmentRequest.setEntity(roleAssignmentRequestBody);
            httpClient.execute(roleAssignmentRequest);

            System.out.println("Role assigned successfully");
        } catch (Exception e) {
            System.err.println("Error assigning role: " + e.getMessage());
            throw e;
        }
    }

    public boolean deleteAuth0User(String userId) throws Exception{
        try {
            // Get an access token to authenticate with the Management API
            HttpClient httpClient = HttpClients.createDefault();
            String accessToken = this.GetAccessToken();

            userId = userId.replace("|", "%7C");
            HttpDelete userDeleteRequest = new HttpDelete(managementApiUrl + "/api/v2/users/" + userId);
            userDeleteRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            userDeleteRequest.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

            HttpResponse response = httpClient.execute(userDeleteRequest);

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 204) {
                return true;
            } else
                throw new Exception("Error al eliminar en auth0");

        } catch (Exception e) {
        System.err.println("Error al eliminar: " + e.getMessage());
        throw e;
    }
    }

    public User createAuth0User(User user) throws Exception {
        try {
            // Get an access token to authenticate with the Management API
            HttpClient httpClient = HttpClients.createDefault();
            String accessToken = this.GetAccessToken();

            // Assign the role to the user using the Management API

            HttpPost userCreationRequest = new HttpPost(managementApiUrl + "/api/v2/users");
            userCreationRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            userCreationRequest.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
            StringEntity roleAssignmentRequestBody = new StringEntity(
                    "{\"email\": \"" + user.getUserEmail() + "\"," +
                    "\"connection\": \"" + "Username-Password-Authentication" + "\"," +
                    "\"password\": \"" + "Buensa1234" + "\"," +
                    "\"given_name\": \"" + user.getName() + "\"," +
                    "\"family_name\": \"" + user.getLastName() + "\"" +
                    "}");
            userCreationRequest.setEntity(roleAssignmentRequestBody);
            String userResponse = EntityUtils.toString(httpClient.execute(userCreationRequest).getEntity());

            String auth0UserId = (new JSONObject(userResponse)).getString("user_id");
            user.setAuth0Id(auth0UserId);

            assignRoleToUser(auth0UserId,user.getRole().getAuth0RoleId());

            System.out.println("User created successfully");

            return user;

//            {
//                "email": "seba.sulia@gmail.com",
//                    "connection": "Username-Password-Authentication",
//                    "password": "Secret1234"
//            } Datos obligatorios para crear usuario


        } catch (Exception e) {
            System.err.println("Error creating user: " + e.getMessage());
            throw e;
        }
    }

    public String getUserConnectionTypeByAuth0Id(String id) throws Exception{
        // Get an access token to authenticate with the Management API
        HttpClient httpClient = HttpClients.createDefault();
        String accessToken = this.GetAccessToken();


        id = id.replace("|", "%7C");

        HttpGet userGetRequest = new HttpGet(managementApiUrl + "/api/v2/users/"+id);
        userGetRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        userGetRequest.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

        String userResponse = EntityUtils.toString(httpClient.execute(userGetRequest).getEntity());
        JSONArray auth0Identities = (new JSONObject(userResponse)).getJSONArray("identities");
        JSONObject first = auth0Identities.getJSONObject(0);
        String connection = first.getString("connection");

        return connection;
    }

    public User changePassword(User user) throws Exception {
        try {
            // Get an access token to authenticate with the Management API
            HttpClient httpClient = HttpClients.createDefault();
            String accessToken = this.GetAccessToken();

            String userId = user.getAuth0Id();
            userId = userId.replace("|", "%7C");
            String connection = getUserConnectionTypeByAuth0Id(userId);

            if (!connection.equals("Username-Password-Authentication"))
                throw new Exception("No se puede actualizar la contraseña debido al metodo de autenticación que eligió");

            HttpPatch userCreationRequest = new HttpPatch(managementApiUrl + "/api/v2/users/"+userId);
            userCreationRequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            userCreationRequest.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
            StringEntity roleAssignmentRequestBody = new StringEntity(
                    "{" +
                            "\"connection\": \"" + "Username-Password-Authentication" + "\"," +
                            "\"password\": \"" + user.getPassword() + "\"" +
                            "}");
            userCreationRequest.setEntity(roleAssignmentRequestBody);
            HttpResponse userResponse = httpClient.execute(userCreationRequest);
            int statusCode = userResponse.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                return user;
            } else
                throw new Exception("Error actualizar contraseña");

        } catch (Exception e) {
            System.err.println("Error updating password: " + e.getMessage());
            throw e;
        }
    }
}
