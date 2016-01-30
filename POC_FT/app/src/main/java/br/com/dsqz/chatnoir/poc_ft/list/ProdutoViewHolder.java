package br.com.dsqz.chatnoir.poc_ft.list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import br.com.dsqz.chatnoir.poc_ft.R;
import br.com.dsqz.chatnoir.poc_ft.activity.ProdutoDetalheActivity;
import br.com.dsqz.chatnoir.poc_ft.dto.Produto;

public class ProdutoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private Context   mContext;
    private ImageView mFotoView;
    private TextView  mDescricaoView;
    private TextView  mPrecoView;
    private Produto   mProdutoData;

    public ProdutoViewHolder(Context context, View itemView){
        super(itemView);
        this.mContext = context;
        mFotoView = (ImageView) itemView.findViewById(R.id.foto_produto);
        mDescricaoView = (TextView) itemView.findViewById(R.id.produto_descriicao);
        mPrecoView = (TextView) itemView.findViewById(R.id.produto_preco);
        itemView.setOnClickListener(this);
    }

    public Context getmContext(){
        return mContext;
    }

    public void setmContext(Context mContext){
        this.mContext = mContext;
    }

    public ImageView getmFotoView(){
        return mFotoView;
    }

    public void setmFotoView(ImageView mFotoView){
        this.mFotoView = mFotoView;
    }

    public TextView getmDescricaoView(){
        return mDescricaoView;
    }

    public void setmDescricaoView(TextView mDescricaoView){
        this.mDescricaoView = mDescricaoView;
    }

    public TextView getmPrecoView(){
        return mPrecoView;
    }

    public void setmPrecoView(TextView mPrecoView){
        this.mPrecoView = mPrecoView;
    }

    @Override
    public void onClick(View v){

        Intent i = new Intent(mContext, ProdutoDetalheActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("PRODUTO", new Gson().toJson(mProdutoData));

        mContext.startActivity(i);
    }

    public void setProdutoData(Produto produtoData){
        this.mProdutoData = produtoData;
    }
}
