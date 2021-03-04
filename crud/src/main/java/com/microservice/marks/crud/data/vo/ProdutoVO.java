package com.microservice.marks.crud.data.vo;

import com.microservice.marks.crud.entity.Produto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

@Data
public class ProdutoVO extends RepresentationModel<ProdutoVO> {
    private Long id;
    private String nome;
    private Integer estoque;
    private Double preco;

    public static ProdutoVO create(Produto produto) {
        return new ModelMapper().map(produto, ProdutoVO.class);
    }
}
