package br.com.dsqz.chatnoir.poc_ft.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import br.com.dsqz.chatnoir.poc_ft.R;
import br.com.dsqz.chatnoir.poc_ft.dto.Produto;
import br.com.dsqz.chatnoir.poc_ft.list.ProdutoAdapter;

public class ProdutoActivity extends Activity{

    private RecyclerView       mRecyclerViewProdutos;
    private ArrayList<Produto> mProdutos;
    private ProdutoAdapter     mProdutoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        mRecyclerViewProdutos = (RecyclerView) findViewById(R.id.recycleView_produtos);

        initializeProdutos();

        mProdutoAdapter = new ProdutoAdapter(getApplicationContext(),mProdutos);
        mRecyclerViewProdutos.setAdapter(mProdutoAdapter);
        mRecyclerViewProdutos.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewProdutos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void initializeProdutos(){
        //TODO
    }

}
