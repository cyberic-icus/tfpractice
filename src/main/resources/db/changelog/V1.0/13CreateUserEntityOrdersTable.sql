CREATE TABLE public.user_entity_orders (
	user_entity_id int8 NOT NULL,
	orders_id int8 NOT NULL,
	CONSTRAINT uk_enkeibsmcd4blsut5v79brvpn UNIQUE (orders_id),
	CONSTRAINT user_entity_orders_pkey PRIMARY KEY (user_entity_id, orders_id),
	CONSTRAINT fk2y1aradm5tkihdptlss82ic6n FOREIGN KEY (orders_id) REFERENCES public.order_entity(id),
	CONSTRAINT fkumym80r2mygxwdx2qsbausrf FOREIGN KEY (user_entity_id) REFERENCES public.user_entity(id)
);
