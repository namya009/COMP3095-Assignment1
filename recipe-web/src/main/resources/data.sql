INSERT INTO user (id, first_name, last_name, email_id, username, password)
VALUES ('1', 'John', 'Doe', 'johndoe@email.com', 'admin',
        '$2a$10$bN7OWEvi6rTqJEYbZfDOg.FHmG.xPTDxJR1k9LzsR4O6Nt8zuIKwq');
INSERT INTO user (id, first_name, last_name, email_id, username, password)
VALUES ('2', 'Pruthvi', 'Soni', 'pruthvisoni@gmail.com', 'pru',
        '$2a$10$A0DiYyivrs5ErT2d/fk5r.aSfUApJhHdxL3O8blzxsMdMeFK6DhK6');

INSERT INTO recipe (id, recipe_name, prep_time, cook_time, total_time, ingredients, instructions, date_added,user_id,is_favorite)
VALUES ('1', 'Pancakes', 5, 10, 15,
        '1 cup all-purpose flour,

2 tablespoons white sugar,

2 teaspoons baking powder,

1 teaspoon salt,

1 egg, beaten,

1 cup milk,

2 tablespoons vegetable oil', 'Step 1
In a large bowl, mix flour, sugar, baking powder and salt. Make a well in the center, and pour in milk, egg and oil. Mix until smooth.

Step 2
Heat a lightly oiled griddle or frying pan over medium high heat. Pour or scoop the batter onto the griddle, using approximately 1/4 cup for each pancake. Brown on both sides and serve hot.',
        '2021-10-31 00:00:00.000',2,true);
