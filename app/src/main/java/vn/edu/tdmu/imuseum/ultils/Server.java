package vn.edu.tdmu.imuseum.ultils;

/**
 * Created by nvulinh on 8/25/17.
 *
 */

public class Server {
    //public final static String LOCALHOST = "http://192.168.1.8:8080";
    public final static String LOCALHOST = "http://113.161.163.147:9001";
    public final static String ARTIFACT = LOCALHOST + "/api/artifact";
    public final static String GET_ARTIFACT_BY_NEAR_BEACON = LOCALHOST + "/api/beacon/";
    public final static String ADD_POSFIX_ARTIFACT = "/artifact";
    public final static String surveyAPI_POST = LOCALHOST + "/api/survey";

    public final static String DATABASE_NAME = "imuseum.sqlite";
}
