DROP TABLE public.product_data_entity (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	color varchar(255) NULL,
	quantity int4 NOT NULL,
	"size" int4 NOT NULL,
	product_entity_id int8 NULL,
	CONSTRAINT product_data_entity_pkey PRIMARY KEY (id),
	CONSTRAINT fkkr6yq8v02614o3t54844r7525 FOREIGN KEY (product_entity_id) REFERENCES public.product_entity(id)
);
