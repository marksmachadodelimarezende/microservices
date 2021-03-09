package com.microservice.marks.pagamento.data.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.microservice.marks.pagamento.entity.ProdutoVenda;
import com.microservice.marks.pagamento.entity.Venda;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.List;

@Data
@JsonPropertyOrder({"id", "data", "produtos", "valorTotal"})
public class VendaVO extends RepresentationModel<VendaVO> {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("data")
    private Date data;

    @JsonProperty("produtos")
    private List<ProdutoVendaVO> produtos;

    @JsonProperty("valorTotal")
    private Double valorTotal;

    public static VendaVO create(Venda venda) {
        return new ModelMapper().map(venda, VendaVO.class);
    }

}
