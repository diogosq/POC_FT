package br.com.dsqz.chatnoir.poc_ft.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.com.dsqz.chatnoir.poc_ft.R;
import br.com.dsqz.chatnoir.poc_ft.dto.Autenticacao;
import br.com.dsqz.chatnoir.poc_ft.dto.Usuario;
import br.com.dsqz.chatnoir.poc_ft.lib.AppController;

public class AutenticacaoActivity extends Activity{

    private final String TAG_JSON_OBJ = getClass().getSimpleName() + "json_obj_req";
    private final String TAG          = getClass().getSimpleName();
    private final Gson   gson         = new Gson();

    private Button   mButtonEntrar;
    private EditText mEditTextUsuario;
    private EditText mEditTextSenha;
    private TextView mCadastro;
    private TextView mEsqueci;
    private Usuario  mUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacao);

        mButtonEntrar = (Button) findViewById(R.id.buttonEntrar);
        mEditTextUsuario = (EditText) findViewById(R.id.autenticacao_editTextUsuario);
        mEditTextSenha = (EditText) findViewById(R.id.autenticacao_editTextSenha);
        mCadastro = (TextView) findViewById(R.id.autenticacao_textViewCadastrar);
        mEsqueci = (TextView) findViewById(R.id.autenticacao_textViewEsqueci);

        cadastroOnclickListener();
        esqueciSenhaOnClickListener();
        entrarClickListener();
    }

    private void cadastroOnclickListener(){
        mCadastro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(AutenticacaoActivity.this, CadastroActivity.class);
                startActivity(i);
            }
        });
    }

    private void esqueciSenhaOnClickListener(){
        mEsqueci.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(AutenticacaoActivity.this, EsqueciMinhaSenhaActivity.class);
                startActivity(i);
            }
        });
    }

    private void entrarClickListener(){
        mButtonEntrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                final ProgressDialog dialog = ProgressDialog.show(AutenticacaoActivity.this, "", "Loading. Please wait...", true);
                dialog.show();

                new AsyncTask<String, Void, Void>(){

                    @Override
                    protected Void doInBackground(String... params){

                        Autenticacao autenticacao = new Autenticacao();
                        autenticacao.email = params[0];
                        autenticacao.senha = params[1];

                        JSONObject jsonBody = null;
                        try{
                            jsonBody = new JSONObject(gson.toJson(autenticacao));
                        }catch(JSONException e){
                            Log.e(TAG, e.getMessage(), e);
                        }

                        String url = getString(R.string.autenticacao_url);

                        final JsonObjectRequest jsonObjReq =
                                new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>(){

                                    @Override
                                    public void onResponse(JSONObject response){
                                        if(dialog.isShowing()){
                                            dialog.dismiss();
                                        }

                                        Log.d(TAG, response.toString());
                                        try{
                                            if(response.getBoolean("sucesso")){
                                                mUsuario = new Gson().fromJson(response.getJSONObject("dados").toString(), Usuario.class);
                                                Intent i = new Intent(AutenticacaoActivity.this, ProdutoActivity.class);
                                                startActivity(i);
                                            }else{
                                                Toast.makeText(getApplicationContext(), response.getString("mensagem"), Toast.LENGTH_SHORT)
                                                     .show();
                                            }
                                        }catch(JSONException e){
                                            Log.e(TAG, e.getMessage(), e);
                                        }
                                    }
                                }, new Response.ErrorListener(){

                                    @Override
                                    public void onErrorResponse(VolleyError error){
                                        if(dialog.isShowing()){
                                            dialog.dismiss();
                                        }

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
                                        return Priority.IMMEDIATE;
                                    }
                                };
                        AppController.getInstance().addToRequestQueue(jsonObjReq, TAG_JSON_OBJ);
                        return null;
                    }

                }.execute(mEditTextUsuario.getText().toString().trim(), mEditTextSenha.getText().toString());

            }
        });
    }


}
