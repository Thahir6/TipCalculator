import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.GridLayout;
import java.util.Locale;

public class TipCalculatorController {

    private static void showCalculator() {
        JFrame window = new JFrame("Tip Calculator");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField billAmountField = new JTextField();
        JTextField tipPercentageField = new JTextField();
        JLabel tipAmountLabel = new JLabel(" ");
        JLabel errorMessageLabel = new JLabel(" ");
        errorMessageLabel.setForeground(java.awt.Color.RED);

        JButton calculateButton = new JButton("Calculate Tip");
        JButton resetButton = new JButton("Reset");

        calculateButton.addActionListener(event -> {
            try {
                errorMessageLabel.setText(" ");
                double bill = Double.parseDouble(billAmountField.getText().trim());
                double tipPercent = Double.parseDouble(tipPercentageField.getText().trim());

                if (!Double.isFinite(bill) || !Double.isFinite(tipPercent)
                        || bill < 0 || tipPercent < 0) {
                    throw new IllegalArgumentException("Amounts must be non-negative and finite.");
                }

                double tip = bill * (tipPercent / 100.0);
                if (!Double.isFinite(tip)) {
                    throw new IllegalArgumentException("Calculated tip is too large.");
                }

                tipAmountLabel.setText(String.format(Locale.US, "Tip Amount: $%.2f", tip));
            } catch (IllegalArgumentException ex) {
                tipAmountLabel.setText(" ");
                errorMessageLabel.setText(ex instanceof NumberFormatException
                        ? "Enter valid numbers."
                        : ex.getMessage());
            }
        });

        resetButton.addActionListener(event -> {
            billAmountField.setText("");
            tipPercentageField.setText("");
            tipAmountLabel.setText(" ");
            errorMessageLabel.setText(" ");
        });

        JPanel content = new JPanel(new GridLayout(0, 1, 8, 8));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel titleLabel = new JLabel("Tip Calculator", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(18.0f).deriveFont(java.awt.Font.BOLD));
        content.add(titleLabel);
        content.add(new JLabel("Bill Amount"));
        content.add(billAmountField);
        content.add(new JLabel("Tip Percentage"));
        content.add(tipPercentageField);

        JPanel buttons = new JPanel();
        buttons.add(calculateButton);
        buttons.add(resetButton);
        content.add(buttons);
        content.add(tipAmountLabel);
        content.add(errorMessageLabel);

        window.setContentPane(content);
        window.pack();
        window.setMinimumSize(new java.awt.Dimension(350, 300));
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TipCalculatorController::showCalculator);
    }
}