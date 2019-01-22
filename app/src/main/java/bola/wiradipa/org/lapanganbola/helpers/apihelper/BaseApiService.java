package bola.wiradipa.org.lapanganbola.helpers.apihelper;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by emrekabir on 2/08/2018.
 */

public interface BaseApiService {

    @FormUrlEncoded
    @POST("players/sign_in")
    Call<ResponseBody> signinRequest(@Field("username") String username,
                                     @Field("password") String password);

    @FormUrlEncoded
    @POST("players/sign_out")
    Call<ResponseBody> logoutRequest(@Field("auth_token") String token);

    @FormUrlEncoded
    @POST("players")
    Call<ResponseBody> signupRequest(@Field("username") String username,
                                     @Field("name") String name,
                                     @Field("email") String email,
                                     @Field("phone_number") String phone_number,
                                     @Field("password") String password,
                                     @Field("password_confirmation") String confirmPassword);

    @FormUrlEncoded
    @POST("players/activate_account")
    Call<ResponseBody> activation(@Field("phone_number") String phone_number,
                                  @Field("activation_number") String activation_number);

    @FormUrlEncoded
    @POST("players/forgot_password")
    Call<ResponseBody> forgotPassword(@Field("phone_number") String phone_number);

    @GET("fields")
    Call<ResponseBody> getFields(@Query("auth_token") String token,
                                 @Query("rental_date") String rent_date,
                                 @Query("start_hour") String start_hour,
                                 @Query("duration") String duration,
                                 @Query("field_owner_id") long field_owner_id,
                                 @Query("city_id") String city_id,
                                 @Query("field_type_id") String field_type_id);

    @GET("field_owners")
    Call<ResponseBody> getFieldOwners(@Query("auth_token") String token,
                                      @Query("rental_date") String rent_date,
                                      @Query("start_hour") String start_hour,
                                      @Query("duration") String duration,
                                      @Query("city_id") String city_id,
                                      @Query("field_type_id") String field_type_id);

    @GET("field_owners/{id}")
    Call<ResponseBody> getFieldOwner(@Path("id") long id,
                                     @Query("auth_token") String token);

    @GET("fields/{id}")
    Call<ResponseBody> getField(@Path("id") long id,
                                @Query("auth_token") String token);

    @FormUrlEncoded
    @POST("field_rentals")
    Call<ResponseBody> createFieldRental(@Field("auth_token") String token,
                                         @Field("rental_date") String rent_date,
                                         @Field("start_hour") int start_hour,
                                         @Field("end_hour") int end_hour,
                                         @Field("field_id") long field_id);

    @GET("areas")
    Call<ResponseBody> getCities(@Query("auth_token") String token);

    @GET("field_types")
    Call<ResponseBody> getFieldTypes(@Query("auth_token") String token);

    @Multipart
    @POST("field_rentals/{id}/upload_receipt")
    Call<ResponseBody> uploadReceiptRent(@Path("id") long id,
                                         @Part("auth_token") RequestBody auth_token,
                                         @Part MultipartBody.Part receipt);

    @GET("players/profile_detail")
    Call<ResponseBody> getPlayerDetail(@Query("auth_token") String token);

    @Multipart
    @PUT("players/{id}")
    Call<ResponseBody> editProfile(@Path("id") long id,
                                   @Part("auth_token") RequestBody auth_token,
                                   @Part MultipartBody.Part avatar);

    @Multipart
    @PUT("players/upload_student_card")
    Call<ResponseBody> uploadCard(@Part("auth_token") RequestBody auth_token,
                                  @Part MultipartBody.Part card);

    @GET("sliders")
    Call<ResponseBody> getSliders();

    @GET("bank_accounts")
    Call<ResponseBody> getBankAccounts();

    @GET("field_rentals")
    Call<ResponseBody> getRentals(@Query("auth_token") String token);

    @GET("player_matches")
    Call<ResponseBody> getMatches(@Query("phone_number") String phone_number);

    @GET("player_matches/{id}")
    Call<ResponseBody> getPlayerMatch(@Path("id") long id,
                                      @Query("phone_number") String phone_number);
}
