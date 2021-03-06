package br.com.dsqz.chatnoir.poc_ft.lib;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by diogosq on 1/28/16.
 */
@SuppressWarnings ("SameParameterValue")
public class ImageSaveLoad{

    private static final String TAG = "ImageSaveLoad";

    /**
     * @param bitmapImage image
     * @param context     context
     * @param imageDir    dir name
     * @param mode        ex: Context.MODE_PRIVATE
     * @param imageName   name.type
     * @return path of image
     */
    @SuppressWarnings ("SameParameterValue")
    public static String saveToInternalSorage(Bitmap bitmapImage, Context context, String imageDir, int mode, String imageName){

        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir(imageDir, mode);
        File mypath = new File(directory, imageName);

        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(fos != null) fos.close();
            }catch(IOException e){
                Log.e(TAG, e.getMessage(), e);
            }
        }
        return directory.getAbsolutePath();
    }

    /**
     * @param imageName image name
     * @param imagePath path
     * @return bitmap
     */
    public static Bitmap loadImage(String imagePath, String imageName){
        Bitmap image = null;
        try{
            image = BitmapFactory.decodeStream(new FileInputStream(new File(imagePath, imageName)));
        }catch(FileNotFoundException e){
            Log.e(TAG, e.getMessage(), e);
        }
        return image;
    }
}
