package com.microservice.marks.pagamento.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.xml.crypto.Data;
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
}
