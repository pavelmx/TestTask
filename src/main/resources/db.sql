CREATE TABLE user(
id INT NOT NULL IDENTITY,
name VARCHAR(30),
username VARCHAR(30),
email VARCHAR(30),
password VARCHAR(30),
PRIMARY KEY (id)
);


CREATE TABLE role (
   id INT  NOT NULL IDENTITY,
   name VARCHAR (30),
   PRIMARY KEY (id)
); 

CREATE TABLE user_role (
   id_user INT  NOT NULL,
   id_role INT NOT NULL,
   PRIMARY KEY (id_user)
); 

CREATE TABLE record (
   id INT  NOT NULL IDENTITY,
   distance FLOAT,
   date DATE,  
   time FLOAT,
   id_user INT,
   FOREIGN KEY (id_user) REFERENCES user(id),
   PRIMARY KEY (id)   
); 