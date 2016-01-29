package br.com.dsqz.chatnoir.poc_ft.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.dsqz.chatnoir.poc_ft.R;
import br.com.dsqz.chatnoir.poc_ft.dto.Produto;
import br.com.dsqz.chatnoir.poc_ft.lib.AppController;
import br.com.dsqz.chatnoir.poc_ft.lib.ImageSaveLoad;
import br.com.dsqz.chatnoir.poc_ft.lib.ProdutoDeserializer;
import br.com.dsqz.chatnoir.poc_ft.lib.VolleyImgCallback;
import br.com.dsqz.chatnoir.poc_ft.lib.VolleyJsonCallback;
import br.com.dsqz.chatnoir.poc_ft.list.ProdutoAdapter;

public class ProdutoActivity extends Activity{

    private final String TAG          = getClass().getSimpleName();
    private final String TAG_JSON_OBJ = getClass().getSimpleName() + "_PRODUTOS";

    private RecyclerView       mRecyclerViewProdutos;
    private ArrayList<Produto> mProdutos;
    private ProdutoAdapter     mProdutoAdapter;
    private GsonBuilder        mGsonBuilder;
    private Gson               mGson;
    private int                load;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        mGsonBuilder = new GsonBuilder();
        mGsonBuilder.registerTypeAdapter(Produto.class, new ProdutoDeserializer());
        mGson = mGsonBuilder.create();

        mRecyclerViewProdutos = (RecyclerView) findViewById(R.id.recycleView_produtos);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewProdutos.setLayoutManager(layoutManager);

        initializeProdutos();
    }

    private void initializeProdutos(){

        consultarServicoProduto(new VolleyJsonCallback(){

            @Override
            public void onSuccess(final JSONObject response, Object... info){
                try{
                    mProdutos = mGson.fromJson(response.getJSONArray("lista").toString(), new TypeToken<ArrayList<Produto>>(){}.getType());
                    load = mProdutos.size();

                    for(Produto p : mProdutos){
                        baixarFoto(p.id, p.fotos.get(0).id, new VolleyImgCallback(){
                            @Override
                            public void onSuccess(ImageLoader.ImageContainer img, Object... info){

                                for(Produto p : mProdutos){
                                    if(p.id.equals(info[0])){

                                        p.fotos.get(0).localPath = ImageSaveLoad
                                                .saveToInternalSorage(img.getBitmap(), getApplicationContext(),
                                                                      getResources().getString(R.string.image_folder), Context.MODE_PRIVATE,
                                                                      p.fotos.get(0).id + ".png");
                                        load--;
                                        break;
                                    }
                                }
                                if(load == 0){
                                    mProdutoAdapter = new ProdutoAdapter(getApplicationContext(), mProdutos);
                                    mRecyclerViewProdutos.setAdapter(mProdutoAdapter);
                                    mRecyclerViewProdutos.setItemAnimator(new DefaultItemAnimator());
                                    mRecyclerViewProdutos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                }
                            }
                        });
                    }
                }catch(JSONException e){
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        });
    }

    private void baixarFoto(final String produtoid, String fotoId, final VolleyImgCallback callback){

        String url = String.format(getString(R.string.fotos_url), fotoId);

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(url, new ImageLoader.ImageListener(){
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate){
                if(response.getBitmap() != null){
                    callback.onSuccess(response, produtoid);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error){
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
    }

    private void consultarServicoProduto(final VolleyJsonCallback callback){

        String url = getString(R.string.produtos_destaque_url);

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response){
                Log.d(TAG, response.toString());
                try{
                    if(response.getBoolean("sucesso")){
                        callback.onSuccess(response);
                    }else{
                        Toast.makeText(getApplicationContext(), response.getString("mensagem"), Toast.LENGTH_SHORT).show();
                    }
                }catch(JSONException e){
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                HashMap<String, String> headers = new HashMap<>();
                headers.put(getString(R.string.tokenkey), getString(R.string.token));
                headers.put(getString(R.string.Acceptkey), getString(R.string.Accept));
                return headers;
            }

            @Override
            public Priority getPriority(){
                return Priority.NORMAL;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonObjReq, TAG_JSON_OBJ);
    }

}
