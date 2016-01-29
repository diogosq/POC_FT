package br.com.dsqz.chatnoir.poc_ft.lib;

import org.json.JSONObject;

/**
 * Created by diogosq on 1/29/16.
 */
public interface VolleyJsonCallback{
    void onSuccess(JSONObject string, Object... info);

    void onError();
}
