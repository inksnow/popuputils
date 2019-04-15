package com.inks.inkslibrary.Utils;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * 获取布局id
 * GetResId.getId(this, "layout", "activity_main")
 *
 * 获取控件id
 *  GetResId.getId(this, "id", "up")
 */

public class GetResId {
    public static int getId(Context context, String paramString1, String paramString2) {
        return context.getResources().getIdentifier(paramString2, paramString1, context.getPackageName());
    }

    /**
     * 获取 string 值
     *
     * @param context
     *            Context
     * @param resName
     *            string name的名称
     * @return string
     */
    public static int getStringId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "string",
                context.getPackageName());
    }

    /**
     * 获取 drawable 布局文件 或者 图片的
     *
     * @param context
     *            Context
     * @param resName
     *            drawable 的名称
     * @return drawable
     */
    public static int getDrawableId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "drawable",
                context.getPackageName());
    }

    /**
     * 获取 style
     *
     * @param context
     *            Context
     * @param resName
     *            style的名称
     * @return style
     */
    public static int getStyleId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "style",
                context.getPackageName());
    }
    /**
     * 获取 anim
     *
     * @param context
     *            Context
     * @param resName
     *            anim xml 文件名称
     * @return anim
     */
    public static int getAnimId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "anim",
                context.getPackageName());
    }
    /**
     * color
     *
     * @param context
     *            Context
     * @param resName
     *            color 名称
     * @return
     */
    public static int getColorId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "color",
                context.getPackageName());
    }

    private static Object getResourceId(Context context, String name, String type) {
        String className = context.getPackageName() + ".R";
        try {
            Class<?> cls = Class.forName(className);
            for (Class<?> childClass : cls.getClasses()) {
                String simple = childClass.getSimpleName();
                if (simple.equals(type)) {
                    for (Field field : childClass.getFields()) {
                        String fieldName = field.getName();
                        if (fieldName.equals(name)) {
                            System.out.println(fieldName);
                            return field.get(null);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}