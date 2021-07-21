CREATE TABLE public.user_authority_users (
	user_authority_id int8 NOT NULL,
	users_id int8 NOT NULL,
	CONSTRAINT user_authority_users_pkey PRIMARY KEY (user_authority_id, users_id),
	CONSTRAINT fk645qjvfma112pl057n1t09jad FOREIGN KEY (users_id) REFERENCES public.user_entity(id),
	CONSTRAINT fksl2ii0oeabmw8k7ar9j7pe2dh FOREIGN KEY (user_authority_id) REFERENCES public.user_authority(id)
);