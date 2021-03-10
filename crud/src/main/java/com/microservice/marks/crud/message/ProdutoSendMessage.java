package com.microservice.marks.crud.message;

import com.microservice.marks.crud.data.vo.ProdutoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProdutoSendMessage {
    private final Logger log = LoggerFactory.getLogger(ProdutoSendMessage.class);

    @Value("${crud.rabbitmq.exchange}")
    String exchange;

    @Value("${crud.rabbitmq.routingkey}")
    String routingkey;

    public final RabbitTemplate rabbitTemplate;

    @Autowired
    public ProdutoSendMessage(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(ProdutoVO produtoVO) {
        log.info("Produto sendo enviado ".concat(produtoVO.toString()));
        rabbitTemplate.convertAndSend(exchange, routingkey, produtoVO);
    }
}
