USE game_store;

INSERT INTO game(game_id, title, esrb_rating, price, studio, quantity, description)
VALUES(1, 'GTA V', '18+', 29.95, 'Rockstar Games', 5, 'Grand Theft Auto V is an action-adventure game. Players complete missions to progress through the story. Outside of the missions, players may freely roam the open world.' ),
        (2, 'Elden Ring','M', 58.99, 'Bandai Namco', 12, 'Rise, Tarnished, and be guided by grace to brandish the power of the Elden Ring and become an Elden Lord in the Lands Between.'),
        (3, 'Horizon Forbidden West', 'T', 54.89,'Sony', 4,'Explore distant lands, fight bigger and more awe-inspiring machines, and encounter astonishing new tribes as you return to the far-future, post-apocalyptic world of Horizon.');

INSERT INTO console(console_id, manufacturer, memory_amount, model, price, processor, quantity)
VALUES(1,'Microsoft','1 TB','X Box Series X', 649.99, 'Zen2 3.8GHz', 12),
      (2,'Sony','667 GB','Playstation 5', 725.99, 'Zen2 3.8GHz', 7),
      (3,'Nintendo','256 GB','Switch', 198.99, 'ARM A-57 1.02GHz', 5);

INSERT INTO t_shirt(t_shirt_id, color, description, price, quantity, size)
VALUES(1,'blue','Legend of Zelda', 5.99, 12, 'Large'),
      (2,'black','Legend of Zelda', 5.99, 4, 'Medium'),
      (3,'black','Kirby', 4.99, 6, 'Extra Large'),
      (4,'black','Sonic the Hedgehog', 8.99, 5, 'Small'),
      (5,'gray','Fortnite', 8.99, 7, 'Large');