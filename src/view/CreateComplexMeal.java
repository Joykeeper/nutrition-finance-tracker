package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class CreateComplexMeal extends JDialog {
    JDialog mainFrame;
    Meal[] availableMeals;
    ComplexMeal complexMeal;
    GUI gui;
    String weekday;
    int orderOfFood;
    public CreateComplexMeal(GUI frame, ComplexMeal complexMeal, Meal[] availableMeals, String dayOfTheWeek, int orderOfMeal){
        this.gui = frame;
        this.complexMeal = complexMeal;
        this.availableMeals = availableMeals;
        this.weekday = dayOfTheWeek;
        this.orderOfFood = orderOfMeal;
    }
    public CreateComplexMeal(GUI frame, Meal[] availableMeals, String dayOfTheWeek, int orderOfMeal){
        this(frame, new ComplexMeal(), availableMeals, dayOfTheWeek, orderOfMeal);
    }
    public void setUpFrame(){
        mainFrame = new JDialog();

        mainFrame.getContentPane().add(BorderLayout.EAST, createResultPanel());
        mainFrame.getContentPane().add(BorderLayout.CENTER, createMealSelectionPanel());


        mainFrame.setSize(1080, 720);
        mainFrame.setVisible(true);
    }
    public void update(){
        mainFrame.getContentPane().remove(mainFrame.getContentPane().getComponents()[0]);
        mainFrame.getContentPane().remove(mainFrame.getContentPane().getComponents()[0]);

        mainFrame.getContentPane().add(BorderLayout.EAST, createResultPanel());
        mainFrame.getContentPane().add(BorderLayout.CENTER, createMealSelectionPanel());

        mainFrame.revalidate();
    }
    public JPanel createMealSelectionPanel(){
        JPanel panel = new JPanel();
        //panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        for (Meal m: this.availableMeals) {
            if (m.getName().equals("Nothing")){
                continue;
            }
            panel.add(createMealSelectionItem(m));
        }
        return panel;
    }
    public JPanel createMealSelectionItem(Meal m){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(200, 200));
        panel.setBorder(BorderFactory.createLineBorder(Color.black));

        JPanel subPanel = new JPanel();
        subPanel.setLayout(new GridLayout(2, 1));

        JLabel mealName = new JLabel(m.getName());
        mealName.setHorizontalAlignment(JLabel.CENTER);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e->{
            this.complexMeal.addMeal(m);
            this.update();
        });

        subPanel.add(mealName);
        subPanel.add(addButton);

        panel.add(subPanel);

        return panel;
    }

    public JPanel createResultPanel(){
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        resultPanel.setPreferredSize(new Dimension(400, mainFrame.getHeight()));

        resultPanel.add(Box.createRigidArea(new Dimension(0, 100)));

        JLabel resultLabel = new JLabel("Result");

        JPanel listOfMeals = new JPanel();
        listOfMeals.setLayout(new BoxLayout(listOfMeals, BoxLayout.Y_AXIS));
        listOfMeals.setPreferredSize(new Dimension(400, 400));

        listOfMeals.setBorder(BorderFactory.createLineBorder(Color.black));
        for(Meal meal:complexMeal.getMeals().keySet()) {
            if(meal.getName().equals("Nothing")) continue;
            listOfMeals.add(addedMealComponent(meal));
        }

        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(e -> {
            if (this.gui == null) return;
            this.gui.addComplexMeal(this.complexMeal, this.weekday, this.orderOfFood);
            this.gui.updateGUI();
            this.mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
        });

        resultPanel.add(resultLabel);
        resultPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        resultPanel.add(listOfMeals);
        resultPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        resultPanel.add(continueButton);
        resultPanel.add(Box.createRigidArea(new Dimension(0, 100)));


        return resultPanel;
    }
    public JPanel addedMealComponent(Meal m){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.black));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2,2,1,2);

        JLabel mealName = new JLabel(m.getName());

        JLabel mealAmount = new JLabel(Integer.toString(complexMeal.getMeals().get(m)));

        JButton deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(200, 200));
        deleteButton.addActionListener(e -> {
            complexMeal.removeMeal(m);
            this.update();
        });

        c.gridx = 0;
        c.gridy = 0;
        panel.add(mealName, c);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(mealAmount, c);
        c.gridx = 0;
        c.gridwidth  = 2;
        c.gridy = 1;
        panel.add(deleteButton, c);
        return panel;
    }

}
