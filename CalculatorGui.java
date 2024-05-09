package mypackage;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGui extends Frame implements ActionListener {
    private TextField textField;
    private Button[] numericButtons;
    private Button[] operatorButtons;

    public CalculatorGui() {
        setTitle("Simple Calculator");
        setLayout(new GridLayout(2, 1));

        // Panel for the text field
        Panel textFieldPanel = new Panel(new FlowLayout());
        textField = new TextField();
        textField.setColumns(15); // Set the preferred width
        textFieldPanel.add(textField);
        add(textFieldPanel);

        // Panel for buttons
        Panel buttonPanel = new Panel(new GridLayout(4, 4));

        // Numeric buttons
        numericButtons = new Button[10];
        for (int i = 0; i <= 9; i++) {
            numericButtons[i] = new Button(String.valueOf(i));
            numericButtons[i].addActionListener(this);
            buttonPanel.add(numericButtons[i]);
        }

        // Operator buttons
        operatorButtons = new Button[4];
        operatorButtons[0] = new Button("+");
        operatorButtons[1] = new Button("-");
        operatorButtons[2] = new Button("*");
        operatorButtons[3] = new Button("/");
        
        for (Button button : operatorButtons) {
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        // Equals and Clear buttons
        Button equalsButton = new Button("=");
        equalsButton.addActionListener(this);
        buttonPanel.add(equalsButton);

        Button clearButton = new Button("C");
        clearButton.addActionListener(this);
        buttonPanel.add(clearButton);

        add(buttonPanel);

        // Setting up the frame
        setSize(400, 400); // Adjusted size for better visibility
        setVisible(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        try {
            Button clickedButton = (Button) e.getSource();
            String currentText = textField.getText();

            if (clickedButton.getLabel().equals("=")) {
                evaluateExpression();
            } else if (clickedButton.getLabel().equals("C")) {
                textField.setText("");
            } else {
                textField.setText(currentText + clickedButton.getLabel());
            }
        } catch (NumberFormatException ex) {
            textField.setText("Invalid Input");
        } catch (ArithmeticException ex) {
            textField.setText("Arithmetic Exception");
        }
    }

    private void evaluateExpression() {
        String expression = textField.getText();
        String[] operands;
        char operator = 0;

        if (expression.contains("+")) {
            operator = '+';
        } else if (expression.contains("-")) {
            operator = '-';
        } else if (expression.contains("*")) {
            operator = '*';
        } else if (expression.contains("/")) {
            operator = '/';
        }

        operands = expression.split("[\\+\\-\\*\\/]");

        // Ensure that there are two operands before proceeding
        if (operands.length != 2) {
            textField.setText("Invalid Expression");
            return;
        }

        try {
            int operand1 = Integer.parseInt(operands[0]);
            int operand2 = Integer.parseInt(operands[1]);

            switch (operator) {
                case '+':
                    textField.setText(String.valueOf(operand1 + operand2));
                    break;
                case '-':
                    textField.setText(String.valueOf(operand1 - operand2));
                    break;
                case '*':
                    textField.setText(String.valueOf(operand1 * operand2));
                    break;
                case '/':
                    if (operand2 != 0) {
                        textField.setText(String.valueOf(operand1 / operand2));
                    } else {
                        throw new ArithmeticException("Division by zero");
                    }
                    break;
                default:
                    break;
            }
        } catch (NumberFormatException ex) {
            textField.setText("Invalid Input");
        } catch (ArithmeticException ex) {
            textField.setText("Arithmetic Exception");
        }
    }

    public static void main(String[] args) {
        new CalculatorGui();
    }
}