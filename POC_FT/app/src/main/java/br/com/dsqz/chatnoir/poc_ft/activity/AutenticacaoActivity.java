package br.com.dsqz.chatnoir.poc_ft.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alirezaafkar.json.requester.Requester;
import com.alirezaafkar.json.requester.interfaces.ContentType;
import com.alirezaafkar.json.requester.interfaces.Methods;
import com.alirezaafkar.json.requester.interfaces.Response;
import com.alirezaafkar.json.requester.requesters.JsonObjectRequester;
import com.alirezaafkar.json.requester.requesters.RequestBuilder;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.com.dsqz.chatnoir.poc_ft.R;
import br.com.dsqz.chatnoir.poc_ft.dto.Autenticacao;

public class AutenticacaoActivity extends Activity{

    private static final int    REQUEST_CODE = 000001;
    private static final String REQUEST_TAG  = "Autenticar";
    private final        String TAG          = getClass().getSimpleName();
    private final        Gson   gson         = new Gson();

    private Button              mButtonEntrar;
    private JsonObjectRequester mRequester;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacao);

        final JsonObjectListener listener = new JsonObjectListener();

        final Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json; charset=utf-8");
        header.put("token", "7PJGH8DUBUD2Q1A69FOLDN");
        header.put("Accept", "application/json");



        Requester.Config config = new Requester.Config(getApplicationContext());
        config.setHeader(header);
        Requester.init(config);

        mButtonEntrar = (Button) findViewById(R.id.buttonEntrar);

        mRequester = new RequestBuilder(this)
                .requestCode(REQUEST_CODE)
          //      .contentType(ContentType.TYPE_JSON) //or ContentType.TYPE_FORM
                .showError(true).priority(Request.Priority.NORMAL)
                .allowNullResponse(true)
                .tag(REQUEST_TAG)
                .buildObjectRequester(listener);

        mButtonEntrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Autenticacao autenticacao = new Autenticacao();
                autenticacao.email = "teste@fourtime.com";
                autenticacao.senha = "senha";

                JSONObject object = null;
                try{
                    object = new JSONObject(gson.toJson(autenticacao));
                }catch(JSONException e){
                    e.printStackTrace();
                }

                mRequester.request(Methods.POST, "http://api.lojas.club/api/cliente/autenticar", object);
                //mRequester.request(Methods.GET,"http://api.lojas.club/api/cliente/esqueci/teste@fourtime.com");

            }
        });


    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(isFinishing()){
            mRequester.setCallback(null);
        }
    }

    private class JsonObjectListener extends Response.SimpleObjectResponse{
        @Override
        public void onResponse(int requestCode, @Nullable JSONObject jsonObject){
            //Ok
            Log.i(TAG, String.valueOf(jsonObject.toString()));
        }

        @Override
        public void onErrorResponse(int requestCode, VolleyError volleyError, @Nullable JSONObject errorObject){
            //Error (Not server or network error)
            Log.i(TAG, String.valueOf(errorObject.toString()));
        }

        @Override
        public void onFinishResponse(int requestCode, VolleyError volleyError, String message){
            //Network or Server error
            Log.i(TAG, message);
        }

        @Override
        public void onRequestStart(int requestCode){
            //Show loading or disable button
            Log.i(TAG, "Request start..." + requestCode);
        }

        @Override
        public void onRequestFinish(int requestCode){
            //Hide loading or enable button
            Log.i(TAG, "Request finish..." + requestCode);
        }
    }
}
