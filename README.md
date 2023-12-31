# Book Store application

## Project description
This is the the third project assignment for subject [BPC-BDS](https://www.vut.cz/studis/student.phtml?gm=gm_detail_predmetu&apid=268496) on BUT-FEEC with the goal to create a book store  javafx app which is connected with PostgreSQL database what was implemented in bds-project-assignment-1. This app was build according these requirements [BDS Assignment 3](Project Assignment 3 - Database Application.pdf).
In this application customer can login to the app when is logged in he/she is able to view book catalog and edit his/her information (first name, last name,...) with the address.
### Login GUI
Customer has to enter correct email and password to be proceed to the main application. When user press button login then in  the database is looking for specific email if it is found then we are getting password hash and salt for the specified email.
For verification correct entered customer's password hash and salt is used. The password is verified by library Bcrypt. This application is created for users what will want to view book catalog. Feature for buying book will be implemented in the future.
![app login](/img/assignment_3/app_login_gui.png
)
## Menu GUI
When customer is logged in then can edit his/her profile and view book catalog.
![app_menu](/img/assignment_3/app_menu.png)

## Book catalog GUI
Here we can view all books which are in the database, customer can sort by the selected column and filter by book title. For each book can be seen in detail view.
![app_book_catalog](/img/assignment_3/app_book_catalog.png)

### Book view GUI
Detail view for a book, where can be seen each information and author.
![app_book_view](/img/assignment_3/app_book_view.png)
### Customer edit GUI
View where customer can edit his/her details. In the address table can be selected address for edit.
![app_customer_edit.png](/img/assignment_3/app_customer_edit.png)
### Customer address edit GUI
View where customer can edit or create his/her address.
![app_address_edit](/img/assignment_3/app_address_edit.png)

## Install steps
Database setup
1. Install pg-admin and run it.
```bash
git clone https://gitlab.com/but-courses/bpc-bds/seminar-projects/bpc-bds-db-setup.git
cd bpc-bds-db-setup
docker-compose up
```
2. In pg-admin create database with name  `bds-zdenva`.
3. In the database what was created open `Query Tool`.
4. Run content of files [postgresql_DDL.sql](/database/postgresql_DDL.sql), [postgresql_DML.sql](/database/posgresql_DML.sql) and [postgres_DCL](/database/postgresql_DCL.sql).
Application setup
- Build JavaFX application.
```bash
mvn clean install
```
- Run JavaFX application. 
```bash
java -jar target/my-bds-app-1.0.0.jar
```
## Diagrams
- PostgreSQL ERD
![ERD_PostgreSQL.png](/img/diagrams/ERD_PostgreSQL.png)

- MySQL ERD
![MySQL ERD](/img/diagrams/ERD_MySQL.png)
- Use Case Diagram
![Use Case Diagram](/img/diagrams/BDS_Use_Case_Diagram.png)
- System Context Diagram
![MySQL ERD](/img/diagrams/BDS_System_Context_Diagram.png)