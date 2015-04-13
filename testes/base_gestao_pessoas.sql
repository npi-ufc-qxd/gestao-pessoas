--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.4
-- Dumped by pg_dump version 9.3.1
-- Started on 2015-04-13 16:26:56

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 171 (class 1259 OID 140198)
-- Name: documento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE documento (
    id bigint NOT NULL,
    arquivo bytea,
    nome character varying(255),
    nomeoriginal character varying(255),
    tipo character varying(255),
    pessoa_id bigint
);


ALTER TABLE public.documento OWNER TO postgres;

--
-- TOC entry 170 (class 1259 OID 140196)
-- Name: documento_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE documento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.documento_id_seq OWNER TO postgres;

--
-- TOC entry 2060 (class 0 OID 0)
-- Dependencies: 170
-- Name: documento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE documento_id_seq OWNED BY documento.id;


--
-- TOC entry 192 (class 1259 OID 140390)
-- Name: estagiario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE estagiario (
    id bigint NOT NULL,
    cep character varying(255) NOT NULL,
    cidade character varying(255) NOT NULL,
    contagithub character varying(255),
    contahangout character varying(255),
    contaredmine character varying(255),
    curso character varying(255),
    datanascimento timestamp without time zone,
    endereco character varying(255) NOT NULL,
    matricula integer NOT NULL,
    nomecompleto character varying(255) NOT NULL,
    nomemae character varying(255) NOT NULL,
    semestre character varying(255) NOT NULL,
    telefone character varying(255) NOT NULL,
    uf character varying(255),
    pessoa_id bigint,
    projeto_id bigint,
    turma_id bigint
);


ALTER TABLE public.estagiario OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 140388)
-- Name: estagiario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE estagiario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.estagiario_id_seq OWNER TO postgres;

--
-- TOC entry 2061 (class 0 OID 0)
-- Dependencies: 191
-- Name: estagiario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE estagiario_id_seq OWNED BY estagiario.id;


--
-- TOC entry 173 (class 1259 OID 140220)
-- Name: folga; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE folga (
    id bigint NOT NULL,
    data date NOT NULL,
    descricao character varying(255) NOT NULL,
    periodo_id bigint
);


ALTER TABLE public.folga OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 140218)
-- Name: folga_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE folga_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.folga_id_seq OWNER TO postgres;

--
-- TOC entry 2062 (class 0 OID 0)
-- Dependencies: 172
-- Name: folga_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE folga_id_seq OWNED BY folga.id;


--
-- TOC entry 177 (class 1259 OID 140239)
-- Name: horario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE horario (
    id bigint NOT NULL,
    dia character varying(255),
    finalexpediente time without time zone,
    inicioexpediente time without time zone,
    turma_id bigint
);


ALTER TABLE public.horario OWNER TO postgres;

--
-- TOC entry 176 (class 1259 OID 140237)
-- Name: horario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE horario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.horario_id_seq OWNER TO postgres;

--
-- TOC entry 2063 (class 0 OID 0)
-- Dependencies: 176
-- Name: horario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE horario_id_seq OWNED BY horario.id;


--
-- TOC entry 179 (class 1259 OID 140247)
-- Name: papel; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE papel (
    id bigint NOT NULL,
    nome character varying(255) NOT NULL
);


ALTER TABLE public.papel OWNER TO postgres;

--
-- TOC entry 178 (class 1259 OID 140245)
-- Name: papel_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE papel_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.papel_id_seq OWNER TO postgres;

--
-- TOC entry 2064 (class 0 OID 0)
-- Dependencies: 178
-- Name: papel_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE papel_id_seq OWNED BY papel.id;


--
-- TOC entry 186 (class 1259 OID 140283)
-- Name: papel_pessoa; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE papel_pessoa (
    pessoa_id bigint NOT NULL,
    papel_id bigint NOT NULL
);


ALTER TABLE public.papel_pessoa OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 140288)
-- Name: periodo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE periodo (
    id bigint NOT NULL,
    ano character varying(255) NOT NULL,
    inicio date NOT NULL,
    semestre character varying(255) NOT NULL,
    termino date NOT NULL,
    statusperiodo character varying(255)
);


