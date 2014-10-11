package com.cartella;

import android.content.res.Resources;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by bluejack on 8/27/14.
 */
public class Utils {
    public static Class getClass(String className)
            throws ClassNotFoundException {
        // final String apkFiles ="/data/app/com.lottomatica.gev.apk";
        //
        // dalvik.system.PathClassLoader myClassLoader =
        // new dalvik.system.PathClassLoader(
        // apkFiles,
        // ClassLoader.getSystemClassLoader());

        try {
            // return Class.forName(fragmentClassName, true,
            // ClassLoader.getSystemClassLoader());

            // Call reflective APIs.
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            Utils.log("ClassNotFoundException\t" + className);
            return null;
        }
    }
    public static int getResource(String name, Class r, String subclassName) {
        // int ret =
        // resources.getIdentifier(name,"drawable","com.lottomatica.gev.R");

        // try {
        // Field idField = c.getDeclaredField(variableName);
        // return idField.getInt(idField);
        // } catch (Exception e) {
        // e.printStackTrace();
        // return -1;
        // }
        int ret = 0;
        try {
            // Class res = R.drawable.class;
            Field field = Utils.getClass(
                    r.getCanonicalName() + "$" + subclassName).getField(name);
            ret = field.getInt(null);
        } catch (Exception e) {
            log("Failure to get drawable id:" + name);
            e.printStackTrace();
        }
        // log("getDrawableResource\t"+name+"->"+ret);
        return ret;
    }
    public static int getDrawableResource(String name, Class r) {
        return getResource(name, r, "drawable");
    }

    public static void log(String message) {
        Log(new Utils(), message);
    }

    public static <T>void Log(Object obj, List<T> list)
    {
        for(T item : list)
            Log(obj,item);
    }
    public static void Log(Object obj, int[] array)
    {
        for(int i=0; i<array.length; i++)
            Log(obj,""+array[i]);
    }
    public static <T>void Log(Object obj, T[] array)
    {
        for(T item : array)
            Log(obj,item);
    }

    public static void Log(Object obj, String message) {
        // if(isDebug())
        Log.v(obj.getClass().getSimpleName(), message);
    }

    public static void Log(Object obj, Object message) {
        // if(isDebug())
        Log.v(obj.getClass().getSimpleName(), message.toString());
    }

}
