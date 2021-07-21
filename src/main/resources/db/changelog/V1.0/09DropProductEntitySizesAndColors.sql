DROP TABLE public.product_entity_sizes_and_colors (
	product_entity_id int8 NOT NULL,
	sizes_and_colors_id int8 NOT NULL,
	CONSTRAINT uk_jga9wvlvm5fwvt8ck00gmcsuy UNIQUE (sizes_and_colors_id),
	CONSTRAINT fk8h854dig6t7p7hcbeyv2y7ows FOREIGN KEY (sizes_and_colors_id) REFERENCES public.product_data_entity(id),
	CONSTRAINT fkfokecqf5ymjx8ehn2wxfeuvyr FOREIGN KEY (product_entity_id) REFERENCES public.product_entity(id)
);