package br.com.dsqz.chatnoir.poc_ft.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Foto{

    @SerializedName ("id")
    @Expose
    public String id;

    @SerializedName ("criacao")
    @Expose
    public String criacao;

    @SerializedName ("padrao")
    @Expose
    public Boolean padrao;

    @SerializedName ("idProduto")
    @Expose
    public String idProduto;

    public String localPath;
}
