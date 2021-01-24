CREATE DATABASE `hotel_db` DEFAULT CHARACTER SET utf8;

CREATE USER 'hotel_user' IDENTIFIED BY 'hotel_password';

GRANT SELECT,INSERT,UPDATE,DELETE
ON `hotel_db`. *
TO 'hotel_user';

USE hotel_db;
show databases;


CREATE TABLE users (
	users_id			INT(11) auto_increment PRIMARY KEY NOT NULL ,
    users_name			VARCHAR (20) NOT NULL,
    users_surname		VARCHAR (30) NOT NULL,
    users_birthDate 	DATE NOT NULL,
    users_email			VARCHAR (64) NOT NULL,
    users_password		VARCHAR (21) NOT NULL
);
CREATE UNIQUE INDEX users_email_uindex ON users (users_email);
#explain  users;
#describe users;
#select * from  users;

INSERT INTO users SET
    users_name			= 'Maikle',
    users_surname		= 'Jordan',
    users_birthDate 	= '1980-10-12',
    users_email			= 'jordan@gmail.com',
    users_password		= '1';
INSERT INTO users SET
    users_name			= 'Alexandre',
    users_surname		= 'Pushkin',
    users_birthDate 	= '1799-06-06',
    users_email			= 'pushkin@mail.ru',
    users_password		= '2';
INSERT INTO users SET
    users_name			= 'Vasja',
    users_surname		= 'Pypkin',
    users_birthDate 	= '1970-01-13',
    users_email			= 'pypkin@yandex.ru',
    users_password		= '3';
INSERT INTO users SET
    users_name			= 'Marija',
    users_surname		= 'Pobeda',
    users_birthDate 	= '1945-05-09',
    users_email			= 'pobeda@gmail.com', 
    users_password		= '4';
    
SELECT * FROM  users;

CREATE TABLE hotel
(
    hotel_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    hotel_name VARCHAR(30) NOT NULL,
    hotel_email VARCHAR(64) NOT NULL
);    

INSERT INTO hotel SET
    hotel_name			= 'Negresko Princes',
    hotel_email			= 'negresko@gmail.com';  
insert into hotel set 
    hotel_name			= 'Royal luxury resort',
    hotel_email			= 'royalLuxuryResort@gmail.com';     
    
SELECT * FROM  hotel;

   
CREATE TABLE roomType
(
  roomType_id			INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  roomType_name  	 	VARCHAR(30) NOT NULL,
  roomType_seats 		INT NOT NULL,
  roomType_price 		INT NOT NULL,
  roomType_currency 	VARCHAR(12) NOT NULL,
  roomType_hotel_id 	INT(11) NOT NULL,
  CONSTRAINT roomType_hotel_hotel_id_fk FOREIGN KEY (roomType_hotel_id) REFERENCES hotel (hotel_id) ON DELETE CASCADE
);

INSERT INTO roomType SET
    roomType_name			= 'ONE',
    roomType_seats			= 1,  
    roomType_price			= 3000,
    roomType_currency 		= 'USD',
    roomType_hotel_id		= 1;  
INSERT INTO roomType SET
    roomType_name			= 'DOUBLE',
    roomType_seats			= 2,  
    roomType_price			= 5000,
    roomType_currency 		= 'USD',
    roomType_hotel_id		= 1; 
INSERT INTO roomType SET
    roomType_name			= 'DOUBLE_SUITE',
    roomType_seats			= 2,  
    roomType_price			= 7000,
    roomType_currency 		= 'USD',
    roomType_hotel_id		= 1; 
INSERT INTO roomType SET
    roomType_name			= 'FOUR_FAMILE',
    roomType_seats			= 4,  
    roomType_price			= 10000,
    roomType_currency 		= 'USD',
    roomType_hotel_id		= 1; 
INSERT INTO roomType SET
    roomType_name			= 'FOUR_SUITE',
    roomType_seats			= 4,  
    roomType_price			= 15000,
    roomType_currency 		= 'USD',
    roomType_hotel_id		= 1;     
    
