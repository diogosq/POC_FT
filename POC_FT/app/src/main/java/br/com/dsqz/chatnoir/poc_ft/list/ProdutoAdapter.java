package br.com.dsqz.chatnoir.poc_ft.list;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.dsqz.chatnoir.poc_ft.R;
import br.com.dsqz.chatnoir.poc_ft.dto.Produto;
import br.com.dsqz.chatnoir.poc_ft.lib.ImageSaveLoad;

/**
 * Created by diogosq on 1/28/16.
 */
public class ProdutoAdapter extends RecyclerView.Adapter{


    private final String TAG = getClass().getSimpleName();

    private Context            mContext;
    private ArrayList<Produto> mItens;
    private NumberFormat       format;

    public ProdutoAdapter(Context context, ArrayList<Produto> mItens){
        this.mItens = mItens;
        this.mContext = context;
        format = NumberFormat.getCurrencyInstance();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_produto, parent, false);
        ProdutoViewHolder viewHolder = new ProdutoViewHolder(mContext, view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        Produto produto = mItens.get(position);

        ProdutoViewHolder produtoViewHolder = (ProdutoViewHolder) holder;

        produtoViewHolder.getmDescricaoView().setText(produto.descriCao);
        produtoViewHolder.getmPrecoView().setText(format.format(produto.preco));
        produtoViewHolder.getmFotoView().setImageBitmap(ImageSaveLoad.loadImage(produto.fotos.get(0).localPath,produto.fotos.get(0).id));
    }

    @Override
    public int getItemCount(){
        return mItens.size();
    }
}
