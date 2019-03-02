package com.icebroken.api;


import com.icebroken.bean.AddressBean;
import com.icebroken.bean.CarBean;
import com.icebroken.bean.DepBean;
import com.icebroken.bean.DepPeronBean;
import com.icebroken.bean.InstituteBean;
import com.icebroken.bean.LnSignInBean;
import com.icebroken.bean.MeetRoomBean;
import com.icebroken.bean.MeetRoomEQBean;
import com.icebroken.bean.MeetingBean;
import com.icebroken.bean.NotiBean;
import com.icebroken.bean.NotiInfoBean;
import com.icebroken.bean.PeopleBean;
import com.icebroken.bean.ScheduleBean;
import com.icebroken.bean.CityAddressBean;
import com.icebroken.bean.SchoolsBean;
import com.icebroken.bean.TaskBean;
import com.icebroken.bean.UserBean;
import com.icebroken.bean.UserInfo;
import com.icebroken.bean.accountExistBean;
import com.mocuz.common.basebean.BaseRespose;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * des:ApiService
 */
public interface ApiService {

    @POST("user/login")
    Observable<BaseRespose<accountExistBean>> login(@Body String body);

    @POST("user/accountExist")
    Observable<BaseRespose<accountExistBean>> accountExist(@Body String body);

    @POST("user/register")
    Observable<BaseRespose<accountExistBean>> register(@Body String body);

    @POST("user/completeInformation")
    Observable<BaseRespose<Object>> completeInformation(@Body String body);

    @POST("user/applyStudent")
    Observable<BaseRespose<Object>> applyStudent(@Body String body);

    @POST("resource/img/upload")
    Observable<BaseRespose<String>> upload(@Body String body);

    @POST("sms/send")
    Observable<BaseRespose<Object>> getcode(@Body String body);

    @POST("sms/check")
    Observable<BaseRespose<Object>> check(@Body String body);

    @GET("user/userInfo")
    Observable<BaseRespose<UserInfo>> getUserInfo();

    @POST("webapp")
    Observable<BaseRespose<Object>> postMeet(@Body String body);

    @POST("webapp")
    Observable<BaseRespose<List<DepBean>>> queryDep(@Body String body);

    @POST("webapp")
    Observable<BaseRespose<List<CarBean>>> queryCar(@Body String body);

    @POST("webapp")
    Observable<BaseRespose<List<MeetRoomBean>>> queryMeetroom(@Body String body);

    @POST("webapp")
    Observable<BaseRespose<List<MeetRoomEQBean>>> queryMeetroomEQ(@Body String body);

    @POST("webapp")
    Observable<BaseRespose<List<DepPeronBean>>> queryDep_person(@Body String body);

    @POST("webapp")
    Observable<BaseRespose<List<PeopleBean>>> queryPeople(@Body String body);

    @POST("webapp")
    Observable<BaseRespose<UserBean>> queryUserInfo(@Body String body);

    @POST("webapp")
    Observable<BaseRespose<UserBean>> queryCode(@Body String body);

    @POST("webapp")
    Observable<BaseRespose<Object>> editPwd(@Body String body);

    @POST("webapp")
    Observable<BaseRespose<List<NotiBean>>> queryNoti(@Body String body);

    @POST("webapp")
    Observable<BaseRespose<List<NotiInfoBean>>> queryNotiInfo(@Body String body);

    @POST("webapp")
    Observable<BaseRespose<List<ScheduleBean>>> queryScheduleList(@Body String body);

    @POST("webapp")
    Observable<BaseRespose<List<MeetingBean>>> queryMeettingList(@Body String body);

    @POST("webapp")
    Observable<BaseRespose<List<TaskBean>>> queryTaskList(@Body String body);

    @POST("webapp")
    Observable<BaseRespose<List<AddressBean>>> queryAddressList(@Body String body);

    @POST("webapp")
    Observable<BaseRespose<Object>> signin(@Body String body);

    @POST("webapp")
    Observable<BaseRespose<Object>> approve(@Body String body);

    @POST("webapp")
    Observable<BaseRespose<Object>> postScheduleList(@Body String body);

    @POST("webapp")
    Observable<BaseRespose<List<LnSignInBean>>> querySignInList(@Body String body);

    @GET("region/{code}/{type}")
    Observable<BaseRespose<List<CityAddressBean>>> selectAddress(@Path("code") int code, @Path("type") int type);

    @GET("school/{cityId}")
    Observable<BaseRespose<List<SchoolsBean>>> selectSchool(@Path("cityId") String cityId);

    @GET("school/department/{schoolId}")
    Observable<BaseRespose<List<InstituteBean>>> selectInstitute(@Path("schoolId") int schoolId);

    @GET("qiniu/token")
    Observable<BaseRespose<String>> qiniuToken();
}