INSERT INTO roomType SET
    roomType_name			= 'ROYAL_SUITE',
    roomType_seats			= 7,  
    roomType_price			= 150000,
    roomType_currency 		= 'USD',
    roomType_hotel_id		= 2;   
    
INSERT INTO roomType SET
    roomType_name			= 'PRESIDENT_SUITE',
    roomType_seats			= 7,  
    roomType_price			= 300000,
    roomType_currency 		= 'USD',
    roomType_hotel_id		= 2;     
     
SELECT * FROM  roomType;


CREATE TABLE room
(
    room_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    room_number VARCHAR(10) NOT NULL,
    room_roomType_id INT(11) NOT NULL,
    CONSTRAINT room_roomType_roomType_id_fk FOREIGN KEY (room_roomType_id) REFERENCES roomType (roomType_id) ON DELETE CASCADE
);   
INSERT INTO room SET
    room_number			= '101',
    room_roomType_id	= 1;
INSERT INTO room SET
    room_number			= '102',
    room_roomType_id	= 1;
INSERT INTO room SET
    room_number			= '103',
    room_roomType_id	= 1;
INSERT INTO room SET
    room_number			= '104',
    room_roomType_id	= 1;
INSERT INTO room SET
    room_number			= '105',
    room_roomType_id	= 1;
INSERT INTO room SET
    room_number			= '106',
    room_roomType_id	= 1;
INSERT INTO room SET
    room_number			= '107',
    room_roomType_id	= 1;
INSERT INTO room SET
    room_number			= '108',
    room_roomType_id	= 1;
INSERT INTO room SET
    room_number			= '109',
    room_roomType_id	= 1;
INSERT INTO room SET
    room_number			= '110',
    room_roomType_id	= 1;

INSERT INTO room SET
    room_number			= '201',
    room_roomType_id	= 2;
INSERT INTO room SET
    room_number			= '202',
    room_roomType_id	= 2;
INSERT INTO room SET
    room_number			= '203',
    room_roomType_id	= 2;
INSERT INTO room SET
    room_number			= '204',
    room_roomType_id	= 2;
INSERT INTO room SET
    room_number			= '205',
    room_roomType_id	= 2;
INSERT INTO room SET
    room_number			= '206',
    room_roomType_id	= 2;
INSERT INTO room SET
    room_number			= '207',
    room_roomType_id	= 2;
INSERT INTO room SET
    room_number			= '208',
    room_roomType_id	= 2;
INSERT INTO room SET
    room_number			= '209',
    room_roomType_id	= 2;
INSERT INTO room SET
    room_number			= '210',
    room_roomType_id	= 2;
    
INSERT INTO room SET
    room_number			= '301',
    room_roomType_id	= 3;  
INSERT INTO room SET
    room_number			= '302',
    room_roomType_id	= 3;  
INSERT INTO room SET
    room_number			= '303',
    room_roomType_id	= 3;  
INSERT INTO room SET
    room_number			= '304',
    room_roomType_id	= 3;  
INSERT INTO room SET
    room_number			= '305',
    room_roomType_id	= 3;  
INSERT INTO room SET
    room_number			= '306',
    room_roomType_id	= 3;  
INSERT INTO room SET
    room_number			= '307',
    room_roomType_id	= 3;      

INSERT INTO room SET
    room_number			= '401',
    room_roomType_id	= 4;  
INSERT INTO room SET
    room_number			= '402',
    room_roomType_id	= 4;  
INSERT INTO room SET
    room_number			= '403',
    room_roomType_id	= 4;  
INSERT INTO room SET
    room_number			= '404',
    room_roomType_id	= 4;  
INSERT INTO room SET
    room_number			= '405',
    room_roomType_id	= 4;  
    
INSERT INTO room SET
    room_number			= '501',
    room_roomType_id	= 5;     
INSERT INTO room SET
    room_number			= '502',
    room_roomType_id	= 5;  
