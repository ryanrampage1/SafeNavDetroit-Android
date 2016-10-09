package com.safenavdetroit.safenavdetroit.Network;

import java.util.ArrayList;

/**
 * Created by ryancasler on 10/8/16
 * SafeNavDetroit
 */

public class GsonClasses {
    public static class OauthResponse {
        public String access_token;
        public int expires_in;
    }

    public static class OauthRequest {
        public String client_id;
        public String client_secret;
        public String grant_type;

        public OauthRequest(String client_id, String client_secret, String grant_type) {
            this.client_id = client_id;
            this.client_secret = client_secret;
            this.grant_type = grant_type;
        }
    }

    public static class Wraper {
        public ArrayList<AddressResult> results;
    }

    public static class AddressResult {
        public String address;
        public Loc location;
        public float score;
    }

    public static class Loc {
        public float x;
        public float y;
    }

    public static class CoordinateResponse {
        public ArrayList<Coordinate> coordinates;
    }

    public static class Coordinate {
        public float latitude;
        public float longitude;
    }
}