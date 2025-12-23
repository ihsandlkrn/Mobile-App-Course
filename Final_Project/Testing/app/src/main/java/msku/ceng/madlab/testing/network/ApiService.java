package msku.ceng.madlab.testing.network;

import java.util.List;
import msku.ceng.madlab.testing.model.AidRequest;
import msku.ceng.madlab.testing.model.LoginRequest;
import msku.ceng.madlab.testing.model.RegisterRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Interface defining the REST API Endpoints.
 * Retrofit uses this interface to generate network calls.
 */
public interface ApiService {

    @POST("/api/auth/login")
    Call<Void> loginUser(@Body LoginRequest loginRequest);

    @POST("/api/aid/create")
    Call<Void> submitAidRequest(@Body AidRequest aidRequest);

    @POST("/api/auth/register")
    Call<Void> registerUser(@Body RegisterRequest registerRequest);



    @GET("/api/aid/list")
    Call<List<AidRequest>> getAllRequests();
}