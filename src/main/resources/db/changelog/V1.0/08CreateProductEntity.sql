CREATE TABLE public.product_entity (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	created_date timestamp NULL,
	description varchar(255) NULL,
	image_url varchar(255) NULL,
	"name" varchar(255) NULL,
	price float8 NOT NULL,
	cart_entity_id int8 NULL,
	category_entity_id int8 NULL,
	order_entity_id int8 NULL,
	CONSTRAINT product_entity_pkey PRIMARY KEY (id),
	CONSTRAINT fkclccqe848ywo3fuu0a1yr4m0u FOREIGN KEY (order_entity_id) REFERENCES public.order_entity(id),
	CONSTRAINT fkd9hettbv6g4haq85xx05qxxug FOREIGN KEY (cart_entity_id) REFERENCES public.cart_entity(id),
	CONSTRAINT fkkhllkj99qwr2gp47i94nqo7tj FOREIGN KEY (category_entity_id) REFERENCES public.category_entity(id)
);