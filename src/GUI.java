import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {
    NutritionManager nutritionManager;
    ProductManager productManager;
    MealManager mealManager;
    String[] daysOfTheWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    public GUI(NutritionManager nutritionManager, ProductManager productManager, MealManager mealManager){
        this.nutritionManager = nutritionManager;
        this.productManager = productManager;
        this.mealManager = mealManager;
    }
    public void setUpGUI(){
        JFrame frame = new JFrame();

        frame.getContentPane().add(BorderLayout.CENTER, createFoodTablePanel());
        frame.getContentPane().add(BorderLayout.EAST, createCountPanel());
        frame.getContentPane().add(BorderLayout.SOUTH, createAdditionalInfoPanel());

        frame.setSize(1000,1000);
        frame.setVisible(true);
    }
    private JPanel createFoodTablePanel(){
        JPanel foodTablePanel = new JPanel();
        foodTablePanel.setLayout(new FlowLayout());

        for (int i = 0; i < 7; i++){
            foodTablePanel.add(createDay(daysOfTheWeek[i]));
        }
        return foodTablePanel;
    }
    private JPanel createCountPanel(){
        JPanel countPanel = new JPanel();
        countPanel.setLayout(new BoxLayout(countPanel, BoxLayout.Y_AXIS));

        JButton button = new JButton("Count");
        JLabel labelForMoney = new JLabel("Money:");
        JLabel labelForTime = new JLabel("Time:");
        button.addActionListener(e -> {
            labelForMoney.setText("Money: " + Evaluator.countMoney(nutritionManager.getCookedMeals(), productManager));
            labelForTime.setText("Time:" + Evaluator.countTime(nutritionManager.getCookedMeals()));
            System.out.println(Evaluator.countMoney(nutritionManager.getCookedMeals(), productManager));
        });

        countPanel.add(button);
        countPanel.add(labelForMoney);
        countPanel.add(labelForTime);
        return countPanel;
    }

    private JPanel createAdditionalInfoPanel(){
        JPanel additionalInfoPanel = new JPanel();
        additionalInfoPanel.setLayout(new BoxLayout(additionalInfoPanel, BoxLayout.Y_AXIS));

        JLabel selectedMealsCountLabel = new JLabel("Selected meals count: ");
        JLabel leftoversLabel = new JLabel("Leftover meals count: ");
        JLabel productsLabel = new JLabel("Needed products: ");

        JLabel[] additionalInfoValues = getAdditionalInfoLabels();

        additionalInfoPanel.add(selectedMealsCountLabel);
        additionalInfoPanel.add(additionalInfoValues[0]);
        additionalInfoPanel.add(leftoversLabel);
        additionalInfoPanel.add(additionalInfoValues[1]);
        additionalInfoPanel.add(productsLabel);
        additionalInfoPanel.add(additionalInfoValues[2]);

        return additionalInfoPanel;
    }
    private JLabel[] getAdditionalInfoLabels(){
        JLabel[] labels = new JLabel[3];

        JLabel selectedMeals = new JLabel();
        for (Meal meal:nutritionManager.getMealCountMap().keySet()) {
            selectedMeals.setText(selectedMeals.getText() + "; " + meal.getName() + " : " + nutritionManager.getMealCountMap().get(meal));
        }

        JLabel leftovers = new JLabel();
        for (Meal meal:nutritionManager.getMealCountMap().keySet()) {
            leftovers.setText(leftovers.getText() + "; " + meal.getName() + " : " + nutritionManager.getLeftOvers().get(meal));
        }

        JLabel products = new JLabel();//???
        for (Meal meal:nutritionManager.getMealCountMap().keySet()) {
            products.setText(products.getText() + "; " + meal.getName() + " : " + nutritionManager.getLeftOvers().get(meal));
        }
        labels[0] = selectedMeals;
        labels[1] = leftovers;
        labels[2] = products;

        return labels;
    }
    private JComboBox createChoiceBar(String dayOfTheWeek, int numberOfFood){
        String[] namesOfMeals = new String[mealManager.getAvailableMeals().size()+1];

        int i = 1;
        namesOfMeals[0] = "Nothing";
        for (String name: mealManager.getAvailableMeals().keySet()) {
            namesOfMeals[i++] = name;
        }

        JComboBox comboBox = new JComboBox(namesOfMeals);

        comboBox.setSelectedItem(nutritionManager.getSelectedMealsMap().get(dayOfTheWeek)[numberOfFood].getName());
        // add ItemListener
        comboBox.addItemListener(e -> {
            if (comboBox.getSelectedItem() == "Nothing"){
                nutritionManager.selectNoMeal(dayOfTheWeek, numberOfFood);
            } else{
                nutritionManager.selectMeal(mealManager.getAvailableMeals().get(comboBox.getSelectedItem()), dayOfTheWeek, numberOfFood);
            }
        });
        return comboBox;
    }
    private JPanel createDay(String dayOfTheWeek){
        JPanel panel = new JPanel();
        JLabel dayName = new JLabel(dayOfTheWeek);
        panel.add(dayName);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (int i = 0; i < 3; i++){
            panel.add(createChoiceBar(dayOfTheWeek, i));
        }
        return panel;
    }
}
