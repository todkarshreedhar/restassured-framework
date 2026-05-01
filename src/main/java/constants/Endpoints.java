package constants;

public class Endpoints {


    public static final String BASE_USER_URL = "/api/users";

    // 🔹 Static endpoints
    public static String getUsers(){
        return BASE_USER_URL;
    }
    public static String createUser(){
        return BASE_USER_URL;
    }
    public static String getUserById(int id){
        return BASE_USER_URL + "/" + id;
    }
    // 🔹 Dynamic endpoints
    public static String updateUser(int id){
        return BASE_USER_URL + "/" + id;
    }
    public static String updatePartialUser(int id){
        return BASE_USER_URL + "/" + id;
    }
    public static String deleteUser(int id){
        return BASE_USER_URL + "/" + id;
    }


}
