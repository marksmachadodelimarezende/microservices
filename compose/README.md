# docker-compose RabbitMQ (Service Broker)
Para iniciar a maquina Docker-compose, basta utilizar a orientações abaixo.

# Up - Iniciar docker da maquina.
- Baixar conteúdo da maquina e iniciar a VM: docker-compose up -d 

# Stop - Parar docker sem perder configurações
- docker-compose stop

# Start - Iniciar docker com ultimas configurações (Snapshot mais recente)
- docker-compose start

# Down - Parar e remover dados configurados na VM (Snapthot)
- docker-compose down

# Configurações necessárias
Ao iniciar serviço CRUD, configurado para registrar uma Exchange, será criado o item na aba exchange automaticamente.
É preciso então criar uma QUEUE para vincular com a exchange foi criada pelo microserviço CRUD - fazer Bind entre itens.
O id (nome) da QUEUE deve ser usado como Listener no microservico que fará a escuta dos dados.

# No projeto
As configurações feitas no RabbitMQ são mapeadas no arquivo "application.properties" dos projetos CRUD e PAGAMENTO.