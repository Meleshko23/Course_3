-- liquibase formatted sql

-- changeset ynmelesh: 1
drop index student_name;
create index student_name ON student (name);

-- changeset ynmelesh: 2
drop index faculty_name_color;
create index faculty_name_color ON faculty (name, color);



