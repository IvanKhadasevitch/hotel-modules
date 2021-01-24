create database hotel_test;
show databases;
use hotel_test;

create table users (
	users_id			int(11) auto_increment primary key not null,
    users_name			varchar(20) not null,
    users_surname		varchar(30) not null,
    users_birthDate 	date not null,
    users_email			varchar(64) not null,
    users_password		varchar(21) not null
);
CREATE UNIQUE INDEX users_email_uindex ON users (users_email);
#explain  users;
#describe users;
#select * from  users;

insert into users set 
    users_name			= 'Maikle',
    users_surname		= 'Jordan',
    users_birthDate 	= '1980-10-12',
    users_email			= 'jordan@gmail.com',
    users_password		= '1';
insert into users set 
    users_name			= 'Alexandre',
    users_surname		= 'Pushkin',
    users_birthDate 	= '1799-06-06',
    users_email			= 'pushkin@mail.ru',
    users_password		= '2';
insert into users set 
    users_name			= 'Vasja',
    users_surname		= 'Pypkin',
    users_birthDate 	= '1970-01-13',
    users_email			= 'pypkin@yandex.ru',
    users_password		= '3';
insert into users set 
    users_name			= 'Marija',
    users_surname		= 'Pobeda',
    users_birthDate 	= '1945-05-09',
    users_email			= 'pobeda@gmail.com', 
    users_password		= '4';
    
select * from  users;    
    
CREATE TABLE hotel
(
    hotel_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    hotel_name VARCHAR(30) NOT NULL,
    hotel_email VARCHAR(64) NOT NULL
);    

insert into hotel set 
    hotel_name			= 'Negresko Princes',
    hotel_email			= 'negresko@gmail.com';  
insert into hotel set 
    hotel_name			= 'Royal luxury resort',
    hotel_email			= 'royalLuxuryResort@gmail.com';     
    
select * from  hotel;   
   
   
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

insert into roomType set 
    roomType_name			= 'ONE',
    roomType_seats			= 1,  
    roomType_price			= 3000,
    roomType_currency 		= 'USD',
    roomType_hotel_id		= 1;  
insert into roomType set 
    roomType_name			= 'DOUBLE',
    roomType_seats			= 2,  
    roomType_price			= 5000,
    roomType_currency 		= 'USD',
    roomType_hotel_id		= 1; 
insert into roomType set 
    roomType_name			= 'DOUBLE_SUITE',
    roomType_seats			= 2,  
    roomType_price			= 7000,
    roomType_currency 		= 'USD',
    roomType_hotel_id		= 1; 
insert into roomType set 
    roomType_name			= 'FOUR_FAMILE',
    roomType_seats			= 4,  
    roomType_price			= 10000,
    roomType_currency 		= 'USD',
    roomType_hotel_id		= 1; 
insert into roomType set 
    roomType_name			= 'FOUR_SUITE',
    roomType_seats			= 4,  
    roomType_price			= 15000,
    roomType_currency 		= 'USD',
    roomType_hotel_id		= 1;     
    
insert into roomType set 
    roomType_name			= 'ROYAL_SUITE',
    roomType_seats			= 7,  
    roomType_price			= 150000,
    roomType_currency 		= 'USD',
    roomType_hotel_id		= 2;   
    
insert into roomType set 
    roomType_name			= 'PRESIDENT_SUITE',
    roomType_seats			= 7,  
    roomType_price			= 300000,
    roomType_currency 		= 'USD',
    roomType_hotel_id		= 2;     
     
select * from  roomType; 


CREATE TABLE room
(
    room_id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    room_number VARCHAR(10) NOT NULL,
    room_roomType_id INT(11) NOT NULL,
    CONSTRAINT room_roomType_roomType_id_fk FOREIGN KEY (room_roomType_id) REFERENCES roomType (roomType_id) ON DELETE CASCADE
);   
insert into room set 
    room_number			= '101',
    room_roomType_id	= 1;
insert into room set 
    room_number			= '102',
    room_roomType_id	= 1;
insert into room set 
    room_number			= '103',
    room_roomType_id	= 1;
insert into room set 
    room_number			= '104',
    room_roomType_id	= 1;
insert into room set 
    room_number			= '105',
    room_roomType_id	= 1;
insert into room set 
    room_number			= '106',
    room_roomType_id	= 1;
insert into room set 
    room_number			= '107',
    room_roomType_id	= 1;
insert into room set 
    room_number			= '108',
    room_roomType_id	= 1;
insert into room set 
    room_number			= '109',
    room_roomType_id	= 1;
insert into room set 
    room_number			= '110',
    room_roomType_id	= 1;

