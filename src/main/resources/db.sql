CREATE TABLE IF NOT EXISTS myuser(
id INT NOT NULL IDENTITY,
name VARCHAR(30),
username VARCHAR(30),
email VARCHAR(30),
password VARCHAR(500),
PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS role (
   id INT  NOT NULL IDENTITY,
   name VARCHAR (30),
   PRIMARY KEY (id)
); 

CREATE TABLE IF NOT EXISTS user_role (
   id_user INT  NOT NULL,
   id_role INT NOT NULL,
   PRIMARY KEY (id_user)
); 

CREATE TABLE IF NOT EXISTS myrecord (
   id INT  NOT NULL IDENTITY,
   distance FLOAT,
   date DATE,  
   time FLOAT,
   id_user INT NOT NULL,  
   FOREIGN KEY (id_user) REFERENCES myuser (id) ON DELETE CASCADE,
   PRIMARY KEY (id)   
); 

CREATE INDEX i_user ON myrecord(id);

INSERT INTO role (id, name) VALUES (1,'ROLE_ADMIN');
INSERT INTO role (id, name) VALUES (2,'ROLE_USER');

INSERT INTO myuser (id, name, username, password, email) VALUES (1, 'AdminName','admin','$2y$10$F.NX4R52gRLich/7XmdZueZczcHAoRvfzBiiLQ8SS/yi3Vk2OLi.m', 'admin@mail.ru');
INSERT INTO myuser (id, name, username, password, email) VALUES (2, 'UserName','user','$2y$10$aZi9zsh/ZL7J95n0k663g.yFNbYXnrmrvxcCKa4YKp/AE0eGe7dqC', 'user@mail.ru');

INSERT INTO user_role (id_user, id_role) VALUES (1,2);
INSERT INTO user_role (id_user, id_role) VALUES (2,1);

INSERT INTO myrecord (id, distance, date, time, id_user) VALUES (1, 3.5, '2019-01-19', 16.5, 2);
INSERT INTO myrecord (id, distance, date, time, id_user) VALUES (2, 2.5, '2019-01-18', 10.5, 2);