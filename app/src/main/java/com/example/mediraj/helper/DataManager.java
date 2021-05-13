package com.example.mediraj.helper;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.mediraj.R;
import com.example.mediraj.model.UserData;
import com.google.gson.Gson;


public class DataManager {

    private static final DataManager ourInstance = new DataManager();

    public static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {
    }

    Dialog mDialog;
    private boolean isProgressDialogRunning = false;
    ProgressBar progressBar;
    TextView textView;

    public void showProgressMessage(Activity activity, String msg){

        try {
            if (isProgressDialogRunning) {
                hideProgressMessage();
            }
            isProgressDialogRunning = true;
            mDialog = new Dialog(activity);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.custom_progress);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            textView = mDialog.findViewById(R.id.progressText);
            textView.setText(msg);
            WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
            lp.dimAmount = 0.8f;
            mDialog.getWindow().setAttributes(lp);
            mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void hideProgressMessage(){
        isProgressDialogRunning = true;
        try {
            if (mDialog != null) {
                mDialog.dismiss();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public UserData getUserData(Context context) {
        UserData userData = new Gson().fromJson(SessionManager.readString(context, Constant.USER_INFO, ""), UserData.class);
        return userData;
    }
}
