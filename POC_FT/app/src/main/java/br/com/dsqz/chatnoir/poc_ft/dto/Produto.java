package br.com.dsqz.chatnoir.poc_ft.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diogosq on 1/28/16.
 */
public class Produto{

    @SerializedName ("preco")
    @Expose
    public Double  preco;
    @SerializedName ("criacao")
    @Expose
    public String  criacao;
    @SerializedName ("termosPesquisa")
    @Expose
    public String  termosPesquisa;
    @SerializedName ("classificacao")
    @Expose
    public Integer classificacao;
    @SerializedName ("estoqueMinimo")
    @Expose
    public Integer estoqueMinimo;
    @SerializedName ("altura")
    @Expose
    public Integer altura;
    @SerializedName ("disponivel")
    @Expose
    public Boolean disponivel;
    @SerializedName ("descri cao")
    @Expose
    public String  descricao;
    @SerializedName ("votos")
    @Expose
    public Integer votos;
    @SerializedName ("peso")
    @Expose
    public Integer peso;
    @SerializedName ("id")
    @Expose
    public String  id;
    @SerializedName ("idCategoria")
    @Expose
    public String  idCategoria;
    @SerializedName ("idMarca")
    @Expose
    public String  idMarca;
    @SerializedName ("profundidade")
    @Expose
    public Integer profundidade;
    @SerializedName ("idEmpresa")
    @Expose
    public String  idEmpresa;
    @SerializedName ("ativo")
    @Expose
    public Boolean ativo;
    @SerializedName ("codigoBarra")
    @Expose
    public String  codigoBarra;
    @SerializedName ("largura")
    @Expose
    public Integer largura;
    @SerializedName ("estoqu e")
    @Expose
    public Integer estoque;
    @SerializedName ("nome")
    @Expose
    public String  nome;
    @SerializedName ("fotos")
    @Expose
    public List<Foto> fotos = new ArrayList<Foto>();
    @SerializedName ("destaque")
    @Expose
    public Boolean destaque;

}