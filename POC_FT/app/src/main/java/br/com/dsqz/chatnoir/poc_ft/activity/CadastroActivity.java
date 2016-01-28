package br.com.dsqz.chatnoir.poc_ft.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.dsqz.chatnoir.poc_ft.R;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;

public class CadastroActivity extends Activity{

    private EditText mEditTextCPF;
    private EditText mEditTextTelefone;
    private EditText mEditTextCelular;
    private EditText mEditTextNascimento;
    private EditText mEditTextNonActive5;
    private EditText mEditTextNonActive6;
    private Spinner  mSpinnerSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        mEditTextCPF = (EditText) findViewById(R.id.editTextCPF);
        mEditTextTelefone = (EditText) findViewById(R.id.editText_telefone);
        mEditTextCelular = (EditText) findViewById(R.id.editText_celular);
        mEditTextNascimento = (EditText) findViewById(R.id.editText_nascimento);
        mEditTextNonActive5 = (EditText) findViewById(R.id.non_active_edit_text5);
        mEditTextNonActive6 = (EditText) findViewById(R.id.non_active_edit_text6);
        mSpinnerSexo = (Spinner) findViewById(R.id.spinner_sexo);

        setMasksToFields();
        initializeSpinner();
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
                }else{
                    mEditTextNonActive5.setVisibility(View.INVISIBLE);
                    mEditTextNonActive6.setVisibility(View.INVISIBLE);
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