ALTER TABLE public.periodo OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 140286)
-- Name: periodo_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE periodo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.periodo_id_seq OWNER TO postgres;

--
-- TOC entry 2065 (class 0 OID 0)
-- Dependencies: 187
-- Name: periodo_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE periodo_id_seq OWNED BY periodo.id;


--
-- TOC entry 190 (class 1259 OID 140299)
-- Name: pessoa; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE pessoa (
    id bigint NOT NULL,
    cpf character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    habilitado boolean NOT NULL,
    nome character varying(255) NOT NULL,
    password character varying(255) NOT NULL
);


ALTER TABLE public.pessoa OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 140297)
-- Name: pessoa_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE pessoa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pessoa_id_seq OWNER TO postgres;

--
-- TOC entry 2066 (class 0 OID 0)
-- Dependencies: 189
-- Name: pessoa_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE pessoa_id_seq OWNED BY pessoa.id;


--
-- TOC entry 181 (class 1259 OID 140255)
-- Name: projeto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE projeto (
    id bigint NOT NULL,
    descricao text,
    nome character varying(255)
);


ALTER TABLE public.projeto OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 140253)
-- Name: projeto_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE projeto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.projeto_id_seq OWNER TO postgres;

--
-- TOC entry 2067 (class 0 OID 0)
-- Dependencies: 180
-- Name: projeto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE projeto_id_seq OWNED BY projeto.id;


--
-- TOC entry 183 (class 1259 OID 140266)
-- Name: servidor; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE servidor (
    id bigint NOT NULL,
    siape character varying(255),
    pessoa_id bigint,
    turma_id bigint
);


ALTER TABLE public.servidor OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 140264)
-- Name: servidor_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE servidor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.servidor_id_seq OWNER TO postgres;

--
-- TOC entry 2068 (class 0 OID 0)
-- Dependencies: 182
-- Name: servidor_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE servidor_id_seq OWNED BY servidor.id;


--
-- TOC entry 185 (class 1259 OID 140274)
-- Name: turma; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE turma (
    id bigint NOT NULL,
    nome character varying(255),
    periodo_id bigint,
    supervisor_id bigint
);


ALTER TABLE public.turma OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 140272)
-- Name: turma_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE turma_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.turma_id_seq OWNER TO postgres;

--
-- TOC entry 2069 (class 0 OID 0)
-- Dependencies: 184
-- Name: turma_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE turma_id_seq OWNED BY turma.id;


--
-- TOC entry 1882 (class 2604 OID 140201)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY documento ALTER COLUMN id SET DEFAULT nextval('documento_id_seq'::regclass);


--
-- TOC entry 1891 (class 2604 OID 140393)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY estagiario ALTER COLUMN id SET DEFAULT nextval('estagiario_id_seq'::regclass);


--
-- TOC entry 1883 (class 2604 OID 140223)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY folga ALTER COLUMN id SET DEFAULT nextval('folga_id_seq'::regclass);


--
-- TOC entry 1884 (class 2604 OID 140242)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY horario ALTER COLUMN id SET DEFAULT nextval('horario_id_seq'::regclass);


--
-- TOC entry 1885 (class 2604 OID 140250)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY papel ALTER COLUMN id SET DEFAULT nextval('papel_id_seq'::regclass);


--
-- TOC entry 1889 (class 2604 OID 140291)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY periodo ALTER COLUMN id SET DEFAULT nextval('periodo_id_seq'::regclass);


--
-- TOC entry 1890 (class 2604 OID 140302)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pessoa ALTER COLUMN id SET DEFAULT nextval('pessoa_id_seq'::regclass);


--
-- TOC entry 1886 (class 2604 OID 140258)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY projeto ALTER COLUMN id SET DEFAULT nextval('projeto_id_seq'::regclass);


--
-- TOC entry 1887 (class 2604 OID 140269)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY servidor ALTER COLUMN id SET DEFAULT nextval('servidor_id_seq'::regclass);


