# nutrition-finance-tracker

- To run the program: run the "Main.java" file

- Descripton:
This program helps you to plan your meals, to sum up the time for the cooking of the food and to count the most optimal price for the required ingredients.

- Usage:
  1. Create ingredient and the first product for it in "Add ingredient" tab
    - Amount of ingredient: float number, which determines how much of the ingredient is in the product
    - Cost: float number, cost of the product in your currency
  2. After creating all of the needed ingredients for your meal. Add a meal in the "Add meal" tab. 
    - Amount of portions: into how many portions can this meal be divided.
      Example: meal “Soup” can be split into 4 portions, as you can’t eat the whole pot in one go.
    - Time for cooking: integer, how much time (in minutes) is required to cook the meal
    - Add ingredient: click to add another ingredient for the meal
    - Amount: float number, required amount of ingredient for the meal
  3. After adding all of the needed meals, proceed to the “Main” tab, where you choose meals for each daytime (breakfast, lunch, dinner) of the day in the week.
  4. After adding all of the needed meals into your calendar, click “Count” to find out how much time and money you will need to cook all of the selected meals.
     The algorithm for counting cost is made in a way that it will tell you the most optimal and cheap way to acquiring all of the needed ingredients.
  5. In the additional information section on the "Main" tab you can learn about:
	  - What meals you have selected and the amount of them
	  - What leftovers you will have according to the number of portions you haven’t used out.
	  - What products you have to buy to get the required ingredients (the most optimal way, the optimal cost received from “Count” function is actually the sum of the value of these products)

-	Additional tabs:
  o	All meals: here you can check the info about all of your meals. It is also possible to modify or delete them here.
  o	All ingredients: here you can check the info about all of your ingredients, particularly the info about each product which contains the ingredient. 
  Here you can delete the ingredient which will also remove every product related to it.
  Here you can modify or delete the products of each ingredient.
