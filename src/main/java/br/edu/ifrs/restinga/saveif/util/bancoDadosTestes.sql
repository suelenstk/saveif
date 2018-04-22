/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  suelenst
 * Created: 15/04/2018

 */

use saveif;


INSERT INTO `curso` (`id`, `nivel`, `nome`) 
VALUES 
(NULL, 'Superior', 'Licenciatura em Letras Português e Espanhol'), 
(NULL, 'Superior', 'Superior de Tecnologia em Análise e Desenvolvimento de Sistemas'),
(NULL, 'Superior', 'Superior de Tecnologia em Gestão Desportiva e de Lazer'), 
(NULL, 'Superior', 'Superior de Tecnologia em Eletrônica Industrial'),

(NULL, 'Técnico Concomitante ao Ensino Médio', 'Técnico em Redes de Computadores - Modalidade Concomitante'), 

(NULL, 'Técnico Subsequente ao Ensino Médio', 'Técnico em Guia de Turismo'),
(NULL, 'Técnico Subsequente ao Ensino Médio', 'Técnico em Administração'), 

(NULL, 'Técnico Integrado ao Ensino Médio modalidade Proeja', 'Técnico em Comércio'),
(NULL, 'Técnico Integrado ao Ensino Médio modalidade Proeja', 'Técnico em Agroecologia'), 
(NULL, 'Técnico Integrado ao Ensino Médio modalidade Proeja', 'Técnico em Recursos Humanos'),

(NULL, 'Técnico Integrado ao Ensino Médio', 'Técnico em Eletrônica Integrado ao Ensino Médio'), 
(NULL, 'Técnico Integrado ao Ensino Médio', 'Técnico em Informática Integrado ao Ensino Médio'),

(NULL, 'Técnico Integrado ao Ensino Médio', 'Técnico em Lazer Integrado ao Ensino Médio'), 
(NULL, 'Técnico Integrado ao Ensino Médio', 'Técnico em Informática para Internet Integrado ao Ensino Médio') 
;




INSERT INTO `categoria` (`id`, `nome`) 
VALUES 
(NULL, 'Informática'), 
(NULL, 'Lazer'),
(NULL, 'Eletrônica'), 
(NULL, 'Linguagens'),
(NULL, 'Desenvolvimento de sistemas'), 
(NULL, 'Gestão de pessoas'),
(NULL, 'Empreendendorismo')

;


/**
 * OBSERVAÇÃO
 * TODOS OS USUÁRIOS USAM A SENHA 12345
 */
INSERT INTO `usuario` (`id`, `cpf`, `data_delecao`, `data_insercao`, `data_nascimento`, `email`, `imagem`, `nome`, `senha`, `sobre_usuario`, `tipo_imagem`, `tipo_vinculo`, `curso_id`) 
VALUES 
(NULL, '61411507053', NULL, '2018-04-15', '1980-03-15', 'agatha.moreira@restinga.ifrs.edu.br', NULL, 'Agatha Isabella Moreira', '$2a$04$0x9YevF53Ji919pUDvxCjOdnbnrO64/vkONzEalYuwJwufHlZcgZe', NULL, NULL, 'professor', NULL),
(NULL, '84059336033', NULL, '2018-04-15', '1990-01-08', 'eduardo.peixoto@restinga.ifrs.edu.br', NULL, 'Eduardo Caleb Peixoto', '$2a$04$6ywdKN0jida4aZHyEu.xge1ktOtrBJ5TMkpUy3a3oVNiED6Vh.Rgq', NULL, NULL, 'servidor', NULL),
(NULL, NULL, NULL, '2018-04-15', '1970-05-25', 'fabio.santos@restinga.ifrs.edu.br', NULL, 'Fábio Sebastião Manuel Santos', '$2a$04$3JMZIXyY1VO0Y6dNjRoqf.LMfgb2ggyDrTU0PCPpyl2TW6qpA9CgS', 'Sou um professor apaixonado pela docência e por tecnologia.', NULL, 'professor', NULL),
(NULL, NULL, NULL, '2018-04-15', '1995-05-18', 'sjlcampos@restinga.ifrs.edu.br', NULL, 'Stella Julia Laís Campos', '$2a$04$0vYH5wkLm6CxNFLq.CnvGOT8V2zDefC29YtV1jFbzWcfVIdYD4Ln.', 'Amo música, cinema e dança.', NULL, 'estudante', '1'), 
(NULL, NULL, NULL, '2018-04-15', '2000-09-14', 'hbpgoncalves@restinga.ifrs.edu.br', NULL, 'Hugo Bryan Pietro Gonçalves', '$2a$04$qYoIkwLHl7qxD7KyaghsduF6mGb8x3MJJLsV3DsutrWMJIMF9tBXS', 'Jogador de LoW', NULL, 'estudante', '14')

