DROP TABLE IF EXISTS user;

CREATE TABLE user (
  email varchar(255) PRIMARY KEY,
  name varchar(255),
  pw varchar(255)
);

DROP TABLE IF EXISTS product;

CREATE TABLE product (
  code varchar(6) PRIMARY KEY,
  name varchar(255) not null,
  description varchar(255),
  price float8  NOT NULL CHECK ( price>= 0 )
);

DROP TABLE IF EXISTS ownership;

CREATE TABLE ownership (
  user_email varchar(255),
  product_code varchar(6)
);

DROP TABLE IF EXISTS rating;

CREATE TABLE rating (
  product_code varchar(6),
  user_email varchar(255),
  rating int
);

ALTER TABLE ownership ADD FOREIGN KEY (user_email) REFERENCES user (email);

ALTER TABLE ownership ADD FOREIGN KEY (product_code) REFERENCES product (`code`);

ALTER TABLE rating ADD FOREIGN KEY (user_email) REFERENCES user (email);

ALTER TABLE rating ADD FOREIGN KEY (product_code) REFERENCES product (`code`);


insert into user (email,name,pw) values ('test@test.com','test','$2a$10$kM74OU261Qw/KO2DgVTjf.EZ3qXv.3puW.iVw1eOjBsCCHE1VLeOK');
insert into user (email,name,pw) values ('test2@test.com','test2','$2a$10$kM74OU261Qw/KO2DgVTjf.EZ3qXv.3puW.iVw1eOjBsCCHE1VLeOK');
insert into user (email,name,pw) values ('test3@test.com','test3','$2a$10$kM74OU261Qw/KO2DgVTjf.EZ3qXv.3puW.iVw1eOjBsCCHE1VLeOK');

insert into product(code,name,description,price) values ('000001','test product','test desc',1234.1234);
insert into product(code,name,description,price) values ('000002','test product','test desc',1234.1234);
insert into product(code,name,description,price) values ('000003','test product','test desc',1234.1234);

insert into ownership (user_email,product_code) values ('test@test.com','000001');
insert into ownership (user_email,product_code) values ('test2@test.com','000002');
insert into ownership (user_email,product_code) values ('test3@test.com','000003');

insert into rating (product_code,user_email,rating) values ('000001','test@test.com',3);

insert into rating (product_code,user_email,rating) values ('000001','test2@test.com',6);

insert into rating (product_code,user_email,rating) values ('000001','test3@test.com',9);

insert into rating (product_code,user_email,rating) values ('000002','test@test.com',3);

insert into rating (product_code,user_email,rating) values ('000002','test2@test.com',6);

insert into rating (product_code,user_email,rating) values ('000002','test3@test.com',9);

insert into rating (product_code,user_email,rating) values ('000003','test@test.com',3);

insert into rating (product_code,user_email,rating) values ('000003','test2@test.com',6);

insert into rating (product_code,user_email,rating) values ('000003','test3@test.com',9);