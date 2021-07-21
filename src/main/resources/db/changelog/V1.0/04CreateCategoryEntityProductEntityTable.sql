CREATE TABLE public.category_entity_product_entity_set (
	category_entity_id int8 NOT NULL,
	product_entity_set_id int8 NOT NULL,
	CONSTRAINT category_entity_product_entity_set_pkey PRIMARY KEY (category_entity_id, product_entity_set_id),
	CONSTRAINT uk_qpxom260lglav3ebvkx6wkp91 UNIQUE (product_entity_set_id),
	CONSTRAINT fk79rpxwkr70obco1lm6yddpq0g FOREIGN KEY (category_entity_id) REFERENCES public.category_entity(id),
	CONSTRAINT fkit0jycd0pbf1qp37i0oy82m0s FOREIGN KEY (product_entity_set_id) REFERENCES public.product_entity(id)
);
