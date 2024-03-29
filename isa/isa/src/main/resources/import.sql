--ordinary users
insert into users (username, email, password, first_name, last_name, address, city, zip_code, country, phone_number, jmbg, gender, job, workplace, points_collected, filled_questionnaire, account_verified) values ('mare','marko@email.com', '$2a$10$iwMIUYsBRR4n5dHJi7ZCUeeeDYgBqrt8dyVCUHvbKju6fKdI.Q2UC', 'Marko', 'Marković', 'Kisacka 45', 'Novi Sad','21000', 'Srbija', '0656693691', 11111111,0, 'student', 'FTN',11, true, true);
insert into users (username, email, password, first_name, last_name, address, city, zip_code, country, phone_number, jmbg, gender, job, workplace, points_collected, filled_questionnaire, account_verified) values ('saki','saki@gmail.com', '$2a$10$iwMIUYsBRR4n5dHJi7ZCUeeeDYgBqrt8dyVCUHvbKju6fKdI.Q2UC', 'Saki', 'Sakic', 'Kisacka 45', 'Novi Sad','21000', 'Srbija', '0656693692', 22222222, 1, 'student', 'FTN',1, false, true);
insert into users (username, email, password, first_name, last_name, address, city, zip_code, country, phone_number, jmbg, gender, job, workplace, points_collected, filled_questionnaire, account_verified) values ('mile','mile@email.com', '$2a$10$iwMIUYsBRR4n5dHJi7ZCUeeeDYgBqrt8dyVCUHvbKju6fKdI.Q2UC', 'Mile', 'Milic', 'Kisacka 45', 'Novi Sad','21000', 'Srbija', '0656693693', 33333333, 1, 'student', 'FTN',0, false, true);
insert into users (username, email, password, first_name, last_name, address, city, zip_code, country, phone_number, jmbg, gender, job, workplace, points_collected, filled_questionnaire, account_verified) values ('stefa','stefa@email.com', '$2a$10$iwMIUYsBRR4n5dHJi7ZCUeeeDYgBqrt8dyVCUHvbKju6fKdI.Q2UC', 'Stefa', 'Stefanovic', 'Kisacka 45', 'Novi Sad','21000', 'Srbija', '0656693694', 44444444, 0, 'student', 'FTN',21, false, false);

--admin
insert into users (username, email, password, first_name, last_name, address, city, zip_code, country, phone_number, jmbg, gender, job, workplace, points_collected, filled_questionnaire, account_verified) values ('zoki','zoki@email.com', '$2a$10$iwMIUYsBRR4n5dHJi7ZCUeeeDYgBqrt8dyVCUHvbKju6fKdI.Q2UC', 'Zoki', 'Jankov', 'Kisacka 45', 'Novi Sad','21000', 'Srbija', '0656693695', 55555555, 1, 'student', 'FTN',10, false, true);

--medic
insert into users (username, email, password, first_name, last_name, address, city, zip_code, country, phone_number, jmbg, gender, job, workplace, points_collected, filled_questionnaire, account_verified) values ('pera','pera@email.com', '$2a$10$iwMIUYsBRR4n5dHJi7ZCUeeeDYgBqrt8dyVCUHvbKju6fKdI.Q2UC', 'Pera', 'Peric', 'Kisacka 45', 'Novi Sad','21000', 'Srbija', '0656693696', 66666666, 1, 'student', 'FTN',4, false, true);
insert into users (username, email, password, first_name, last_name, address, city, zip_code, country, phone_number, jmbg, gender, job, workplace, points_collected, filled_questionnaire, account_verified) values ('daki','daki@email.com', '$2a$10$iwMIUYsBRR4n5dHJi7ZCUeeeDYgBqrt8dyVCUHvbKju6fKdI.Q2UC', 'Daki', 'Dakic', 'Kisacka 45', 'Novi Sad','21000', 'Srbija', '0656693697', 77777777, 1, 'student', 'FTN',41, false, true);
insert into users (username, email, password, first_name, last_name, address, city, zip_code, country, phone_number, jmbg, gender, job, workplace, points_collected, filled_questionnaire, account_verified) values ('baki','baki@email.com', '$2a$10$iwMIUYsBRR4n5dHJi7ZCUeeeDYgBqrt8dyVCUHvbKju6fKdI.Q2UC', 'Baki', 'Bakic', 'Kisacka 45', 'Novi Sad','21000', 'Srbija', '0656693698', 88888888, 1, 'student', 'FTN',42, false, true);
insert into users (username, email, password, first_name, last_name, address, city, zip_code, country, phone_number, jmbg, gender, job, workplace, points_collected, filled_questionnaire, account_verified) values ('lela','lela@email.com', '$2a$10$iwMIUYsBRR4n5dHJi7ZCUeeeDYgBqrt8dyVCUHvbKju6fKdI.Q2UC', 'Lela', 'Lelic', 'Kisacka 45', 'Novi Sad','21000', 'Srbija', '0656693699', 99999999, 1, 'student', 'FTN',43, false, true);

