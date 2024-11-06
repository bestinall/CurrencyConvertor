import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverterGUI extends JFrame {

    private static final Map<String, Double> exchangeRates = new HashMap<>();
    private JComboBox<String> baseCurrencyDropdown;
    private JComboBox<String> targetCurrencyDropdown;
    private JTextField amountField;
    private JLabel resultLabel;

    static {
        // Initialize exchange rates with USD as the base currency
        exchangeRates.put("USD", 1.0);     // USD to USD
        exchangeRates.put("EUR", 0.91);    // USD to EUR
        exchangeRates.put("INR", 82.50);   // USD to INR
        exchangeRates.put("JPY", 148.95);  // USD to JPY
        exchangeRates.put("GBP", 0.78);    // USD to GBP
        exchangeRates.put("AUD", 1.56);    // USD to AUD
        exchangeRates.put("CAD", 1.34);    // USD to CAD
        // Add more currencies as needed
    }

    public CurrencyConverterGUI() {
        setTitle("Currency Converter");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        JLabel baseCurrencyLabel = new JLabel("Base Currency:");
        JLabel targetCurrencyLabel = new JLabel("Target Currency:");
        JLabel amountLabel = new JLabel("Amount:");
        resultLabel = new JLabel("Converted Amount: ");

        baseCurrencyDropdown = new JComboBox<>(exchangeRates.keySet().toArray(new String[0]));
        targetCurrencyDropdown = new JComboBox<>(exchangeRates.keySet().toArray(new String[0]));
        amountField = new JTextField(10);
        JButton convertButton = new JButton("Convert");

        // Set up layout
        setLayout(new GridLayout(5, 2, 10, 10));
        add(baseCurrencyLabel);
        add(baseCurrencyDropdown);
        add(targetCurrencyLabel);
        add(targetCurrencyDropdown);
        add(amountLabel);
        add(amountField);
        add(new JLabel());  // Empty label for spacing
        add(convertButton);
        add(resultLabel);

        // Action listener for conversion
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performConversion();
            }
        });
    }

    private void performConversion() {
        try {
            String baseCurrency = (String) baseCurrencyDropdown.getSelectedItem();
            String targetCurrency = (String) targetCurrencyDropdown.getSelectedItem();
            double amount = Double.parseDouble(amountField.getText());

            double convertedAmount = convertCurrency(baseCurrency, targetCurrency, amount);
            resultLabel.setText(String.format("Converted Amount: %.2f %s", convertedAmount, targetCurrency));
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid amount. Please enter a number.");
        }
    }

    private double convertCurrency(String baseCurrency, String targetCurrency, double amount) {
        // Convert the amount from baseCurrency to USD, then to targetCurrency
        double amountInUSD = amount / exchangeRates.get(baseCurrency);
        return amountInUSD * exchangeRates.get(targetCurrency);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CurrencyConverterGUI converterGUI = new CurrencyConverterGUI();
            converterGUI.setVisible(true);
        });
    }
}
