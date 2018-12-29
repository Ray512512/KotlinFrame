package com.moodsmap.waterlogging.presentation.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;
import android.util.Base64;

import com.moodsmap.waterlogging.App;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ray on 2016/3/27.
 */
public class SPUtils {
    private static final String TAG = "SPUtils";
    private static final String KEY = App.instance.getPackageName() + ".spkey";
    private static  String KEY_USER =KEY;
    private static SharedPreferences mPreferences;

    private static SharedPreferences getmPreferences() {
        if (mPreferences == null) {
            mPreferences = App.instance.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        }
        return mPreferences;
    }

    public static SharedPreferences getCommonSp() {
        return App.instance.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }

    public static void setKeyUser(int memId){
        if(memId==0) {
            KEY_USER=KEY;
        } else {
            KEY_USER=KEY+memId;
        }
        Lg.v(TAG,"userSpKey："+KEY_USER);
        mPreferences = App.instance.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
    }

    /**
     * 存入某个key对应的value值
     *
     * @param key
     * @param value
     */
    public static void put(String key, Object value) {
        SharedPreferences sp = getmPreferences();
        SharedPreferences.Editor edit = sp.edit();
        if (value instanceof String) {
            edit.putString(key, (String) value);
        } else if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            edit.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            edit.putLong(key, (Long) value);
        } else if (value instanceof Serializable) {
            setObject(key, value,false);
            return;
        }
        SharedPreferencesCompat.apply(edit);
    }
    public static void put(String key, Object value,SharedPreferences sp) {
        SharedPreferences.Editor edit = sp.edit();
        if (value instanceof String) {
            edit.putString(key, (String) value);
        } else if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            edit.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            edit.putLong(key, (Long) value);
        } else if (value instanceof Serializable) {
            setObject(key, value,sp);
            return;
        }
        SharedPreferencesCompat.apply(edit);
    }

    /**
     * 得到某个key对应的值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static Object get(String key, Object defValue) {
        SharedPreferences sp = getmPreferences();
        if (defValue instanceof String) {
            return sp.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            return sp.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            return sp.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            return sp.getLong(key, (Long) defValue);
        } else {
            return getObject(key,defValue);
        }
    }
    public static Object get(String key, Object defValue,SharedPreferences sp) {
        if (defValue instanceof String) {
            return sp.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            return sp.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            return sp.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            return sp.getLong(key, (Long) defValue);
        } else {
            return getObject(key,defValue,sp);
        }
    }

    public static void setObject(String key, Object o,boolean isSys){
        if(isSys){
            setObject(key,o);
        }else {
            new Thread(() -> setObject(key,o)).start();
        }
    }
    public static void setObject(String key, Object object) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream out = null;
                try {
                    out = new ObjectOutputStream(baos);
                    out.writeObject(object);
                    String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
                    SharedPreferences.Editor editor = getmPreferences().edit();
                    editor.putString(key, objectVal);
                    editor.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                    uploadCrash(e);
                } finally {
                    try {
                        if (baos != null) {
                            baos.close();
                        }
                        if (out != null) {
                            out.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
    }

    public static void setObject(String key, Object object,SharedPreferences sp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, objectVal);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
            uploadCrash(e);
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                uploadCrash(e);
            }
        }
    }

    private static <T> T getObject(String key,T defaultValue) {
        if (getmPreferences().contains(key)) {
            String objectVal = getmPreferences().getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
                uploadCrash(e);
            } catch (IOException e) {
                e.printStackTrace();
                uploadCrash(e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                uploadCrash(e);
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    uploadCrash(e);
                }
            }
        }
        return defaultValue;
    }

    private static <T> T getObject(String key,T defaultValue,SharedPreferences sp) {
        if (sp.contains(key)) {
            String objectVal = sp.getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
                uploadCrash(e);
            } catch (IOException e) {
                e.printStackTrace();
                uploadCrash(e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                uploadCrash(e);
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    uploadCrash(e);
                }
            }
        }
        return defaultValue;
    }

    private static void uploadCrash(Exception e){
//        CrashReport.postCatchedException(e);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}
