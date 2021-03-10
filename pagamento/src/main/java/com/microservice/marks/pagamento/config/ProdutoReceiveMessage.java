package com.microservice.marks.pagamento.config;

import com.microservice.marks.pagamento.data.vo.ProdutoVO;
import com.microservice.marks.pagamento.entity.Produto;
import com.microservice.marks.pagamento.repository.ProdutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ProdutoReceiveMessage {

    private final Logger log = LoggerFactory.getLogger(ProdutoReceiveMessage.class);

    private ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoReceiveMessage(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @RabbitListener(queues = {"${crud.rabbitmq.queue}"})
    public void receive(@Payload ProdutoVO produtoVO) {
        log.info("Recuperou dados do RabbitMQ: ".concat(produtoVO.toString()));
        produtoRepository.save(Produto.create(produtoVO));
    }
}
