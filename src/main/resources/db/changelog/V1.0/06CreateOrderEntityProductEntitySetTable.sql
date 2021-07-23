CREATE TABLE public.order_entity_product_entity_set (
	order_entity_id int8 NOT NULL,
	product_entity_set_id int8 NOT NULL,
	CONSTRAINT order_entity_product_entity_set_pkey PRIMARY KEY (order_entity_id, product_entity_set_id),
	CONSTRAINT uk_ge00rmf8luph2y3ft3x3a45e UNIQUE (product_entity_set_id),
	CONSTRAINT fk5wjtrh07uy04d8p6ryjgc8r9f FOREIGN KEY (product_entity_set_id) REFERENCES public.product_entity(id),
	CONSTRAINT fk7s8aroy2te954lypnw9q1qe4p FOREIGN KEY (order_entity_id) REFERENCES public.order_entity(id)
);