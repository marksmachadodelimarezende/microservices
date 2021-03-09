package com.microservice.marks.pagamento.services;

import com.microservice.marks.pagamento.data.vo.ProdutoVendaVO;
import com.microservice.marks.pagamento.data.vo.VendaVO;
import com.microservice.marks.pagamento.entity.ProdutoVenda;
import com.microservice.marks.pagamento.entity.Venda;
import com.microservice.marks.pagamento.exception.ResourceNotFoundException;
import com.microservice.marks.pagamento.repository.ProdutoVendaRepository;
import com.microservice.marks.pagamento.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendaService {
    private static final String MESAGE_NOT_FOUND_ID = "No record found for this ID";

    private VendaRepository vendaRepository;
    private ProdutoVendaRepository produtoVendaRepository;

    @Autowired
    public VendaService(VendaRepository vendaRepository, ProdutoVendaRepository produtoVendaRepository) {
        this.vendaRepository = vendaRepository;
        this.produtoVendaRepository = produtoVendaRepository;
    }

    public VendaVO create(VendaVO vendaVO) {
        Venda vendaSalva = vendaRepository.save(Venda.create(vendaVO));
        List<ProdutoVenda> produtoVendaSalvos = vendaVO.getProdutos().stream().map(p -> getProdutoVenda(p, vendaSalva)).collect(Collectors.toList());
        vendaSalva.setProdutos(produtoVendaSalvos);
        return VendaVO.create(vendaSalva);
    }

    public Page<VendaVO> findAll(Pageable pageable) {
        var page = vendaRepository.findAll(pageable);
        return page.map(this::convertToVendaVO);
    }

    public VendaVO findById(Long id ){
        var entity = vendaRepository.findById(id).orElseThrow(() -> resorceNotFoundException());
        return VendaVO.create(entity);
    }

    private ProdutoVenda getProdutoVenda(ProdutoVendaVO produtoVendaVO, Venda vendaSalva) {
        ProdutoVenda produtoVenda = ProdutoVenda.create(produtoVendaVO);
        produtoVenda.setVenda(vendaSalva);
        return produtoVendaRepository.save(produtoVenda);
    }

    private VendaVO convertToVendaVO(Venda venda) {
        return VendaVO.create(venda);
    }

    private ResourceNotFoundException resorceNotFoundException() {
        throw new ResourceNotFoundException(MESAGE_NOT_FOUND_ID);
    }
}
