package com.sunmi.wallpaperservice.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 定制 壁纸测试类
 *
 * @author sm2886
 */
public class CustomWallpaperFile {
    private static final String PATH_LIGHT = "/media/wallerpaper/light";
    private static final String PATH_DARK = "/media/wallerpaper/dark";
    private static final String PATH_LIGHT_SMALL = "/media/wallerpaper/light_small";
    private static final String PATH_DARK_SMALL = "/media/wallerpaper/dark_small";
    private static final String ROOT_PATH_CUSTOM = "/smconf/customer";
    private static final String ROOT_PATH_LOCAL = "/system_ext";
    private static final String RO_SM = "ro.sm.confroot";
    private static final String RO_SYS = "ro.sm.system.confroot";

    /**********************************************************************************************/
    public static List<String> getImageList(List<String> imageList, int isLightOrDark) {
        String filePathAbsolute = SystemPropertyUtil.getInstance().getProperty(RO_SM, ROOT_PATH_CUSTOM);
        File file = new File(filePathAbsolute + PATH_LIGHT);
        if (!file.exists()) {
            filePathAbsolute = SystemPropertyUtil.getInstance().getProperty(RO_SYS, ROOT_PATH_LOCAL);
            file = new File(filePathAbsolute + PATH_LIGHT);
        }
        if (file.exists()) {
            boolean currentMode = (isLightOrDark != 1);
            String wallpaperPath = filePathAbsolute + (currentMode ? PATH_DARK : PATH_LIGHT);
            File wallpaperDirectory = new File(wallpaperPath);
            if (wallpaperDirectory.exists() && wallpaperDirectory.isDirectory() && wallpaperDirectory.canRead()) {
                File[] wallpaperFiles = wallpaperDirectory.listFiles();
                if (wallpaperFiles != null) {
                    imageList.clear();
                    for (File imageFile : wallpaperFiles) {
                        if (imageFile.isFile() && imageFile.canRead()) {
                            imageList.add(imageFile.getAbsolutePath());
                        }
                    }
                }
            }
        }
        Collections.sort(imageList);
        return imageList;
    }

    public static List<Bitmap> convertFilePathsToBitmaps(List<String> filePaths) {
        List<Bitmap> bitmaps = new ArrayList<>();
        for (String filePath : filePaths) {
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            if (bitmap != null) {
                bitmaps.add(bitmap);
            }
        }
        return bitmaps;
    }
}

