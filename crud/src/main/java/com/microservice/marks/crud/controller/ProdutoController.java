package com.microservice.marks.crud.controller;

import com.microservice.marks.crud.data.vo.ProdutoVO;
import com.microservice.marks.crud.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final PagedResourcesAssembler<ProdutoVO> produtoPagedResourcesAssembler;

    @Autowired
    public ProdutoController(ProdutoService produtoService, PagedResourcesAssembler<ProdutoVO> produtoPagedResourcesAssembler) {
        /* ERRO DE INJECAO DO AUTOWIRED NO INTELLIJ É FALSO. BUILD OCORRE SEM PROBLEMAS */
        this.produtoService = produtoService;
        this.produtoPagedResourcesAssembler = produtoPagedResourcesAssembler;
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    @ResponseStatus(HttpStatus.OK)
    public ProdutoVO findById(@PathVariable("id") Long id) {
        ProdutoVO produtoVO = produtoService.findById(id);
        addLink(id, produtoVO);
        return produtoVO;
    }

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "limit", defaultValue = "12") int limit,
                                     @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, by(sortDirection, "nome"));

        Page<ProdutoVO> produtos = produtoService.findAll(pageable);
        produtos.stream()
                .forEach(p -> p.add(linkTo(methodOn(ProdutoController.class).findById(p.getId())).withSelfRel()));

        PagedModel<EntityModel<ProdutoVO>> pagedModel = produtoPagedResourcesAssembler.toModel(produtos);

        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
    @ResponseStatus(HttpStatus.OK)
    public ProdutoVO create(@RequestBody ProdutoVO produtoVO) {
        ProdutoVO produtoVOResult = produtoService.create(produtoVO);
        addLink(produtoVOResult.getId(), produtoVOResult);
        return produtoVOResult;
    }

    @PutMapping(produces = {"application/json", "application/xml", "application/x-yaml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
    @ResponseStatus(HttpStatus.OK)
    public ProdutoVO update(@RequestBody ProdutoVO produtoVO) {
        ProdutoVO produtoVOResult = produtoService.update(produtoVO);
        addLink(produtoVOResult.getId(), produtoVOResult);
        return produtoVOResult;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private void addLink(Long id, ProdutoVO produtoVO) {
        produtoVO.add(linkTo(methodOn(ProdutoController.class).findById(id)).withSelfRel());
    }
}
