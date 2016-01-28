package br.com.dsqz.chatnoir.poc_ft.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import br.com.dsqz.chatnoir.poc_ft.R;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class CadastroActivity extends Activity{

    private EditText mEditTextCPF;
    private EditText mEditTextTelefone;
    private EditText mEditTextCelular;
    private EditText mEditTextNascimento;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        mEditTextCPF = (EditText) findViewById(R.id.editTextCPF);
        mEditTextTelefone = (EditText) findViewById(R.id.editText_telefone);
        mEditTextCelular = (EditText) findViewById(R.id.editText_celular);
        mEditTextNascimento = (EditText) findViewById(R.id.editText_nascimento);

        mEditTextCPF.addTextChangedListener(new MaskEditTextChangedListener("###.###.###-##", mEditTextCPF));
        mEditTextTelefone.addTextChangedListener(new MaskEditTextChangedListener("(##) ####-####", mEditTextTelefone));
        mEditTextCelular.addTextChangedListener(new MaskEditTextChangedListener("(##) #####-####", mEditTextCelular));
        mEditTextNascimento.addTextChangedListener(new MaskEditTextChangedListener("##/##/####", mEditTextNascimento));
    }
}
