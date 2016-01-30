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

    private Produto mProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_detalhe);

        ImageView imageView = (ImageView) findViewById(R.id.produto_detalhe_foto);
        TextView nome = (TextView) findViewById(R.id.produto_detalhe_nome);
        TextView preco = (TextView) findViewById(R.id.produto_detalhe_preco);
        TextView fabricante = (TextView) findViewById(R.id.produto_detalhe_fabricante);
        TextView categoria = (TextView) findViewById(R.id.produto_detalhe_categoria);
        TextView codigo = (TextView) findViewById(R.id.produto_detalhe_codigo);
        TextView descricao = (TextView) findViewById(R.id.produto_detalhe_ref);

        deserializeProduto();

        NumberFormat mFormat = NumberFormat.getCurrencyInstance();

        imageView.setImageBitmap(ImageSaveLoad.loadImage(mProduto.fotos.get(0).localPath, mProduto.fotos.get(0).id + ".png"));
        nome.setText(mProduto.nome);
        preco.setText(mFormat.format(mProduto.preco));
        fabricante.setText(mProduto.idEmpresa);
        categoria.setText(mProduto.idCategoria);
        codigo.setText(mProduto.codigoBarra);
        descricao.setText(mProduto.descricao);
    }

    private void deserializeProduto(){
        GsonBuilder mGsonBuilder = new GsonBuilder();
        mGsonBuilder.registerTypeAdapter(Produto.class, new ProdutoDeserializer());
        Gson mGson = mGsonBuilder.create();
        mProduto = mGson.fromJson(this.getIntent().getStringExtra("PRODUTO"), Produto.class);
    }
}
