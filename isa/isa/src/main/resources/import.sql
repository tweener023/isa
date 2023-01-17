--insert into users (email, password, first_name, last_name, address, city, country, phone_number, jmbg, gender, job, workplace, role, points_collected) values ('marko@email.com', '1234', 'Marko', 'Marković', 'Kisacka 45', 'Novi Sad', 'Srbija', '1234', 1234, 0, 'student', 'FTN', 0, 5);
--insert into users (email, first_name, last_name, points_collected) values ('milan@email.com', 'Milan', 'Milanović', 4);
--insert into users (email, first_name, last_name, points_collected) values ('ivana@email.com', 'Ivana', 'Ivanović', 4);
--insert into users (email, first_name, last_name, points_collected) values ('bojan@email.com', 'Bojan', 'Bojanović', 2);
--insert into users (email, first_name, last_name, points_collected) values ('pera@email.com', 'Pera', 'Perić', 1);
--insert into users (email, first_name, last_name, points_collected) values ('zoran@email.com', 'Zoran', 'Zoranović', 2);
--insert into users (email, first_name, last_name, points_collected) values ('bojana@email.com', 'Bojana', 'Bojanović', 5);
--insert into users (email, first_name, last_name, points_collected) values ('milana@email.com', 'Milana', 'Milanović', 7);
--insert into users (email, first_name, last_name, points_collected) values ('jovana@email.com', 'Jovana', 'Jovanić', 9);
insert into users (username, email, password, first_name, last_name, address, city, zip_code, country, phone_number, jmbg, gender, job, workplace, points_collected) values ('mare','marko@email.com', '1234', 'Marko', 'Marković', 'Kisacka 45', 'Novi Sad','21000', 'Srbija', '1234', 1234,null, 'student', 'FTN',11);
insert into users (username, email, password, first_name, last_name, address, city, zip_code, country, phone_number, jmbg, gender, job, workplace, points_collected) values ('saki','saki@email.com', '1234', 'Saki', 'Sakić', 'Kisacka 45', 'Novi Sad','21000', 'Srbija', '1233232', 123421, null, 'student', 'FTN',1);
insert into users (username, email, password, first_name, last_name, address, city, zip_code, country, phone_number, jmbg, gender, job, workplace, points_collected) values ('mile','mile@email.com', '1234', 'Mile', 'Milic', 'Kisacka 45', 'Novi Sad','21000', 'Srbija', '123324', 12324, null, 'student', 'FTN',0);
insert into users (username, email, password, first_name, last_name, address, city, zip_code, country, phone_number, jmbg, gender, job, workplace, points_collected) values ('stefa','stefa@email.com', '1234', 'Stefa', 'Stefanovic', 'Kisacka 45', 'Novi Sad','21000', 'Srbija', '121234', 21345, null, 'student', 'FTN',21);
insert into users (username, email, password, first_name, last_name, address, city, zip_code, country, phone_number, jmbg, gender, job, workplace, points_collected) values ('zoki','zoki@email.com', '1234', 'Zoki', 'Jankov', 'Kisacka 45', 'Novi Sad','21000', 'Srbija', '1234', 1234983, null, 'student', 'FTN',10);
insert into users (username, email, password, first_name, last_name, address, city, zip_code, country, phone_number, jmbg, gender, job, workplace, points_collected) values ('pera','pera@email.com', '1234', 'Pera', 'Peric', 'Kisacka 45', 'Novi Sad','21000', 'Srbija', '1234', 2431234, null, 'student', 'FTN',4);


insert into facility ( center_name, center_address, center_description, center_admins, center_supplies) values ('Klinicki centar Vojvodine', 'Vojvodjanska 16', 'ovo je opis za kcv', null, 123);
insert into facility ( center_name, center_address, center_description,  center_admins, center_supplies) values ('Klinicki centar Beograd', 'Beogradska 16', 'ovo je opis za kc bg', null, 12);
insert into facility ( center_name, center_address, center_description,  center_admins, center_supplies) values ('Klinicki centar Kragujevac', 'Carapanska 16', 'ovo je opis za kc kg', null, 1);
insert into facility ( center_name, center_address, center_description,  center_admins, center_supplies) values ('Klinicki centar Subotica', 'Madjarska 16', 'ovo je opis za kc subotice', null, 3);


insert into appointments ( user_id, center_id, date_of_appointment) values (1,2, '2021-08-09');
insert into appointments ( user_id, center_id, date_of_appointment) values (2,2, '2021-08-09');
insert into appointments ( user_id, center_id, date_of_appointment) values (3,2, '2021-08-09');
insert into appointments ( user_id, center_id, date_of_appointment) values (4,2, '2021-08-09');

insert into roles ( name ) values ('ROLE_USER');
insert into roles ( name ) values ('ROLE_MEDIC');
insert into roles ( name ) values ('ROLE_ADMINISTRATOR');

insert into user_roles(user_id,role_id) values (1,1);

insert into user_roles(user_id,role_id) values (2,1);

insert into user_roles(user_id,role_id) values (3,1);

insert into user_roles(user_id,role_id) values (4,1);

insert into user_roles(user_id,role_id) values (5,1);

insert into user_roles(user_id,role_id) values (6,1);