--
-- TOC entry 1888 (class 2604 OID 140277)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY turma ALTER COLUMN id SET DEFAULT nextval('turma_id_seq'::regclass);


--
-- TOC entry 2036 (class 0 OID 140198)
-- Dependencies: 171
-- Data for Name: documento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2070 (class 0 OID 0)
-- Dependencies: 170
-- Name: documento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('documento_id_seq', 1, false);


--
-- TOC entry 2055 (class 0 OID 140390)
-- Dependencies: 192
-- Data for Name: estagiario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO estagiario (id, cep, cidade, contagithub, contahangout, contaredmine, curso, datanascimento, endereco, matricula, nomecompleto, nomemae, semestre, telefone, uf, pessoa_id, projeto_id, turma_id) VALUES (4, '63.900-000', 'Quixadá', 'github', 'hangout', 'redmine', 'REDES_COMPUTADORES', '2015-03-16 00:00:00', 'Rua Mercedes, N° 123, Bairo', 4444444, 'Maria', 'maemaemaemaemae', '10', '(88) - 8888-8888', 'CEARA', 5, NULL, 5);
INSERT INTO estagiario (id, cep, cidade, contagithub, contahangout, contaredmine, curso, datanascimento, endereco, matricula, nomecompleto, nomemae, semestre, telefone, uf, pessoa_id, projeto_id, turma_id) VALUES (5, '45.654-654', '146.546546sgfdsgsd''', 'github', 'hangout', 'redmine', 'CIÊNCIA_COMPUTAÇÃO', '2015-03-17 00:00:00', 'sfg,masdgasdfasd', 6656565, 'Jose', 'maemaemaemaemae', '14', '(88) - 8888-8888', 'CEARA', 7, NULL, 4);
INSERT INTO estagiario (id, cep, cidade, contagithub, contahangout, contaredmine, curso, datanascimento, endereco, matricula, nomecompleto, nomemae, semestre, telefone, uf, pessoa_id, projeto_id, turma_id) VALUES (6, '11.111-111', 'Quixada', 'gitgub', 'hangout', 'redmine', 'REDES_COMPUTADORES', '2015-02-23 00:00:00', 'dfghdfgsdfg', 9898989, 'dafsdfsadf', 'asdfasd', '10', '(77) - 7777-7777', 'ESPIRITO_SANTO', 8, NULL, 5);
INSERT INTO estagiario (id, cep, cidade, contagithub, contahangout, contaredmine, curso, datanascimento, endereco, matricula, nomecompleto, nomemae, semestre, telefone, uf, pessoa_id, projeto_id, turma_id) VALUES (7, '23.333-333', 'weqwdf', 'gitgub', 'hangout', 'redmine', 'DESIGN_DIGITAL', '2015-02-22 00:00:00', '1sdfasdfasdfasd', 1111111, 'zzzzzzz', 'zzzzzzzzzz', '12', '(22) - 2222-2222', 'ACRE', 6, 1, 4);


--
-- TOC entry 2071 (class 0 OID 0)
-- Dependencies: 191
-- Name: estagiario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('estagiario_id_seq', 7, true);


--
-- TOC entry 2038 (class 0 OID 140220)
-- Dependencies: 173
-- Data for Name: folga; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO folga (id, data, descricao, periodo_id) VALUES (1, '2015-04-21', 'Tiradentes', 2);


--
-- TOC entry 2072 (class 0 OID 0)
-- Dependencies: 172
-- Name: folga_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('folga_id_seq', 1, true);


