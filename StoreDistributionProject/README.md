# PROP Group 13.2
Programming Project, Group 13, Subgroup 13.2.  
Professor: Carles Arnal ([carles.arnal@upc.edu]()).  

## Introduction
The project is originally made in catalan language, therefore the comments in the code is not in english.
Regarding the documentation in the /DOCS directory, there is located all the documentation of the project in JAVADOC format and the user's manual for the application in original version.

All the executable files .class of the project are located in the /EXE directory.  

All the files that consitute the project itself are located in the /FONTS directory.  
The program is created with the command `make` in the /FONTS directory.

## Group Members

- Daniel Ermoshin Danichkin  
- Otman Ezzayat Maid  
- Gerard Godet Batllori  
- Guillermo O’Tuama Pascual  

- ([daniel.ermoshin@estudiantat.upc.edu]())  
- ([otman.ezzayat@estudiantat.upc.edu]())  
- ([gerard.godet@estudiantat.upc.edu]())  
- ([guillermo.o.tuama@estudiantat.upc.edu]())


## Project Statement

#### Product Distribution in a Supermarket

We have a supermarket, and we want to find the optimal distribution of the products offered to maximize sales. We will assume that the likelihood of a customer purchasing a product increases if that product is placed next to a related product (e.g., if a customer buys beer and sees chips nearby, they will likely remember to buy them). We assume that every pair of products has a degree of similarity or relationship, which the user knows and can provide.

To simplify, we will assume there is only one circular shelf in the supermarket, and it has a single level to place the products (or several levels within this shelf). Based on the degree of similarity between the products the user wants to offer, the system must determine the optimal arrangement of products to maximize the likelihood of customers buying more.

To ensure the project is approved, the system must provide at least the following functionalities:

- **Product Management**

- **Management of Product Similarities**

- **Distribution Calculation.** The program must provide at least two algorithms to find this optimal distribution: a basic solution (using “brute force” or a greedy algorithm) and an approximation algorithm (further information will be provided). The parameters of these algorithms, if any, must be configurable interactively through the application. Any additional optimization of both solutions will be considered.

- **Post-Modification of Proposed Solutions**

- **Data Input Options.** The data must be definable via the program or imported from a text file.

Each team may extend the system with optional features, such as the use of constraints in product placement, implementation of additional algorithms, etc. In addition to other quality factors of any program (design, coding, reusability, modifiability, usability, documentation, ...), the efficiency and flexibility of the system will be particularly valued.

