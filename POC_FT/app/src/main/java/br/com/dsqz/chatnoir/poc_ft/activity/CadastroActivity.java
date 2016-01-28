package br.com.dsqz.chatnoir.poc_ft.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import br.com.dsqz.chatnoir.poc_ft.R;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class CadastroActivity extends Activity{

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

        nomeWatcher(mEditTextNome, mIcon1, R.drawable.ic_nome, R.drawable.ic_nome_check);
        nomeWatcher(mEditTextEmail, mIcon2, R.drawable.ic_email, R.drawable.ic_email_check);
        nomeWatcher(mEditTextSenha, mIcon3, R.drawable.ic_senha, R.drawable.ic_senha_check);
        nomeWatcher(mEditTextCPF, mIcon4, R.drawable.ic_cpf, R.drawable.ic_cpf_check);
        nomeWatcher(mEditTextTelefone, mIcon5, R.drawable.ic_telefone, R.drawable.ic_telefone_check);
        nomeWatcher(mEditTextCelular, mIcon6, R.drawable.ic_celular, R.drawable.ic_celular);
        nomeWatcher(mEditTextNascimento, mIcon7, R.drawable.ic_nascimento, R.drawable.ic_nascimento_check);

        setMasksToFields();
        initializeSpinner();
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
