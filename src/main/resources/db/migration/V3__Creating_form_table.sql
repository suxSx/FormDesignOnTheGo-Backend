CREATE TABLE IF NOT EXISTS forms (
    form_id SERIAL PRIMARY KEY,
    form_time VARCHAR (80)
);

CREATE TABLE IF NOT EXISTS form_info (
    form_info_id SERIAL PRIMARY KEY,
    form_id serial,
    title VARCHAR(255),
    subtitle VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS info (
    info_id SERIAL PRIMARY KEY,
    form_info_id SERIAL,
    action varchar(80),
    icon varchar(80),
    id serial,
    text varchar(80),
    type varchar(80)
);

CREATE TABLE IF NOT EXISTS form_items (
  form_items_id SERIAL PRIMARY KEY,
  form_id SERIAL,
  description varchar(80),
  error varchar(80),
  id serial,
  needed serial,
  options varchar(80),
  rank serial,
  title varchar(80),
  type varchar(80),
  validation varchar(80)
);