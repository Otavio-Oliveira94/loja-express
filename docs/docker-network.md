# Tarefa 8 - Docker Network

## Objetivo

Criar uma rede Docker para conectar os containers `product-container` e `order-container`. Dessa forma, o `order-service` pode consultar o `product-service` pelo nome do container, sem utilizar `localhost` ou IP fixo na comunicação interna.

## Rede criada

Foi criada uma rede Docker do tipo bridge com o nome `loja-network`.

```powershell
docker network create loja-network
```

O comando abaixo pode ser utilizado para listar as redes disponíveis e confirmar sua criação:

```powershell
docker network ls
```

## Preparação dos containers

Os containers anteriores foram interrompidos e removidos. As imagens `product-image` e `order-image` foram mantidas, pois não houve alteração no código Java, nos Dockerfiles ou nas imagens nesta tarefa.

```powershell
docker stop product-container
docker stop order-container
docker rm product-container
docker rm order-container
```

## Execução na mesma rede

O `product-container` foi iniciado na rede `loja-network` e manteve a porta `8080` publicada para acesso externo.

```powershell
docker run -d --name product-container --network loja-network -p 8080:8080 product-image
```

O `order-container` foi iniciado na mesma rede. A variável de ambiente `PRODUCT_SERVICE_URL` foi configurada com o endereço interno do `product-container`.

```powershell
docker run -d --name order-container --network loja-network -p 8081:8081 -e PRODUCT_SERVICE_URL=http://product-container:8080 order-image
```

## Como os microsserviços se comunicam

Em uma rede Docker criada, os containers conseguem localizar outros containers conectados a ela pelo nome. Portanto, quando o `order-service` recebe uma requisição para criar um pedido, ele utiliza a URL abaixo para consultar o produto:

```text
http://product-container:8080/products/{id}
```

O nome `product-container` é resolvido internamente pela rede `loja-network`. O uso de `localhost` permanece somente para o acesso externo feito pelo Postman:

| Serviço | Endereço externo |
|---|---|
| product-service | `http://localhost:8080/products` |
| order-service | `http://localhost:8081/orders` |

Assim, a comunicação interna ocorre entre containers, enquanto o Postman acessa as portas publicadas no computador hospedeiro.

## Verificação da rede

O comando abaixo apresenta os containers conectados à rede:

```powershell
docker network inspect loja-network
```

Na seção `Containers`, devem aparecer `product-container` e `order-container`.

## Evidência da comunicação

Com os containers em execução, foi criado um produto pelo endpoint do `product-service` e, em seguida, foi criado um pedido utilizando o ID desse produto. O retorno `201 Created` no endpoint `POST /orders` comprova que o `order-service` conseguiu consultar o `product-service` pela Docker Network antes de criar o pedido.
