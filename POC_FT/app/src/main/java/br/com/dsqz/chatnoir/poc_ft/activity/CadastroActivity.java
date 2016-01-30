package br.com.dsqz.chatnoir.poc_ft.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import br.com.dsqz.chatnoir.poc_ft.dto.Cadastro;
import br.com.dsqz.chatnoir.poc_ft.lib.AppController;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class CadastroActivity extends Activity{

    private final String TAG_JSON_OBJ = getClass().getSimpleName() + "json_obj_req";
    private final String TAG          = getClass().getSimpleName();
    private final Gson   gson         = new Gson();

    private ImageView mIcon1;
    private ImageView mIcon2;
    private ImageView mIcon3;
    private ImageView mIcon4;
    private ImageView mIcon5;
    private ImageView mIcon6;
    private ImageView mIcon7;
    private ImageView mIcon8;
    private EditText  mEditTextNome;
    private EditText  mEditTextEmail;
    private EditText  mEditTextSenha;
    private EditText  mEditTextCPF;
    private EditText  mEditTextTelefone;
    private EditText  mEditTextCelular;
    private EditText  mEditTextNascimento;
    private EditText  mEditTextNonActive5;
    private EditText  mEditTextNonActive6;
    private TextView  mCheckBoxText;
    private CheckBox  mCheckbox;
    private Spinner   mSpinnerSexo;
    private Button    mButtonConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        mIcon1 = (ImageView) findViewById(R.id.cadastro_icon1);
        mIcon2 = (ImageView) findViewById(R.id.cadastro_icon2);
        mIcon3 = (ImageView) findViewById(R.id.cadastro_icon3);
        mIcon4 = (ImageView) findViewById(R.id.cadastro_icon4);
        mIcon5 = (ImageView) findViewById(R.id.cadastro_icon5);
        mIcon6 = (ImageView) findViewById(R.id.cadastro_icon6);
        mIcon7 = (ImageView) findViewById(R.id.cadastro_icon7);
        mIcon8 = (ImageView) findViewById(R.id.cadastro_icon8);
        mEditTextNome = (EditText) findViewById(R.id.editText_nome);
        mEditTextEmail = (EditText) findViewById(R.id.editText_email);
        mEditTextSenha = (EditText) findViewById(R.id.editText_senha);
        mEditTextCPF = (EditText) findViewById(R.id.editText_CPF);
        mEditTextTelefone = (EditText) findViewById(R.id.editText_telefone);
        mEditTextCelular = (EditText) findViewById(R.id.editText_celular);
        mEditTextNascimento = (EditText) findViewById(R.id.editText_nascimento);
        mEditTextNonActive5 = (EditText) findViewById(R.id.non_active_edit_text5);
        mEditTextNonActive6 = (EditText) findViewById(R.id.non_active_edit_text6);
        mSpinnerSexo = (Spinner) findViewById(R.id.spinner_sexo);
        mButtonConfirmar = (Button) findViewById(R.id.button_ConfirmarCadastro);
        mCheckBoxText = (TextView) findViewById(R.id.cadastro_checkboxText);
        mCheckbox = (CheckBox) findViewById(R.id.checkbox_termosUso);

        nomeWatcher(mEditTextNome, mIcon1, R.drawable.ic_nome, R.drawable.ic_nome_check);
        nomeWatcher(mEditTextEmail, mIcon2, R.drawable.ic_email, R.drawable.ic_email_check);
        nomeWatcher(mEditTextSenha, mIcon3, R.drawable.ic_senha, R.drawable.ic_senha_check);
        nomeWatcher(mEditTextCPF, mIcon4, R.drawable.ic_cpf, R.drawable.ic_cpf_check);
        nomeWatcher(mEditTextTelefone, mIcon5, R.drawable.ic_telefone, R.drawable.ic_telefone_check);
        nomeWatcher(mEditTextCelular, mIcon6, R.drawable.ic_celular, R.drawable.ic_celular_check);
        nomeWatcher(mEditTextNascimento, mIcon7, R.drawable.ic_nascimento, R.drawable.ic_nascimento_check);

        setMasksToFields();
        initializeSpinner();
        botaoConfirmarOnClickListener();
        mCheckBoxText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCheckbox.setChecked((!mCheckbox.isChecked()));
            }
        });
    }

    private void botaoConfirmarOnClickListener(){
        mButtonConfirmar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Cadastro cadastro = new Cadastro();
                cadastro.nome = mEditTextNome.getText().toString().trim();
                cadastro.email = mEditTextEmail.getText().toString().trim();
                cadastro.senha = mEditTextSenha.getText().toString().trim();
                cadastro.cpf = mEditTextCPF.getText().toString().trim();
                cadastro.telefone = mEditTextTelefone.getText().toString().trim();
                cadastro.celular = mEditTextCelular.getText().toString().trim();
                cadastro.nascimentoString = mEditTextNascimento.getText().toString().trim();
                cadastro.sexo = mSpinnerSexo.getSelectedItem().toString().equals("Masculino")? "M" :
                                mSpinnerSexo.getSelectedItem().toString().equals("Feminino")? "F" : "";

                final ProgressDialog dialog = ProgressDialog.show(CadastroActivity.this, "", "Loading. Please wait...", true);
                dialog.show();
                new AsyncTask<Cadastro, Void, Void>(){
                    @Override
                    protected Void doInBackground(Cadastro... params){
                        JSONObject jsonBody = null;
                        try{
                            jsonBody = new JSONObject(gson.toJson(params[0]));
                        }catch(JSONException e){
                            Log.e(TAG, e.getMessage(), e);
                        }

                        String url = getString(R.string.cadastro_url);
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
                                                Toast.makeText(getApplicationContext(), "Usuário cadastrado com sucesso", Toast.LENGTH_LONG)
                                                     .show();
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

                }.execute(cadastro);
            }
        });
    }

    private void nomeWatcher(@NonNull EditText view, @NonNull final ImageView icon, @NonNull final int iconDrawable1,
                             @NonNull final int iconDrawableCheck){
        view.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){

            }

            @Override
            public void afterTextChanged(Editable s){
                icon.setImageDrawable((s.toString().isEmpty())? getDrawable(iconDrawable1) : getDrawable(iconDrawableCheck));
            }
        });
    }

    private void setMasksToFields(){
        mEditTextCPF.addTextChangedListener(new MaskEditTextChangedListener("###.###.###-##", mEditTextCPF));
        mEditTextTelefone.addTextChangedListener(new MaskEditTextChangedListener("(##) ####-####", mEditTextTelefone));
        mEditTextCelular.addTextChangedListener(new MaskEditTextChangedListener("(##) #####-####", mEditTextCelular));
        mEditTextNascimento.addTextChangedListener(new MaskEditTextChangedListener("##/##/####", mEditTextNascimento));
    }

    private void initializeSpinner(){
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.sexo_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerSexo.setAdapter(adapter);

        mSpinnerSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                if(position == 0){
                    mEditTextNonActive5.setVisibility(View.VISIBLE);
                    mEditTextNonActive6.setVisibility(View.VISIBLE);
                    mIcon8.setImageDrawable(getDrawable(R.drawable.ic_sexo));
                }else{
                    mEditTextNonActive5.setVisibility(View.INVISIBLE);
                    mEditTextNonActive6.setVisibility(View.INVISIBLE);
                    mIcon8.setImageDrawable(getDrawable(R.drawable.ic_sexo_check));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
                mEditTextNonActive5.setVisibility(View.VISIBLE);
                mEditTextNonActive6.setVisibility(View.VISIBLE);
            }
        });
    }
}