INSERT INTO room SET
    room_number			= '503',
    room_roomType_id	= 5;  
INSERT INTO room SET
    room_number			= '504',
    room_roomType_id	= 5;  
INSERT INTO room SET
    room_number			= '505',
    room_roomType_id	= 5;      

#hotel_name			= 'Royal luxury resort'
INSERT INTO room SET
    room_number			= '101',
    room_roomType_id	= 6;
INSERT INTO room SET
    room_number			= '102',
    room_roomType_id	= 6;  
INSERT INTO room SET
    room_number			= '103',
    room_roomType_id	= 6;
INSERT INTO room SET
    room_number			= '104',
    room_roomType_id	= 6;
INSERT INTO room SET
    room_number			= '105',
    room_roomType_id	= 6;  
    
INSERT INTO room SET
    room_number			= '201',
    room_roomType_id	= 7;    
INSERT INTO room SET
    room_number			= '202',
    room_roomType_id	= 7;  
INSERT INTO room SET
    room_number			= '203',
    room_roomType_id	= 7;  
INSERT INTO room SET
    room_number			= '204',
    room_roomType_id	= 7;  
INSERT INTO room SET
    room_number			= '205',
    room_roomType_id	= 7;      

SELECT * FROM room;

CREATE TABLE `order`
(
    order_id 				INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    order_dateProcessing	DATE NOT NULL,
	order_admin_id 			INT(11) NOT NULL,
    order_status 			VARCHAR(15) NOT NULL,
    order_users_id 			INT(11) NOT NULL,
    order_roomType_id 		INT(11) NOT NULL,
    order_room_id 			INT(11) NOT NULL,
    order_arrivalDate 		DATE NOT NULL,
    order_eventsDate 		DATE NOT NULL,
    order_total 			INT(11),
    order_bill_id  			INT(11)
);   
CREATE  INDEX order_arrivalDate_index ON `order` (order_arrivalDate );
CREATE  INDEX order_eventsDate_index ON `order` (order_eventsDate );
SELECT * FROM  `order`;

CREATE TABLE `admin`
(
    admin_id 			INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    admin_hotel_id		INT(11) NOT NULL,
    admin_name 			VARCHAR(30) NOT NULL,
    admin_password		VARCHAR(51) NOT NULL,
    CONSTRAINT admin_hotel_hotel_id_fk FOREIGN KEY (admin_hotel_id) REFERENCES hotel (hotel_id) ON DELETE CASCADE
);

INSERT INTO `admin` SET
    admin_hotel_id		= '1',
    admin_name			= 'Cool Admin negro',
    admin_password 		= 'xMpCOKC5I4INzFCab3WEmw==';
INSERT INTO `admin` SET
    admin_hotel_id		= '2',
    admin_name			= 'Cool Admin royal',
    admin_password 		= 'yB5yjZ1ML2NvBn+JzBSGLA==';


SELECT * FROM  `admin`;

CREATE TABLE bill
(
    bill_id 			INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    bill_data 			DATE NOT NULL,
    bill_order_id 		INT(11) NOT NULL,
    bill_users_id 		INT(11) NOT NULL,
    bill_room_id 		INT(11) NOT NULL,
    bill_arrivalDate 	DATE NOT NULL,
    bill_eventsDate		DATE NOT NULL,   
    bill_total			INT(11),   
    bill_status			VARCHAR(12) NOT NULL,
    CONSTRAINT bill_room_room_id_fk FOREIGN KEY (bill_room_id) REFERENCES room (room_id),
    CONSTRAINT bill_users_users_id_fk FOREIGN KEY (bill_users_id) REFERENCES users (users_id),
    CONSTRAINT bill_order_order_id_fk FOREIGN KEY (bill_order_id) REFERENCES `order` (order_id)
);

SELECT * FROM  bill;

#delete from bill;

#удаляем базу hotel_db
#drop database hotel_db;
show databases; 