, filled_questionnaire
insert into facility ( user_id, center_name, center_address, center_description, center_supplies, grade, is_available) values (7,'Klinicki centar Vojvodine', 'Vojvodjanska 16', 'ovo je opis za kcv', 123, 4.3, 'true');
insert into facility ( user_id, center_name, center_address, center_description, center_supplies, grade, is_available) values (8,'MediGroup', 'Medigroup 16', 'ovo je opis za medigroup', 1, 4.4, 'true');
insert into facility ( user_id, center_name, center_address, center_description, center_supplies, grade, is_available) values (6,'Klinicki centar Beograd', 'Beogradska 16', 'ovo je opis za bg', 3, 4.4, 'false');
insert into facility ( user_id, center_name, center_address, center_description, center_supplies, grade, is_available) values (9,'Klinicki centar AAAA', 'AAAA 16', 'ovo je opis za kcv', 13, 4.5, 'true');


insert into appointments ( user_id, center_id, date_of_appointment, time_of_appointment) values (5,1, '2023-06-13', '20:30:00');
insert into appointments ( user_id, center_id, date_of_appointment, time_of_appointment) values (2,1, '2021-03-09', '11:00:00');
insert into appointments ( user_id, center_id, date_of_appointment, time_of_appointment) values (5,1, '2023-06-13', '11:30:00');
insert into appointments ( user_id, center_id, date_of_appointment, time_of_appointment) values (5,2, '2021-08-09', '12:00:00');
insert into appointments ( user_id, center_id, date_of_appointment, time_of_appointment) values (5,2, '2021-08-09', '13:30:00');
insert into appointments ( user_id, center_id, date_of_appointment, time_of_appointment) values (5,3, '2021-08-09', '14:00:00');
insert into appointments ( user_id, center_id, date_of_appointment, time_of_appointment) values (2,4, '2021-08-09', '15:00:00');
insert into appointments ( user_id, center_id, date_of_appointment, time_of_appointment) values (5,1, '2023-06-05', '16:30:00');
insert into appointments ( user_id, center_id, date_of_appointment, time_of_appointment) values (5,3, '2023-02-09', '10:30:00');
insert into appointments ( user_id, center_id, date_of_appointment, time_of_appointment) values (2,2, '2023-08-09', '10:30:00');

insert into roles ( name ) values ('ROLE_USER');
insert into roles ( name ) values ('ROLE_MEDIC');
insert into roles ( name ) values ('ROLE_ADMINISTRATOR');

insert into user_roles(user_id,role_id) values (1,1);

insert into user_roles(user_id,role_id) values (2,1);

insert into user_roles(user_id,role_id) values (3,1);

insert into user_roles(user_id,role_id) values (4,1);

insert into user_roles(user_id,role_id) values (5,3);

insert into user_roles(user_id,role_id) values (6,2);

insert into user_roles(user_id,role_id) values (7,2);

insert into user_roles(user_id,role_id) values (8,2);

insert into user_roles(user_id,role_id) values (9,2);

insert into questionnaire (date, first_name, parent_name, last_name, jmbg, date_of_birth, gender, address, city, phone_number, workplace, job, times_given, blood_type, accepted, drunk_alcohol, had_tattoo, dangerous_job, donated_blood, user_id) values ('2023-05-26', 'Marko', 'Nikola', 'Markovic', '1234567890', '1990-01-01', 0, '123 Main St', 'City', '1234567890', 'FTN', 'student', 3, 0, true, true, false, true, false, 1);

insert into analytics(average_grade, appointments_last_month, appointments_last_year, appointments_total, supplies_last_month, supplies_last_year, supplies_total, equipment_last_month, equipment_last_year, equipment_total, center_id) values (4.3, 5, 10 , 15, 5, 10, 15, 5,10,15,3);
insert into analytics(average_grade, appointments_last_month, appointments_last_year, appointments_total, supplies_last_month, supplies_last_year, supplies_total, equipment_last_month, equipment_last_year, equipment_total, center_id) values (4.3, 5, 10 , 15, 5, 10, 15, 5,10,15,4);
insert into analytics(average_grade, appointments_last_month, appointments_last_year, appointments_total, supplies_last_month, supplies_last_year, supplies_total, equipment_last_month, equipment_last_year, equipment_total, center_id) values (4.3, 5, 10 , 15, 5, 10, 15, 5,10,15,2);
insert into analytics(average_grade, appointments_last_month, appointments_last_year, appointments_total, supplies_last_month, supplies_last_year, supplies_total, equipment_last_month, equipment_last_year, equipment_total, center_id) values (4.3, 5, 10 , 15, 5, 10, 15, 5,10,15,1);
