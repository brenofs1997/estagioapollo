PGDMP                         y            apollo    11.8    11.8 �               0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                       false            	           1262    16585    apollo    DATABASE     �   CREATE DATABASE apollo WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Portuguese_Brazil.1252' LC_CTYPE = 'Portuguese_Brazil.1252';
    DROP DATABASE apollo;
             postgres    false            �            1255    18929    atualizar_caixa()    FUNCTION     5  CREATE FUNCTION public.atualizar_caixa() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN	
	IF (TG_OP = 'INSERT') THEN
		UPDATE CAIXA SET SALDO = SALDO - NEW.TOTAL 
		WHERE ID = 1;
	ELSIF (TG_OP = 'DELETE') THEN
	   UPDATE CAIXA SET SALDO = SALDO + OLD.TOTAL 
		WHERE ID = 1;
	END IF;
	RETURN NULL;
END$$;
 (   DROP FUNCTION public.atualizar_caixa();
       public       postgres    false            �            1255    18930    baixa_estoque()    FUNCTION     w  CREATE FUNCTION public.baixa_estoque() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN
 	IF (TG_OP = 'INSERT') THEN
        UPDATE PRODUTO SET QTDE = QTDE - NEW.QTDE 
        WHERE CODIGO = NEW.CODIGO_PRODUTO;
    ELSIF (TG_OP = 'DELETE') THEN
       UPDATE PRODUTO SET QTDE = QTDE + OLD.QTDE 
        WHERE CODIGO = OLD.CODIGO_PRODUTO;
    END IF;
	RETURN NULL;
END
$$;
 &   DROP FUNCTION public.baixa_estoque();
       public       postgres    false            �            1255    18931    baixa_estoquecompra()    FUNCTION     |  CREATE FUNCTION public.baixa_estoquecompra() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN
 	IF (TG_OP = 'INSERT') THEN
        UPDATE PRODUTO SET QTDE = QTDE + NEW.QTDE 
        WHERE CODIGO = NEW.CODIGO_PRODUTO;
    ELSIF (TG_OP = 'DELETE') THEN
       UPDATE PRODUTO SET QTDE = QTDE - OLD.QTDE 
        WHERE CODIGO = OLD.CODIGO_PRODUTO;
    END IF;
	RETURN NULL;
END$$;
 ,   DROP FUNCTION public.baixa_estoquecompra();
       public       postgres    false            �            1259    18932    caixa_cod_seq    SEQUENCE     v   CREATE SEQUENCE public.caixa_cod_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.caixa_cod_seq;
       public       postgres    false            �            1259    18934    caixa    TABLE     �   CREATE TABLE public.caixa (
    id integer DEFAULT nextval('public.caixa_cod_seq'::regclass) NOT NULL,
    saldo numeric(20,2) NOT NULL,
    status character(1) DEFAULT NULL::bpchar
);
    DROP TABLE public.caixa;
       public         postgres    false    196            �            1259    18939    categoria_cod_seq    SEQUENCE     z   CREATE SEQUENCE public.categoria_cod_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.categoria_cod_seq;
       public       postgres    false            �            1259    18941 	   categoria    TABLE     �   CREATE TABLE public.categoria (
    codigo integer DEFAULT nextval('public.categoria_cod_seq'::regclass) NOT NULL,
    descricao character varying(30)
);
    DROP TABLE public.categoria;
       public         postgres    false    198            �            1259    18945    categorias_fornecedor    TABLE     w   CREATE TABLE public.categorias_fornecedor (
    cod_categoria integer NOT NULL,
    cod_fornecedor integer NOT NULL
);
 )   DROP TABLE public.categorias_fornecedor;
       public         postgres    false            �            1259    18948    cidade    TABLE     �   CREATE TABLE public.cidade (
    cid_cod integer NOT NULL,
    est_cod smallint NOT NULL,
    cid_nome character varying(50) NOT NULL
);
    DROP TABLE public.cidade;
       public         postgres    false            �            1259    18951    cliente_codigo_seq    SEQUENCE     {   CREATE SEQUENCE public.cliente_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.cliente_codigo_seq;
       public       postgres    false            �            1259    18953    cliente    TABLE       CREATE TABLE public.cliente (
    codigo integer DEFAULT nextval('public.cliente_codigo_seq'::regclass) NOT NULL,
    nome character varying(70),
    data_cadastro date,
    cpf character varying(20),
    endereco character varying(70),
    bairro character varying(30),
    email character varying(100),
    limite_fiado numeric(8,2),
    cep character varying(20),
    telefone character varying(20),
    cod_cidade integer,
    ativo character varying(10),
    numero character varying(10),
    saldo_devedor numeric(8,2)
);
    DROP TABLE public.cliente;
       public         postgres    false    202            �            1259    18957    cod_venda_seq    SEQUENCE     v   CREATE SEQUENCE public.cod_venda_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.cod_venda_seq;
       public       postgres    false            �            1259    18959    condicao_pgto_cod_seq    SEQUENCE     ~   CREATE SEQUENCE public.condicao_pgto_cod_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.condicao_pgto_cod_seq;
       public       postgres    false            �            1259    18961    condicao_pgto    TABLE     �   CREATE TABLE public.condicao_pgto (
    codigo integer DEFAULT nextval('public.condicao_pgto_cod_seq'::regclass) NOT NULL,
    descricao character varying(30)
);
 !   DROP TABLE public.condicao_pgto;
       public         postgres    false    205            �            1259    18965    contas_pagar_seq    SEQUENCE     y   CREATE SEQUENCE public.contas_pagar_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.contas_pagar_seq;
       public       postgres    false            �            1259    18967    contas_pagar    TABLE       CREATE TABLE public.contas_pagar (
    codigo integer DEFAULT nextval('public.contas_pagar_seq'::regclass) NOT NULL,
    flag_despesa integer,
    parcela character varying(5),
    valor numeric(8,2),
    valor_pago numeric(8,2),
    emissao date,
    data_pago date,
    vencimento date,
    funcionario integer,
    cond_pgto integer,
    tipo_despesa integer,
    qtde_parcelas integer,
    dias_entreparc integer,
    status character varying(10),
    cod_fornecedor integer,
    cod_compra integer,
    flag_parcial integer
);
     DROP TABLE public.contas_pagar;
       public         postgres    false    207            �            1259    18971    contas_receber_seq    SEQUENCE     {   CREATE SEQUENCE public.contas_receber_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.contas_receber_seq;
       public       postgres    false            �            1259    18973    contas_receber    TABLE     �  CREATE TABLE public.contas_receber (
    codigo integer DEFAULT nextval('public.contas_receber_seq'::regclass) NOT NULL,
    cod_venda integer,
    parcela character varying(5),
    valor numeric(8,2),
    valor_pago numeric(8,2),
    emissao date,
    data_pago date,
    vencimento date,
    cod_funcionario integer,
    cod_cliente integer,
    cod_condicaopagamento integer,
    status character varying(3)
);
 "   DROP TABLE public.contas_receber;
       public         postgres    false    209            �            1259    18977    despesa_cod_seq    SEQUENCE     x   CREATE SEQUENCE public.despesa_cod_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.despesa_cod_seq;
       public       postgres    false            �            1259    18979    despesas    TABLE     �   CREATE TABLE public.despesas (
    codigo integer DEFAULT nextval('public.despesa_cod_seq'::regclass) NOT NULL,
    descricao character varying(30),
    dia_pgto integer
);
    DROP TABLE public.despesas;
       public         postgres    false    211            �            1259    18983    empresa_codigo_seq    SEQUENCE     {   CREATE SEQUENCE public.empresa_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.empresa_codigo_seq;
       public       postgres    false            �            1259    18985    empresa_parametros    TABLE        CREATE TABLE public.empresa_parametros (
    codigo integer DEFAULT nextval('public.empresa_codigo_seq'::regclass) NOT NULL,
    fantasia character varying(100),
    razao_social character varying(100),
    endereco character varying(70),
    cod_cidade integer,
    telefone character varying(20),
    cnpj character varying(20),
    email character varying(100),
    cep character varying(20),
    site character varying(50),
    ie character varying(50),
    numero character varying(10),
    bairro character varying(30),
    logo bytea
);
 &   DROP TABLE public.empresa_parametros;
       public         postgres    false    213            �            1259    18992    entrada_cod_seq    SEQUENCE     x   CREATE SEQUENCE public.entrada_cod_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.entrada_cod_seq;
       public       postgres    false            �            1259    18994    entrada_de_produtos    TABLE     �   CREATE TABLE public.entrada_de_produtos (
    codigo integer DEFAULT nextval('public.entrada_cod_seq'::regclass) NOT NULL,
    emissao date,
    codigo_fornecedor integer,
    codigo_condpagto integer,
    total numeric(20,2)
);
 '   DROP TABLE public.entrada_de_produtos;
       public         postgres    false    215            �            1259    18998    estado    TABLE     �   CREATE TABLE public.estado (
    est_cod smallint NOT NULL,
    pais_cod smallint NOT NULL,
    est_sgl character(2) NOT NULL,
    est_nome character varying(50) NOT NULL
);
    DROP TABLE public.estado;
       public         postgres    false            �            1259    19001    fornecedor_cod_seq    SEQUENCE     {   CREATE SEQUENCE public.fornecedor_cod_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.fornecedor_cod_seq;
       public       postgres    false            �            1259    19003 
   fornecedor    TABLE     �  CREATE TABLE public.fornecedor (
    codigo integer DEFAULT nextval('public.fornecedor_cod_seq'::regclass) NOT NULL,
    nomefantasia character varying(100),
    cnpj character varying(20),
    ativo character varying(10),
    endereco character varying(70),
    bairro character varying(30),
    numero character varying(10),
    email character varying(100),
    razaosocial character varying(100),
    cep character varying(20),
    telefone character varying(20),
    cod_cidade integer
);
    DROP TABLE public.fornecedor;
       public         postgres    false    218            �            1259    19007    funcionario_cod_sequence    SEQUENCE     �   CREATE SEQUENCE public.funcionario_cod_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.funcionario_cod_sequence;
       public       postgres    false            �            1259    19009    funcionario    TABLE     b  CREATE TABLE public.funcionario (
    codigo integer DEFAULT nextval('public.funcionario_cod_sequence'::regclass) NOT NULL,
    nome character varying(70) NOT NULL,
    telefone character varying(20),
    endereco character varying(70),
    numero character varying(10),
    cep character varying(20),
    login character varying(20) NOT NULL,
    senha character varying(20) NOT NULL,
    ativo character varying(10),
    cod_cidade integer,
    cod_nivel integer,
    cpf character varying(20),
    email character varying(100),
    bairro character varying(30),
    primeiro_acesso character varying(10)
);
    DROP TABLE public.funcionario;
       public         postgres    false    220            �            1259    19013    item_compra    TABLE     �   CREATE TABLE public.item_compra (
    codigo_compra integer NOT NULL,
    codigo_produto integer NOT NULL,
    qtde integer,
    unitario numeric(8,2),
    total numeric(8,2)
);
    DROP TABLE public.item_compra;
       public         postgres    false            �            1259    19016 
   item_venda    TABLE     �   CREATE TABLE public.item_venda (
    codigo_produto integer NOT NULL,
    qtde integer,
    unitario numeric(8,2),
    codigo_venda integer NOT NULL,
    total numeric(8,2)
);
    DROP TABLE public.item_venda;
       public         postgres    false            �            1259    19019    lancar_despesas_cod_seq    SEQUENCE     �   CREATE SEQUENCE public.lancar_despesas_cod_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.lancar_despesas_cod_seq;
       public       postgres    false            �            1259    19021    lancar_despesas    TABLE     '  CREATE TABLE public.lancar_despesas (
    codigo integer DEFAULT nextval('public.lancar_despesas_cod_seq'::regclass) NOT NULL,
    descricao character varying(50),
    valor numeric(20,4),
    cod_tpdespesa integer,
    dtemissao date,
    dtvenc date,
    cod_condpgto integer,
    parcelas integer,
    valor_pago numeric(20,4),
    cod_func integer,
    id_despesa integer,
    id_parcela character varying(10),
    valor_pend numeric(20,4),
    total numeric(20,2),
    dtpago date,
    entreparc integer,
    meses integer,
    restos integer
);
 #   DROP TABLE public.lancar_despesas;
       public         postgres    false    224            �            1259    19025    movimento_caixa_cod_seq    SEQUENCE     �   CREATE SEQUENCE public.movimento_caixa_cod_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.movimento_caixa_cod_seq;
       public       postgres    false            �            1259    19027    movimento_caixa    TABLE     �   CREATE TABLE public.movimento_caixa (
    id integer DEFAULT nextval('public.movimento_caixa_cod_seq'::regclass) NOT NULL,
    descricao character varying(45) NOT NULL,
    valor numeric(20,2) NOT NULL,
    cod_caixa integer
);
 #   DROP TABLE public.movimento_caixa;
       public         postgres    false    226            �            1259    19031    nivel_funcionario_cod_seq    SEQUENCE     �   CREATE SEQUENCE public.nivel_funcionario_cod_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE public.nivel_funcionario_cod_seq;
       public       postgres    false            �            1259    19033    nivel_funcionario    TABLE     �   CREATE TABLE public.nivel_funcionario (
    codigo integer DEFAULT nextval('public.nivel_funcionario_cod_seq'::regclass) NOT NULL,
    descricao character varying(30),
    cod_restricao integer
);
 %   DROP TABLE public.nivel_funcionario;
       public         postgres    false    228            �            1259    19037    pagar_cod_seq    SEQUENCE     v   CREATE SEQUENCE public.pagar_cod_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.pagar_cod_seq;
       public       postgres    false            �            1259    19039    pagar    TABLE     �   CREATE TABLE public.pagar (
    id integer DEFAULT nextval('public.pagar_cod_seq'::regclass) NOT NULL,
    data date NOT NULL,
    valor numeric(20,2) NOT NULL
);
    DROP TABLE public.pagar;
       public         postgres    false    230            �            1259    19043    pais    TABLE     �   CREATE TABLE public.pais (
    pais_cod smallint NOT NULL,
    pais_sgl character(2) NOT NULL,
    pais_nome character varying(50) NOT NULL
);
    DROP TABLE public.pais;
       public         postgres    false            �            1259    19046    produto_cod_seq    SEQUENCE     x   CREATE SEQUENCE public.produto_cod_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.produto_cod_seq;
       public       postgres    false            �            1259    19048    produto    TABLE     �   CREATE TABLE public.produto (
    codigo integer DEFAULT nextval('public.produto_cod_seq'::regclass) NOT NULL,
    descricao character varying(50),
    qtde integer,
    preco numeric(8,2),
    cod_categoria integer,
    ativo boolean
);
    DROP TABLE public.produto;
       public         postgres    false    233            �            1259    19052    receber_cod_seq    SEQUENCE     x   CREATE SEQUENCE public.receber_cod_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.receber_cod_seq;
       public       postgres    false            �            1259    19054    receber    TABLE     �   CREATE TABLE public.receber (
    id integer DEFAULT nextval('public.receber_cod_seq'::regclass) NOT NULL,
    data date NOT NULL,
    valor numeric(20,2) NOT NULL
);
    DROP TABLE public.receber;
       public         postgres    false    235            �            1259    19058 
   restricoes    TABLE     e   CREATE TABLE public.restricoes (
    codigo integer NOT NULL,
    descricao character varying(40)
);
    DROP TABLE public.restricoes;
       public         postgres    false            �            1259    19061    tipo_despesa_cod_seq    SEQUENCE     }   CREATE SEQUENCE public.tipo_despesa_cod_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.tipo_despesa_cod_seq;
       public       postgres    false            �            1259    19063    tipo_despesa    TABLE     �   CREATE TABLE public.tipo_despesa (
    codigo integer DEFAULT nextval('public.tipo_despesa_cod_seq'::regclass) NOT NULL,
    descricao character varying(50)
);
     DROP TABLE public.tipo_despesa;
       public         postgres    false    238            �            1259    19067    venda    TABLE     �   CREATE TABLE public.venda (
    codigo integer DEFAULT nextval('public.cod_venda_seq'::regclass) NOT NULL,
    emissao date,
    total numeric(8,2),
    cod_cliente integer,
    cod_condpagto integer
);
    DROP TABLE public.venda;
       public         postgres    false    204            �          0    18934    caixa 
   TABLE DATA               2   COPY public.caixa (id, saldo, status) FROM stdin;
    public       postgres    false    197   ��       �          0    18941 	   categoria 
   TABLE DATA               6   COPY public.categoria (codigo, descricao) FROM stdin;
    public       postgres    false    199   ��       �          0    18945    categorias_fornecedor 
   TABLE DATA               N   COPY public.categorias_fornecedor (cod_categoria, cod_fornecedor) FROM stdin;
    public       postgres    false    200   �       �          0    18948    cidade 
   TABLE DATA               <   COPY public.cidade (cid_cod, est_cod, cid_nome) FROM stdin;
    public       postgres    false    201   3�       �          0    18953    cliente 
   TABLE DATA               �   COPY public.cliente (codigo, nome, data_cadastro, cpf, endereco, bairro, email, limite_fiado, cep, telefone, cod_cidade, ativo, numero, saldo_devedor) FROM stdin;
    public       postgres    false    203   #U      �          0    18961    condicao_pgto 
   TABLE DATA               :   COPY public.condicao_pgto (codigo, descricao) FROM stdin;
    public       postgres    false    206   �U      �          0    18967    contas_pagar 
   TABLE DATA               �   COPY public.contas_pagar (codigo, flag_despesa, parcela, valor, valor_pago, emissao, data_pago, vencimento, funcionario, cond_pgto, tipo_despesa, qtde_parcelas, dias_entreparc, status, cod_fornecedor, cod_compra, flag_parcial) FROM stdin;
    public       postgres    false    208   �U      �          0    18973    contas_receber 
   TABLE DATA               �   COPY public.contas_receber (codigo, cod_venda, parcela, valor, valor_pago, emissao, data_pago, vencimento, cod_funcionario, cod_cliente, cod_condicaopagamento, status) FROM stdin;
    public       postgres    false    210   �V      �          0    18979    despesas 
   TABLE DATA               ?   COPY public.despesas (codigo, descricao, dia_pgto) FROM stdin;
    public       postgres    false    212   �W      �          0    18985    empresa_parametros 
   TABLE DATA               �   COPY public.empresa_parametros (codigo, fantasia, razao_social, endereco, cod_cidade, telefone, cnpj, email, cep, site, ie, numero, bairro, logo) FROM stdin;
    public       postgres    false    214   �W      �          0    18994    entrada_de_produtos 
   TABLE DATA               j   COPY public.entrada_de_produtos (codigo, emissao, codigo_fornecedor, codigo_condpagto, total) FROM stdin;
    public       postgres    false    216   �W      �          0    18998    estado 
   TABLE DATA               F   COPY public.estado (est_cod, pais_cod, est_sgl, est_nome) FROM stdin;
    public       postgres    false    217   .X      �          0    19003 
   fornecedor 
   TABLE DATA               �   COPY public.fornecedor (codigo, nomefantasia, cnpj, ativo, endereco, bairro, numero, email, razaosocial, cep, telefone, cod_cidade) FROM stdin;
    public       postgres    false    219   �Y      �          0    19009    funcionario 
   TABLE DATA               �   COPY public.funcionario (codigo, nome, telefone, endereco, numero, cep, login, senha, ativo, cod_cidade, cod_nivel, cpf, email, bairro, primeiro_acesso) FROM stdin;
    public       postgres    false    221   Z      �          0    19013    item_compra 
   TABLE DATA               [   COPY public.item_compra (codigo_compra, codigo_produto, qtde, unitario, total) FROM stdin;
    public       postgres    false    222   �Z      �          0    19016 
   item_venda 
   TABLE DATA               Y   COPY public.item_venda (codigo_produto, qtde, unitario, codigo_venda, total) FROM stdin;
    public       postgres    false    223   �Z      �          0    19021    lancar_despesas 
   TABLE DATA               �   COPY public.lancar_despesas (codigo, descricao, valor, cod_tpdespesa, dtemissao, dtvenc, cod_condpgto, parcelas, valor_pago, cod_func, id_despesa, id_parcela, valor_pend, total, dtpago, entreparc, meses, restos) FROM stdin;
    public       postgres    false    225   c[      �          0    19027    movimento_caixa 
   TABLE DATA               J   COPY public.movimento_caixa (id, descricao, valor, cod_caixa) FROM stdin;
    public       postgres    false    227   A\      �          0    19033    nivel_funcionario 
   TABLE DATA               M   COPY public.nivel_funcionario (codigo, descricao, cod_restricao) FROM stdin;
    public       postgres    false    229   ^\      �          0    19039    pagar 
   TABLE DATA               0   COPY public.pagar (id, data, valor) FROM stdin;
    public       postgres    false    231   �\      �          0    19043    pais 
   TABLE DATA               =   COPY public.pais (pais_cod, pais_sgl, pais_nome) FROM stdin;
    public       postgres    false    232   �\      �          0    19048    produto 
   TABLE DATA               W   COPY public.produto (codigo, descricao, qtde, preco, cod_categoria, ativo) FROM stdin;
    public       postgres    false    234   �\      �          0    19054    receber 
   TABLE DATA               2   COPY public.receber (id, data, valor) FROM stdin;
    public       postgres    false    236   g]                 0    19058 
   restricoes 
   TABLE DATA               7   COPY public.restricoes (codigo, descricao) FROM stdin;
    public       postgres    false    237   �]                0    19063    tipo_despesa 
   TABLE DATA               9   COPY public.tipo_despesa (codigo, descricao) FROM stdin;
    public       postgres    false    239   �]                0    19067    venda 
   TABLE DATA               S   COPY public.venda (codigo, emissao, total, cod_cliente, cod_condpagto) FROM stdin;
    public       postgres    false    240   ?^      
           0    0    caixa_cod_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.caixa_cod_seq', 1, true);
            public       postgres    false    196                       0    0    categoria_cod_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.categoria_cod_seq', 3, true);
            public       postgres    false    198                       0    0    cliente_codigo_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.cliente_codigo_seq', 4, true);
            public       postgres    false    202                       0    0    cod_venda_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.cod_venda_seq', 74, true);
            public       postgres    false    204                       0    0    condicao_pgto_cod_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.condicao_pgto_cod_seq', 3, true);
            public       postgres    false    205                       0    0    contas_pagar_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.contas_pagar_seq', 55, true);
            public       postgres    false    207                       0    0    contas_receber_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.contas_receber_seq', 64, true);
            public       postgres    false    209                       0    0    despesa_cod_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.despesa_cod_seq', 1, false);
            public       postgres    false    211                       0    0    empresa_codigo_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.empresa_codigo_seq', 6, true);
            public       postgres    false    213                       0    0    entrada_cod_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.entrada_cod_seq', 16, true);
            public       postgres    false    215                       0    0    fornecedor_cod_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.fornecedor_cod_seq', 7, true);
            public       postgres    false    218                       0    0    funcionario_cod_sequence    SEQUENCE SET     G   SELECT pg_catalog.setval('public.funcionario_cod_sequence', 30, true);
            public       postgres    false    220                       0    0    lancar_despesas_cod_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.lancar_despesas_cod_seq', 326, true);
            public       postgres    false    224                       0    0    movimento_caixa_cod_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.movimento_caixa_cod_seq', 1, true);
            public       postgres    false    226                       0    0    nivel_funcionario_cod_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.nivel_funcionario_cod_seq', 9, true);
            public       postgres    false    228                       0    0    pagar_cod_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.pagar_cod_seq', 1, false);
            public       postgres    false    230                       0    0    produto_cod_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.produto_cod_seq', 6, true);
            public       postgres    false    233                       0    0    receber_cod_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.receber_cod_seq', 2, true);
            public       postgres    false    235                       0    0    tipo_despesa_cod_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.tipo_despesa_cod_seq', 7, true);
            public       postgres    false    238            $           2606    19072    cidade PK_cidade 
   CONSTRAINT     U   ALTER TABLE ONLY public.cidade
    ADD CONSTRAINT "PK_cidade" PRIMARY KEY (cid_cod);
 <   ALTER TABLE ONLY public.cidade DROP CONSTRAINT "PK_cidade";
       public         postgres    false    201            4           2606    19074    estado PK_estado 
   CONSTRAINT     U   ALTER TABLE ONLY public.estado
    ADD CONSTRAINT "PK_estado" PRIMARY KEY (est_cod);
 <   ALTER TABLE ONLY public.estado DROP CONSTRAINT "PK_estado";
       public         postgres    false    217            F           2606    19076    pais PK_pais 
   CONSTRAINT     R   ALTER TABLE ONLY public.pais
    ADD CONSTRAINT "PK_pais" PRIMARY KEY (pais_cod);
 8   ALTER TABLE ONLY public.pais DROP CONSTRAINT "PK_pais";
       public         postgres    false    232                       2606    19078    caixa caixa_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.caixa
    ADD CONSTRAINT caixa_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.caixa DROP CONSTRAINT caixa_pkey;
       public         postgres    false    197            "           2606    19080 0   categorias_fornecedor categorias_fornecedor_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public.categorias_fornecedor
    ADD CONSTRAINT categorias_fornecedor_pkey PRIMARY KEY (cod_categoria, cod_fornecedor);
 Z   ALTER TABLE ONLY public.categorias_fornecedor DROP CONSTRAINT categorias_fornecedor_pkey;
       public         postgres    false    200    200            &           2606    19082    cliente cliente_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (codigo);
 >   ALTER TABLE ONLY public.cliente DROP CONSTRAINT cliente_pkey;
       public         postgres    false    203            2           2606    19084    entrada_de_produtos compra_pkey 
   CONSTRAINT     a   ALTER TABLE ONLY public.entrada_de_produtos
    ADD CONSTRAINT compra_pkey PRIMARY KEY (codigo);
 I   ALTER TABLE ONLY public.entrada_de_produtos DROP CONSTRAINT compra_pkey;
       public         postgres    false    216            (           2606    19086     condicao_pgto condicao_pgto_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.condicao_pgto
    ADD CONSTRAINT condicao_pgto_pkey PRIMARY KEY (codigo);
 J   ALTER TABLE ONLY public.condicao_pgto DROP CONSTRAINT condicao_pgto_pkey;
       public         postgres    false    206            *           2606    19088    contas_pagar contas_pagar_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.contas_pagar
    ADD CONSTRAINT contas_pagar_pkey PRIMARY KEY (codigo);
 H   ALTER TABLE ONLY public.contas_pagar DROP CONSTRAINT contas_pagar_pkey;
       public         postgres    false    208            ,           2606    19090 "   contas_receber contas_receber_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public.contas_receber
    ADD CONSTRAINT contas_receber_pkey PRIMARY KEY (codigo);
 L   ALTER TABLE ONLY public.contas_receber DROP CONSTRAINT contas_receber_pkey;
       public         postgres    false    210            .           2606    19092    despesas despesas_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.despesas
    ADD CONSTRAINT despesas_pkey PRIMARY KEY (codigo);
 @   ALTER TABLE ONLY public.despesas DROP CONSTRAINT despesas_pkey;
       public         postgres    false    212            6           2606    19094    fornecedor fornecedor_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.fornecedor
    ADD CONSTRAINT fornecedor_pkey PRIMARY KEY (codigo);
 D   ALTER TABLE ONLY public.fornecedor DROP CONSTRAINT fornecedor_pkey;
       public         postgres    false    219            8           2606    19096    funcionario funcionario_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT funcionario_pkey PRIMARY KEY (codigo);
 F   ALTER TABLE ONLY public.funcionario DROP CONSTRAINT funcionario_pkey;
       public         postgres    false    221            :           2606    19098    item_compra item_compra_pkey 
   CONSTRAINT     u   ALTER TABLE ONLY public.item_compra
    ADD CONSTRAINT item_compra_pkey PRIMARY KEY (codigo_compra, codigo_produto);
 F   ALTER TABLE ONLY public.item_compra DROP CONSTRAINT item_compra_pkey;
       public         postgres    false    222    222            <           2606    19100    item_venda item_venda_pkey 
   CONSTRAINT     r   ALTER TABLE ONLY public.item_venda
    ADD CONSTRAINT item_venda_pkey PRIMARY KEY (codigo_venda, codigo_produto);
 D   ALTER TABLE ONLY public.item_venda DROP CONSTRAINT item_venda_pkey;
       public         postgres    false    223    223            >           2606    19102 $   lancar_despesas lancar_despesas_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.lancar_despesas
    ADD CONSTRAINT lancar_despesas_pkey PRIMARY KEY (codigo);
 N   ALTER TABLE ONLY public.lancar_despesas DROP CONSTRAINT lancar_despesas_pkey;
       public         postgres    false    225            @           2606    19104 $   movimento_caixa movimento_caixa_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.movimento_caixa
    ADD CONSTRAINT movimento_caixa_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.movimento_caixa DROP CONSTRAINT movimento_caixa_pkey;
       public         postgres    false    227            B           2606    19106 !   nivel_funcionario nivel_func_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY public.nivel_funcionario
    ADD CONSTRAINT nivel_func_pkey PRIMARY KEY (codigo);
 K   ALTER TABLE ONLY public.nivel_funcionario DROP CONSTRAINT nivel_func_pkey;
       public         postgres    false    229            D           2606    19108    pagar pagar_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.pagar
    ADD CONSTRAINT pagar_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.pagar DROP CONSTRAINT pagar_pkey;
       public         postgres    false    231                        2606    19110    categoria pk_cat_codigo 
   CONSTRAINT     Y   ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT pk_cat_codigo PRIMARY KEY (codigo);
 A   ALTER TABLE ONLY public.categoria DROP CONSTRAINT pk_cat_codigo;
       public         postgres    false    199            0           2606    19112     empresa_parametros pk_par_codigo 
   CONSTRAINT     b   ALTER TABLE ONLY public.empresa_parametros
    ADD CONSTRAINT pk_par_codigo PRIMARY KEY (codigo);
 J   ALTER TABLE ONLY public.empresa_parametros DROP CONSTRAINT pk_par_codigo;
       public         postgres    false    214            H           2606    19114    produto pk_prod_codigo 
   CONSTRAINT     X   ALTER TABLE ONLY public.produto
    ADD CONSTRAINT pk_prod_codigo PRIMARY KEY (codigo);
 @   ALTER TABLE ONLY public.produto DROP CONSTRAINT pk_prod_codigo;
       public         postgres    false    234            J           2606    19116    receber receber_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.receber
    ADD CONSTRAINT receber_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.receber DROP CONSTRAINT receber_pkey;
       public         postgres    false    236            L           2606    19118    restricoes restricoes_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.restricoes
    ADD CONSTRAINT restricoes_pkey PRIMARY KEY (codigo);
 D   ALTER TABLE ONLY public.restricoes DROP CONSTRAINT restricoes_pkey;
       public         postgres    false    237            N           2606    19120    tipo_despesa tipo_despesa_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.tipo_despesa
    ADD CONSTRAINT tipo_despesa_pkey PRIMARY KEY (codigo);
 H   ALTER TABLE ONLY public.tipo_despesa DROP CONSTRAINT tipo_despesa_pkey;
       public         postgres    false    239            P           2606    19122    venda venda_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.venda
    ADD CONSTRAINT venda_pkey PRIMARY KEY (codigo);
 :   ALTER TABLE ONLY public.venda DROP CONSTRAINT venda_pkey;
       public         postgres    false    240            ]           2620    19123    venda atualizar_caixa    TRIGGER        CREATE TRIGGER atualizar_caixa AFTER INSERT OR DELETE ON public.venda FOR EACH ROW EXECUTE PROCEDURE public.atualizar_caixa();
 .   DROP TRIGGER atualizar_caixa ON public.venda;
       public       postgres    false    240    253            [           2620    19124    item_compra atualizar_estoque    TRIGGER     �   CREATE TRIGGER atualizar_estoque AFTER INSERT OR DELETE ON public.item_compra FOR EACH ROW EXECUTE PROCEDURE public.baixa_estoquecompra();
 6   DROP TRIGGER atualizar_estoque ON public.item_compra;
       public       postgres    false    222    255            \           2620    19125    item_venda atualizar_estoque    TRIGGER     �   CREATE TRIGGER atualizar_estoque AFTER INSERT OR DELETE ON public.item_venda FOR EACH ROW EXECUTE PROCEDURE public.baixa_estoque();
 5   DROP TRIGGER atualizar_estoque ON public.item_venda;
       public       postgres    false    254    223            W           2606    19126    fornecedor cid_cod    FK CONSTRAINT     z   ALTER TABLE ONLY public.fornecedor
    ADD CONSTRAINT cid_cod FOREIGN KEY (cod_cidade) REFERENCES public.cidade(cid_cod);
 <   ALTER TABLE ONLY public.fornecedor DROP CONSTRAINT cid_cod;
       public       postgres    false    201    2852    219            X           2606    19131    funcionario cid_codigo    FK CONSTRAINT     �   ALTER TABLE ONLY public.funcionario
    ADD CONSTRAINT cid_codigo FOREIGN KEY (cod_cidade) REFERENCES public.cidade(cid_cod) NOT VALID;
 @   ALTER TABLE ONLY public.funcionario DROP CONSTRAINT cid_codigo;
       public       postgres    false    2852    221    201            R           2606    19136    cliente cod_cidade    FK CONSTRAINT     z   ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cod_cidade FOREIGN KEY (cod_cidade) REFERENCES public.cidade(cid_cod);
 <   ALTER TABLE ONLY public.cliente DROP CONSTRAINT cod_cidade;
       public       postgres    false    2852    203    201            T           2606    19141    entrada_de_produtos cod_cond    FK CONSTRAINT     �   ALTER TABLE ONLY public.entrada_de_produtos
    ADD CONSTRAINT cod_cond FOREIGN KEY (codigo_condpagto) REFERENCES public.condicao_pgto(codigo) NOT VALID;
 F   ALTER TABLE ONLY public.entrada_de_produtos DROP CONSTRAINT cod_cond;
       public       postgres    false    216    2856    206            U           2606    19146 "   entrada_de_produtos cod_fornecedor    FK CONSTRAINT     �   ALTER TABLE ONLY public.entrada_de_produtos
    ADD CONSTRAINT cod_fornecedor FOREIGN KEY (codigo_fornecedor) REFERENCES public.fornecedor(codigo) NOT VALID;
 L   ALTER TABLE ONLY public.entrada_de_produtos DROP CONSTRAINT cod_fornecedor;
       public       postgres    false    219    2870    216            Y           2606    19151    movimento_caixa fk_caixa    FK CONSTRAINT     �   ALTER TABLE ONLY public.movimento_caixa
    ADD CONSTRAINT fk_caixa FOREIGN KEY (cod_caixa) REFERENCES public.caixa(id) NOT VALID;
 B   ALTER TABLE ONLY public.movimento_caixa DROP CONSTRAINT fk_caixa;
       public       postgres    false    2846    197    227            Z           2606    19156    produto fk_cod_categoria    FK CONSTRAINT     �   ALTER TABLE ONLY public.produto
    ADD CONSTRAINT fk_cod_categoria FOREIGN KEY (cod_categoria) REFERENCES public.categoria(codigo);
 B   ALTER TABLE ONLY public.produto DROP CONSTRAINT fk_cod_categoria;
       public       postgres    false    2848    199    234            Q           2606    19161    cidade fk_est_cid_cod    FK CONSTRAINT     �   ALTER TABLE ONLY public.cidade
    ADD CONSTRAINT fk_est_cid_cod FOREIGN KEY (est_cod) REFERENCES public.estado(est_cod) ON DELETE CASCADE;
 ?   ALTER TABLE ONLY public.cidade DROP CONSTRAINT fk_est_cid_cod;
       public       postgres    false    217    2868    201            V           2606    19166    estado fk_pais_est_cod    FK CONSTRAINT     �   ALTER TABLE ONLY public.estado
    ADD CONSTRAINT fk_pais_est_cod FOREIGN KEY (pais_cod) REFERENCES public.pais(pais_cod) ON DELETE CASCADE;
 @   ALTER TABLE ONLY public.estado DROP CONSTRAINT fk_pais_est_cod;
       public       postgres    false    2886    232    217            S           2606    19171     empresa_parametros fk_par_cidade    FK CONSTRAINT     �   ALTER TABLE ONLY public.empresa_parametros
    ADD CONSTRAINT fk_par_cidade FOREIGN KEY (cod_cidade) REFERENCES public.cidade(cid_cod) ON UPDATE CASCADE ON DELETE SET NULL;
 J   ALTER TABLE ONLY public.empresa_parametros DROP CONSTRAINT fk_par_cidade;
       public       postgres    false    201    2852    214            �      x�3�4443�36�t����� ��      �   <   x�3�t���M�+�/Vp:��83��2�ru�twrt��2��q�t���rb���� ��      �      x�3�4�2b# 6�4bC ې+F��� 5�~      �      x�|��r�H�%����/�f����JM�bQ���Y}`���L��	��������}��z�VmV����-_��9'@&��d&�{ �@ �G��	�½�a5�����v�Ky��v'?ͮ[�LM�?�~�������������57���0>��П��'����to�}���x��to����/k��n��ѿt�	v�]zr�m���A�l�v}�q��?^:O�Ю�4��/͸i��pg%��r��몳g߶'���~0=̽�}'_�|���Y��᥃�W�P�q��_���v���vw����ݽt��C��7'W�����0Z|��n����v�y��$�n�������f�}���u34c�z����+k�G�G���/�������Ef��x!9����E�}�nPV��c3|����v���]��W�V�'pj���,��|i^�	{��m^�Nە�v߾L=�~ew��{���ʿ�������on �^�9_G4��iAis�����A43��4դygw�5MM��~�%���][�s�{��ƻ�L{���'�Ck�e0�U��~��y��+���gҜ���}����V@+���ʓ�����������eFK����s*�����W��Ǉ7���{�R��f��l�a�x�f���n�z�y�ߢqD'sOͺ{<y��Zk�/s8�v�~�u�������z�0�	�0���ٷ�\������^�˼�C��9y�?X�>��b5���3k@�gw��~�nw/�D���w�p�����n{g_��bh�CC�+`�Y����XEl��(`��h�5�"���}Y��sT����n3���SX-�����Y��uEM�}A�;���^eB�p�6���i�4v���e��СY��D���^7ɤ�n����9���^�B��^{�n��X	̾h�g��S��N�8Z��e�b%�Ƨ�OXc�v/+�m��^Q��ݠ���Y��̕�������Nޮ���J�����/��,�_�,����?���;?��ԻU��Y��Ϡ�$n�6�,����Hk�<�D��7��ϧ?�鼬^o����k���=_�)�v�Q��)F�X��u�M����e�m͝V_v��|3>�s|YT��HZ���2��i�=�]MM������ۮ���q	-eG��Y? `ܤ1�.�[]�'�Ukul��ow~��{��oX�8o;	,���l�.�ق����{o�6�I�X�����ٶ@��:,��/�y��
�{���_�T�D�Y��WlOi�ڽ�{Vpm}� ���ܾ�����G���X(\``_��e�)��Wr���\�?��C�;��S��<�.����ЬN_DZk�?+�j���n͚��p���f'��5P�y8{=4��L�{�۩ه'4��������Ï��6ͽuQ� 55 Y[�5?����3DM�c����!i��tߘ[k�j��a�W5�3C�p4E����Bzl�X1�鋟���cbj"�`@|��Y����\5��]�F_��D�0:4�`���PoMκ@<V[ıo��Y?>C�?L�1���zC��;f�?ಡm���
��f/�����w��mϟe��~�Ն�Y�L-�p���y�}K�,(YY�����f<�UM�Z�n��}�۝����-��l���P�Sg�x�U���rςP���R��Zs_5�� o.ϩؚy��"�4>����;�f	�ا�_����ɯz��H��m��lC�tøm�~3���>�+(`�[�ŉ�b2�{76\����Wq�eA�m�m�x�L;E��V�6k��ހ���ki6�rkɓ�%L;C���0��bZ
)�}��Zª�_o�Z�(�����jSAa!*�,��踬$^p��)[Xe��J(�O�!��5�4�`B{Ŝ�Y��hPU�"{�Q�U٤5[����uxBA��V����8ZlѢ�ߢ�����`4�x�|�o,�rlW�~�kn�#��2����NB����7�4j����]s3t�5"�#�>�&�fwuK��7�h�'��5��`����6(S���}×[����3�GW��mꨆ�Qg6*I���a\�9��Ѿ@v�ް3�Í��zRIx��p҄��/�!ߎ
;�eF<o�c��c�oR	���ް��� �oPy�0���9:+��Dڵy�m�a��r�O�����u?�-.��[��pGC��!�7��ثw�vn��6�r��X����a���V3�N���Fd�-��z�]�3�i�}Ϫ�4���{8�u\�KZٻ7���0_��z"�+I{�}$�>��6b��˂��G뽷1v�����a�4�j��<�ˤ�Ҁ
H��7?{���֪�K�7��K�Ek��㪥��_���:(��,	2�I��v��cV�y��bS�b��=��6�a��<��\����
hy�99kw�N��=~P�	�V@���5J�άl�it�NCzZ��G���L�m������4�Ӭ��i��[�B߀8� .�����-�C�� �vrڊO�C�0l4�w�הn0�Ƈ���a�/�^�N�ƷRxJ�8�Eʿm$�5�ED��M�)~��{3�ꂲ��]���m��|jg�ݘ�[�� Y�A�.� o��̚�PE��}�Q�~*}�~����>�z)��
>}������f�mYF�caDVjI[��0���K_�����O���d�v�E�U��Sj�q��W��c����J��	
_�A~�@���-5�B̴Jv�7c�V��]�*�3&��ܗ�e�~n��,G�S��ZWMKR�RM#������ب�״���Uk�ݨ��ijw�h��R��jׯP?5뷘�'�ϰ��2M��"�nC�[�煚&�2��l�I	hV�9)��f���^������`�O��)��U��:��1�ɹ�V3Xi"����D���b���X��99g�ߖؙ:9�o�0O��^�{�^OJ�{5t�؍��8h����#���R(i�H��l����]4뚐��������l<h:���������k{��?�0���a���fm#Q���?��.�ٮ�L���X3jb�e����3����7���}���3��ձ_~5r����f���������=&�Sb����i�d�if�BJ�|k^�Zt��M�|�ਾ���i�ǵ�����&F�0�n�C(o�����(�o�m39��������-��XJl|�?���������ӟqv�;�����['�6�2)�d���9�gS��_-~����ȑ#Oo���ƕ
)��+EV)�2>��)�"��)�Ѥ��R���%%>����Rb���#�W�j4�M	��9����<�Ҽ�"�Zi.����)a�t���"��5�xz�'[�R��47����/O��`(%b:vs3��%�޷+�I<�@h<���D?أ�N�-*���>�}E��Na(#ؙ�H:%Ι���N���g��̪�D7JCÛ�A�`3%�at��>%��ز%����GH��7x 1��Ƣ��)Ҡ`|������&�Y��1��a���yԫO����~��Q����;774,�i�=��vn�p����c6��V��:�i��n[gQ
M�V�[sŧRQͯf��P�����`�,�]�����g�DV,�Y"��e����YmgĤ��~iâ��G0�u���J��yL�o�B�9�[��A���s$sq��s�`��w^��Z�n��2��Ykho���:NX2�̙�?��8#�Ө?��c�Ei"��&�ٸ��^�$��x�k`,��܂Ϯ����ކ��	�)Z��ԯ)r*��7E�����b�ie��6��W�CM���DF�1�GaԢ�[� �Ɉ��?��A�����wN �S$�Aλ��ۭf��[0�ч�j �`h��!#�\��x>#����8b�U)>�Hr��*|~�Z��O.���3��E�W�E�k/Ꝉq��3F�3�Y&#�����FVB�L�nw�j<��K�*��9�Ԭ
��\V��F�@N>��#V�d�ne�I�u?����Y��ke�v�
�N��3�I����4MG��xFD���*�    �B��������ˌ�r���3��"e���Y3'��z?ꮄ���ψ/��1�6��q{�N�735���?{����{<��ܦ����G���y���nǵQ��a$� ]�a2!#�o	!�}g����Fk��2��{���ʈE���(ˠ4# Q���D�a3j��$�^#���DՕ��0��Q�ZK�!TF��".
d+����#'2B��C7n?۽Z����*���53���/`�NX����;��L�yF��J���l���`q�?�)�O�̈a�S�ĸ fu3��e3�썌P�9�>�[�3��%��0s�������g�8�f�<���v�5{�-B�e�F;���l�����ĵ�p�.;'��bo��ii�hp��͉p������.���r"�%����FN�
����6�i��(w�W�-���q�;ȉqZd���!��	s���0W��.	H91n1e�z�c��`�P��k��f)R~��k��0�l=�
9��籵�j!�<v֢��1���-D�9�����Du��9��h�Q2G�f������.Z@|�C�����0('R^u7�8�ac��[�/���r���Z?�	�W:�Q�+�x�d��X��"`8*�wN$��o9�G��_,�n�����}��N���>�[�*z���Ă"�����-�A9<�~�V��c�W͍u�k�7�tQ�����2'��ń�ӿ���&׎M�''��N�W� �A�e�Tvݢ�γ<j�㣉��x����4��g�9�M��f����8Z̓���d*}��?���IN&���G��wh���m�|�O�0,�a%��X��3M~��p�S(9��Ť�r�Tդ�T|�\�	ڇ��s�����輦|�����y�]���õ�M|�^�}�^�P��o�/�ҋ�.�ǧ?�$��Ck����њuH/"�+r��췟ar"�Ɨ`�}ZY����791^긎��SJVN��F�q��:쓷���{���j��:PZ����^��"�8o��{���y�蜩����'����k)�mb}HLL�C���};�@��5WU7���v���Q�s���2��/�>_1޴���V�t�U��.������[t}�k�9�"��#�r��ǰD��M2s �t�9�cwC�&��܌���$�>Z?Q�?jTY�?rD�B���ݟ��V0B7EC������Y]���\����Z��A4�ԘƠ� �j��&;�V9&��9�A`/�1�?��m;��Q��bX������ݮ����wc�	�A�o�����|Q`��34��v��ρ�	�Avm�RTC�5�SV�؍L�5�.��i/�=���	��A2�>$
�bS`�_aa\`���Ps`Uxx?v�q_���(�1O��h�5M��w�*u�����EꃌN�HSI\.Rڧ,M�qJ�00GE�4�b-x��"�-�WP��ms�	�"�)�"��������2��Zd)5{W��&��޴��"�)�ck�`�����vc
�^"�q��U��Z��DX�)�*��[<1wﭕ�&��S�hú�"O��Zɳ(u셊<��?q�B�V�GE^Rޭ��c�l�cQǜ�eR�L*d�}�Ȱ,h�z��}ip˂�|�$SQdA�2T4�Z6߂���ni]QҔ�o�-a�0�u{?Z�ON1+�l-��~Q�h	���`�ڢ�eoF�����E ��S���{�le.�"��rd���Eh�1*J������YOqQ��w\fYT���W.
2��{v�VL|76��+�L"����Y|�UK6a�VT������E��1iQ��SC��YJEUS�}�׉������"C�:����UC1;��VV��S����Y�	Ʀ�����ߖ}u!Ũ!hQ����J)�(jZe>cݹLI���4i�``L�Y&�I*��ie���7�69�2gˤ�<�a�$�L�]��T��I=i:M�K��d7�X�0��b�5�K�R���t0���3D�X�j��Q:X�,��^��zNe�D���a���i�$l̩���a��u��v�sc�:��.(lȈ)ϒ0�	���N��U4#{gTY�|o\I�h8�c��h��Oؘf�J���6T4�fq�Z&j\t��C�t��@|U%����w"(�AM{�����(�ӟ_��&�4�RG.�W�ܠ�6j��$���3M5W�"��N8��𑕄��V|�����3ĥ��s��� �JB��=*�8b���z�� r	��"��������<p��eI������ ~p���>J|�c�<j*�e��������U&cI��L�B"�e�'q��ii%��TaIH��CIH�,�z���r���-R�+�P	�!� پ�y�Ya8Ɂ��$�@l�O���2���|�D��]j�%��22�-	0��`>Xۓ��`�JIl��vb����Q���
�JB͢@0nzج�m��J���p�CkN��y^ZCʩ�""�r�b�!:�<��~s�B�J$�)��&��~�O}��>�q}�����~YR���gn��(�|�n���g�Ad���-�����mTQ�kf-q�F"_���>rI�$}���"oظ$�'����W�lP��C�5q�2i*�R�f��~��J�r��Ac�Q��|�ɫ�ӯ�E��T�_��v����J%��QV�Yv�����gN�!��íJ�<�H1ެ�L�]}�0���L��6�U[sDU���I�n����f���~�`:�jkf#�
*�R��	6��W�#�}�æmq9�S��䩃#�[v���1E��A\)��\�R��VXe�\M���5����W�Z+/�wR|�ZXy\Ľ0�6p�u����������Y�!�U�ϩF�v��Qyx���g���<\|����.���EW��%\I~Y��	�0c]��0��*�CbA���Sm��R�y:������M.$O��Rp�Ja�)C�*�A�7�k�2�ca
&�6a_��*�i�h޷�Ԭ2X66p	��F�#r涤f^�ǚ��*˩Cv����Fۯ���kUVJ?MvTl�萗��V|��\=�r��u.l��E�`����Se��j��L�x[�0�ߥٖ�Z-o0�U�������{�a�~d��a�bV���[��yY0��I�� �*\�0ވ08L����"��a�1��U�M�����p�`u�E,\�3$��2�Mӗ���eE/l�gqOU�Qs�s�N���k�*��>�U	/�G�a5V������5�H���NX��Іᨊ2��k�̙�,�擙�y�,'#Ϊ��Ӵ�aj�y�[�廾C^�5�k��A�Z}D�&�N��+C�z��~p�l*����Q��F�[-�M�����]R�GU���uɪ���	T�boYӝ��B���
ƚͫ�[Z�m��P.Vu���O_u�~���:���	�UMO�6�D�Zӏ{|���TC����D����s�H��u�u�$�YW���:�Ao\'i����y4�6s�T'�zЮ�:������	�~@"�����U�#.�)L�浃��@
�Ӌ�,&lr�xP�c�uQ��d�ya�ˤ�p���#���I@UPem]u���r�c�S�j�-YO��~������M�����k�������$.�>���������'3�j_P�悤��OU����}EE�E~��:�X����dR��S��������;v��Y��ey�ʁa�ǖ�+�7�CbsJ�Ͼ�Ҹ����-ԩ<yh�+�xuJЛ��3��>j&��h�k׷1�ANH��h�$���" ��G��nf��+�B�:���M�ݜ]Zg��ƍa��Mʩ�I禡3�*V�RguT,wu�H�������o��d�����h�����fs��L�i~��sjM7�կ��aU�����~��52`���7H��EB�tu{�=��g������)�~�P�T0ZY����VV�Ҟ���`��I��Q�V�pިPuT�r�u�L����ߺtR�N���\����.�    P�P�.s���ܚKg�-��범��������=�\���w�Rk��"Ջ� ��`Ƴ&�J�%ڞb�6`O�K��{��yEA�ˈ,���zz� �L�(17�&���fv��R_a1S�7~N��}|�I$��nHNZ�pe��u\�5��	��ڹk�&*b_1�.���޾/��L���sb�i`�.%z�![�䔲�m&�³f�u&�<Þ�?ՒL�����TL�bR��0/D��O~�_�Zce0�
�E&���s1����DOq�
�q�tB�#���(���,��t0;�Vg��z���D��&�⋾SKp	�.&_���X�;y�n�
xP�C�u2�G�5˭�Ŀy��yP&ݍ��n/����6�-+��x�0̵@L\������q�r\B8|�<j�c2�z��+��{����&2<��%��&��L�GU��MWD]ܢ`�2�Nc���ab��g{2"� :.!4r VS��zǽ�	q��)#̔p�'�ѼN��~`��t#��%�a�n���+����T�	[�Eu,"�b�xU�Kl�`�$.^r|߉��ti�q�b2��l�EAl�l��I� F�T�PM�H.�eް�����[�k���\���AGb~�D���Y�j
����=�"e�@V/@��fv� yi�'b���nw�~@��g$�/̈́�W�-AH���T�*��p!�4>h�6_��-����i�պ]|���ì���&�.e�T�V��sQ5����ai=�jW:E�I��0�h�h&�����k6i� ���j�<"�<�xT��"|?� ��2�,��Yq��������UC%���u�Yf�:*�a�3XN����NS�V�
���G� a9/Rz�®H�W<.���!���]1�8*F��&�%�1q�~x�}�/����̺�>+qE�UC�Ӳb0{34��v�pw�;�NsG���������5����U���t��$%���u��\O�ڝ\٫#�:F* �a>�sU�,4�^]��� �(�>�1<��`D����A3��1:���`������1.�Ԭ�O�8� ���a\�[V0c�O݊{:�jh��1D���߇ws�k�sP&(Ê�=:����p����_d�&�єnU.76s��,&�vϼ�g�)C�<�>����L'+ub�%�\8�t`ԁ��=ǷL](�dȳR�ƶ����4h����(��k���S9��"�ّ��w�=Jˠؐ@�
�O@��B!(��WIJ�(��Im@�3���,�j��{�r�C�X�n0A����~���˨�/��^N`�^V��_�"��$O�B�ɹ���}.���K}�w�Q�$f��4<(Z�,����@�ò8mU>�����C�~�y�ǻf�F~"gR5I�A5��Fr��<��IDX�@+7+W#8���3�DM�S�S]��	>,��L�?�ȣ���($NSY�5_Ѝ�: �b8���g\i��Z��n�(��)`�aIL;0�H1�#-X��F�+�@DNZj)/�����2�
{��n��|(+eq����r��Z�{GT���k.�\�:��P��]m�r���sZ����
!6��o���}�Ns ��+�|�;�Wr`�Қ����G,�طڮ� +zVP:��V�i��AMw��<��,�v*��������*����@;B�!򧥡XipLe�߅�,��PN)�������*�u� �#9����������0���A4B�%on�]�=9�o��������2���9؆/9��@�@>tX����{{�uRQ���|�"kE˃�D(gr����:Q�A�}��L��q��2���b��)yx8� �)�W]_G�ꃂ�U���Z�&���Ȋ�o�.fۜe�-r�, d�
R#�FwԒ�ldeg;�!�g��C�ȧ�o�@u�BF����'�^)�n�8�zݸ]ޠ@�"|v�џ��/��C����
c�v��ir^����+�	T�aS��
`�U�[�x[u�i��+����6Ĝ��p^!�"z�#�i��k���ܸC�yE,Jͳng���+T������������+R�6U͇x�*ܟ�f�0�d��y)A^ �W�����F���8���p��ߚW ���B�!��y2a؃8f)��4Ez����]+��$R�ͽ��!͗V��n��[�����*���.薫��;(ιl�(��ڦC��脩��;P�ì��N��!,"�Wd�!���*5�y�7��c���WT���Hd��<��hg 9�o�yŏW��rȜ�3~^QrA ���v�.DT{f�;��&F��.̂Zi��+���~�o�����T톆)����z�4ڀ�6po{E5���{��(�y��y�8�}��nEr3����n�J�"�E�d?����y���V'�p��;����:V2�*҉�*����=��[̸�}T���)����u6���n��gmW�r��tVj���{�;�W�s�\k��
^1�QN���E�W���T߱���aw��u��`���YTq�/Cq�A��a&���%R�U������%���h1[0'���%qs"BD�ݼ§��I���W߿���t���G��er�"���Q�=��{^IP��I��&�2���O�lQ���eh�(͏K���(�|�1B-T�
���ͳ�&M��R�ҳ�
�bsr@��R�_�ݟ`�BK�\iWM��Z�6rƔ}^�ss[�I�#�\��Z��S���kO�A��Qw.|���F��*@�3x������R�j��0�۰"6����ԅz�L��Tۇ�F�a�'����e��ť�ۮI �Y:l\
ݮqB�jLQ�)�-$:zm0���%�	��_���H�*L���q��vN�T���r�0UTv=�y&�K��L	���U
�B��D��h,�REb���J�~Ԡ�-��H�(-2�LC�TA���M
�	gr ��(�L�.�r�|�5� ЌA�v9�V���f_�N'���^�@�����тV3�TT*3��-�fue�B�F��A��`d��U��*0�&6_���f���h�w�P��"5�GQ��l�����V��Y����ӯ�6���(�haV����%��XZϥa�갨�X����5ɹ��-�����"M7�T��AZ�Ũ�q>������S+j�ˁ,��P�\v^/�0��yׯ�~�eTȡ~���Ǩ�~f���{���,e��5u�����6���X� v͆%�-M�$6�JEsuB�{dSAG�U�T�?�;V[I���py��>K���M�[��P��t��^)���y�ʫ��,�f�b.�W�f;	\�U.�����)�B���e�!�oUI���� i<f��~gۨ��al%{ɻU���ff�s���n�4�|��K�`8SQgQ)�A�/H�>u�u)�~�}M�1�4X/�D��7��@yFq���3*�V�M�W���,�|5�B:0�M%:�h�o6�(ey�:������/�Iy�X�pKe9)���\j˂���z�2�$�,�զ�� v���ɾ=�rp_7���ą��w����&NP�5�I�K?]>�3g�bR�������*���^ll�*�{P�AǍh"|p�e����k��"��4��E!5cfMv�kCI�귘0aed.���-�S޶�ܝ��w�O�N|9���d��S	|5qN���`x[�0+'"�����qY*g���64d} |���Glv�[�7S]� �����&�(��3w7~�C�Ʒei�C�;<�
�oP�؛yr:S��'?E�����w�2E!��6-����5�}�O�S.S��|����].��G/��`�p��%��L!�a��[(/���3{(�����"�	7Ң��)^u�0+us�2f��|00����@
�A(��#�2�*�@����ס`�I�_ �˙d٤�\��~�ǊW��G`˄�
R�3S��2�)q�\�~3)��f�),��4������v�I�W�3�+���c`�s�3��r�~X��%    ���r�AW�x�n����
q
U�(u�ޡ��V�L�/�?�:�����a/��œ���?��O`[�S/|x%:�2��٢���,������G��+T��G��.�5d�w��s���Ut�UÕ�?��Rl��@]玎Ws ���x�U.ywpo�}�}��R��W�p7�Φ������	�m�N�%��.����0j��#��t �uM17�\8g�t�:�H|�*.��X<:�n���˛�m?�%��!�ή�td
ۓ�=��;�����N��J���B���1	�:����ǃ��}���t'�B�u��A��	VG�Е��7��!d';P�I�esP�Qܷ�!�>�<3�V�c�5'AwG�@��R�i�1��P�Ұ0 �f.sO�rg*<\q��}����8ک���>%V<8���m|�`�s�;A�~��N�Rx��w��N�+��YN���х���g��/�A�4w�s�v�#�	C0�9$$L�]ІCa@p4S�)֮"���.�W_&��������Д���@�w(m���@}��4Vmo�^Ё�e������y��s8����w.�5�[�T��[VtJ/��u�CHҜpc���-�0�ݩ���w����
UF��o�S�T��P,fJ�k7il {O�?j;�y>��]�lN����IT�<�6=����݁�
	Q��1����e=�s�l�y!��v�'�A�"=�l���,ر|sy-���+��;��I�@Y��Q�o�V��N
�Lj��tҒ�Y�P8�~���@KG�x�x�V\y8xv�'v�Ah�|u(!���I��z���L�b?���ʻ�Nn-��Q���^	��[j�������L"`'WA���w]�d�TA{���QTIP�����M
uO��
.Ȁ�.<�ϯ��ʂ�ʃ���F�ウN]�l	�ॣx�i J:*�K�vĲ!(���^��ݾ_��"X��޷�X���Ɲ��:�u���!�*�ΥX��B��'ˁ����-(��b���>�bz�v�]R;��������|-��:�k����>��T@w�2��#��B[�rk��]TG:һ��t88�1���]E�8@z'y:�Á��:a9��A���1d�u+��dv'&5jd�!;��ի&$�F�]5<���Kev�A��=R�p^��F�x零x��e�A%���&�Y���w$�t��EW�}?���<�>z�Β
Ӂ2/�ˍ�����m������v>OāS��=��pY��f-Z=hp�g��u ףn���J�XJޒ��|*��CZRש��<<J1����a�&t:7���z�]&�0�I�0.g��e���B�>m1��$���
�)�B��h�Y&ե`�`rt[xPt�|m�Z��oPv��_�W��	�_����
�� ']���<��&Dyph|�G�zT���<�z�"/'9Ly0�y �pE^�:~E:e���|��F���qE��*2[���fՔ�V��%��-�Ť��~��\(�Y���,�'K{��epc^o)��G8g�ep��㻆����e��'iq7�+�|�iz�(��	әE��)[q֬(��\����J��!����7ۧ�hu�f�\�)�����כ��?U�8�)�|R݆��IsׯyM5ѭ*x0ܠ��&�?�a�u0�����R��hssc�+j5���E��>������Q�W�3��8�/�vE]�-�����}�I��٭�{���D��$���������/�=5����w�I��J[�VNJnW&�9���YU�N�2��t'�UƖg�&����g��$�<��7��7Ε��W1+l[���喙J����i���՗�_I��J��NֺW���W*�a]z��S�a�X����Y� �(?�+�|��?�$���O$]�ڿ���ҿ�ל�)�Du�L�01P
�Q0�BK�QRM3������V�Ua��Ux�a�\*8.��r�₃�v>@�T�p���I��L��%�w.��1�e��p�X���v���u�63�G�K`��4��g-[����@w���%K��܁fٚ�X	��ٕY�{�r=��:���q�%#�2������e=ܓؕ!��tl7�����X?�޻2D�u�0�*C�@�4-C��=�
N=��!�0q6s�!���o1��"��+CL�0o�gK��u�-;�]��p����\�H³��Ԙx�6�~NJ�je��GTk�����9�L��_J�Gxv�pU�x�4#�C(��yԂ+C�Gl�k��YϷ��a\�s�e�C��1�=s	�2�%V*n@W��dD����2�%������	7R�3�u�
K^�ko�k�b�稣T|bZ֊����Ra	�񶃘�(�R��\�Ў6):�2pwE'�����9�t*���YK;��
VD��X�T�"R>��,�s[�E�J�-*~>�)�,	]���r7�;�R�L�*�+k7��+�ᩕ���bh@�C@U մ:^*z��;dIC�J�.Њ]�:y�[��i���40qB�He҃��rw*,W�)�?xH�▩ \{�������'\uΛ���9��
z�f��G7m?����޵��>�ÜJ!̢�Uh�]�HFM�m�i�J���.o�R0�fx_u�p��n��=O��"xJ�#�iPbr�?̂�)_I�:]�U�j�ծRLi~^+W�Rh� ��k^�͢��O�b�7��b�\)�y�?o���`���_U��7���v����T��[f�W�m�qn&��Jq*%�P�X�oSwW)�	�ˬ�\[��+�;�A�� "��+ʕB��t��`μC�Z)Z�F�Rx�ؖ6�q�%aG�A�\���l����%�G�|����_�����_���`Z`��G�r�p��i�((�K[��T
H�"Ԅ�
H&��W�| W)y�-����*�'5�Rq���N�B�w��Y�J�ȻfӮCZ)y�n��V)�S�q\) 	�R���� zW)	Yא3��C��撧�v�"^�~L�q����N)�x7��K]�Xc��)� �:!�3�>Tn�����BYzv*���
r6�v+P�6����R�`�ocx��۞���3�R�UL�Q�Jo��>kfkU�pH.2(y�>m��Rtp�%�����@�ݹJ���1ܯ�MV�+���]E�y��q��σAI�x E6��W
NuB���2:la�"�a��R�5p�E �_e���5�lY.�qV�����v���mHů��~�5e!=�.}����lb�	�q��=���C&������n#�pW	�O�TI��J�nr�[p�c{1�R	�O�G�T�j���Q�ւn��ѝZ�-9Ҧ�e����^-�桼�x�|R<���B�@_�kʠ��&k�4O���^-�G��@{W��RL��������Iv�.����lpd��]6�\��]�T��#4Ť�7�Mt�t�vэ��烺�E?��U+
��'��r��FI�\���O.������h���f�j����G&F�><FzW�`r���}�x���:֎�BZ���t,�"��c�Zx��� fQ�n�K�&!���ga����Yd:��8�����uP����F�y�yX�&���� l-�<��*`�����\bѹe�Nj�_v-�\.K�Bʠ���Zh�!k�k��Y�s+\-�49�H-��v��~��悙�Zp)���d�!�I��P 7��6>[������P]q��r�l�,�rW��ѳ1p��B�3�Ǳc�ZXz6NIԵ��\����g���0­���.����hgW-۱���ZPwiO��j!��ӪK-X�6`�Z����mJa��47Q\ϛ��FAkHs�����*�d7_C\��,8�3���ja�y�MV�|�������F�ZH���v����A:2��r��k����	\��f�9��Ȟ�[.����q��k!,4S�Wg�s��E�DH{�]�����Y}i������'փ����S{aA�
sK|����&��Gkҗ�E��R    {?�x����PNk]u�N�Ә�����x���k>I�Ii��I�lvC��J{cp�$i��^�r�'IpaXw:�'I�9A��I�P�����ţ	}�w�qU�'Ipg?s���%Qi/M����V���p"�ąw��Y��S��t�~����΢)�>q��}?䊨z��O\���Ļˏpj6uP�\ȫ	�9��k��C;��9�'Bߋvǎ
�M�b�C�D�k�[�>�"?���>��� �V�~uV�*��!�'Bߋg�D�k҆���_�5���q{>�i��pn����i4�,�q(Ţ�O��RqQ�'Bd��z/m5k��'i=k�����,YrO�O27�"�)�~�>���,x7��tp���Tm��>_聚�����b��{�(�����@[d|"���b��'�����G��`��E%������}�Tғ�w%�6�}�+iri���'����}�A�!U��}���DH��G-���'�^$$ֶ��*$�^��?n��DpL%��@�.Td�$��D�N?���2�����+�I���ʠ�O��7�դ�H��\D���%��Z7�������%q@���&.���A0T�"[*b��O�?!fbZG��#: !�O�#�7��D����
�����}"Ц�m�W+̦F$>h����x�%6�C���i��O�MȤ|"7�V39>�CC��\x��xHu�f�v_bԨ"ח�U2^��T�O�4*�FN��YT��>�����R���>��Yy�"�'u5���S��Iw��%I����U�]��/玽K���lL0X��]��8�]�K��3�d\���j,8���B� ���f������	��mʻ�Nx-�-�?Q��q��;�
b���Z�Q{'��Z���1��%�N��y��;��#�7�լ�GC<�h�;�D�R�k���TE�O�O�������ƀT�=�ح���@��kF��ky��M�ԓwB�E��r��;�T���0��N@?�NK�	���F�KP����v?׮��� �@
2,{�Q!�r�9�]Z-TB=��Q�˒I��me.*8X�.����e��q��]n���/�5]��ԅe+��x'��Wf'*Tȍc���;a��v�8�W_x~�w�n)9qޯ���;���9�y`��>�������΃�.n���X*���چ���\Z��bR�w���!�qy'_0b/8�.1�w��9R�M��ٰ��4;*���P�wB��a�� �WX�3�k�g�IB���v=>�ғ���ۯ��(N�~ծ����_��#o!T�G`"�N�n��u���,P7ծ	�4{'@'Hh�rj�@��Q)-)U7����dy'l���N�RNu8��Q��3�9[и����	ڧ��	ԡ��#TYT��u�_�Qô�"*�l�U�B��f�]UME��,ԓ#��96M=9BWO�,j��,���>l:Z>[���*t{��1�s��i��-CـB���a<�=�����Vb�̛��%/��Ь�t��B�������ȵ�}&�����zގ����y�r׾���_u=�=���+.8�w��6���f�\r\�&���%z���7�;2g.�^��������]:���S���Ƶ�J�[T�¼w�j� 7Ry���슫޻�Y1����zW�0���_ԋ��͸�{^�u.������Yp�}��<V�ώ�,0�L>����Bmz�ы��Z�lQY�u������^$D(�J���?�}��2����;(#G>�O�Q�;.�}��a5	����h���>͏
�a�O��2u�()Kv�:ES�R��ާ�����:�Q'{�%߿�ݠz��������߿"��x��߿�B�c�e�/��NF��悎I��7^T���u�NO���)��°���aO�xL�ir��)6;��k�Q��W���/�K^��^���?�¶�3ph������Z�b��y����i�,s����Xɕ�P^?/G&J���}1W��i�!����}��� ��n��+/�{M@|_d߽d�!��=w{_�uv@#xXoEyt�6��,���]���0����Y�}�84�C_��ra��E���*���ۭ�M_f�/��/�祗ZE��<����<��76�2S��򸮖���\V����-y�W�
#1I<.���=/;7@�^���,T�����POUv\<��|�?/i��W��B�a��y陒����x{]�����Ý����Y��������Z�짯�q�AGX�ϊw'!3��z�"��󅎇��]_=Ẇ������W`��.��U���%��w/���M�L���$�7iҧ��7�-?M���f�I�I��sQ��d���/V��)O��s^4M�ڽ��j��k���"���O��2#���B�����>���������`g�i���_�q�C�Y9y�.��R�*�a�!�j���K�> 1��4�ޡ���f<�i�>���p�x�0W'���Ϝj�6AA}X���vjd��4��H��x.!w�Sc�	�a�sY�!�f;�Q������aעOCd
v'��OCT�匎O.gųm_>!�O���������§!bn#�OC�ܭD��� ٔ��3�����X�1��a1�N�[���7��}��~=M`F7_77�Z�l������@@�S���ȼ��b����QB����-U�i
���'*V��	��Ppg�yM!����u�l�S��V�[ت�˰����k-�
6��7P�&�+�_qgE�ר �Wq�}�7��L&�H؟�X"U��/�,f%I`����9�mŇ�<>S�s>ULx=���@&�O���B��7�B�}�Q�+��H�/���
�>��Q�>l�w��77#�um����$u�� �O�b:�j�Wf�Ul؊��%��#�Idm\�HYp���T��e�
��1Y�ѱ�E�#�7�E~���5���S�H
�*^"��)P2e{����4�/������Y��K}u����|�H�<x/y���CqΧ��1�d�"�u��O�@��K�@�hR
e>al��ܸ��*��ԃ8�m�
a��ݮ�ݺq�#��Q&��v��T�a&zU}i[2�p�B�O?��*H��Q�w�y~f8�~�5������jԭ��3����S��);�i�-|�mG^���IFr����U2���.F\d��p�;{R�vf/~lخ��	I�!Y:!n���d'yX!- Oq47������^s=d��xf�CǕ rpf����|�Yd|�$��^�9����Y�����Y8�j��&�DOBN^0N��L��1t ����Wp�ɾ�-x?��d�<*��������6�^r!90�2�(�Z_�,dU{�mf��y.3A]KM�c�U$��^�]��4���j��ܚ��V�Em�i,�!�mw�<R9�����zRlB?t3q�'�fv�i��T��_5�����`���G����Nd�;���޽�t�4�i�͑_�z�a���u4?�[��K�3��c�$ө�篓�<i3Z����=d���SC�N�o���19�<šv�6�_�AW�CzL(�)OP$Cf����-N��2�q{�;����aAN�ϭ;�FN�#Rxcf/.���N���bꏍf�ȑ�1ɐNv�,0⁍ܓSk���
4ѩ� nޒ�I�	m /�d�4E�
K�d���+�.d�"wJ>r�'�e�&���o�L�+���MJp��{���j�z0�c�G̠�j����d�4�n:�ӑ3{�S8yn%��i�uLN����4�C�*�3��܎Kzy	NB��J$O'1�O���p�FA�1���P�J7� Ӂ]cEg8��ˏ=�TEw.�;Ѥ�13���t� :��xOV�,c��b��2$&f��d%$��f�`hh6�j�{��F_��m8}�gt�Br�.���wY3��q�;2a:��*(��gB��+`�ֹ �p+�F.�_�R|�K?�.�Qa:��~���#�+(8ZE�~�O�ۣN3W��25�<)4d������f�CT5Odg�_�O�6 _�    DE�O��mQ|��
�Ѡ�O�3)���}�����w�E"��ˇ���,+�˴�t#�����WS�<7�n�	�>ws�͉M�T�܇fHe}���p��>��<��*�Bb�\�'��\A���~B��*�.���+E ��\
��u�UUP��r�+d��A�@�+`�<p^1W��q��=��`��W�8�SwG��|R̐+Y1�bJ���3qp�'ug�K�m��ܙ��i�@��I���w��+RvB� vE*�]92DVDs�����ȩ+zd��gbfh��ГdUP��u�id�ܲ�r7I:�)�y�u�"���졏}J.Kw�1v�d��m��;Ǥx�SQ�"�L7(�
��{2u�/&f�y}e��2��H�	�N����7�t)�GH�E�[�0�>���3�'�b61�#����^��RWL�x�<��@<�;�F=O��|��7�(XY)�ı��,�S?��������8��L��i�'�gJlQgzrvBf��n�"�C�|j)�<�ٝ<�ﭦAI��R���8aO"σ���ѓ�s*�=*'����R|m�YU�T�ѓ�3�r3�'�gN>�5��d��'ʷ�C�ʨ��V��T����u�a@���9����{��=�~B�����˾_~���I�ɂQ;�=�?���"�n� C�O�|`�;��r���D�9	ſ�͓�31���B�\�[��1Y>!�<�|FQ�dQZ��<�w���h`^1���������x�l4�@v�|�g1��#��Ost�t":�ϓ��T '�q=y>�oT�,�;=Y>sWލ���$���pw.AK��5�=p:�'�g���J�!�g6���!�'5_嘧����x������4�tc��f�3�:sl���3��g/z�t�I]C2uF��[QW��c���5��CFNS`}[�Hș�͝������������ؒ����H+�Iƙ���b��V7�U0t���T�ԍh~XY/�a�ܫB������s��x�zqb�/��6��{�
���8�}!T��8f5B拆�Q!`>h�B�&4�F��8*#�j�B�|�1@!l��8�=� ��������%�x�4(B�X��<�ȃ�{�&;�LV�w��+��ܜ��D����_���c�qx�g�q=2�*���c��j��ch	_����|!�]`]>�������I�P�Rv c���۰/E����\ֹ6�>���`Y[�V(hwض��B�*%7��p����m�޸�j����;�e��}��Є���r+
�h�V;M�"N!���O��wq��~^v�䇾r^���G2�i��7����W�!�=�B�Ɍ�5D��y�B�i�ṵǡ�酝��7�b�Ο7��	6�o�d*��X���#�BX9��AeT(i�ل��4L��ϘU7Ƒ,.��p��N�B����ke�����.p�R+U?E���ث�S�J��})��g��fU8�֗B��!���������	���·�4p)���IlU�K钃B�e���ҹ�P����,R<�߅z/������h�ߞ�W
��310p�H�ԕ�gg�����c�J���%��K�q�P�T���_��U�w���$���0.}�������;��Dqq\�BRz�җ�)<\��z~͇�fD�W��Y!W����2M�s���e:W��i�p�L纊�$��d�5�2��j9G�x�nϠ�L��^ٟ`�8��,��)ݳ�*��Ra��/b�_*��8��u�XD�8�(�Hye E�+�P���R!����|��Y�KE%���WyP���RA������'2���|7tU����v`bO�p�lu����{�JE!�����R�(�z���gʴ�c���"�eu+	+ِi!���T������@y��ɈB
,��9�O�M�o��<96����!�}k�:���E��Mz�bZ���][=$e�bV�@K|N���~'ŋ�~<�6���ׄ${�R�m�/�@�C6���4�O������O�HAa��yPh����RVc1�p)�ñ����4
�P��n��*	���H�Iy\�Ǔ8*�VL$癠��������#���m���[�ɇݾ�xSپ���B�y�x��#Cfqx�'Cf�"P@0w����,�cq��d��#�dG\��2����δ�H/�I�Y%�ɜy�f��}���,�RS��<K\QMWD3ӈ��,�C)��Hb��pI>�X0�e�U�@��\Uѣ�����EX�o�"��r�+܂����r�LfM���5)5�{�ɘ᧵���uV��H�Y�,M)�LS
�kiJdwG�b�>��ɥ�(�gv�w%�fq4�4�XڟĤjF%"�v��k�iƂ��6�\�QwрtѓJs�Y�.K�|��9Gw3`����x���܌�x2j�\�hs.�y��DǾ���b}��fpК'�&53�4��?`X@:M*8Z'�fA�w����r��;�b}��ܽ�eNfM���8i�̗�^I@Vri��K(�B
&FZvOMS�ۇE�K�C�F��:��i�f��;��ɘY�o_4�&Y��P�o �6��j���BÅhpwv|`�Hc=��q�*)4��O�C���A=��'_��L>b�0� yjN�EDZ�ò�1Mz��="�Q�i֢�!�f��%��hS
���ɴYL3��qʞ,�˂i��\��%� ��C?uqM��bqv��f�I� ��)*���_v܀V)��1��C�ƂiQ�SU��$ �kO��n�"�/�}ۇ/D�e�B��Fߔkɏho��+o���D�93�No�R ��`�"L�L;���VX�B�}��d�����e(&y�8l�
I9ڲ����kB!JP�w���He^�AY������<;4�W�\����/g������CQD�������N��R��n���"�;����`�8ΝV�S��i_�8��nT)8	|���I�v���Pw?6lj
F���lm�_�j5�T)��k��^�=մ����٤�VxqzC�j��7� X_+�|���V�Ň�VAZU�4����	��EY�-=���0�����^�3�"�C�T_+f8�&�k���^��b�X4�Z�����Z�)N�S��A��G�؀$��5��1�"����͘�b2x~eJ�kE,ĠQ+,����Y+&qy�����("0W�j���k��X8!�W��o-�'�&���I����p�|
֊L���F�r�kA��f=	��Yj1Q��m�s����U4X�O���9/Z��~�i�V p6�-@�Vp6�X_+89����7��8֨�ʌ���7���3���MU#h?��P��|�C����B�x�ͫ���D9,���UR#�LI��^������enh-ܾh��v��h��Z };b�]��n��먚&�k��>/�|j�s���$g-t6e�-��uka��F_ЯǘY�b� t�l]䓒�CuQD��@�.ʩ(����_��:�Z��$*f�Ju��&83��o����=���e�}����`�~�h�2X�AP]{���&_���qy���H�T�����/l��Zh�<8@�Q�P�5'\ŎW�t�i�Ѹ����4��K!�I���jf.���=H��m\���c�X�
���̓���	pU���Jc�^�[j��ʙP��tH��
�RP�O��WS�>K!��t"7���\��k�
�o�.'m`��v��+6�R]Ϯ���I��1S'M���k&��I]'g�
#�jz���o��DP����~KE�\��4�_2c�i"h�^>��b�}H��)z�8�Dp�h�aC��EP����@�Ϊ;�9���]�{Rc���]:��4q��}8���Si�ʥ�������=su�bI���i�-�P���sQpV�~iMYi"X�&���������G�����v��i�IK5i"�W!b�4�Cd��hL�<�:u%M�"��gyTKOA<X�4d\    Nͦ� ?����z�!sQ�7.&����r������ _	�?l�G2����^�u��	�f��&B|�&r�4��,�D9M���h
_�V�&
lW�(��S{QT��h���lױ@n�+bH�(0 V��TAQ��}�B��P�q	A{)n�4Q��#՗�J�(h���Ǉ4ˏL���V�4�G�P�3�n�ڜ�&
 �l�q����]��ȵ,ѱ�+�y���c��ס���#1u���A���VD8[��QAv�N���tOr�k��6���LiX��ݱ���]%��5��w���-^v���k��r�����n�{p���{��^s�v��4��,7Ey�\���������(��$��/U\�:��}�K�8���.�7��e?[�^do�*1��Mߠ(e����U"�w��E1"���D0��J�"k_���g|�F�d��
ю�����0�*��=��]%�1[L��U��Ap���h�rC]���"ѐ-�]�Q�&_�5Y쾻J��}wU
l��O�ѷ��[��w������jP��9������zE�c���h�pl�[��МXS�MEqE����sO�� "�ج���{W`�}� 3�߿`�6q��}���1�j��%6��n�)�r�Ϥ�Ѯt��*Q����El�~?�f	��Eo���^��-m��Z�Ǭ��b���yJS��S[)��j���9`�����~����[]���;-��u�"�S�����L��4��u�|b��:��,DRX�w��)u��V�(w��|ŏw�l��:w�: ���~wO�W7��1���MIP5WV,,�Dn!��j�����x� ~o�/�[����k���q&Y}W���n�X��]*w�����W-2vi�G j�!�b) m+�Zl���w+����\�Θ������j�0�v_	��qV~�>���L׫���2�W�u������sI�@�e�
�|]N�'g�yu%}��a������X�R�ZT�W�ba��"Y�ށ��u�JJ�Н�ű������u��Y�
[���p���G��K�۩��R���jq����N��Z�㸿���V�#R��"N'g�]-������/�wϸ��|-�vG�~���3A3�M}�O
�	V*��V�O�QJ�	ʾvv.9����
��\�+��8j��T@'@1f����3��|��k��QEvEA�NGj(��6ʬ`�*﯑��ޱ��Q�7���>��(��͇OL�x��O��5^y��j	�v�P���+�ocrt�����(�*�=�-H�t�$p��^m�洣�k�C%r�}He�ھM���R`V����57z�~z��
�*�������Cz|Ce�8�ec�Z���[04!5��N�M|'8R� ��9�v<.g䑖j�]�w�A��j�4O6K��+�Xѹ�֚{;9���1ߺ�n%]��t�f�d̟i�����Qܕ�	b���(���(�
��d��R���J��������ޟ�&k72qTaE9�ƚ�Qw�ȑ��2��)���N��RE�y�PG�Cֻ�Y[h���V��W�����}OG}�.mG�}IY�QiKQ;�RD���#����!�=C��v9�$� 1LӖ?�z�0 q� '�@y�)[�>�7ES������}��"G��;b�r�&�ϐ���H;9�O���=G��.mm9��vϊ�"�v��\Q�U����!�n�+C�����l[�Q����@;m>��0�j��g�AJOvK���^��(�|x�d������f�����yCqwG=O�+V-��"�?n>Q��Q����s~^]Mَׄ��_�<�S��7TU8�9����B��c��jZ��r�K��G��򳋤_���}���6ȱ&u ����I���ɳΪᮑ[�9�=%�
���t��)����5�\���9U�[ޘ���3(|��V�hs���/6�7r����yT,S�5�\��',7jS����,� 4r�Z~�v�k�//0ަ�J�@��a^���96��I��[�xJN����W^��,���@UW��R��)w�F�1��F���u�?�1	\ w�~}=~�+"G�N.D�����y�y
���S%�Fn�No�3�K,�6�Fn�do��`Dv��"���\�<`q��9��r9fE!ٔ��9���zBА���|��q���ꌓ\�#��^W�'0+��đ�Um��K"��n[�^��:�%N�F���3̖+,i�s��ₓk]j�&�d��h�SyDt������7w��S�[�$'������^�F���aS�p"w��q'�t�/VN���[ñ��?�/�F]"��^h�l�Z:'7}��㉓���F,�;y�"��9�gI���+�N޺|��ך���8ylHĘ�qrۗ;4�� �Na��}rܗ{��|br��B��߆���2�8'�̢G���V�/7��7��/+=n9���������y2�����j�H#$I*�G��l�����
�tG���h�wTH�1���hH��~)�਑��	��W8�(��Ոld:ri��_.�04
�_�ۿ����B:
(������{�
�k�J�
j����X��:�)�9��Rn|��c�<���ڸ7K!žSX�N-{`F��8��*��4D�r�W�)�ا��������9�[4ɥb$J�n}�!��~8:��F�m;�q���3�i,dC/��q����8����Nʹ���':�0yٱ]�څv���6�/8�0
�i��?��e_wj�B-j�İ4��Z���c�E�j#CZI�������;*3�c�~���FƤ��4:3N��&?����ln�9��t��O~�M����=�H�n��]���lf(��:d+v/�U�J�v�0Z�-�\�s��G,8���J����f�x���30�g�g�T@9�2e5��cZ��&���.,����큓������Q�J<��WF-���	�S%!��<�		`�cF'��!��T/jE��/(-	?I��~�ex9ꞝ��. @m�c<(���َ1���5%{�w�i2LU�^�X妦d��"RR�n��U�K�*�����;��n���Z�����y��G��^"�_�E��#��Lg۝�#�7X���ԅ��d���f}�"Y��H5%'�C�hAuT��St�B)=�#p[�z��6l��/��Ǔ�	�D�VG���f�'�2�7η�BY�qə'u(K���8�Q� � �.��|���e�pl�qs5q��*�=�2��8oȞaz������Lq��y�V�;6�,~53��d!F.3Pv���x��،3�@C��VF)B�3��#_h3>�O��s�]�`�������W�a����1�΋(�Z��'ذ���t��A/��u�s^)M.���E����c>����|>x/�"J�ܱ��Ē�>��a���V����:�?�JE
���F���um/�4/��r,n�-.�x��?<�q�G)���;4ҋ;����h�΋0��W�2��W�����z��2�9/�d;�����aD� ��+���Al��S<�*�����l/�R����>�-M�@t��w�I�(���o�@���K9Es����%��8/����\TU�I���Wɸ�YnS�š�<�/>�����V|Ă���R�j^R*��]q�~n��D�N.Xh�;/�U4r��Ű��~�{�c�|"�X�/i��*5K����QF\�  ;�q����i�}�X}Y?����Am�/L"P���K���bN\Sėa��[^�(��>�S����+�<-�ѷָ���^�H��gg ��[a!�Ƚ� �t1���ȑ�LWg��8L��E���=���`!�@5g��w,��ɻ�y�ٌ�I��V�O�$=�eA�������l-0�����kNփ��y�;����L}�8P>׵�;6{�����#�m�	�_�=/�1�Y|Ȑ��vRk���P�m���R��`��p�X� ~�$hQI1�E4��&�-��W�c:���6[��[:�����ޕ��|A���|KD����J?!    ��T)Ŝ�
�K��O�33��yj�� F��!3��H�?�B�Ԗ7�O���ma\fKr�uO�`q��߱Ϳ��
Y�=��?Ųf�W>wл����9)D)-Q'��{rM1-��|����e��K9Ti�{����)��ز%i"�E��|����n�'"B�a�]s�Y�>��ÂX!N��~;"��|�O̭)�炸������չ��Mg��~�,�2m��4��&J�a;-=�b~��,�����	]�����K3�bc�K����V37W��4$	�_g����l����F��Ùk����p����J� �.�R$��hJw��8�z��IB����	���o����d�xq?�kt�v�_�<�"�:��R	����;zs����0_�uBL5Ԓ�_Gc/#�Y�}����$�8��v�sŔz�C�J�(:pa�~}5�Ɛ�׻�ୣl(�;��W�-�r�����vC��w�ߞ�Y�R�+��r�D-��߫��t�M��dZm��(0�~�u �X���N���E��}KJ���*�߁-���ك�!�+$��5�w��`�� ��zC�L��h`S��� )+:p/Z3Z��p���bcE�SL�[�J�ەe�q�x΍�q��e�.):�)OJ+�(:pۚ	��C�R� �D����"���(��z9G����^�Ov�iG�PC�HM��i�^1��2��'�� :��
?�N��o���Ok�j��-.*�fر{�T��O�b�x������� qe�?cx���x�[wD��ÃR����R���z�c���A��
��s
���h�90���¨�T�֗D��a�G{��!�M*������L��m�o��x��ђ���EH�%����L��q�Q�ti�YD-NS�tXp��s&�r*N�\��uR�@G��$c�X���}3O�n���U��(m
 ���N��e���cn|ML�0Ʊb��7 6J6�Q�t�'���ꦃx~��y�_}�, q�^�R˂P]�8
��G����>�Х�9 ��?b��)��9h��/���WK�p#�@��n{��7Q^�FXu��t�+7�\<"TB������PmN��ŋo���SX���&F������j0�Oa��!���(Q�=��d��NQ�lO�k>��͐,��dD����6�����f'�mP�¬��-�iI<�S��]6���B!y���]l�C���b*�u�B���Z7T�ސ-.�+�T��]q��Lz���B��H2L��A5�+��=[�by(K� �J�k\�=��^����W���6�k��}W��B�0B	�Ȳڦ��m��f�2|�Z3�^j4��^��S[�*~�<��T�Oļ4S�=��}� [�z���l/��
��J5�h`�0�[��Q��c�ܧ� �t����DfaȣF�ͼEqT ��T��P�<���b/ǯ?|X��v��mT\Bqc��-�A5g�x���YT��|Y��C����C��%����㟱T��oqx��r�R�SC���0�\܋��b��L�S�%Yi! p���B�ڦ�)+j;ț¢́��y�RՆ�j�:��,��!a
�Y9:���:��2���Pi��>��%N�e�!�R!F:C�+#�	��7����I��Ai�t8�T -J���usl	�'�0!'j ��J_�}i/ J��0bX�h��P�Ac4�AY���	;����9��/���۱T׶�熄̹�4Bg�J)����􌥃Th~^�@;4BQBdJ!���
����XhD��C���t 
3ާ��P��|B��V��T�f�Z��&6~5�́�%���8�!߀�h����$J�ob*Lϋ�Ӯ���
w�	u���I'w/�da5@��J,�ur��Q n#�Է]'O/y칫����W���[z���)Wk����:UJ^�b��tr'�~�i�ޔ.���7Û�M@;��9�3={����5�n'@��3��H�yeJ��N ����ӂt"�ߞ��&06����D
:��1��+�(y�8�w������8:т�Mɉ�������u-�n��U'��d]6�ub�PLv���rRp>ޏXT�D!���xk�Vb'R�s�S�Yq\7T'�L@���C�[�����z˪�.t��r����;��LvC����[��Wq>�}�M|�E5���zǑ��f�<LYn�Eщ|̪s�X/�!��K2ڋ�0H�>�I�s;�ʷ���	��f\_Y˱	��h��\]�6ښ�������EC.����1��L�⭇��b�u��(88��?ai^��EQ�U�**�� މ^<��R�60�3lٯwHY���E��R���O�����J>�c��q͟����Ћ��������k��di崔��A�4b�uFɳ�Ɍ��f)����~�*3�@7#��W9}i�&%p��(M4��Q�a� X[����0	:�I���)lR�Ʃ��ɧ�89���z�n��n�ܑ"�Ք��m��7�ѠR*@�l)ey��H��H
����O�7KT��2�%Qw},�����j�>�T���6)0��^�m��|��]g���I��P"ei�]�`'%�Ңx,���E�������8�RY�N�R;�HK�_)I�aa��r�J�4$���Ζ�W,�H��k���f��@��")���KdU[�茽v�oT[���0�Ia(D��oz8��@�tiV��ޠ�?1�����  uRCr�-�.�)���6���㗑%bTJi)cp-�2���#���R)�+���F���N��M�w�#����ݯ��tA�kL�Qn��<ۅ]i�׹�ı��/wW�k� 4~zG��}e���9���[��!Cj ��=�2ūs-zk��b��f�~��Q�ma<��%ܴg H:#���a����D�H3���A�J�zov�vP'�kGn(C�T�zâ5+���C�T��k����]���+���ֈ{�������:� �R�?��J��o�w�T���J�l�\�C�4�6G�h)��5/����*4K	����J	`󨨒�^�8A��Nް\�r��@�t����0ߩ��&1rS`����A�Tej�S���Q;>�8J@M��}U(���0�g�~��m�AT����M�:�(&0�q�AD ����4fr�yx��fWt�8AZ�K�샘�G����Dc��n7@�=Fu���>���A��B�Q�E9�Dd�9�s�N��� 2�r�0�(D�RQ���NW�tD
^��šx=t��a}w�?Ex9n���t��r��AD���g{�����J�d��xVw��4�. ���Z���PL=�6�.$LU*���L�fn��v-��G���:�Cq$J�|wD'�d����Z��[&:�=BG?	>� B���y�a�X�~��a��p�Ek���ī��j���'�
�� ���ë�6ijb�?�"?0d]xu�c*d��x©>%�&�-�΋����jQl��@D�c���Gi�
"N�9� G#��A<����Ԇ���v�C�Q����I�!��x��{��DL ����=_33��\� 	�c.���� z`�9H!��M[ݮK8犈`���?x�������|Nbحө�A$�֔�i���9�� ��z��A����Ň~��f0��o9��f� ���]��g��2I�fY����� ��f�l�n=x�>*"<��-G��7�����_"n�7��"^`��:5%݆A� Y�NG�r�� �@�/���Z�D$��5������$��čp�c7������P�꬯�	���~)�:_��e.9 s��VԈ�H�ٕ�|%>����x��(eƕݬ����ھ/ 6]��X�[m��E8������8�l��D��k��,_����o�~^��{\sW�W��g�����*[�����+�3N�|%�v\��Mr�IxbR�l�+���JΟ!*W�M���bb �x�߰�+    �Z��������ԭ����M�Q�W���V�r���)��+9��ffE�F�K�S���a#�H|%�����B�i|%�/��5���T���z���SQ&r���J>�Y+�����ŉ�~Ž��78a_��S3k��WaI�Wr��Nƍ_ɷ�cJE�z@^�'8��J^�
x�J>ݎS|ՙr����-_L�p�T���Fy�%9꼁��r���6���1��_���1�-Xxb&/L�*X;�,X�;�����
팿[k6�`��:ލ��" �Aa_�i��9��+���F0�·|%�ͽ��/����ьC:��+�p��3�z�r�l���+9���:$�6j�ȉ�LGڐ��|yB�����9*�o8�ə�cJw�r��q,c��9�J��|� (�=+��s���?\2Õ���	�Մo�<�����9!�J>��m��W��B�iؕ�&��[t�>������G�rи(����穰�䲿+����Ӧ9z��7@n�,��W��dQ �敡c��s���W}n�{xWb�y�wwe�r�2�R�c_��B�k�*�����9�jp3���j�3x	�xi�J>�����J>�b5ޙ����ՓLLn�z>S�{_ɻK\�S-�
���Z�����L��r�
�C��bN��k9�&]�҉���/�0�Z]YktR��_��߲����l��!1k�b�6�r��͝V��_��LZq���E��k9��:\�~b���mu�����>����E(�#S$l]a+�v-�Ȇ���<�r|�zq�Y��ע���6[U\� B�;kq
S���E�kׄT���}-b �R��Z�@ � �3�72|-� �H���.v�a���bx��XC^���CD�:����k�H��MHKҾ?��Vjl_� @j��z1����+w|9D�#?����{�,o-պ�������oy����EV)_�0��Sk_�D�}{"�qfUa�3x���9��ע��O�Z�@�ڴ.}-r H$�?`nD��������r�>�n��zu�;C��i2�O
3�¬V.�� ��aŞܟA�(C����o�HoH����~�V��&��i/��٢My_��	����n]�2���[�!�K�2;y,|m;_�=�g�u�-��c#Y��6��۹#��-�����Ȃ�}Y_wUF�ԫ�~"������,P�|�]�u��{�:_\�}.�!�\�#�fp�"�������w�/�}?Ce*l�~��OϪE���S;�O�^��7ѡ#��ע)K�l�.�)@�Wd���S#6�7q�3n���d!k@m%e���ݩ�xM��;FJVR������b&[�S��|=�OmT����<1E75���C�w�]di��;�Cjz�C��]���^r��a_]�%V3�OмO��!��|Э����al`��9���sغݱ�ܩ�S�����O�싦�}�vA��TmaG>o�T�CT)J�T����P����u8���Թ3�@Z�BR9��6h((���F
���Ft+6�m|#��a-��R��_nX�k���<e5��i��|#����$�K�ӊk�	;�Gx�Oܯ�gJ1߈iY�r�}Q���|�X;�$���G�X�RLM95nļp��=�`�F��C��qB��ZI��7��3��F��ru�7E<k�<��h����S7���\5��29��\��6���UJ����cZ��LqM���Z�	�r��F�*Ϗ�İ.w��=�$zu	]�X��r/!<���U����"j�(Eo������}0�b� E��|4���z��d����9��oĴΘo߈e%���`�S�oī.����S#FE�w��"�]����XbmD��m� �!j¤$��M� ��&�F,)���PD)����[/f��>�����5�; ��
!e�oD���oD��HQ�7"<t�j���a��4;����8M1�iDa>�s��A��~u��KTP4�㸺���و�D�N�hK)_NGv��JD�g�O�"*�2G���b&����߮��5:ۈ�,���I��(�%M �$S��IӷٚRD����%��t�b"���~�aC��� �,�k��><�]eݕ�zZB��}�}N��!�J4)�s4��r��m4&;�r@���j��w���՜��C�U��o�DS��+�T+�Q� �g���q��,M�{�l_:�`��×b�^|���-?`lK�������0�!�hh�:����+���D=]cy΢<��\��8��+)�`#�L�/�i!]�s��빎5�2�b�(�?��z����25�w>b�@��&~f�0тn+M{���(���E�w�Pj���WB�0	��;���;n�yH�&�N�{��
#���׺lm���4�1.ll��JC�e.�ǚ!W P�ѓ�2�0���^��E�s�f�[�t2�����
��h������1��}锬��vl��r��;�&[$9�V�&��x�yc�C6]��"J;���s><���[9�̐��y)��gD�rϗͱ�Ƀ��������ba���TP�����Ћv��?:��z(���&�Els�J`#N2���aXA�ȀQ�Q�\�((�G�E ���[�p>���^�7G�Q`=��j�e�~������ȏ,�و)��W�ڞH ����n����j���H��Xǘ��=��,�!�j�����L���k���rw� ��)!
�?G�������z?�� ����~+��f�������y%|�jt~n�����kjӧ�}hբ�HϘ�<�]�=�tW	����g�~�y:�C�����z�s놶����?�Ps%t���eW�Cmd:5�qȘ7A���z��٩⑓���j�t�
��X��83�K��K� `H�.�/$���'����f5>�T� ����C���.mG]�eP�4:��ҽ�Z��
�l���`%`J�Z��
+��Szȯ�@�pE@-�*on�6�#���lc���j�����%���])'���*��G|��Y�$?�z�V�e2�p��Ck��)��[v�&$Vc�qĈ/��U%0b	"�,�WQ�T�5�S����r�3�ػ�`�ly�|L��	J��=dS	n��d���>�P*��R/�C$U؈5r m��/���F�_��|�V_Qi5g�9��PD%�c#(�Cƀ����U@tQͨr� L!�nըM�m�fϱ��bqC�i��B��Q�I�C���uh�
�|:*�
�S�!h��'\�O� 4S~�f@MC�<P���_���$�ּ\�WP��@����"��g�'<�PaC��;ݸ F�Pruz�ϑ��؊WP��P?e!�F� ~�@��,L��!�
��60�r4P/��{H��0A~r>̥{i=�9�	�xh�Y�����JTP#�f�y�[*�w������ �@�E(���t��6��^k�N��X4�?��J6鞭!��c��fH�a� 4}Ɂ�ދD�u0T�h�;�K/R��1��x@,]�$��� ��K!�f��^^�,>d~r�gH�ķR��,�
������q�şmo�������n��In�a`(�r��.�t/�~vo>�˵�r�$9vQ?{5���ǇRg�#K�d|�����S?#��r���ȫC@_�\8��sr�L`d+�^��N?�z���t	�p3�8��o�+�=�^�Y�e�_�^�-ғM�!�L/Z|Vr�ow�D|Rr�o�p�0n��噵5��n<���@�d8b���Ş����
��/����S��b�Ҟ��.d<}�D	��g�~�������?nq� ��.Na�܏�V��E4��.	��J̿V#�ab�A�8Rp|�(���u��y���'���� ��Z����1���9�.P��A���}��=��r���Eo��
�<m��>���S�P��Yؤ ���n���l�w���hS��=���]f?�A��zq*�`��<���yڟ��v��� O�'K�A^V�Y���X9Y{m�9Xb#����Ê������߬W    ��A>��㊙�ws���j.��rUD�<�F� �K�5���qpTGȡr�뮄��� O��"x� oz��O{�A�4O��<�E��l�V�<�V����?�c�M2�>�wJ�Þa՟>�s^�E0 ��e�*�9/x0Mߒ\��~,V#��a=���A~�A<z��9�f�Lp����Ď�A��¢���>3T�� ?*'7�5��B?靖�V���Br�� ?ʄ�-�;-V�R�;J��kqE
��͟�~^Cy�y��\#�"���u�����-�,�I�8ۘd�,*��O��!��|���j���bO3�+�0��o�)[r�#G� ǜr��� ����I㤜�{,;�+�{V �jD|��œ{F��)�x����l,��r���Փ��^f��3lY�}��.�-����K�b����in0佟�Q�']}�tBW�U¥!����{��=����R����),ѳ��^��,�?)%���EJt`!cEyH��E;�S�	�nFk��7�lՂڼ(DN�Sv�3�Vai�����6ް����d ]@���/!~0T��m�g�AJ�V�qs�r���nn����9 ��r��y����'�չ�wη�}�F~ �~������u���@���F�V��C���r+��!Q�v���؆ 6��:�D>f�ߊy@?@Z`��4�(;�q%?^��K�y󭘆��`݊j���v�#>�V$#����V4C�%���XFZz����������te�CZ��i;�t�bJ�[����(ǯ8�V�+�d��(G,#����Wk��0x��7T��[q����N��V�#b�.��3�Cb�f�d+�������[��_m�j�׊i�=N��T+��w�{@�u�o�������ُ7G�? � �c&(�,���B�bZ����!Y��55��/M��I\N.�*7��A�"���$u8ق���Et`�uQE2p�wė�VV,&kԐ�*H��uh�q��h�x{�k��2�"�!�v��uZ5i�0ng�KE� Q��A�j��V�w�e���7Ɯl�S`�{��S^2��|�N-��0P��rk��� >��OiI��;~���p����E�@���$a�
u�:zW��r�$ju��b:�b�����ݩ��r��(z+ޯy�>Xy����iԫ��������|9z�z����Z��P�b~����X=��>��[[#�F$���TJC����崫�
�$d,a� ��:��O�ޠ�>����J�q���S��W����������k��`#ps�Ā�R�x�}sXaE�Z��b���A��yp趼ak&�/x�=����Z��d����!˴X��h����sJ@
^s�GH�T�$01v�ʏ(�z��1�7�ZO{�Vu^_�����L�eV0��w��_�AR��㸃��c���%�yķ�*6.���SD�7r�sj���aN��)��'�x�:�89�cXl� �}�p�;��a�^�l��Fo�S���F�G���2<��\���B�G"k'�y�������c�h6�{��z�I;�Ty��1ڔx��������[�pfFq��x�TP\������c@<Nl�f\T��dZ?����A��� i�$����;3�Ȗ���<	��_�����|r,Z�A'��v��HFɰ�.�T�2l��_v��`����<XUM��R�]a����H �-Z�٨��ݞY�|�V���c�ߵ֢8��K�6	�M�g����/�,���Z��}�Z3��T��JY��-�V�o]kM�n k��Cu]����LZK$i���q���}�Yk~�T���I�V{^:���rLKW]g��"_�_̠������R�_<���n����~\�����޼���]�7�� jz���Nn��f}�6����J�`�����0 ��n{��l�<���MU]�f����2��T����ۃL��;�`�ٞ^'/�bw�˿'l��n��YM��X:����"̸xy����H�-w��/&(O�s��%T�{�c�0�Y�^����w�ĩ��^+���厕�g<z9嗌�f�U9����?����A/��r�)P�^~�%�T��%v|�<{y�'�T�	�
(�֯Ⳕ���傓v / A��W:���#3�(%���Jɽ}/w����<1
?����^n��Nx�6�)>,����O���\dG-{y��\a���~�Շv(0��r�ICe�½���������z3�&�K�ˇ[BR��Y(!�k6w(�����V�jE��H�µ�^.S�ux �q��� 9�F�^^��1�7��ˉ���/ҙ� w C?vY�����v�I��U/��ƾ�^��M�)���F��QGBn�[V]��M��5ה,���ړ��� �'k�����xm[߽�z|MK��r�@�0;��թ���֙V�^`�u!��1�T��c�u�ƶ^��v�Qs�*�'7/��\}�fd�}�3�Q'3rd�#�m�6U}�	Vߦ�﹧ѷs�5��۹��6�6U��\U����������_OȪ�e�u ��6��ʺ����b��]�2\z/?}��_//}&a@��Ac���G����\rD��o/���^��p���ٚ�,����-#t.�ر0��$*��Q���I�����L��bww��t��&�a�Q�9���⁗^�Xq"5������?f9���g�8./�q/�|v��	�^�����(6V�>�>�7���͛������QRe}0���r�g�U����{�A��'�2���6 ;�vQ�PrV��1�� ����'��Aޖ��\�)�)�^q����x	>���2k�*�h����2��s��UN��q�S+�M2�~.���6!+��Z��P����o�S�5��Tume�0�u��ꎚs��:#|`CcV�7VW�~c�����'�	WBXCFu�X� 7�e�6�za�6\r�o9br�8)>�r��2��u�K|�#냼��8 �r��>��&gx28��~ܢ��j��A����DV��sW��AO�.�!���RLI�h�M��|}���/�\���3����𝳏��" ��|�`�?��I��e{��Rh ��ػ_�_-^_��w���< d�,<r�|�(8����>��	�ƺA�0/D��D8����J.m�r��\<��b��~M��t�,VjJ�9��)�,��y?���J����w�}$zR�~念�j�A��2�{��c��r{���)�AN�|u70�坯 �@�An���|�9&�����6�\�E�* ��'QCgu�_�<"uV���]�.*���������h�4�V{��A��(�DU�?�+� Gx�3a�A^Pz����Y{49�A.�{�'�D?�/2�װyEʴ{�K���@�veJ�~����p��AN2�����ݎ\���V�y�i�va�Ӵh���V9�sl����t�� �͈�*)u��7O��c�1T���t��H�����?ּ��oS1���ٖ"�BU��Ʃ�vF0�{��Z��.�HI�>T��m?Ŷ\�.�6,?Ƣ�9H^��\�jG��Yֹp��k�m�}��dS�����?�և�&��%{n�J�������4Tr�&�5r��0��FA��$~�*)C���hkL��V�؄x]`nư���P�)$y7�*�g��-e
C%GMNl5�J��/q�=�:�ۛ��2�,Tr�?O��rڿ\�}�J��l�h��/��?b/�r6�
�2-T�ٖn����s�7T�ؿ�ӗ*��_�?�8����r���m�rxXmn,cD���l>T��Eh\��q&*yj�Zc)$T�� &G=��Y��x���Y��懡��V��
/���EE�>*����b�R���6l���*�m��u���^�~�
� '��!$(֘�TB�<N�_��w�|Q����k���m�-g�|�m� q�P��|�a~a[�@%��	�Pɝ_ �c�U�Pɡ+� jghvr��_����9ʳ9ȯ����� �  ��1(��[�o��_��`�P��[���s�8/�� ��
�\��(o*9��Q�����7�}@��/ p��n�������%?_F7�J���O��P���K+wA�� oO@����C����}���Ä���T;���T��BI��@T��![S=�*a�ô�4u��/T�U�����J�}*
�\{�c�K���	˗_�6�㣨��y���r�p�l��8"4�Q���*� �G�7�]�����jy��WD�!s�PW�����֕��/���T�r�����9C�壑Ye�Nj9�Ehh���b�h-?]�{*�<�rׅ�vle�'�WHE�YT����~������GD� �j�����j���bS�:�n�7r���\�u���A�Qy�X�L,6*�u	�|42@�oP�*b7�#S-�LU9K�=s�Z^�����~��<�pgpf⡖WH��P�/'1��#+�
�u.�n��c.
��|t6�zY�:��S�g����jy�l�_��l���F���\#d-��~����ୱ�k=Uo�-z�[3SN�P{��v��j���=�G�y�T �������rꈐU�z+�s�'?]�9���\9���Z��
�(6*ުS�%j9m�����6r܄s�y���i�����C�C-��A�_-�m
~J���8Z�e��ck���aecUk�G�ُ7����u��y��g<K�%bT��0k#�m�A���6*�aevN]�+��W�\0<��T�	uW�^`ɪ������� �]�3�s`g���s�Į����sv��1��K�=�I�_wE��da~���O���zm�^tDm�^�S���Y1T�k�NH��!V|Ek���>�_���O��b�Q�na���A��]A�����P�>`[�Y�0%:u�}�
�Q�k�=;�sG�����e�P}����P���!๧�c歹� bQsWq�i9��������>�8"j���_S�w~3Eq�H�P��,��3��#�p�Ј�,��/v*��K��K�ϓ�EhĊN���_$moĒ�����>4�/�����xMN/(��CS�O��W�T�a�k���}V*M�{�����ν�N[̲-��Թ��W[$�M�{�"��Ƿ��S���|
P�!E ��K(o����BMY@��8!��;8��F3�����0�鏚��<34�}0{m����Oe?���@�2�g����t#�f����)]EhDψmY�P�}4�eT��`�Ј�Q��&���Y���E�gv�X-CY�`�+��Z1��!{����G����lߡ��0}[�if#�E���zC�OS�F$@zwD���sIQ��8/`�D�.M�?4�X�+���F��\ӈZ]�E�rPy�����֚~����ni���20���*���{��U�/Q�`�Ow�`>��
V�I;ӡ��\��AhĦ.׻8�Ɛ{w���������WVG�֪vhD�px������9�8�&���5�Q�{%{	����~}�f�Ј2���~g�Q0���8yG�=�-+���X�hD��?2
(4�>�3�׈��z���D$'��1��@pE��Ј��z�y��kgB�[�����ye#���s����e�+���%�	�8�GT�+|�hI�祱F��N��F,�N�,3��F,di,�,.b�_6&�	�F��#��{�o|��%��C#rq*ħ��F�bVܳ�G������9u^C#.��Xۈ?�7�)�c� Z;�0)�;%��ѩެ��ƀ6��ȇ�&�*�LnD��V�3s�*^;�������x-]���k-�8�7(��Z�+�3O����O���X���?��j�{�b�➳)j�8�,����=D����1���t�E<�ߵ
��q��|�^�3�as^s��"���w�?�[S��E������9z�|�ۖk�k�с3�)06P�U�
x��J
ݵ
8٫36!���ˈ&*�	QrV�w$��t�ٔ��;�1�Rv�v�j�Ƽ[ݮ�|�M�2��m8�b]��ػ+=�f�2�!-'����;Xԩ�׻�X��f:v"P��=�q�Ak���k�q5^!@�]���u~e���Ny-��	�1�	T�;��1���߻ �f{Zn��}�<m���=� z��;(c�r?Þ�g_h��|�AK�K��e�Z.���Ae$_����p��H����j,Գkm�z��&φ]�w�{�:�ڵ�ޏ�56��r�s߁�vf��eI}�6-#�O#�e���#g�T�k����@m�v�q���Ͽ��f���t��][��?xB����㋪��"xmq���⶚��@1���\�Bx�����C�*P/Zɳ����0��൑��i�l���
Q��5� �i��͚Q���"��g<�3/�Q�{�L�yՎ
x�VI�]�x$���w�2�E�s���u8m�`^T��)`]yHN�3	o
�u�_��^ݡO�d�P��%����x3�Ⱦ�ЖZ�d��;��w��^��c����̖P�lR�3���Ո��W��,ll�O���_œ��������JW�0
���	ͫ���ͫV����؅���l�R��䊠+�я�6�ac�� ѓ�c�,TJ�u�4Ӻ��A(>����pmw��T�#�����Nɼ���Ix)��-2J�u)o0F�� kXX�z��^��>�dg�;�+SW $9�QT�{V,���F��B.M'�o��|��5mj���νP7��W�I�j6��j_�s��p��Ă�����/~�m_&�=���rZ�_��j�2|ݳ׏�9`S�/��p4צ�oVX��Ć6l�4��Z�t�u��E���Nk{��|ݳ7;DE��A���z�ƽ��E��{(�9Έ"�rv�6.���1K$�%��#5�����&aI��@�f "�Mx��4�ѱ���K�&m�Q�o6��J'�4���`� 6�����bٳ]p�����2%.�/��蒣�/�� �c(��)�l}���5=Dφ�'�g����+�/��`��c��<�B%�:*�u�c;Ȕ��R�C�]g���F���>c:b��gXZ��j_�,m/1�b�b���4&����Xe�2��ѴUa����r�?j����~(s�wOU��B1~���Zd����+jֶ*�I����u9��}վ�ױr�Eg.�	%��g*"����^��M����._���z���w��:ڍ���G�b/��]o���`R��ۻ��6�__O�C���wȤ���Ew�g��G��=I1I3-/���n˫��ye̋�p�0�W�g�1V���A'��.!Z�V-�]a7%����v��zr�prER6*/MZ��,��l�mѯO.h�2k_\���ti�K|�d�hqQ�/��}N(�H����Wt����1�=8�i��x~ �KsKX}
"S������ĩ.W�v\�"S�ĖӃU���vA�꒷��!�l���prau�;�����?��/����;      �   �   x���1�0��+��Ow�qJSR��U��!������1ф
�j�Yi:���{	�*J;�9e���b*��2��Y�c�N�Oc�=Eے�5�jL[|��+��Kp,���͗��u�t�[7�s��+�      �   3   x�3�t���H�,��2�t.:�2%�$�˄����$˔�-31%�+F��� X�      �   �   x���I�0���]yLn����8���@E��m7H.���q+���m�z\oX.c�Q� �>Q2#�cԲU3Z��Y
E&��fs � �@��J!]|�?Zȯ@(V4���3�3��� �g��p����L�      �   �   x���Kn!D��.����Y��I�s�s��Jғ(*��Lat@@�\~)2ݐo4~�
�3�#�#�nS���X�4�y����T[��='����=�f�:4ϕ�~ȉ���|��*<>�((�]��*h���{�*�n0]a�V���=@g��a�i�2a'��WtL6�[O�{�#B�<�m�3�-���]�m.�^z��G=0�?[�󬥔O�rs      �       x�3��)��4�2���+I-�K-rb���� Y2      �   6   x�3�t,�H�K,���4501��4�326�362�7000�55℀?�=... 	      �   #   x�34�4202�50�529�9���,�b���� Hz�      �   N  x�]�=n�0�g�>AQ����8F�:��]�DH8R �Kot���b%=��)�{T�����'���?�������3]�+>�jE}���'�e%�<�3����Q��V�%,|?$?�l�.Q������2�悥0D|c��@�t�Qݳlh)Q8M��g�iR�{���������p���1��7{�*�� =*�ץ(�n;B%��� Aנ]
tލ{�(����q��j��y��V�Os�7��nx�k������	r.i�L��'�As	a;�f-���e�8��j�|�L��1�D�5X����[%�v۸�>��y�����      �   �   x�3�,I-.IU�)IITH)N�45�351�313�7313�54��;�����Q%�� ,������P�!���������!�gl�ifh�eę���Z��%��4!'375�$�X!*��ʜL�Y���� �P�      �   r   x�]��
�0�/iI����e��%�B�5%��K:���,����8M~��= -�H��9��e��'B\`pu�K�j��`����m�����Ak�����VaSIv�f�1}��      �      x�34�4�4�42ҳ4��\1z\\\ .M�      �   �   x�]�a
!��aD3������k��L|�3j 0i2t%�R_	Rюc���Ck��,���|�V�Zk N#�d���C�Âtp�,�*�d��b�xiz���AZ����2/���Z�r��v�u֏��g/      �   �   x���M� ���)zqf����	��I�������c�i���x��0��:�(�wI�M�Ζ+�C���X<`��	����\�s��n����-9}&�N�:]��2�ey�� �W$a� e(�h7Ƙ{$�����I4'�4��{Ƚ5w���� ��`m�X�i돈�!Wl�X���C0��i��_Z)��g�      �      x������ � �      �   8   x�3�LL����,.)JL�/�4�2��/Hs8���9�S��RK�29��b���� �[9      �      x������ � �      �      x�3�t
�t*J,������� %��      �   k   x��K
� F���*\��G��A�ZB�� ���.N��RٳL��R��#PP�h��-�SB�%�`8L��O�e�V�E�Gc=�vpe=ĮGL�sw����6B�dK�      �   )   x�3�4202�54�50�45�30�2�r��b���� ��          K   x�3�tLN-.�W�/I��2�tNLI,.)�w��L�+I�K�KI�wJL�.-�2�KC%�rJ+�3+�jb���� ��         4   x�3�tr�t��2�,I-.I�2�L��/J�2��)��2�LL/M����� �A         �   x�e��D!@�5��_�^��:F�F���x
�*�cR�08�mU��~\�/��������R���`i�A�5x��&�/���6\���}��r̺���7�;o�f�o�KDIs��V�!D���6     