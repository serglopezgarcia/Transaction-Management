GRANT ALL PRIVILEGES ON DATABASE "deutsche_transactions_db" to transactions_admin;

CREATE SEQUENCE IF NOT EXISTS public.transaction_transaction_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.transaction
(
    transaction_id bigint NOT NULL DEFAULT nextval('transaction_transaction_id_seq'::regclass),
    account_number character varying(255) COLLATE pg_catalog."default",
    transaction_type character varying(255) COLLATE pg_catalog."default",
    amount numeric(38,2),
    transaction_timestamp timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT transaction_pkey PRIMARY KEY (transaction_id)
)
