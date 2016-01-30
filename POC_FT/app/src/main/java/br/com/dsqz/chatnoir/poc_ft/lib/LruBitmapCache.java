package br.com.dsqz.chatnoir.poc_ft.lib;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by diogosq on 1/27/16.
 */

@SuppressWarnings ("WeakerAccess")
public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache{
    public LruBitmapCache(){
        this(getDefaultLruCacheSize());
    }

    public LruBitmapCache(int sizeInKiloBytes){
        super(sizeInKiloBytes);
    }

    public static int getDefaultLruCacheSize(){
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //noinspection UnnecessaryLocalVariable
        final int cacheSize = maxMemory / 8;

        return cacheSize;
    }

    @Override
    protected int sizeOf(String key, Bitmap value){
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url){
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap){
        put(url, bitmap);
    }
}
