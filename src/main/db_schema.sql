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
    checked_by_id int null,
    foreign key (checked_by_id) references managers(manager_id)
);

create table tables (
    number int primary key
);

create table bookings (
    booking_id serial primary key,
    date date,
    start_time time,
    end_time time
);

create table booking_tables (
    booking_table_id serial primary key,
    booking_id int,
    table_number int,
    foreign key (booking_id) references bookings(booking_id),
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
    expected_order_date date,
    expected_order_time time
);

create table order_drinks (
    order_drink_id serial primary key,
    order_id int,
    drink_id int,
    foreign key (order_id) references orders(order_id),
    foreign key (drink_id) references drinks(drink_id)
);

create table reservations (
    reservation_id serial primary key,
    booking_id int,
    order_id int null,
    received_by_id int null,
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
    creation_time timestamp,
    foreign key (reservation_id) references reservations(reservation_id),
    foreign key (feedback_id) references feedbacks(feedback_id)
);

-- Inserting managers
INSERT INTO managers (firstname, lastname, cpr, birthdate, hire_date)
VALUES
    ('John', 'Doe', '1234567890', '1980-01-01', '2020-01-01'),
    ('Jane', 'Smith', '0987654321', '1985-05-15', '2021-03-10');

-- Inserting registrations
INSERT INTO registrations (manager_id, login, password)
VALUES
    (1, 'john_doe', 'password123'),
    (2, 'jane_smith', 'letmein');

-- Inserting feedbacks
INSERT INTO feedbacks (author_firstname, author_lastname, content, type, checked_by_id)
VALUES
    ('Alice', 'Johnson', 'Great service!', 'Positive', 1),
    ('Bob', 'Anderson', 'Could improve cleanliness.', 'Negative', 2);

-- Inserting tables
INSERT INTO tables (number) VALUES (1), (2), (3);

-- Inserting bookings
INSERT INTO bookings (date, start_time, end_time)
VALUES
    ('2024-05-15', '18:00:00', '20:00:00'),
    ('2024-05-16', '19:00:00', '21:00:00'),
    ('2024-05-17', '20:00:00', '22:00:00');

-- Inserting booking_tables
INSERT INTO booking_tables (booking_id, table_number)
VALUES
    (1, 1),
    (1, 2),
    (2, 3);

-- Inserting drinks
INSERT INTO drinks (name, price, quantity)
VALUES
    ('Beer', 5.00, 100),
    ('Wine', 8.00, 50),
    ('Cocktail', 10.00, 30);

-- Inserting orders
INSERT INTO orders (expected_order_date, expected_order_time)
VALUES
    ('2024-05-15', '18:30:00'),
    ('2024-05-16', '19:15:00'),
    ('2024-05-17', '20:30:00');

-- Inserting order_drinks
INSERT INTO order_drinks (order_id, drink_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 2),
    (3, 3);

-- Inserting reservations
INSERT INTO reservations (booking_id, order_id, received_by_id, notes, client_firstname, client_lastname, client_phone_number, creation_datetime)
VALUES
    (1, 1, 1, 'VIP Guests', 'Alice', 'Johnson', '123-456-7890', CURRENT_TIMESTAMP),
    (2, 2, 2, 'Birthday party', 'Bob', 'Anderson', '987-654-3210', CURRENT_TIMESTAMP),
    (3, 3, NULL, 'Anniversary', 'Charlie', 'Brown', '555-555-5555', CURRENT_TIMESTAMP);

-- Inserting notifications
INSERT INTO notifications (reservation_id, content, was_seen, creation_time)
VALUES
    (1, 'Your reservation is confirmed.', 0, CURRENT_TIMESTAMP),
    (2, 'Your reservation is confirmed.', 0, CURRENT_TIMESTAMP),
    (3, 'Your reservation is confirmed.', 0, CURRENT_TIMESTAMP);
