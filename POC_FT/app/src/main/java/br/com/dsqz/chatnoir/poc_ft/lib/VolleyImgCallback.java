package br.com.dsqz.chatnoir.poc_ft.lib;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by diogosq on 1/29/16.
 */
public interface VolleyImgCallback{
    void onSuccess(ImageLoader.ImageContainer img, Object... info);
}

