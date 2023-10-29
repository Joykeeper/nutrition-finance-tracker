import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {
    JFrame mainFrame;
    JFrame addSthFrame;
    FoodFinanceTracker fft;
    String[] daysOfTheWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    public GUI(FoodFinanceTracker fft){
        this.fft = fft;
    }
    public void setUpGUI(){
        mainFrame = new JFrame();

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem saveMenuItem = new JMenuItem("Save", KeyEvent.VK_S);
        saveMenuItem.addActionListener(e -> {
            this.fft.save();
            System.out.println("Saved");
        });
        menu.add(saveMenuItem);
        JMenuItem loadMenuItem = new JMenuItem("Load");
        loadMenuItem.addActionListener(e -> {
            /*
            for (Meal meal:fft.nutritionManager.getMealCountMap().keySet()) {
                System.out.println(meal.getName() + " " + fft.nutritionManager.getMealCountMap().get(meal));
            }

             */
            this.fft.load();
            this.updateGUI();
            System.out.println("Loaded");
            for (Meal meal:fft.nutritionManager.getMealCountMap().keySet()) {
                System.out.println(meal.getName() + " " + fft.nutritionManager.getMealCountMap().get(meal));
            }
        });
        menu.add(loadMenuItem);

        mainFrame.getContentPane().add(BorderLayout.NORTH, menuBar);
        mainFrame.getContentPane().add(BorderLayout.CENTER, createMainPanel());


        mainFrame.setSize(1000,1000);
        mainFrame.setVisible(true);
    }
    private void updateGUI(){
        mainFrame.getContentPane().remove(mainFrame.getContentPane().getComponents()[1]);
        mainFrame.getContentPane().add(BorderLayout.CENTER, createMainPanel());
        mainFrame.revalidate();
    }
    public JPanel createMainPanel(){
        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(createFoodTablePanel(),gbc);

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(createCountPanel(),gbc);

        gbc.fill = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(createAdditionalInfoPanel(),gbc);

        return mainPanel;
    }
    public void setUpCardLayout(){
        addSthFrame = new JFrame("Add");
        JTabbedPane addSthPane = new JTabbedPane();
        addSthPane.addTab("Add meal", null, createAddMealPanel());
        addSthPane.addTab("Add product", null, createAddProductPanel());
        addSthPane.addTab("Add ingredient", null, createAddIngredientPanel());

        addSthFrame.getContentPane().add(BorderLayout.CENTER, addSthPane);
        addSthFrame.setSize(500,500);
        addSthFrame.setVisible(true);
    }
    private void updateCardLayout(){
        addSthFrame.getContentPane().remove(addSthFrame.getContentPane().getComponents()[0]);
        JTabbedPane addSthPane = new JTabbedPane();
        addSthPane.addTab("Add meal", null, createAddMealPanel());
        addSthPane.addTab("Add product", null, createAddProductPanel());
        addSthPane.addTab("Add ingredient", null, createAddIngredientPanel());

        addSthFrame.getContentPane().add(BorderLayout.CENTER, addSthPane);
        addSthFrame.revalidate();
    }
    public JPanel createAddProductPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton addProduct = new JButton("Add product");

        JLabel nameLabel = new JLabel("Name of product: ");
        JTextField name = new JTextField();

        JLabel ingredientLabel = new JLabel("Ingredient: ");
        JComboBox containingIngredient = new JComboBox(fft.ingredientManager.getAvailableIngredients().toArray());

        JLabel amountLabel = new JLabel("Amount of ingredient: ");
        JTextField amountOfIngredient = new JTextField();

        JLabel costLabel = new JLabel("Cost of product: ");
        JTextField cost = new JTextField();

        addProduct.addActionListener(e -> {
            fft.productManager.addProduct(new Product(name.getText(),
                    new Ingredient((String) containingIngredient.getSelectedItem(),
                            Float.parseFloat(amountOfIngredient.getText())),
                    Float.parseFloat(cost.getText())));

            /*
            for (Product p:fft.productManager.getAvailableProducts()) {
                System.out.println(p.getName());
            }
            */
        });

        {
            panel.add(nameLabel);
            panel.add(name);
            panel.add(ingredientLabel);
            panel.add(containingIngredient);
            panel.add(amountLabel);
            panel.add(amountOfIngredient);
            panel.add(costLabel);
            panel.add(cost);
            panel.add(addProduct);
        } // adding components to panel

        return panel;
    }
    public JPanel createAddIngredientPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton addIngredient = new JButton("Add ingredient and new product");

        JLabel ingredientNameLabel = new JLabel("Name of ingredient: ");
        JTextField ingredientName = new JTextField();

        JLabel productNameLabel = new JLabel("Name of product: ");
        JTextField productName = new JTextField();

        JLabel amountLabel = new JLabel("Amount of ingredient: ");
        JTextField amountOfIngredient = new JTextField();

        JLabel costLabel = new JLabel("Cost of product: ");
        JTextField cost = new JTextField();

        addIngredient.addActionListener(e -> {
            fft.ingredientManager.addIngredient(ingredientName.getText());
            fft.productManager.addProduct(new Product(productName.getText(),
                    new Ingredient(ingredientName.getText(),Float.parseFloat(amountOfIngredient.getText())),
                    Float.parseFloat(cost.getText())));

            for (Product p:fft.productManager.getAvailableProducts()) {
                System.out.println(p.getName());
            }
            for (String ing:fft.ingredientManager.getAvailableIngredients()) {
                System.out.println(ing);
            }
            this.updateCardLayout();
            //this.setUpCardLayout();
        });

        {
            panel.add(ingredientNameLabel);
            panel.add(ingredientName);
            panel.add(productNameLabel);
            panel.add(productName);
            panel.add(amountLabel);
            panel.add(amountOfIngredient);
            panel.add(costLabel);
            panel.add(cost);
            panel.add(addIngredient);
        }//adding components to panel

        return panel;
    }
    public JPanel createAddMealPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel subPanel1 = new JPanel();
        subPanel1.setLayout(new BoxLayout(subPanel1, BoxLayout.Y_AXIS));
        JPanel subPanel2 = new JPanel();
        subPanel2.setLayout(new BoxLayout(subPanel2, BoxLayout.Y_AXIS));


        JLabel mealNameLabel = new JLabel("Name of meal: ");
        JTextField mealName = new JTextField();

        JLabel portionsLabel = new JLabel("Amount of portions: ");
        JTextField portions = new JTextField();

        JLabel requiredTimeLabel = new JLabel("Time for cooking: ");
        JTextField requiredTime = new JTextField();

        JPanel ingredientsPanel = new JPanel();

        JButton addMeal = new JButton("Add meal");
        addMeal.addActionListener(e -> {
            Component[] components = ingredientsPanel.getComponents();
            Ingredient[] ingredients = new Ingredient[components.length/4];
            for (Component comp:components) {
                System.out.println(comp);
            }
            for (int i = 0; i < components.length/4; i+=1) {
                ingredients[i] = new Ingredient( (String) ((JComboBox) components[i*4+1]).getSelectedItem(),
                        Float.parseFloat(((JTextField) components[i*4+3]).getText()));
            }

            fft.mealManager.addMeal(new Meal(mealName.getText(),
                    Integer.parseInt(requiredTime.getText()),
                    ingredients,
                    Integer.parseInt(portions.getText())));
            this.updateGUI();
            System.out.println(fft.mealManager.getAvailableMeals());
        });


        ingredientsPanel.setLayout(new BoxLayout(ingredientsPanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(ingredientsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setMinimumSize(new Dimension(200, 200));
        JButton addIngredient = new JButton("Add ingredient");
        addIngredient.addActionListener(e -> {
            JLabel ingredientNameLabel = new JLabel("Ingredient:");
            JComboBox ingredientName = new JComboBox(fft.ingredientManager.getAvailableIngredients().toArray());


            JLabel amountLabel = new JLabel("Amount");
            JTextField amount = new JTextField();

            ingredientsPanel.add(ingredientNameLabel);
            ingredientsPanel.add(ingredientName);
            ingredientsPanel.add(amountLabel);
            ingredientsPanel.add(amount);

            ingredientsPanel.revalidate();
            subPanel2.revalidate();
        });
        addIngredient.doClick();

        {
            subPanel1.add(mealNameLabel);
            subPanel1.add(mealName);
            subPanel1.add(portionsLabel);
            subPanel1.add(portions);
            subPanel1.add(requiredTimeLabel);
            subPanel1.add(requiredTime);

            subPanel2.add(addIngredient);
            subPanel2.add(scroll);

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridheight = 4;
            panel.add(subPanel1, gbc);

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.gridheight = 4;
            panel.add(subPanel2, gbc);

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            panel.add(addMeal, gbc);
        }// adding components to panel

        return panel;
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
            labelForMoney.setText("Money: " + Evaluator.countMoney(fft.nutritionManager.getCookedMeals(), fft.productManager));
            labelForTime.setText("Time:" + Evaluator.countTime(fft.nutritionManager.getCookedMeals()));
            System.out.println(Evaluator.countMoney(fft.nutritionManager.getCookedMeals(), fft.productManager));
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
        for (Meal meal:fft.nutritionManager.getMealCountMap().keySet()) {
            if (!meal.getName().equals("Nothing")){
                String t = selectedMeals.getText().equals("") ? "" : selectedMeals.getText() + "; ";
                selectedMeals.setText(t + meal.getName() + " : " + fft.nutritionManager.getMealCountMap().get(meal));
            }
        }

        JLabel leftovers = new JLabel();
        for (Meal meal:fft.nutritionManager.getMealCountMap().keySet()) {
            if (!meal.getName().equals("Nothing")) {
                String t = leftovers.getText().equals("") ? "" : leftovers.getText() + "; ";
                leftovers.setText(t + meal.getName() + " : " + fft.nutritionManager.getLeftOvers().get(meal));
            }
        }

        JLabel products = new JLabel();//???
        for (Meal meal:fft.nutritionManager.getMealCountMap().keySet()) {
            if (!meal.getName().equals("Nothing")) {
                String t = products.getText().equals("") ? "" : products.getText() + "; ";
                products.setText(t + meal.getName() + " : " + fft.nutritionManager.getLeftOvers().get(meal));
            }
        }
        labels[0] = selectedMeals;
        labels[1] = leftovers;
        labels[2] = products;

        return labels;
    }
    private JComboBox createChoiceBar(String dayOfTheWeek, int numberOfFood){
        String[] namesOfMeals = new String[fft.mealManager.getAvailableMeals().size()];

        int i = 0;
        for (String name: fft.mealManager.getAvailableMeals().keySet()) {
            namesOfMeals[i++] = name;
        }
        ComboBoxModel model =  new DefaultComboBoxModel(namesOfMeals);

        JComboBox comboBox = new JComboBox();

        comboBox.setModel(model);


        comboBox.setSelectedItem(fft.nutritionManager.getSelectedMealsMap().get(dayOfTheWeek)[numberOfFood].getName());
        // add ItemListener
        comboBox.addActionListener(e -> {
            System.out.println(comboBox.getSelectedItem());
            comboBox.setModel(model);
            if (comboBox.getSelectedItem() == "Nothing"){
                fft.nutritionManager.selectNoMeal(dayOfTheWeek, numberOfFood);
            } else{
                fft.nutritionManager.selectMeal(fft.mealManager.getAvailableMeals().get(comboBox.getSelectedItem()), dayOfTheWeek, numberOfFood);
            }
            //this.updateGUI();
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
