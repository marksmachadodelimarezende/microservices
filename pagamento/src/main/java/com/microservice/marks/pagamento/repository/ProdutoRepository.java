package com.microservice.marks.pagamento.repository;

import com.microservice.marks.pagamento.entity.Produto;
import com.microservice.marks.pagamento.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
