package br.com.dsqz.chatnoir.poc_ft.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diogosq on 1/28/16.
 */
public class Produto{

    public Double  preco;
    public String  criacao;
    public String  termosPesquisa;
    public Integer classificacao;
    public Integer estoqueMinimo;
    public Integer altura;
    public Boolean disponivel;
    public String  descriCao;
    public Integer votos;
    public Integer peso;
    public String  id;
    public String  idCategoria;
    public String  idMarca;
    public Integer profundidade;
    public String  idEmpresa;
    public Boolean ativo;
    public String  codigoBarra;
    public Integer largura;
    public Integer estoquE;
    public String  nome;
    public List<Foto> fotos = new ArrayList<Foto>();
    public Boolean destaque;

}