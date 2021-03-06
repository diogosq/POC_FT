package br.com.dsqz.chatnoir.poc_ft.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import br.com.dsqz.chatnoir.poc_ft.dto.Existe;
import br.com.dsqz.chatnoir.poc_ft.lib.AppController;

public class EsqueciMinhaSenhaActivity extends Activity{

    private final String TAG_JSON_OBJ = getClass().getSimpleName() + "json_obj_req";
    private final String TAG          = getClass().getSimpleName();

    private Button   mButtonEnviar;
    private EditText mEditTextEmail;
    private Existe   mExiste;
    private Dialog   mCustomDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_minha_senha);

        mButtonEnviar = (Button) findViewById(R.id.esqueciSenha_buttonEnviar);
        mEditTextEmail = (EditText) findViewById(R.id.esqueciSenha_editTextEmail);

        customDialogInit();

        buttonEnviarOnClickListener();
    }

    private void customDialogInit(){

        mCustomDialog = new Dialog(this);
        mCustomDialog.setCancelable(false);
        mCustomDialog.setCanceledOnTouchOutside(false);
        mCustomDialog.setContentView(R.layout.custom_dialog_enviei_senha);
        Button dialogButton = (Button) mCustomDialog.findViewById(R.id.esqueciSenha_buttonOk);
        dialogButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }

    private void buttonEnviarOnClickListener(){
        mButtonEnviar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                final ProgressDialog dialog = ProgressDialog.show(EsqueciMinhaSenhaActivity.this, "", "Loading. Please wait...", true);
                dialog.show();
                new AsyncTask<String, Void, Void>(){

                    @Override
                    protected Void doInBackground(String... params){


                        String url = getString(R.string.esqueci_senha_url).concat(params[0].trim());

                        final JsonObjectRequest jsonObjReq =
                                new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>(){

                                    
                                    @Override
                                    public void onResponse(JSONObject response){
                                        if(dialog.isShowing()){
                                            dialog.dismiss();
                                        }

                                        Log.d(TAG, response.toString());
                                        try{
                                            if(response.getBoolean("sucesso")){

                                                mExiste = new Gson().fromJson(response.getJSONObject("dados").toString(), Existe.class);
                                                if(mExiste.existe){
                                                    mEditTextEmail.setVisibility(View.INVISIBLE);
                                                    mCustomDialog.show();
                                                }else{
                                                    Toast.makeText(getApplicationContext(), "Usuário não existe", Toast.LENGTH_SHORT)
                                                         .show();
                                                }
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
                                        return Priority.NORMAL;
                                    }
                                };
                        AppController.getInstance().addToRequestQueue(jsonObjReq, TAG_JSON_OBJ);
                        return null;
                    }
                }.execute(mEditTextEmail.getText().toString());
            }
        });
    }
}
