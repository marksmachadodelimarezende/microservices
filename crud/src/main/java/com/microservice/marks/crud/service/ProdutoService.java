package com.microservice.marks.crud.service;

import com.microservice.marks.crud.data.vo.ProdutoVO;
import com.microservice.marks.crud.entity.Produto;
import com.microservice.marks.crud.exception.ResourceNotFoundException;
import com.microservice.marks.crud.message.ProdutoSendMessage;
import com.microservice.marks.crud.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {
    private static final String MESAGE_NOT_FOUND_ID = "No record found for this ID";
    private final ProdutoRepository produtoRepository;
    private ProdutoSendMessage produtoSendMessage;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository, ProdutoSendMessage produtoSendMessage) {
        this.produtoRepository = produtoRepository;
        this.produtoSendMessage = produtoSendMessage;
    }

    public ProdutoVO create(ProdutoVO produtoVO) {
        var entityVO = ProdutoVO.create(produtoRepository.save(Produto.create(produtoVO)));
        produtoSendMessage.sendMessage(entityVO);
        return entityVO;
    }

    public Page<ProdutoVO> findAll(Pageable pageable) {
        var page = produtoRepository.findAll(pageable);
        return page.map(this::convertToProdutoVO);
    }

    public ProdutoVO findById(Long id) {
        var entity = produtoRepository.findById(id).orElseThrow(this::resorceNotFoundException);
        return ProdutoVO.create(entity);
    }

    public ProdutoVO update(ProdutoVO produtoVO) {
        final Optional<Produto> optionalProduto = produtoRepository.findById(produtoVO.getId());
        if(!optionalProduto.isPresent())
            resorceNotFoundException();

        return ProdutoVO.create(produtoRepository.save(Produto.create(produtoVO)));
    }

    public void delete(Long id) {
        var entity = produtoRepository.findById(id).orElseThrow(this::resorceNotFoundException);
        produtoRepository.delete(entity);
    }

    private ProdutoVO convertToProdutoVO(Produto produto) {
        return ProdutoVO.create(produto);
    }

    private ResourceNotFoundException resorceNotFoundException() {
        throw new ResourceNotFoundException(ProdutoService.MESAGE_NOT_FOUND_ID);
    }
}
