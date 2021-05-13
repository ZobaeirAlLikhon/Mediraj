package com.example.mediraj.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mediraj.webapi.ApiInterface;

public class SessionManager {
    public static final String PREF_NAME = "Mediraj";
    public static final int MODE = Context.MODE_PRIVATE;


    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }


    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static void logout(final Context context, ApiInterface apiInterface) {
//        final Activity activity = (Activity) context;
//        DataManager.getInstance().showProgressMessage(activity, "Please wait...");
//        Map<String, String> map = new HashMap<>();
//        map.put("android_token", FirebaseInstanceId.getInstance().getToken());
//        map.put("ios_token", "");
//        map.put("user_id",DataManager.getInstance().getUserData(activity).userinfo.id);
//        Log.e("MapMap", "LOGOUT REQUEST" + map);
//        Call<Map<String,String>> logoutCall = apiInterface.logout(Constant.AUTH, map);
//        logoutCall.enqueue(new Callback<Map<String,String>>() {
//            @Override
//            public void onResponse(Call<Map<String,String>> call, Response<Map<String,String>> response) {
//                DataManager.getInstance().hideProgressMessage();
//                try {
//                    Map<String,String> data = response.body();
//                    if (data.get("success").equals("1")) {
//                        getEditor(context).clear().commit();
//                        if (context instanceof Activity) {
//                            context.startActivity(new Intent(activity, SplashActivity.class));
//                            activity.finish();
//                        }
//
//                    } else if (data.get("success").equals("0")) {
//
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Map<String,String>> call, Throwable t) {
//                call.cancel();
//                DataManager.getInstance().hideProgressMessage();
//            }
//        });
    }

    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();

    }

    public static boolean readBoolean(Context context, String key, boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

}
