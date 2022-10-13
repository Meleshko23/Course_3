-- liquibase formatted sql

-- changeset ynmelesh: 1
create index student_name
    on student (name);

-- changeset ynmelesh: 2
create index faculty_name_color
    on faculty (name, color);



