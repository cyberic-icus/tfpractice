CREATE TABLE public.cart_entity_products (
	cart_entity_id int8 NOT NULL,
	products_id int8 NOT NULL,
	CONSTRAINT cart_entity_products_pkey PRIMARY KEY (cart_entity_id, products_id),
	CONSTRAINT uk_blcuijc94y2vwl91kfle3jnch UNIQUE (products_id),
	CONSTRAINT fk1q69mfa6vd8yqee83yjx6kl3f FOREIGN KEY (products_id) REFERENCES public.product_entity(id),
	CONSTRAINT fkbnyhh99fhjn1hwehye2bv6ut3 FOREIGN KEY (cart_entity_id) REFERENCES public.cart_entity(id)
);
