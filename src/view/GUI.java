package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GUI {
    JFrame mainFrame;
    JTabbedPane mainCardLayout;
    FoodFinanceTracker fft;
    String[] daysOfTheWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    public GUI(FoodFinanceTracker fft) {
        this.fft = fft;
    }

    public void setUpGUI() {
        mainFrame = new JFrame();

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem saveMenuItem = new JMenuItem("Save", KeyEvent.VK_S);
        saveMenuItem.addActionListener(e -> {
            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(mainFrame);
            this.fft.save(fileSave.getSelectedFile());
            System.out.println("Saved");
        });
        menu.add(saveMenuItem);
        JMenuItem loadMenuItem = new JMenuItem("Load", KeyEvent.VK_L);
        loadMenuItem.addActionListener(e -> {
            JFileChooser fileLoad = new JFileChooser();
            fileLoad.showSaveDialog(mainFrame);
            this.fft.load(fileLoad.getSelectedFile());
            this.updateGUI();
            System.out.println("Loaded");
        });
        menu.add(loadMenuItem);

        mainFrame.getContentPane().add(BorderLayout.NORTH, menuBar);
        mainFrame.getContentPane().add(BorderLayout.CENTER, createMainCardLayout());


        mainFrame.setSize(1000, 1000);
        mainFrame.setVisible(true);
    }

    public void updateGUI() {
        int chosenTab = mainCardLayout.getSelectedIndex();
        mainFrame.getContentPane().remove(mainFrame.getContentPane().getComponents()[1]);
        mainFrame.getContentPane().add(BorderLayout.CENTER, createMainCardLayout());
        mainFrame.revalidate();
        mainCardLayout.setSelectedIndex(chosenTab);
    }

    private JTabbedPane createMainCardLayout() {
        mainCardLayout = new JTabbedPane();
        //mainCardLayout.addTab("Main", null, createMainPanel());
        mainCardLayout.addTab("Add meal", null, createAddMealPanel());
        mainCardLayout.addTab("Add product", null, createAddProductPanel());
        mainCardLayout.addTab("Add ingredient", null, createAddIngredientPanel());
        mainCardLayout.addTab("All meals", null, createAllMealPanel());
        mainCardLayout.addTab("All ingredients", null, createAllIngredientPanel());
        mainCardLayout.addTab("Test calendar", null, createCalendar());
        return mainCardLayout;
    }

    private JPanel createAllMealPanel() {
        JPanel allMealPanel = new JPanel();
        allMealPanel.setLayout(new FlowLayout());

        for (Meal meal : fft.mealManager.getAvailableMeals().values()) {
            if (meal.getName() != "Nothing") {
                allMealPanel.add(createMealPanel(meal));
            }
        }


        return allMealPanel;
    }

    private JPanel createMealPanel(Meal meal) {
        JPanel mealPanel = new JPanel();
        mealPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        mealPanel.setLayout(new BoxLayout(mealPanel, BoxLayout.Y_AXIS));

        JPanel subPanel1 = new JPanel();
        subPanel1.setLayout(new BoxLayout(subPanel1, BoxLayout.X_AXIS));

        JButton editMealButton = new JButton("Edit");
        editMealButton.addActionListener(e -> {
            editMeal(meal);
        });
        JButton deleteMealButton = new JButton("Delete");
        deleteMealButton.addActionListener(e -> {
            fft.mealManager.removeMeal(meal);
            updateGUI();
        });

        JLabel mealName = new JLabel(meal.getName());
        mealName.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));


        JPanel subPanel2 = new JPanel();
        subPanel2.setLayout(new BoxLayout(subPanel2, BoxLayout.Y_AXIS));

        JLabel requiredTimeLabel = new JLabel("Required time: " + meal.getRequiredTime());
        JLabel portionsLabel = new JLabel("Portions: " + meal.getPortions());
        JLabel ingredientLabel = new JLabel("ingredients:");
        JPanel ingredientsInfo = new JPanel();
        ingredientsInfo.setLayout(new BoxLayout(ingredientsInfo, BoxLayout.Y_AXIS));
        for (Ingredient ingredient : meal.getIngredients().keySet()) {
            JLabel ingredientInfo = new JLabel(ingredient.getName() + " : " + meal.getIngredients().get(ingredient));
            ingredientInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
            ingredientsInfo.add(ingredientInfo);
        }
        JScrollPane scroll = new JScrollPane(ingredientsInfo, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(70, 100));

        subPanel1.add(deleteMealButton);
        subPanel1.add(Box.createRigidArea(new Dimension(10, 0)));
        subPanel1.add(mealName);
        subPanel1.add(Box.createRigidArea(new Dimension(10, 0)));
        subPanel1.add(editMealButton);

        subPanel2.add(requiredTimeLabel);
        subPanel2.add(portionsLabel);
        subPanel2.add(ingredientLabel);
        subPanel2.add(scroll);

        mealPanel.add(subPanel1);
        mealPanel.add(subPanel2);

        return mealPanel;
    }

    private void editMeal(Meal meal) {
        mainCardLayout.setComponentAt(1, createAddMealPanel(meal));
        mainCardLayout.setSelectedIndex(1);

    }

    private JPanel createAllIngredientPanel() {
        JPanel allIngPanel = new JPanel();
        allIngPanel.setLayout(new FlowLayout());

        for (String ing : fft.ingredientManager.getAvailableIngredients()) {
            allIngPanel.add(createIngredientPanel(ing));
        }


        return allIngPanel;
    }

    private JPanel createIngredientPanel(String ingredient) {
        JPanel ingredientPanel = new JPanel();
        ingredientPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        ingredientPanel.setLayout(new BoxLayout(ingredientPanel, BoxLayout.Y_AXIS));

        JPanel subPanel1 = new JPanel();
        subPanel1.setLayout(new BoxLayout(subPanel1, BoxLayout.X_AXIS));

        JButton deleteMealButton = new JButton("Delete");
        deleteMealButton.addActionListener(e -> {
            for (Product product : Evaluator.findProductsForIngredient(ingredient, fft.productManager.getAvailableProducts())) {
                fft.productManager.removeProduct(product);
            }

            fft.ingredientManager.removeIngredient(ingredient);
            updateGUI();
        });

        JLabel ingredientName = new JLabel(ingredient);
        ingredientName.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));

        JPanel subPanel2 = new JPanel();
        subPanel2.setLayout(new BoxLayout(subPanel2, BoxLayout.Y_AXIS));

        JLabel productLabel = new JLabel("Available products:");
        JPanel productsInfo = new JPanel();
        productsInfo.setLayout(new BoxLayout(productsInfo, BoxLayout.Y_AXIS));
        for (Product product : Evaluator.findProductsForIngredient(ingredient, fft.productManager.getAvailableProducts())) {

            JPanel productPanel = new JPanel();
            productPanel.setBorder(BorderFactory.createLineBorder(Color.black));
            JPanel productInfoPanel = new JPanel();
            JPanel productManagementPanel = new JPanel();
            productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.X_AXIS));
            productInfoPanel.setLayout(new BoxLayout(productInfoPanel, BoxLayout.Y_AXIS));
            productManagementPanel.setLayout(new BoxLayout(productManagementPanel, BoxLayout.Y_AXIS));

            JLabel productName = new JLabel(product.getName());
            productName.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            JLabel ingredientAmount = new JLabel("Amount : " + product.getAmountOfIngredient());
            JLabel productCost = new JLabel("Cost : " + product.getCost());
            productName.setAlignmentX(Component.CENTER_ALIGNMENT);
            ingredientAmount.setAlignmentX(Component.CENTER_ALIGNMENT);
            productCost.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton editProduct = new JButton("Edit");
            editProduct.addActionListener(e -> {
                editProduct(product);
            });
            JButton deleteProduct = new JButton("Delete");
            deleteProduct.addActionListener(e -> {
                fft.productManager.removeProduct(product);
                updateGUI();
            });
            editProduct.setAlignmentX(Component.CENTER_ALIGNMENT);
            deleteProduct.setAlignmentX(Component.CENTER_ALIGNMENT);


            productInfoPanel.add(productName);
            productInfoPanel.add(Box.createRigidArea(new Dimension(0, 12)));
            productInfoPanel.add(ingredientAmount);
            productInfoPanel.add(Box.createRigidArea(new Dimension(0, 7)));
            productInfoPanel.add(productCost);

            productManagementPanel.add(editProduct);
            productManagementPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            productManagementPanel.add(deleteProduct);

            productPanel.add(productInfoPanel);
            productManagementPanel.add(Box.createRigidArea(new Dimension(80, 0)));
            productPanel.add(productManagementPanel);
            productsInfo.add(productPanel);
        }
        JScrollPane scroll = new JScrollPane(productsInfo, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(70, 100));

        subPanel1.add(deleteMealButton);
        subPanel1.add(Box.createRigidArea(new Dimension(10, 0)));
        subPanel1.add(ingredientName);
        subPanel1.add(Box.createRigidArea(new Dimension(10, 0)));

        subPanel2.add(productLabel);
        subPanel2.add(scroll);

        ingredientPanel.add(subPanel1);
        ingredientPanel.add(subPanel2);

        return ingredientPanel;
    }

    private void editProduct(Product product) {
        mainCardLayout.setComponentAt(2, createAddProductPanel(product));
        mainCardLayout.setSelectedIndex(2);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(createFoodTablePanel(), gbc);

        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(createCountPanel(), gbc);

        gbc.fill = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(createAdditionalInfoPanel(), gbc);

        return mainPanel;
    }

    public JPanel createAddProductPanel() {
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
                    new Ingredient((String) containingIngredient.getSelectedItem()),
                    Float.parseFloat(amountOfIngredient.getText()),
                    Float.parseFloat(cost.getText())));
            this.updateGUI();
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

    public JPanel createAddProductPanel(Product product) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton addProduct = new JButton("Add product");

        JLabel nameLabel = new JLabel("Name of product: ");
        JTextField name = new JTextField(product.getName());

        JLabel ingredientLabel = new JLabel("Ingredient: ");
        JComboBox containingIngredient = new JComboBox(fft.ingredientManager.getAvailableIngredients().toArray());
        containingIngredient.setSelectedItem(product.getIngredient().getName());

        JLabel amountLabel = new JLabel("Amount of ingredient: ");
        JTextField amountOfIngredient = new JTextField(Float.toString(product.getAmountOfIngredient()));

        JLabel costLabel = new JLabel("Cost of product: ");
        JTextField cost = new JTextField(Float.toString(product.getCost()));

        addProduct.addActionListener(e -> {
            fft.productManager.addProduct(new Product(name.getText(),
                    new Ingredient((String) containingIngredient.getSelectedItem()),
                    Float.parseFloat(amountOfIngredient.getText()),
                    Float.parseFloat(cost.getText())));
            this.updateGUI();
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

    public JPanel createAddIngredientPanel() {
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
                    new Ingredient(ingredientName.getText()), Float.parseFloat(amountOfIngredient.getText()),
                    Float.parseFloat(cost.getText())));

            for (Product p : fft.productManager.getAvailableProducts()) {
                System.out.println(p.getName());
            }
            for (String ing : fft.ingredientManager.getAvailableIngredients()) {
                System.out.println(ing);
            }
            this.updateGUI();
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

    public JPanel createAddMealPanel() {
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
            Map<Ingredient, Float> ingredientAmountMap = new HashMap<>();
            for (int i = 0; i < components.length / 5; i += 1) {
                String selectedIngredient = (String) ((JComboBox) components[i * 5 + 1]).getSelectedItem();
                float amountOfIngredient = Float.parseFloat(((JTextField) components[i * 5 + 3]).getText());
                ingredientAmountMap.put(new Ingredient(selectedIngredient), amountOfIngredient);
            }

            fft.mealManager.addMeal(
                    new Meal(
                            mealName.getText(),
                            Integer.parseInt(requiredTime.getText()),
                            ingredientAmountMap,
                            Integer.parseInt(portions.getText())));
            this.updateGUI();
        });


        ingredientsPanel.setLayout(new BoxLayout(ingredientsPanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(ingredientsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(100, 100));

        JButton addIngredient = new JButton("Add ingredient");
        addIngredient.addActionListener(e -> {
            JPanel ingredientBlock = new JPanel();
            if (ingredientsPanel.getComponents().length != 0) {
                ingredientBlock.setName(":(");
            } else {
                ingredientBlock.setName("0");
            }
            ingredientBlock.setLayout(new BoxLayout(ingredientBlock, BoxLayout.Y_AXIS));
            JLabel ingredientNameLabel = new JLabel("Ingredient:");
            JComboBox ingredientName = new JComboBox(fft.ingredientManager.getAvailableIngredients().toArray());


            JLabel amountLabel = new JLabel("Amount");
            JTextField amount = new JTextField();
            JButton removeIngredient = new JButton("Remove");

            int indexOfComponent = ingredientsPanel.getComponents().length;

            removeIngredient.addActionListener(e1 -> {
                for (int i = 0; i < 5; i++) {
                    ingredientsPanel.remove(indexOfComponent);
                    ingredientsPanel.revalidate();
                }
            });

            ingredientsPanel.add(ingredientNameLabel);
            ingredientsPanel.add(ingredientName);
            ingredientsPanel.add(amountLabel);
            ingredientsPanel.add(amount);
            ingredientsPanel.add(removeIngredient);

            ingredientsPanel.revalidate();
            subPanel2.revalidate();
        });
        addIngredient.doClick();

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

        return panel;
    }

    public JPanel createAddMealPanel(Meal meal) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel subPanel1 = new JPanel();
        subPanel1.setLayout(new BoxLayout(subPanel1, BoxLayout.Y_AXIS));
        JPanel subPanel2 = new JPanel();
        subPanel2.setLayout(new BoxLayout(subPanel2, BoxLayout.Y_AXIS));


        JLabel mealNameLabel = new JLabel("Name of meal: ");
        JTextField mealName = new JTextField(meal.getName());

        JLabel portionsLabel = new JLabel("Amount of portions: ");
        JTextField portions = new JTextField(Integer.toString(meal.getPortions()));

        JLabel requiredTimeLabel = new JLabel("Time for cooking: ");
        JTextField requiredTime = new JTextField(Integer.toString(meal.getRequiredTime()));

        JPanel ingredientsPanel = new JPanel();

        JButton addMeal = new JButton("Add meal");
        addMeal.addActionListener(e -> {
            Component[] components = ingredientsPanel.getComponents();
            Ingredient[] ingredients = new Ingredient[components.length / 5];
            Map<Ingredient, Float> ingredientAmountMap = new HashMap<>();
            for (int i = 0; i < components.length / 5; i += 1) {
                ingredientAmountMap.put(new Ingredient((String) ((JComboBox) components[i * 5 + 1]).getSelectedItem()),
                        Float.parseFloat(((JTextField) components[i * 5 + 3]).getText()));
            }

            fft.mealManager.addMeal(new Meal(mealName.getText(),
                    Integer.parseInt(requiredTime.getText()),
                    ingredientAmountMap,
                    Integer.parseInt(portions.getText())));
            this.updateGUI();
        });


        ingredientsPanel.setLayout(new BoxLayout(ingredientsPanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(ingredientsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(100, 100));
        JButton addIngredient = new JButton("Add ingredient");

        addIngredient.addActionListener(e -> {
            JLabel ingredientNameLabel = new JLabel("Ingredient:");
            JComboBox ingredientName = new JComboBox(fft.ingredientManager.getAvailableIngredients().toArray());


            JLabel amountLabel = new JLabel("Amount");
            JTextField amount = new JTextField();
            JButton removeIngredient = new JButton("Remove");
            int indexOfComponent = ingredientsPanel.getComponents().length;
            removeIngredient.addActionListener(e1 -> {
                for (int i = 0; i < 5; i++) {
                    ingredientsPanel.remove(indexOfComponent);
                    ingredientsPanel.revalidate();
                }
            });

            ingredientsPanel.add(ingredientNameLabel);
            ingredientsPanel.add(ingredientName);
            ingredientsPanel.add(amountLabel);
            ingredientsPanel.add(amount);
            ingredientsPanel.add(removeIngredient);

            ingredientsPanel.revalidate();
            subPanel2.revalidate();
        });

        int indexCounter = 0;
        for (Ingredient ingredient : meal.getIngredients().keySet()) {
            JLabel ingredientNameLabel = new JLabel("Ingredient:");
            JComboBox ingredientName = new JComboBox(fft.ingredientManager.getAvailableIngredients().toArray());
            ingredientName.setSelectedItem(ingredient.getName());


            JLabel amountLabel = new JLabel("Amount");
            JTextField amount = new JTextField(Float.toString(meal.getIngredients().get(ingredient)));
            JButton removeIngredient = new JButton("Remove");

            int indexOfComponent = ingredientsPanel.getComponents().length;
            removeIngredient.addActionListener(e1 -> {
                for (int i = 0; i < 5; i++) {
                    ingredientsPanel.remove(indexOfComponent);
                    ingredientsPanel.revalidate();
                }
            });

            ingredientsPanel.add(ingredientNameLabel);
            ingredientsPanel.add(ingredientName);
            ingredientsPanel.add(amountLabel);
            ingredientsPanel.add(amount);
            ingredientsPanel.add(removeIngredient);

            ingredientsPanel.revalidate();
            subPanel2.revalidate();
        }

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

        return panel;
    }

    private JPanel createFoodTablePanel() {
        JPanel foodTablePanel = new JPanel();
        foodTablePanel.setLayout(new FlowLayout());

        for (int i = 0; i < 7; i++) {
            foodTablePanel.add(createDay(daysOfTheWeek[i]));
        }
        return foodTablePanel;
    }

    private JPanel createCountPanel() {
        JPanel countPanel = new JPanel();
        countPanel.setLayout(new BoxLayout(countPanel, BoxLayout.Y_AXIS));

        JButton button = new JButton("Count");
        JLabel labelForMoney = new JLabel("Money:");
        JLabel labelForTime = new JLabel("Time:");
        button.addActionListener(e -> {
            labelForMoney.setText("Money: " + Evaluator.countMoney(fft.nutritionManager.getCookedMeals(), fft.productManager));
            labelForTime.setText("Time:" + Evaluator.countTime(fft.nutritionManager.getCookedMeals()));
            System.out.println(Evaluator.countMoney(fft.nutritionManager.getCookedMeals(), fft.productManager));
            //System.out.println(model.Evaluator.countMoneyPlus(fft.nutritionManager.getCookedMeals(), fft.productManager));
        });

        countPanel.add(button);
        countPanel.add(labelForMoney);
        countPanel.add(labelForTime);
        return countPanel;
    }

    private JPanel createAdditionalInfoPanel() {
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

    private JLabel[] getAdditionalInfoLabels() {
        JLabel[] labels = new JLabel[3];

        JLabel selectedMeals = new JLabel();
        for (Meal meal : fft.nutritionManager.getMealCountMap().keySet()) {
            if (!meal.getName().equals("Nothing")) {
                String t = selectedMeals.getText().isEmpty() ? "" : selectedMeals.getText() + "; ";
                selectedMeals.setText(t + meal.getName() + " : " + fft.nutritionManager.getMealCountMap().get(meal));
            }
        }

        JLabel leftovers = new JLabel();
        for (Meal meal : fft.nutritionManager.getLeftOvers().keySet()) {
            String t = leftovers.getText().isEmpty() ? "" : leftovers.getText() + "; ";
            leftovers.setText(t + meal.getName() + " : " + fft.nutritionManager.getLeftOvers().get(meal));
        }

        JLabel products = new JLabel();
        Map<String, Number> neededProducts = Evaluator.getNeededProducts(fft.nutritionManager.getCookedMeals(), fft.productManager.getAvailableProducts());
        for (String product : neededProducts.keySet()) {
            String t = products.getText().isEmpty() ? "" : products.getText() + "; ";
            products.setText(t + product + " : " + neededProducts.get(product));
        }
        labels[0] = selectedMeals;
        labels[1] = leftovers;
        labels[2] = products;

        return labels;
    }

    private JComboBox createChoiceBar(String dayOfTheWeek, int numberOfFood) {
        String[] namesOfMeals = new String[fft.mealManager.getAvailableMeals().size()];

        int i = 0;
        for (String name : fft.mealManager.getAvailableMeals().keySet()) {
            namesOfMeals[i++] = name;
        }
        ComboBoxModel model = new DefaultComboBoxModel(namesOfMeals);

        JComboBox comboBox = new JComboBox();

        comboBox.setModel(model);


        comboBox.setSelectedItem(fft.nutritionManager.getSelectedMealsMap().get(dayOfTheWeek)[numberOfFood].getName());
        // add ItemListener
        comboBox.addActionListener(e -> {
            comboBox.setModel(model);
            for (String day : fft.nutritionManager.getSelectedMealsMap().keySet()) {
                System.out.println(day + " : " + Arrays.toString(fft.nutritionManager.getSelectedMealsMap().get(day)));
            }

            if (comboBox.getSelectedItem() == "Nothing") {
                //fft.nutritionManager.deselectMeal(dayOfTheWeek, numberOfFood);
            } else {
                //fft.nutritionManager.selectMeal(fft.mealManager.getAvailableMeals().get(comboBox.getSelectedItem()), dayOfTheWeek, numberOfFood);
            }
            this.updateGUI();
        });
        return comboBox;
    }

    private JPanel createDay(String dayOfTheWeek) {
        JPanel panel = new JPanel();
        JLabel dayName = new JLabel(dayOfTheWeek);
        panel.add(dayName);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (int i = 0; i < 3; i++) {
            panel.add(createChoiceBar(dayOfTheWeek, i));
        }
        return panel;
    }
    private JPanel createCalendar(){
        JPanel mainPanel = new JPanel(new GridLayout());

        JPanel panel = new JPanel(new GridLayout());
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));

        for (String nameOfTheDay :fft.nutritionManager.getSelectedMealsMap().keySet()) {
            panel.add(createDayPlus(nameOfTheDay));
        }

        JScrollPane scroll = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        mainPanel.add(scroll);

        return mainPanel;
    }
    private JPanel createDayPlus(String dayOfTheWeek){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.setBorder(BorderFactory.createLineBorder(Color.black));


        JLabel dayLabel = new JLabel(dayOfTheWeek);
        dayLabel.setSize( new Dimension(100, 200));
        dayLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));



        JButton addButton = new JButton("Add");
        addButton.setSize( new Dimension(100, 200));
        addButton.addActionListener(e -> {
            CreateComplexMeal meal = new CreateComplexMeal(this,
                    fft.mealManager.getAvailableMeals().values().toArray(new Meal[0]), dayOfTheWeek,
                    fft.nutritionManager.getSelectedComplexMealsMap().get(dayOfTheWeek).length);
            meal.setUpFrame();
        });


        JPanel dayAndAdd = new JPanel(new GridLayout(1, 2));
        dayAndAdd.setBorder(BorderFactory.createLineBorder(Color.black));
        dayAndAdd.add(dayLabel);

        dayAndAdd.add(addButton);


        JPanel mealsPanel = new JPanel();
        mealsPanel.setLayout(new BoxLayout(mealsPanel, BoxLayout.Y_AXIS));

        int i = 1;
        for (ComplexMeal cm : fft.nutritionManager.getSelectedComplexMealsMap().get(dayOfTheWeek)) {
            mealsPanel.add(createMealOfTheDay(i++, cm, dayOfTheWeek));
            mealsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        panel.add(dayAndAdd);
        panel.add(mealsPanel);

        return panel;
    }
    private JPanel createMealOfTheDay(int number, ComplexMeal complexMeal, String dayOfTheWeek){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.black));

        GridBagConstraints c = new GridBagConstraints();

        JLabel numberOfMeal = new JLabel(Integer.toString(number));
        numberOfMeal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(numberOfMeal, c);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            fft.nutritionManager.deleteComplexMeal(dayOfTheWeek, number);
            updateGUI();
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(deleteButton, c);

        JPanel mealsList = new JPanel();
        mealsList.setLayout(new BoxLayout(mealsList, BoxLayout.Y_AXIS));

        for (Meal m : complexMeal.getMeals().keySet()) {
            JPanel mealRow = new JPanel();
            mealRow.setLayout(new GridLayout(1, 2));

            JLabel name = new JLabel(m.getName());

            JLabel amount = new JLabel(" x " + complexMeal.getMeals().get(m));
            mealRow.add(name);
            mealRow.add(amount);

            mealsList.add(mealRow);
        }
        mealsList.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 2;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(mealsList, c);

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(e->{
            CreateComplexMeal meal = new CreateComplexMeal(this, complexMeal,
                    fft.mealManager.getAvailableMeals().values().toArray(new Meal[0]), dayOfTheWeek,
                    number-1);
            meal.setUpFrame();
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 2;
        c.gridx = 2;
        c.gridy = 0;
        panel.add(editButton, c);


        return panel;
    }
    public void addComplexMeal(ComplexMeal complexMeal, String day, int orderOfComplexMeal){
        this.fft.nutritionManager.addComplexMeal(complexMeal, day, orderOfComplexMeal);
    }
}
