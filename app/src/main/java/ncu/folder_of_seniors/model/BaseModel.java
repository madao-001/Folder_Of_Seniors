package ncu.folder_of_seniors.model;

import android.content.Context;

import ncu.folder_of_seniors.app.MyApplication;

public abstract class BaseModel {

    public int CODE_NULL=1000;
    public static int CODE_NOT_EQUAL=1001;

    public static final int DEFAULT_LIMIT=20;

    public Context getContext(){
        return MyApplication.getClientAppContext();
    }
}
