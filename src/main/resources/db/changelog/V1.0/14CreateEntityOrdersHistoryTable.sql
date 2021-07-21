CREATE TABLE public.user_entity_orders_history (
	user_entity_id int8 NOT NULL,
	orders_history_id int8 NOT NULL,
	CONSTRAINT uk_h843vif19ts7ua60s2tgsvh8c UNIQUE (orders_history_id),
	CONSTRAINT fkh9yoea7e4jk6s3nwmxnl17s7y FOREIGN KEY (user_entity_id) REFERENCES public.user_entity(id),
	CONSTRAINT fkqu4v05qoj0eve8tk2w7lgb5ru FOREIGN KEY (orders_history_id) REFERENCES public.order_entity(id)
);