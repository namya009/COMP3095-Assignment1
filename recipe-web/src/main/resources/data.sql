INSERT INTO user (id, first_name, last_name, email_id, username, password)
VALUES ('1', 'John', 'Doe', 'johndoe@email.com', 'admin',
        '$2a$10$bN7OWEvi6rTqJEYbZfDOg.FHmG.xPTDxJR1k9LzsR4O6Nt8zuIKwq');
INSERT INTO user (id, first_name, last_name, email_id, username, password)
VALUES ('2', 'Pruthvi', 'Soni', 'pruthvisoni@gmail.com', 'pru',
        '$2a$10$A0DiYyivrs5ErT2d/fk5r.aSfUApJhHdxL3O8blzxsMdMeFK6DhK6');

INSERT INTO recipe (id, recipe_name, prep_time, cook_time, total_time, ingredients, instructions, date_added,user_id,is_favorite)
VALUES (1, 'Basic Omelette',2,4,6,'2 eggs, water, salt, pepper',
        'Step 1 - Whisk eggs, water, salt and pepper.' ||
        'Step 2 - Spray 8-inch (20 cm) non-stick skillet with cooking spray. Heat over medium heat. Pour in egg mixture. As eggs set around edge of skillet, with spatula, gently push cooked portions toward centre of skillet. Tilt and rotate skillet to allow uncooked egg to flow into empty spaces.' ||
        'Step 3 - When eggs are almost set on surface but still look moist, cover half of omelette with filling. Slip spatula under unfilled side; fold over onto filled half.' ||
        'Step 4 - Cook for a minute, then slide omelette onto plate.',
        '2021-11-5 00:00:00.000', 1,true);
INSERT INTO recipe (id, recipe_name, prep_time, cook_time, total_time, ingredients, instructions, date_added,user_id,is_favorite)
VALUES (2, 'Boiled Eggs',2,12,14,'6 eggs, 1 teaspoon baking soda',
        'Step 1 - Put the eggs in a large pot with a lid. Pour cool water over the eggs until fully submerged and add the baking soda to the water. Put the pot over high heat and bring to a boil. Once the water is at a rolling boil, turn off the heat and cover the pot with the lid. Allow the eggs to sit in the hot water for the following times according to the desired doneness: 3 minutes for SOFT boiled; 6 minutes for MEDIUM boiled; 12 minutes for HARD boiled.' ||
        'Step 2 - Prepare a bowl of ice water. Transfer the cooked eggs to the ice water to cool completely before peeling.',
        '2021-11-7 00:00:00.000', 1,false);

INSERT INTO meal("ID","DATE_TO_BE_MADE","RECIPE_ID","USER_ID")VALUES(1,'2021-11-10 00:00:00.000',1,1);
INSERT INTO meal("ID","DATE_TO_BE_MADE","RECIPE_ID","USER_ID")VALUES(2,'2021-11-10 00:00:00.000',2,1);
