create database diet_tracker;
use diet_tracker;

create table users (
    id int auto_increment primary key,
    username varchar(50) not null unique,
    password varchar(50) not null,
    age INT,
    height_cm double, 
    weight_kg double,  
    goal varchar(20)   
);
use diet_tracker;

create table users (
    id int auto_increment primary key,
    username varchar(50) not null unique,
    password VARCHAR(50) not null,
    age int,
    height_cm double, 
    weight_kg double, 
    goal varchar(20)   
);
CREATE TABLE meals (
    id int auto_increment primary key,
    name varchar(100) not null,
    type varchar(30),       
    goal varchar(20),      
    calories int,
    description text        
);
create table meal_logs (
    id int auto_increment primary key,
    user_id int,
    meal_id int,
    date date,
    foreign key (user_id) references users(id),
    foreign key (meal_id) references meals(id)
);

insert into meals (name, type, goal, calories, description) VALUES
('Grilled Chicken Salad', 'Lunch', 'Weight Loss', 250, 'High-protein salad with lean chicken and greens'),
('Steamed Broccoli with Quinoa', 'Dinner', 'Weight Loss', 300, 'Fiber-rich meal with vegetables and whole grains'),
('Oatmeal with Berries', 'Breakfast', 'Weight Loss', 200, 'Healthy oats topped with fresh berries'),
('Boiled Eggs and Veggies', 'Snack', 'Weight Loss', 220, 'Light snack with protein and fiber'),
('Greek Yogurt with Honey', 'Snack', 'Weight Loss', 180, 'Low-fat yogurt sweetened with honey');

insert into meals (name, type, goal, calories, description) VALUES
('Grilled Salmon with Brown Rice', 'Lunch', 'Weight Gain', 500, 'Nutritious high-calorie meal rich in omega-3'),
('Protein Shake with Banana', 'Snack', 'Weight Gain', 400, 'High-protein shake with added carbs'),
('Chicken Breast with Sweet Potato', 'Dinner', 'Weight Gain', 550, 'Lean protein with energy-rich carbs'),
('Egg Omelette with Cheese', 'Breakfast', 'Weight Gain', 450, 'Protein-packed breakfast with healthy fats'),
('Beef Stir Fry with Rice', 'Lunch', 'Weight Gain', 600, 'Muscle-building meal with beef and carbs');

insert into meals (name, type, goal, calories, description) VALUES
('Veggie Sandwich', 'Lunch', 'Maintain Weight', 350, 'Balanced sandwich with whole grain bread and veggies'),
('Fruit Smoothie', 'Snack', 'Maintain Weight', 300, 'Blended fruit drink with natural sugars'),
('Rice with Lentils', 'Dinner', 'Maintain Weight', 400, 'Wholesome vegetarian protein meal'),
('Grilled Paneer Wrap', 'Lunch', 'Maintain Weight', 370, 'Paneer wrap with salad and whole wheat roti'),
('Baked Potato with Salad', 'Dinner', 'Maintain Weight', 380, 'Carb-based meal balanced with fresh salad');





