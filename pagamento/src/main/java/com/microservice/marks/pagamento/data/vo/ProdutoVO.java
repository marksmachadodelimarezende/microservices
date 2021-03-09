package com.microservice.marks.pagamento.data.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.microservice.marks.pagamento.entity.Produto;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

@Data
@JsonPropertyOrder({"id","estoque"})
public class ProdutoVO extends RepresentationModel<ProdutoVO> {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("estoque")
    private Integer estoque;

    public static ProdutoVO create(Produto produto){
        return new ModelMapper().map(produto, ProdutoVO.class);
    }
}
