package com.microservice.marks.pagamento.entity;

import com.microservice.marks.pagamento.data.vo.VendaVO;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="venda")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data", nullable = false)
    private Date data;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "venda", cascade = {CascadeType.REFRESH})
    private List<ProdutoVenda> produtos;

    @Column(name = "valor_total", nullable = false, length = 10)
    private Double valorTotal;

    public static Venda create(VendaVO vendaVO) {
        return new ModelMapper().map(vendaVO, Venda.class);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<ProdutoVenda> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoVenda> produtos) {
        this.produtos = produtos;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
