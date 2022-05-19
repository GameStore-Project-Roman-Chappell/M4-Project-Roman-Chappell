# Game Store API Project

### Description
This project is an assignment as part of a java development bootcamp, and was built as an exercise in building a back end api based on a well-defined specification set.

### Authors
<a href="https://github.com/adamchappell00" rel="nofollow">Adam Chappell</a>

<a href="https://github.com/alonzofroman" rel="nofollow">Alonzo Roman</a>

### Key Content
Swagger OpenAPI Documentation is contained in the file: 
  - GameStoreSwagger.yml

There are example properties which must be replaced in order to run the application contained in the file below. It must be renamed to application.properties:
 - example.properties

Tests are set up with example.properties in a similar way but will use the 'game_store_test' database instead, to keep concerns separate.

In order to match with the specification schema, we do not create or update the database through the application. We use a set of sql scripts to create the schema and insert given data into it before using the application. They are contained in the resources/static directory.

- This script contains the schema and all data tables from the specifications:
  - schema.sql
- This script inserts the data for product processing fees and state tax rates:
  - tax-fee-seeder.sql
- This Script migrates our starter data of products:
  - migrations.sql

### Matching Front End
The repository for a matching front end React app set up to use this API is contained <a href="https://github.com/GameStore-Project-Roman-Chappell/gamestore-front-end" rel="nofollow">HERE</a>