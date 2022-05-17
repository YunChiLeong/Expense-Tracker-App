# Expense-Tracker-App
An android app that allows user to keep track of their expenses. User can add, delete, update their expense entry. 

## How It Looks
User can add, delete update and organize their expenses.
Each entries shows entry_ID, expense name, entry date, amount in dollar($), additional notes.

![download (4)](https://user-images.githubusercontent.com/47125700/168929819-c071d750-9a21-4a08-a78f-585d3986d555.png)![image](https://user-images.githubusercontent.com/47125700/168930194-e8e0f58d-317b-4b65-bf94-3f278dda6e9a.png)

![download (3)](https://user-images.githubusercontent.com/47125700/168929829-967df14c-ec80-4d53-8924-dfea02ffe64e.png)![image](https://user-images.githubusercontent.com/47125700/168930408-662846ab-4398-4fdf-bd48-10d6c832eaea.png)

## How It Works
Lists are stored in a local SQLite Database. Add, Update and Delete calls changes the data in the local database.

## Security Feature
SQL injection attacks protection.All editTexts that calls add and update functions are coded such that users entries are not directly inserted into SQL command line.





