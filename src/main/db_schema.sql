create schema cueandbrew;
set schema 'cueandbrew';

create table managers (
    manager_id serial primary key,
    firstname varchar(100),
    lastname varchar(100),
    cpr varchar(10),
    birthdate date,
    hire_date date,
    fire_date date null
);

create table registrations (
    registration_id serial primary key,
    manager_id int,
    login varchar(100),
    password varchar(100),
    foreign key (manager_id) references managers(manager_id)
);

create table feedbacks (
    feedback_id serial primary key,
    author_firstname varchar(100),
    author_lastname varchar(100),
    content text,
    type varchar(20),
    checked_by_id int,
    foreign key (checked_by_id) references managers(manager_id)
);

create table tables (
    number int primary key
);

create table bookings (
    booking_id serial primary key,
    table_number int,
    date date,
    start_time time,
    end_time time,
    foreign key (table_number) references tables(number)
);

create table drinks (
    drink_id serial primary key,
    name varchar(100),
    price decimal(10, 2),
    quantity int
);

create table orders (
    order_id serial primary key,
    drink_id int,
    expected_order_date date,
    expected_order_time time
);

create table reservations (
    reservation_id serial primary key,
    booking_id int,
    order_id int null,
    received_by_id int,
    notes text,
    client_firstname varchar(100),
    client_lastname varchar(100),
    client_phone_number varchar(15),
    creation_datetime timestamp,
    foreign key (booking_id) references bookings(booking_id),
    foreign key (order_id) references orders(order_id),
    foreign key (received_by_id) references managers(manager_id)
);


create table notifications (
    notification_id serial primary key,
    reservation_id int null,
    feedback_id int null,
    content text,
    was_seen int,
    foreign key (reservation_id) references reservations(reservation_id),
    foreign key (feedback_id) references feedbacks(feedback_id)
);

-- Inserting mock data into the managers table
INSERT INTO managers (firstname, lastname, cpr, birthdate, hire_date, fire_date)
VALUES
    ('John', 'Doe', '1234567890', '1990-05-15', '2023-01-01', NULL),
    ('Jane', 'Smith', '0987654321', '1985-08-20', '2023-02-10', NULL),
    ('Michael', 'Johnson', '4567890123', '1992-11-30', '2023-03-20', NULL);

-- Inserting mock data into the registrations table
INSERT INTO registrations (manager_id, login, password)
VALUES
    (1, 'john_doe@example.com', 'password123'),
    (2, 'jane_smith@example.com', 'pass123'),
    (3, 'michael_johnson@example.com', 'password');

-- Inserting mock data into the feedbacks table
INSERT INTO feedbacks (author_firstname, author_lastname, content, type, checked_by_id)
VALUES
    ('Alice', 'Johnson', 'Great service!', 'Positive', 1),
    ('Bob', 'Williams', 'Food was cold.', 'Negative', 2),
    ('Emily', 'Brown', 'Friendly staff.', 'Positive', 3);

-- Inserting mock data into the tables table
INSERT INTO tables (number) VALUES (1), (2), (3), (4), (5);

-- Inserting mock data into the bookings table
INSERT INTO bookings (table_number, date, start_time, end_time)
VALUES
    (1, '2023-05-20', '18:00:00', '20:00:00'),
    (2, '2023-05-22', '19:00:00', '21:00:00'),
    (3, '2023-05-25', '20:00:00', '22:00:00');

-- Inserting mock data into the drinks table
INSERT INTO drinks (name, price, quantity)
VALUES
    ('Coke', 2.50, 100),
    ('Beer', 5.00, 50),
    ('Wine', 10.00, 30);

-- Inserting mock data into the orders table
INSERT INTO orders (drink_id, expected_order_date, expected_order_time)
VALUES
    (1, '2023-05-20', '19:00:00'),
    (2, '2023-05-22', '20:00:00'),
    (3, '2023-05-25', '21:00:00');

-- Inserting mock data into the reservations table
INSERT INTO reservations (booking_id, order_id, received_by_id, notes, client_firstname, client_lastname, client_phone_number, creation_datetime)
VALUES
    (1, 1, 1, 'Preferred seating near the window', 'John', 'Smith', '123-456-7890', '2023-05-19 15:00:00'),
    (2, 2, 2, NULL, 'Alice', 'Johnson', '234-567-8901', '2023-05-21 10:00:00'),
    (3, NULL, 3, 'Special occasion - anniversary', 'Michael', 'Brown', '345-678-9012', '2023-05-24 14:00:00');

-- Inserting mock data into the notifications table
INSERT INTO notifications (reservation_id, feedback_id, content, was_seen)
VALUES
    (1, NULL, 'Your reservation has been confirmed.', 0),
    (NULL, 1, 'Thank you for your positive feedback!', 0),
    (3, NULL, 'Reminder: Your reservation is tomorrow.', 0);