;


INSERT INTO `usuario_permissoes` (`usuario_id`, `permissoes`) 
VALUES 
('1', 'administrador'),
('2', 'usuario'),
('3', 'usuario'),
('4', 'usuario'),
('5', 'usuario'),
('2', 'certificado')
;


INSERT INTO `usuario_categorias` (`usuario_id`, `categorias_id`) 
VALUES 
('5', '5')
;


INSERT INTO `grupo` (`id`, `data_criacao`, `data_delecao`, `descricao`, `imagem`, `nome`, `tipo_imagem`, `tipo_privacidade`, `categoria_id`, `dono_grupo_id`) 
VALUES 
(NULL, '2018-04-15', NULL, 'Grupo para um sistema de controle de concessionária de veículos.', NULL, 'Programação II', NULL, 'Público', '5', '5')
;

INSERT INTO `atividade` (`id`, `nome`, `descricao`, `localizacao`, `data_atividade`) 
VALUES 
(NULL, 'Criação de serviço', 'Criar serviço tal.', 'IFRS', '2018-04-16');


INSERT INTO `grupo_coordenadores_grupo` (`grupos_coordenados_id`, `coordenadores_grupo_id`) 
VALUES 
('1', '5')
;

INSERT INTO `topico` (`id`, `data_criacao`, `data_delecao`, `data_finalizacao`, `nome`, `criador_topico_id`) 
VALUES 
(NULL, '2018-04-15', NULL, NULL, 'Geral', '5'),
(NULL, '2018-04-15', NULL, NULL, 'FrontEnd', '5')
;

INSERT INTO `grupo_topicos` (`grupo_id`, `topicos_id`) 
VALUES 
('1', '1'), 
('1', '2')
;

INSERT INTO `topico` (`id`, `data_criacao`, `data_delecao`, `data_finalizacao`, `nome`, `criador_topico_id`) 
VALUES 
(NULL, '2018-04-15', NULL, NULL, 'Geral', '5'),
(NULL, '2018-04-15', NULL, NULL, 'FrontEnd', '5')
;

INSERT INTO `grupo_integrantes_grupo` (`grupos_integrados_id`, `integrantes_grupo_id`) 
VALUES ('1', '3')
;


INSERT INTO `grupo_solicitantes_grupo` (`grupo_id`, `solicitantes_grupo_id`) 
VALUES 
('1', '4')
;


INSERT INTO `grupo_convites_grupo` (`grupos_convidado_id`, `convites_grupo_id`) 
VALUES 
('1', '2')
;

INSERT INTO `post` (`id`, `data_delecao`, `data_postagem`, `texto`, `titulo`, `anexo_post_id`, `autor_post_id`) 
VALUES 
(NULL, NULL, '2018-04-15', 'Esse é o post inicial, vamos conversar sobre nosso projeto.', 'Primeiro post', NULL, '5')
;

INSERT INTO `topico_posts` (`topico_id`, `posts_id`) 
VALUES 
('1', '1')
;