--
-- TOC entry 2040 (class 0 OID 140239)
-- Dependencies: 177
-- Data for Name: horario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO horario (id, dia, finalexpediente, inicioexpediente, turma_id) VALUES (1, 'SEGUNDA', '12:00:00', '08:00:00', 4);
INSERT INTO horario (id, dia, finalexpediente, inicioexpediente, turma_id) VALUES (2, 'TERCA', '12:00:00', '08:00:00', 4);
INSERT INTO horario (id, dia, finalexpediente, inicioexpediente, turma_id) VALUES (3, 'QUARTA', '08:00:00', '10:00:00', 4);
INSERT INTO horario (id, dia, finalexpediente, inicioexpediente, turma_id) VALUES (4, 'SEGUNDA', '12:00:00', '08:00:00', 5);
INSERT INTO horario (id, dia, finalexpediente, inicioexpediente, turma_id) VALUES (5, 'TERCA', '12:00:00', '08:00:00', 5);
INSERT INTO horario (id, dia, finalexpediente, inicioexpediente, turma_id) VALUES (6, 'QUARTA', '12:00:00', '08:00:00', 5);


--
-- TOC entry 2073 (class 0 OID 0)
-- Dependencies: 176
-- Name: horario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('horario_id_seq', 6, true);


--
-- TOC entry 2042 (class 0 OID 140247)
-- Dependencies: 179
-- Data for Name: papel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO papel (id, nome) VALUES (1, 'ROLE_COORDENADOR');
INSERT INTO papel (id, nome) VALUES (2, 'ROLE_ESTAGIARIO');


--
-- TOC entry 2074 (class 0 OID 0)
-- Dependencies: 178
-- Name: papel_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('papel_id_seq', 2, true);


