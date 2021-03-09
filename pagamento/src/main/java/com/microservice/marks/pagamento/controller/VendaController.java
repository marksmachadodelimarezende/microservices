package com.microservice.marks.pagamento.controller;

import com.microservice.marks.pagamento.data.vo.VendaVO;
import com.microservice.marks.pagamento.services.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.by;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/venda")
public class VendaController {

    private final VendaService vendaService;
    private final PagedResourcesAssembler<VendaVO> vendaPagedResourcesAssembler;

    @Autowired
    public VendaController(VendaService vendaService, PagedResourcesAssembler<VendaVO> vendaPagedResourcesAssembler) {
        /* ERRO DE INJECAO DO AUTOWIRED NO INTELLIJ É FALSO. BUILD OCORRE SEM PROBLEMAS */
        this.vendaService = vendaService;
        this.vendaPagedResourcesAssembler = vendaPagedResourcesAssembler;
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    @ResponseStatus(HttpStatus.OK)
    public VendaVO findById(@PathVariable("id") Long id) {
        VendaVO vendaVO = vendaService.findById(id);
        addLink(id, vendaVO);
        return vendaVO;
    }

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "limit", defaultValue = "12") int limit,
                                     @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, by(sortDirection, "data"));

        Page<VendaVO> produtos = vendaService.findAll(pageable);
        produtos.stream().forEach(p -> p.add(linkTo(methodOn(VendaController.class).findById(p.getId())).withSelfRel()));

        PagedModel<EntityModel<VendaVO>> pagedModel = vendaPagedResourcesAssembler.toModel(produtos);

        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
    @ResponseStatus(HttpStatus.OK)
    public VendaVO create(@RequestBody VendaVO vendaVO) {
        VendaVO vendaVOResult = vendaService.create(vendaVO);
        addLink(vendaVOResult.getId(), vendaVOResult);
        return vendaVOResult;
    }

    private void addLink(Long id, VendaVO vendaVO) {
        vendaVO.add(linkTo(methodOn(VendaController.class).findById(id)).withSelfRel());
    }
}
