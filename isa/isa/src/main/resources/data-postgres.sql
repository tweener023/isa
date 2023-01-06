insert into users (email, password, first_name, last_name, address, city, country, phone_number, jmbg, gender, job, workplace, role, points_collected) values ('marko@email.com', '1234', 'Marko', 'Marković', 'Kisacka 45', 'Novi Sad', 'Srbija', '1234', 1234, 0, 'student', 'FTN', 0, 5);
--insert into users (email, first_name, last_name, points_collected) values ('milan@email.com', 'Milan', 'Milanović', 4);
--insert into users (email, first_name, last_name, points_collected) values ('ivana@email.com', 'Ivana', 'Ivanović', 4);
--insert into users (email, first_name, last_name, points_collected) values ('bojan@email.com', 'Bojan', 'Bojanović', 2);
--insert into users (email, first_name, last_name, points_collected) values ('pera@email.com', 'Pera', 'Perić', 1);
--insert into users (email, first_name, last_name, points_collected) values ('zoran@email.com', 'Zoran', 'Zoranović', 2);
--insert into users (email, first_name, last_name, points_collected) values ('bojana@email.com', 'Bojana', 'Bojanović', 5);
--insert into users (email, first_name, last_name, points_collected) values ('milana@email.com', 'Milana', 'Milanović', 7);
--insert into users (email, first_name, last_name, points_collected) values ('jovana@email.com', 'Jovana', 'Jovanić', 9);

insert into facility ( center_name, center_address, center_description, center_appointments, center_admins, center_supplies) values ('Klinicki centar Vojvodine', 'Vojvodjanska 16', 'ovo je opis za kcv', null, null, 123);
insert into facility ( center_name, center_address, center_description, center_appointments, center_admins, center_supplies) values ('Klinicki centar Beograd', 'Beogradska 16', 'ovo je opis za kc bg', null, null, 12);
insert into facility ( center_name, center_address, center_description, center_appointments, center_admins, center_supplies) values ('Klinicki centar Kragujevac', 'Carapanska 16', 'ovo je opis za kc kg', null, null, 1);
insert into facility ( center_name, center_address, center_description, center_appointments, center_admins, center_supplies) values ('Klinicki centar Subotica', 'Madjarska 16', 'ovo je opis za kc subotice', null, null, 3);


insert into appointments ( user_id, date_of_appointment, facility_name) values ('1', '2021-08-09' , 'Klinicki Centar Vojvodine');
insert into appointments ( user_id, date_of_appointment, facility_name) values ('2', '2021-08-09', 'Klinicki Centar Vojvodine');
insert into appointments ( user_id, date_of_appointment, facility_name) values (null, '2021-08-09' , 'Klinicki Centar Vojvodine');
insert into appointments ( user_id, date_of_appointment, facility_name) values (null , '2021-08-09' , 'Klinicki Centar Vojvodine');
