package br.com.dsqz.chatnoir.poc_ft.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.NumberFormat;

import br.com.dsqz.chatnoir.poc_ft.R;
import br.com.dsqz.chatnoir.poc_ft.dto.Produto;
import br.com.dsqz.chatnoir.poc_ft.lib.ImageSaveLoad;
import br.com.dsqz.chatnoir.poc_ft.lib.ProdutoDeserializer;

public class ProdutoDetalheActivity extends Activity{

    private ImageView    mImageView;
    private TextView     mNome;
    private TextView     mPreco;
    private TextView     mFabricante;
    private TextView     mCategoria;
    private TextView     mCodigo;
    private TextView     mDescricao;
    private Produto      mProduto;
    private GsonBuilder  mGsonBuilder;
    private Gson         mGson;
    private NumberFormat mFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_detalhe);

        mImageView = (ImageView) findViewById(R.id.produto_detalhe_foto);
        mNome = (TextView) findViewById(R.id.produto_detalhe_nome);
        mPreco = (TextView) findViewById(R.id.produto_detalhe_preco);
        mFabricante = (TextView) findViewById(R.id.produto_detalhe_fabricante);
        mCategoria = (TextView) findViewById(R.id.produto_detalhe_categoria);
        mCodigo = (TextView) findViewById(R.id.produto_detalhe_codigo);
        mDescricao = (TextView) findViewById(R.id.produto_detalhe_ref);

        deserializeProduto();

        mFormat = NumberFormat.getCurrencyInstance();

        mImageView.setImageBitmap(ImageSaveLoad.loadImage(mProduto.fotos.get(0).localPath, mProduto.fotos.get(0).id + ".png"));
        mNome.setText(mProduto.nome);
        mPreco.setText(mFormat.format(mProduto.preco));
        mFabricante.setText(mProduto.idEmpresa);
        mCategoria.setText(mProduto.idCategoria);
        mCodigo.setText(mProduto.codigoBarra);
        mDescricao.setText(mProduto.descricao);
    }

    private void deserializeProduto(){
        mGsonBuilder = new GsonBuilder();
        mGsonBuilder.registerTypeAdapter(Produto.class, new ProdutoDeserializer());
        mGson = mGsonBuilder.create();
        mProduto = mGson.fromJson(this.getIntent().getStringExtra("PRODUTO"), Produto.class);
    }
}
