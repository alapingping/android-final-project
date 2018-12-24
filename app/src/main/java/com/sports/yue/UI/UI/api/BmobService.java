package com.sports.yue.UI.UI.api;

import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BmobService {

    @Headers({"X-Bmob-Application-Id: 5fad9f2543ffa83e56155a46398d6ede",
            "X-Bmob-REST-API-Key: 918a3c131997a216e99fd565230832f5",})
    @GET("/1/classes/User")
    Call<ResponseBody> getUser(@Query("where") JSONObject username);

    @Headers({"X-Bmob-Application-Id: 5fad9f2543ffa83e56155a46398d6ede",
            "X-Bmob-REST-API-Key: 918a3c131997a216e99fd565230832f5",
            "Content-Type: application/json"})
    @POST("/1/classes/User")
    Call<ResponseBody> postUser(@Body RequestBody body);

    @Headers({"X-Bmob-Application-Id: 5fad9f2543ffa83e56155a46398d6ede",
            "X-Bmob-REST-API-Key: 918a3c131997a216e99fd565230832f5",
            "Content-Type: application/json"})
    @GET("/1/classes/Room")
    Call<ResponseBody> getRoomNum(@Query("count") int Count,@Query("limit") int Limit);

    @Headers({"X-Bmob-Application-Id: 5fad9f2543ffa83e56155a46398d6ede",
            "X-Bmob-REST-API-Key: 918a3c131997a216e99fd565230832f5",
            "Content-Type: application/json"})
    @GET("/1/classes/RoomUser")
    Call<ResponseBody> getRoomUserNum(@Query("count") int Count,@Query("limit") int Limit);

}
