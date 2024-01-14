- using `formant()` to print the output of database
- using `lables` to break loops instead of many condition
- create only one `Scanner` in you project and use it as if you create and cloase many scanners that will
  cause ` NoSuchElementException error`
- you can create one `Scanner` in the main class and make it `public static`. like in this porject
- function that used to **retrive** from data base is `executeQuery()`
- function that used to **update** database is `executeUpdate()`
- using **text block** to put multi lines in block
    - it works like this
  ```
  System.out.println("""
                     Admin operations.
                     1- List product full info.
                     2- Add new product to database.
                     3- Modify product info into database.
                     4 - Delete product from database.
                     6 - Print my account info. 
                     10 Logout. """);
  - ```
    - good choice for sensitive information like card balance
        - to use in database -> `dicimal` datatype.
        - and in java -> `double` datatype.

# to do

- [ ] check from every input before exceeding
- [x] mack view of product discount in discoiunt% in listproducts;
- after success payment
    - [x] update the quantity of products in database after success payment
    - [x] clear the carte 's products *reset the carte*
    - [x] function `resetCarte()`
    - [x] update `Updatesoldproducts()`
- [x] remove getting info of card from `Customer` to `Payment

# admin Operatons

- [ ] list my info
- [ ] list admin products
- [ ] add product to data base
    - [ ] get product info
    - [ ] 
    - [ ] check if the provided ID is uniqe
    - [ ] 
