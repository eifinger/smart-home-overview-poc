CREATE TABLE IF NOT EXISTS public.room (
    id bigserial PRIMARY KEY NOT NULL,
    "name" varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.home (
    id bigserial PRIMARY KEY NOT NULL,
    "name" varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.thermostat (
    id bigserial PRIMARY KEY NOT NULL,
    "name" varchar(255) NOT NULL,
    temperature real NOT NULL,
    humidity real NOT NULL
);