--
-- TOC entry 2049 (class 0 OID 140283)
-- Dependencies: 186
-- Data for Name: papel_pessoa; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (1, 1);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (2, 1);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (3, 1);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (4, 1);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (5, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (6, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (7, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (8, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (9, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (10, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (11, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (12, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (13, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (14, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (15, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (16, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (17, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (18, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (19, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (20, 2);


--
-- TOC entry 2051 (class 0 OID 140288)
-- Dependencies: 188
-- Data for Name: periodo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO periodo (id, ano, inicio, semestre, termino, statusperiodo) VALUES (1, '2014', '2014-07-13', '2', '2014-11-19', 'FECHADO');
INSERT INTO periodo (id, ano, inicio, semestre, termino, statusperiodo) VALUES (2, '2015', '2015-02-19', '1', '2015-06-26', 'ABERTO');
INSERT INTO periodo (id, ano, inicio, semestre, termino, statusperiodo) VALUES (3, '2015', '2015-07-13', '2', '2015-11-20', 'EM_ESPERA');
INSERT INTO periodo (id, ano, inicio, semestre, termino, statusperiodo) VALUES (4, '2016', '2016-02-22', '1', '2016-06-24', 'EM_ESPERA');
INSERT INTO periodo (id, ano, inicio, semestre, termino, statusperiodo) VALUES (5, '2016', '2015-07-06', '2', '2016-11-25', 'EM_ESPERA');
INSERT INTO periodo (id, ano, inicio, semestre, termino, statusperiodo) VALUES (12, '2013', '2015-03-25', '2', '2015-03-25', 'FECHADO');


--
-- TOC entry 2075 (class 0 OID 0)
-- Dependencies: 187
-- Name: periodo_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('periodo_id_seq', 12, true);


--
-- TOC entry 2053 (class 0 OID 140299)
-- Dependencies: 190
-- Data for Name: pessoa; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO pessoa (id, cpf, email, habilitado, nome, password) VALUES (1, '11111111111', 'supervisor111@ufc.com', true, 'Camilo Supervisor', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
INSERT INTO pessoa (id, cpf, email, habilitado, nome, password) VALUES (2, '22222222222', 'supervisor222@ufc.com', true, 'Regis Supervisor', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
INSERT INTO pessoa (id, cpf, email, habilitado, nome, password) VALUES (3, '33333333333', 'supervisor333@ufc.com', true, 'Maria Supervisora', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
INSERT INTO pessoa (id, cpf, email, habilitado, nome, password) VALUES (4, '44444444444', 'supervisor444@ufc.com', true, 'José Supervisor', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
INSERT INTO pessoa (id, cpf, email, habilitado, nome, password) VALUES (5, '55555555555', 'caiocavalcante14@gmail.com', true, 'Francisco', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
INSERT INTO pessoa (id, cpf, email, habilitado, nome, password) VALUES (6, '66666666666', 'ingridsc@hotmail.com', true, 'Ingrid', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
INSERT INTO pessoa (id, cpf, email, habilitado, nome, password) VALUES (7, '77777777777', 'laynanh3@hotmail.com', true, 'Layna ', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
INSERT INTO pessoa (id, cpf, email, habilitado, nome, password) VALUES (8, '88888888888', 'joaopbern7@gmail.com', true, 'João', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
INSERT INTO pessoa (id, cpf, email, habilitado, nome, password) VALUES (9, '99999999999', 'eng.erivelton@gmail.com', true, 'Antonio', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
INSERT INTO pessoa (id, cpf, email, habilitado, nome, password) VALUES (10, '10101010101', 'andre.luisfs01@gmail.com', true, 'André', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
INSERT INTO pessoa (id, cpf, email, habilitado, nome, password) VALUES (11, '10111111111', 'fabiojanyo.fm@gmail.com', true, 'Fabio', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
INSERT INTO pessoa (id, cpf, email, habilitado, nome, password) VALUES (12, '12222222222', 'andre.martins313@gmail.com', true, 'André ', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
INSERT INTO pessoa (id, cpf, email, habilitado, nome, password) VALUES (13, '13333333333', 'talitavasconcelos15@hotmail.com', true, 'Talita ', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
INSERT INTO pessoa (id, cpf, email, habilitado, nome, password) VALUES (14, '14444444444', 'biiabriito02@gmail.com', true, 'Beatriz ', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
INSERT INTO pessoa (id, cpf, email, habilitado, nome, password) VALUES (15, '15555555555', 'famancio0@gmail.com', true, 'Fernanda ', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
INSERT INTO pessoa (id, cpf, email, habilitado, nome, password) VALUES (16, '16666666666', 'kleitonhd@hotmail.com', true, 'Kleiton ', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
INSERT INTO pessoa (id, cpf, email, habilitado, nome, password) VALUES (17, '17777777777', 'anacristinasoriano@gmail.com', true, 'Ana ', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
INSERT INTO pessoa (id, cpf, email, habilitado, nome, password) VALUES (18, '18888888888', 'iurryy@yahoo.com.br', true, 'Iury ', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
INSERT INTO pessoa (id, cpf, email, habilitado, nome, password) VALUES (19, '19999999999', 'jefferson.sb.es@gmail.com', true, 'Jefferson ', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');
INSERT INTO pessoa (id, cpf, email, habilitado, nome, password) VALUES (20, '20202020202', 'wellington.tylon@alu.ufc.br', true, 'Wellington ', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4');


--
-- TOC entry 2076 (class 0 OID 0)
-- Dependencies: 189
-- Name: pessoa_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('pessoa_id_seq', 4, true);


--
-- TOC entry 2044 (class 0 OID 140255)
-- Dependencies: 181
-- Data for Name: projeto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO projeto (id, descricao, nome) VALUES (1, 'Gestão de Pessoas visa o gerenciamento do ciclo de estagio no NPI.', 'Gestão de Pessoas');
INSERT INTO projeto (id, descricao, nome) VALUES (2, 'Gestão de Projetos Acadêmicos', 'GPA - Pesquisa');
INSERT INTO projeto (id, descricao, nome) VALUES (3, 'Suporte a Assuntos Estudantis', 'GPA - MAE');
INSERT INTO projeto (id, descricao, nome) VALUES (4, 'Sistema de Afastamentos de Professores.', 'SiAf');
INSERT INTO projeto (id, descricao, nome) VALUES (5, 'Sistema de apoio ao setor de atendimento social.', 'Sisat');
INSERT INTO projeto (id, descricao, nome) VALUES (6, 'Gestão de Aquisição de Livros.', 'GAL');


--
-- TOC entry 2077 (class 0 OID 0)
-- Dependencies: 180
-- Name: projeto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('projeto_id_seq', 6, true);


--
-- TOC entry 2046 (class 0 OID 140266)
-- Dependencies: 183
-- Data for Name: servidor; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2078 (class 0 OID 0)
-- Dependencies: 182
-- Name: servidor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('servidor_id_seq', 1, false);


--
-- TOC entry 2048 (class 0 OID 140274)
-- Dependencies: 185
-- Data for Name: turma; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (14, 'Turma 14', 4, 2);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (15, 'Turma 15', 4, 2);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (16, 'Turma 16', 4, 2);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (17, 'Turma 17', 4, 2);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (18, 'Turma 18', 4, 2);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (19, 'Turma 19', 4, 2);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (20, 'Turma 20', 4, 2);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (21, 'Turma 21', 5, 2);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (22, 'Turma 22', 5, 2);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (23, 'Turma 23', 5, 2);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (24, 'Turma 24', 5, 2);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (25, 'Turma 25', 5, 2);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (26, 'Turma 26', 5, 2);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (27, 'Turma 27', 5, 2);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (28, 'Turma 28', 5, 2);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (29, 'Turma 29', 5, 2);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (30, 'Turma 30', 5, 2);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (1, 'Turma 1', 1, 1);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (2, 'Turma 2', 1, 1);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (3, 'Turma 3', 1, 1);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (4, 'Turma 4', 2, 1);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (5, 'Turma 5', 2, 1);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (6, 'Turma 6', 2, 1);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (7, 'Turma 7', 2, 1);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (8, 'Turma 8', 3, 1);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (9, 'Turma 9', 3, 2);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (10, 'Turma 10', 3, 2);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (11, 'Turma 11', 3, 2);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (12, 'Turma 12', 3, 2);
INSERT INTO turma (id, nome, periodo_id, supervisor_id) VALUES (13, 'Turma 13', 3, 2);


--
-- TOC entry 2079 (class 0 OID 0)
-- Dependencies: 184
-- Name: turma_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('turma_id_seq', 30, true);


--
-- TOC entry 1893 (class 2606 OID 140206)
-- Name: documento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY documento
    ADD CONSTRAINT documento_pkey PRIMARY KEY (id);


--
-- TOC entry 1915 (class 2606 OID 140398)
-- Name: estagiario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY estagiario
    ADD CONSTRAINT estagiario_pkey PRIMARY KEY (id);


--
-- TOC entry 1895 (class 2606 OID 140225)
-- Name: folga_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY folga
    ADD CONSTRAINT folga_pkey PRIMARY KEY (id);


--
-- TOC entry 1897 (class 2606 OID 140244)
-- Name: horario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY horario
    ADD CONSTRAINT horario_pkey PRIMARY KEY (id);


--
-- TOC entry 1899 (class 2606 OID 140252)
-- Name: papel_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY papel
    ADD CONSTRAINT papel_pkey PRIMARY KEY (id);


--
-- TOC entry 1907 (class 2606 OID 140296)
-- Name: periodo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY periodo
    ADD CONSTRAINT periodo_pkey PRIMARY KEY (id);


--
-- TOC entry 1911 (class 2606 OID 140307)
-- Name: pessoa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pessoa
    ADD CONSTRAINT pessoa_pkey PRIMARY KEY (id);


--
-- TOC entry 1901 (class 2606 OID 140263)
-- Name: projeto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY projeto
    ADD CONSTRAINT projeto_pkey PRIMARY KEY (id);


--
-- TOC entry 1903 (class 2606 OID 140271)
-- Name: servidor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY servidor
    ADD CONSTRAINT servidor_pkey PRIMARY KEY (id);


--
-- TOC entry 1905 (class 2606 OID 140282)
-- Name: turma_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY turma
    ADD CONSTRAINT turma_pkey PRIMARY KEY (id);


--
-- TOC entry 1909 (class 2606 OID 140309)
-- Name: uk_eij1698us3frbj6mgem9ys2vm; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY periodo
    ADD CONSTRAINT uk_eij1698us3frbj6mgem9ys2vm UNIQUE (ano, semestre);


--
-- TOC entry 1913 (class 2606 OID 140311)
-- Name: uk_ep5sgykgcja0n7l38mpomc3jy; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pessoa
    ADD CONSTRAINT uk_ep5sgykgcja0n7l38mpomc3jy UNIQUE (id, cpf);


--
-- TOC entry 1922 (class 2606 OID 140372)
-- Name: fk_23vcfmbo6avvu0cdmen8h5ae6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY turma
    ADD CONSTRAINT fk_23vcfmbo6avvu0cdmen8h5ae6 FOREIGN KEY (supervisor_id) REFERENCES pessoa(id);


--
-- TOC entry 1923 (class 2606 OID 140377)
-- Name: fk_24etpgof2g75x61ngndmipwma; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY papel_pessoa
    ADD CONSTRAINT fk_24etpgof2g75x61ngndmipwma FOREIGN KEY (papel_id) REFERENCES papel(id);


--
-- TOC entry 1917 (class 2606 OID 140332)
-- Name: fk_2scfhh13mk98asa50aex0le60; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY folga
    ADD CONSTRAINT fk_2scfhh13mk98asa50aex0le60 FOREIGN KEY (periodo_id) REFERENCES periodo(id);


--
-- TOC entry 1926 (class 2606 OID 140404)
-- Name: fk_5tjga9wxt9lpc2ihcl791lpf6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY estagiario
    ADD CONSTRAINT fk_5tjga9wxt9lpc2ihcl791lpf6 FOREIGN KEY (projeto_id) REFERENCES projeto(id);


--
-- TOC entry 1916 (class 2606 OID 140312)
-- Name: fk_79blm7igng6ytsuni8uik9pcx; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY documento
    ADD CONSTRAINT fk_79blm7igng6ytsuni8uik9pcx FOREIGN KEY (pessoa_id) REFERENCES pessoa(id);


--
-- TOC entry 1920 (class 2606 OID 140362)
-- Name: fk_austt5cqg46ojlbcgk12n2uid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY servidor
    ADD CONSTRAINT fk_austt5cqg46ojlbcgk12n2uid FOREIGN KEY (turma_id) REFERENCES turma(id);


--
-- TOC entry 1927 (class 2606 OID 140409)
-- Name: fk_c1215tokjdb317pgowhbogg6e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY estagiario
    ADD CONSTRAINT fk_c1215tokjdb317pgowhbogg6e FOREIGN KEY (turma_id) REFERENCES turma(id);


--
-- TOC entry 1925 (class 2606 OID 140399)
-- Name: fk_dlw0njses1e4uu5qi7fbf8yig; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY estagiario
    ADD CONSTRAINT fk_dlw0njses1e4uu5qi7fbf8yig FOREIGN KEY (pessoa_id) REFERENCES pessoa(id);


--
-- TOC entry 1921 (class 2606 OID 140367)
-- Name: fk_gho49v5t6gwo1sn2pa05tew0q; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY turma
    ADD CONSTRAINT fk_gho49v5t6gwo1sn2pa05tew0q FOREIGN KEY (periodo_id) REFERENCES periodo(id);


--
-- TOC entry 1924 (class 2606 OID 140382)
-- Name: fk_l55dwty6ypxia8ont5qvvlpd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY papel_pessoa
    ADD CONSTRAINT fk_l55dwty6ypxia8ont5qvvlpd FOREIGN KEY (pessoa_id) REFERENCES pessoa(id);


--
-- TOC entry 1918 (class 2606 OID 140347)
-- Name: fk_t1wj6falabpjji7ij02t5cihh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY horario
    ADD CONSTRAINT fk_t1wj6falabpjji7ij02t5cihh FOREIGN KEY (turma_id) REFERENCES turma(id);


--
-- TOC entry 1919 (class 2606 OID 140357)
-- Name: fk_t6c8uqjrc3l47dmvomb70p3er; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY servidor
    ADD CONSTRAINT fk_t6c8uqjrc3l47dmvomb70p3er FOREIGN KEY (pessoa_id) REFERENCES pessoa(id);


-- Completed on 2015-04-13 16:26:56

--
-- PostgreSQL database dump complete
--

