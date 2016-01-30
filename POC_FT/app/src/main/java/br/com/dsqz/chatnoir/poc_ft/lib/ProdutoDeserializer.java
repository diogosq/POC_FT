package br.com.dsqz.chatnoir.poc_ft.lib;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

import br.com.dsqz.chatnoir.poc_ft.dto.Foto;
import br.com.dsqz.chatnoir.poc_ft.dto.Produto;


/**
 * Created by diogosq on 1/28/16.
 */
@SuppressWarnings ("UnnecessaryBoxing")
public class ProdutoDeserializer implements JsonDeserializer<Produto>{

    @Override
    public Produto deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException{

        final Produto produto = new Produto();
        final JsonObject jsonObject = json.getAsJsonObject();

        produto.preco = Double.valueOf(jsonObject.get("preco").getAsDouble());
        produto.criacao = jsonObject.get("criacao").getAsString();
        produto.termosPesquisa = jsonObject.get("termosPesquisa").getAsString();
        produto.classificacao = Integer.valueOf(jsonObject.get("classificacao").getAsInt());
        produto.estoqueMinimo = Integer.valueOf(jsonObject.get("estoqueMinimo").getAsInt());
        produto.altura = Integer.valueOf(jsonObject.get("altura").getAsInt());
        produto.disponivel = jsonObject.get("disponivel").getAsBoolean();
        produto.descricao = jsonObject.get("descricao").getAsString();
        produto.votos = Integer.valueOf(jsonObject.get("votos").getAsInt());
        produto.peso = Integer.valueOf(jsonObject.get("peso").getAsInt());
        produto.id = jsonObject.get("id").getAsString();
        produto.idCategoria = jsonObject.get("idCategoria").getAsString();
        produto.idMarca = jsonObject.get("idMarca").getAsString();
        produto.profundidade = Integer.valueOf(jsonObject.get("profundidade").getAsInt());
        produto.idEmpresa = jsonObject.get("idEmpresa").getAsString();
        produto.ativo = jsonObject.get("ativo").getAsBoolean();
        produto.codigoBarra = jsonObject.get("codigoBarra").getAsString();
        produto.largura = Integer.valueOf(jsonObject.get("largura").getAsInt());
        produto.estoque = Integer.valueOf(jsonObject.get("estoque").getAsInt());
        produto.nome = jsonObject.get("nome").getAsString();

        final JsonArray jsonFotoArray = jsonObject.get("fotos").getAsJsonArray();
        produto.fotos = new ArrayList<>();
        Foto temp;
        for(int i = 0; i < jsonFotoArray.size(); i++){
            final JsonObject jsonFoto = (JsonObject) jsonFotoArray.get(i);

            temp = new Foto();
            temp.id = jsonFoto.get("id").getAsString();
            temp.criacao = jsonFoto.get("criacao").getAsString();
            temp.padrao = jsonFoto.get("padrao").getAsBoolean();
            temp.idProduto = jsonFoto.get("idProduto").getAsString();
            temp.localPath = (jsonFoto.get("localPath") != null)? jsonFoto.get("localPath").getAsString() : null;

            produto.fotos.add(temp);
        }

        produto.destaque = jsonObject.get("destaque").getAsBoolean();

        return produto;
    }
}
