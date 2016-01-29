package br.com.dsqz.chatnoir.poc_ft.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private NumberFormat       mFormat;

    public ProdutoAdapter(Context context, ArrayList<Produto> mItens){
        this.mItens = mItens;
        this.mContext = context;
        mFormat = NumberFormat.getCurrencyInstance();
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

        produtoViewHolder.getmDescricaoView().setText(produto.descricao);
        produtoViewHolder.getmPrecoView().setText(mFormat.format(produto.preco));
        produtoViewHolder.getmFotoView()
                         .setImageBitmap(ImageSaveLoad.loadImage(produto.fotos.get(0).localPath, produto.fotos.get(0).id + ".png"));
        produtoViewHolder.setProdutoData(produto);
    }

    @Override
    public int getItemCount(){
        return (mItens == null)? 0 : mItens.size();
    }


}
