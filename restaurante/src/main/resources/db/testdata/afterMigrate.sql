SET session_replication_role = 'replica';

delete
from cidade;
delete
from cozinha;
delete
from estado;
delete
from forma_pagamento;
delete
from grupo_permissao;
delete
from grupo;
delete
from permissao;
delete
from produto;
delete
from pedido;
delete
from restaurante_forma_pagamento;
delete
from restaurante;
delete
from restaurante_produto;
delete
from usuario_grupo;
delete
from usuario;
delete
from item_pedido;

SET session_replication_role = 'origin';

alter sequence cidade_id_seq restart;
alter sequence cozinha_id_seq restart;
alter sequence estado_id_seq restart;
alter sequence forma_pagamento_id_seq restart;
alter sequence grupo_id_seq restart;
alter sequence pedido_id_seq restart;
alter sequence item_pedido_id_seq restart;
alter sequence permissao_id_seq restart;
alter sequence produto_id_seq restart;
alter sequence restaurante_id_seq restart;
alter sequence usuario_id_seq restart;

insert into cozinha (id, nome)
values (1, 'Tailandesa'),
       (2, 'Indiana'),
       (3, 'Argentina'),
       (4, 'Brasileira');

insert into estado (id, nome)
values (1, 'Minas Gerais'),
       (2, 'São Paulo'),
       (3, 'Ceará');

insert into cidade (id, nome, estado_id)
values (1, 'Uberlândia', 1),
       (2, 'Belo Horizonte', 1),
       (3, 'São Paulo', 2),
       (4, 'Campinas', 2),
       (5, 'Fortaleza', 3);

insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto,
                         endereco_cidade_id,
                         endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro)
values (1, 'Thai Gourmet', 10, 1, now(), now(), TRUE, TRUE, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto)
values (2, 'Thai Delivery', 9.50, 1, now(), now(), TRUE, TRUE),
       (3, 'Tuk Tuk Comida Indiana', 15, 2, now(), now(), TRUE, TRUE),
       (4, 'Java Steakhouse', 12, 3, now(), now(), TRUE, TRUE),
       (5, 'Lanchonete do Tio Sam', 11, 4, now(), now(), TRUE, TRUE),
       (6, 'Bar da Maria', 6, 4, now(), now(), TRUE, TRUE);

insert into forma_pagamento (id, descricao)
values (1, 'Cartão de crédito'),
       (2, 'Cartão de débito'),
       (3, 'Dinheiro');

insert into permissao (id, nome, descricao)
values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas'),
       (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 3),
       (3, 2),
       (3, 3),
       (4, 1),
       (4, 2),
       (5, 1),
       (5, 2),
       (6, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id)
values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, false, 1),
       ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, true, 1),
       ('Salada picante com carne grelhada',
        'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20,
        true, 2),
       ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, true, 3),
       ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, true, 3),
       ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé',
        79, true, 4),
       ('T-Bone',
        'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89,
        true, 4),
       ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, true, 5),
       ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, true, 6);


insert into grupo (id, nome)
values (1, 'Gerente'),
       (2, 'Vendedor'),
       (3, 'Secretária'),
       (4, 'Cadastrador');

insert into grupo_permissao (grupo_id, permissao_id)
values (1, 1),
       (1, 2),
       (2, 1),
       (2, 2),
       (3, 1);

insert into usuario (id, nome, email, senha, data_cadastro)
values (1, 'João da Silva', 'joao.ger@algafood.com.br', '123', now()),
       (2, 'Maria Joaquina', 'maria.vnd@algafood.com.br', '123', now()),
       (3, 'José Souza', 'jose.aux@algafood.com.br', '123', now()),
       (4, 'Sebastião Martins', 'sebastiao.cad@algafood.com.br', '123', now()),
       (5, 'Manoel Lima', 'manoel.loja@gmail.com', '123', now()),
       (6, 'Débora Mendonça', 'email.teste.aw+debora@gmail.com', '123', now()),
       (7, 'Carlos Lima', 'email.teste.aw+carlos@gmail.com', '123', now());

insert into usuario_grupo (usuario_id, grupo_id)
values (1, 1),
       (1, 2),
       (2, 2);

insert into restaurante_usuario_responsavel (restaurante_id, usuario_id)
values (1, 5),
       (3, 5);


insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id,
                    endereco_cep,
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
                    status_pedido, data_criacao, subtotal, taxa_frete, valor_total)
values (1, 'f9981ca4-5a5e-4da3-af04-933861df3e55', 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801',
        'Brasil',
        'CRIADO', now(), 298.90, 10, 308.90);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (1, 1, 1, 1, 78.9, 78.9, null);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');


insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id,
                    endereco_cep,
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
                    status_pedido, data_criacao, subtotal, taxa_frete, valor_total)
values (2, 'd178b637-a785-4768-a3cb-aa1ce5a8cdab', 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
        'CRIADO', now(), 79, 0, 79);

insert into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (3, 2, 6, 1, 79, 79, 'Ao ponto');