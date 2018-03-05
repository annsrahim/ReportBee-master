package com.technocarrot.service;

import com.technocarrot.beans.TokenBean;
import com.technocarrot.beans.UserInfoBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Anns on 08/02/18.
 */

public interface APIInterface {
    @FormUrlEncoded
    @POST("oauth/token")
    Call<TokenBean> doLoginCheck(@Field("grant_type") String grant_type,
                                        @Field("client_id") String client_id,
                                        @Field("client_secret") String client_secret,
                                        @Field("username") String user_name,
                                        @Field("password") String password,
                                        @Field("scope") String scope
    );

    @FormUrlEncoded
    @POST("api/v1/user/register")
    Call<TokenBean> doRegisterUser(@Field("name") String name,
                                   @Field("email") String email,
                                   @Field("password") String password,
                                   @Field("password_confirmation") String password_confirmation,
                                   @Field("role") String role

    );

    @GET("api/v1/user")
    Call<UserInfoBean> doGetUserInfo();


}
