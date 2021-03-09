package com.microservice.marks.pagamento.entity;

import com.microservice.marks.pagamento.data.vo.ProdutoVO;
import com.microservice.marks.pagamento.data.vo.VendaVO;
import org.modelmapper.ModelMapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    private Long id;

    @Column(name = "estoque", nullable = false, length = 10)
    private Integer estoque;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public static Produto create(ProdutoVO produtoVO) {
        return new ModelMapper().map(produtoVO, Produto.class);
    }
}
