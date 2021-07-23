DROP TABLE public.user_entity_roles (
	user_entity_id int8 NOT NULL,
	roles_id int8 NOT NULL,
	CONSTRAINT user_entity_roles_pkey PRIMARY KEY (user_entity_id, roles_id),
	CONSTRAINT fk70m20em46a9gcdkycb9mmpqi2 FOREIGN KEY (roles_id) REFERENCES public.user_authority(id),
	CONSTRAINT fkjvvinok3stf32dvgie3vr73s0 FOREIGN KEY (user_entity_id) REFERENCES public.user_entity(id)
);