insert into room set 
    room_number			= '201',
    room_roomType_id	= 2;
insert into room set 
    room_number			= '202',
    room_roomType_id	= 2;
insert into room set 
    room_number			= '203',
    room_roomType_id	= 2;
insert into room set 
    room_number			= '204',
    room_roomType_id	= 2;
insert into room set 
    room_number			= '205',
    room_roomType_id	= 2;
insert into room set 
    room_number			= '206',
    room_roomType_id	= 2;
insert into room set 
    room_number			= '207',
    room_roomType_id	= 2;
insert into room set 
    room_number			= '208',
    room_roomType_id	= 2;
insert into room set 
    room_number			= '209',
    room_roomType_id	= 2;
insert into room set 
    room_number			= '210',
    room_roomType_id	= 2;
    
insert into room set 
    room_number			= '301',
    room_roomType_id	= 3;  
insert into room set 
    room_number			= '302',
    room_roomType_id	= 3;  
insert into room set 
    room_number			= '303',
    room_roomType_id	= 3;  
insert into room set 
    room_number			= '304',
    room_roomType_id	= 3;  
insert into room set 
    room_number			= '305',
    room_roomType_id	= 3;  
insert into room set 
    room_number			= '306',
    room_roomType_id	= 3;  
insert into room set 
    room_number			= '307',
    room_roomType_id	= 3;      

insert into room set 
    room_number			= '401',
    room_roomType_id	= 4;  
insert into room set 
    room_number			= '402',
    room_roomType_id	= 4;  
insert into room set 
    room_number			= '403',
    room_roomType_id	= 4;  
insert into room set 
    room_number			= '404',
    room_roomType_id	= 4;  
insert into room set 
    room_number			= '405',
    room_roomType_id	= 4;  
    
insert into room set 
    room_number			= '501',
    room_roomType_id	= 5;     
insert into room set 
    room_number			= '502',
    room_roomType_id	= 5;  
insert into room set 
    room_number			= '503',
    room_roomType_id	= 5;  
insert into room set 
    room_number			= '504',
    room_roomType_id	= 5;  
insert into room set 
    room_number			= '505',
    room_roomType_id	= 5;      

#hotel_name			= 'Royal luxury resort'
insert into room set 
    room_number			= '101',
    room_roomType_id	= 6;
insert into room set 
    room_number			= '102',
    room_roomType_id	= 6;  
insert into room set 
    room_number			= '103',
    room_roomType_id	= 6;
insert into room set 
    room_number			= '104',
    room_roomType_id	= 6;
insert into room set 
    room_number			= '105',
    room_roomType_id	= 6;  
    
insert into room set 
    room_number			= '201',
    room_roomType_id	= 7;    
insert into room set 
    room_number			= '202',
    room_roomType_id	= 7;  
insert into room set 
    room_number			= '203',
    room_roomType_id	= 7;  
insert into room set 
    room_number			= '204',
    room_roomType_id	= 7;  
insert into room set 
    room_number			= '205',
    room_roomType_id	= 7;      

select * from  room; 

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
select * from  `order`; 

CREATE TABLE `admin`
(
    admin_id 			INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    admin_hotel_id		INT(11) NOT NULL,
    admin_name 			VARCHAR(30) NOT NULL,
    admin_password		VARCHAR(51) NOT NULL,
    CONSTRAINT admin_hotel_hotel_id_fk FOREIGN KEY (admin_hotel_id) REFERENCES hotel (hotel_id) ON DELETE CASCADE
);

insert into `admin` set 
    admin_hotel_id		= '1',
    admin_name			= 'Cool Admin negro',
    admin_password 		= 'xMpCOKC5I4INzFCab3WEmw==';
insert into `admin` set 
    admin_hotel_id		= '1',
    admin_name			= 'Пчелка Жу. Отель Негреско',
    admin_password 		= 'xMpCOKC5I4INzFCab3WEmw==';    
insert into `admin` set 
    admin_hotel_id		= '2',
    admin_name			= 'Cool Admin royal',
    admin_password 		= 'yB5yjZ1ML2NvBn+JzBSGLA==';    
insert into `admin` set 
    admin_hotel_id		= '2',
    admin_name			= 'Трудяга Вася. Отель Роял',
    admin_password 		= 'yB5yjZ1ML2NvBn+JzBSGLA==';     


select * from  `admin`; 
#update admin set admin_hotel_id ='2' where admin_id = '2';

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

select * from  bill; 

#total tables select
select * from  users; 
select * from  `admin`; 
select * from  hotel;  
select * from  roomType; 
select * from  room; 

select * from  `order`;
select * from  bill; 

#удаляем базу hotel_test
#drop database hotel_test;
show databases; 