import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

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
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelForMoney.setText("Money: " + Evaluator.countMoney(nutritionManager.getCookedMeals(), productManager));
                labelForTime.setText("Time:" + Evaluator.countTime(nutritionManager.getCookedMeals()));
                System.out.println(Evaluator.countMoney(nutritionManager.getCookedMeals(), productManager));
            }
        });

        countPanel.add(button);
        countPanel.add(labelForMoney);
        countPanel.add(labelForTime);
        return countPanel;
    }

    private JPanel createAdditionalInfoPanel(){
        JPanel additionalInfoPanel = new JPanel();
        additionalInfoPanel.setLayout(new BoxLayout(additionalInfoPanel, BoxLayout.Y_AXIS));

        JLabel selectedMealsCountLabel = new JLabel("Selected meals count");

        return additionalInfoPanel;
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
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                nutritionManager.selectMeal(mealManager.getAvailableMeals().get(comboBox.getSelectedItem()), dayOfTheWeek, numberOfFood);
                System.out.println("Selected meal: " + comboBox.getSelectedItem());
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
