package com.example.cal;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private EditText editTextExpressionInput;
    private TextView textViewResult;

    private double lastResult = 0.0;
    private boolean isNewCalculation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextExpressionInput = findViewById(R.id.editTextExpressionInput);
        textViewResult = findViewById(R.id.textViewResult);

        setupButtonListeners();

        editTextExpressionInput.setText("");
        textViewResult.setText("");
        lastResult = 0.0;
        isNewCalculation = true;
    }

    private void setupButtonListeners() {
        // Nút số và dấu thập phân
        findViewById(R.id.button0).setOnClickListener(v -> appendToExpression("0"));
        findViewById(R.id.button1).setOnClickListener(v -> appendToExpression("1"));
        findViewById(R.id.button2).setOnClickListener(v -> appendToExpression("2"));
        findViewById(R.id.button3).setOnClickListener(v -> appendToExpression("3"));
        findViewById(R.id.button4).setOnClickListener(v -> appendToExpression("4"));
        findViewById(R.id.button5).setOnClickListener(v -> appendToExpression("5"));
        findViewById(R.id.button6).setOnClickListener(v -> appendToExpression("6"));
        findViewById(R.id.button7).setOnClickListener(v -> appendToExpression("7"));
        findViewById(R.id.button8).setOnClickListener(v -> appendToExpression("8"));
        findViewById(R.id.button9).setOnClickListener(v -> appendToExpression("9"));
        findViewById(R.id.buttonDot).setOnClickListener(v -> appendToExpression("."));

        // Nút phép toán
        findViewById(R.id.buttonAdd).setOnClickListener(v -> handleOperator("+"));
        findViewById(R.id.buttonSubtract).setOnClickListener(v -> handleOperator("-"));
        findViewById(R.id.buttonMultiply).setOnClickListener(v -> handleOperator("*"));
        findViewById(R.id.buttonDivide).setOnClickListener(v -> handleOperator("/"));
        findViewById(R.id.buttonPercent).setOnClickListener(v -> handleOperator("%")); // Xử lý nút %

        // Nút bằng
        findViewById(R.id.buttonEquals).setOnClickListener(v -> evaluateAndDisplayResult());

        // Nút xóa (Clear)
        findViewById(R.id.buttonClear).setOnClickListener(v -> {
            editTextExpressionInput.setText("");
            textViewResult.setText("");
            lastResult = 0.0;
            isNewCalculation = true;
        });

        // Nút xóa số (Delete/Backspace)
        findViewById(R.id.buttonDelete).setOnClickListener(v -> deleteLastCharacter());

        // Ẩn các nút không dùng đến trong layout (nếu vẫn còn trong XML)
        // findViewById(R.id.buttonX).setVisibility(View.GONE);
        // findViewById(R.id.buttonPower).setVisibility(View.GONE);
    }

    // Hàm xử lý xóa ký tự cuối cùng
    private void deleteLastCharacter() {
        String currentText = editTextExpressionInput.getText().toString();
        if (!currentText.isEmpty()) {
            // Xóa ký tự cuối cùng
            editTextExpressionInput.setText(currentText.substring(0, currentText.length() - 1));
            // Di chuyển con trỏ về cuối chuỗi mới
            editTextExpressionInput.setSelection(editTextExpressionInput.length());
        }
    }

    private void appendToExpression(String value) {
        if (isNewCalculation) {
            editTextExpressionInput.setText("");
            textViewResult.setText("");
            isNewCalculation = false;
        }
        editTextExpressionInput.append(value);
    }

    private void handleOperator(String operator) {
        String currentInput = editTextExpressionInput.getText().toString();

        if (currentInput.isEmpty() && textViewResult.getText() != null && !textViewResult.getText().toString().isEmpty()) {
            try {
                double resultValue = Double.parseDouble(textViewResult.getText().toString());
                editTextExpressionInput.setText(String.valueOf(resultValue));
                editTextExpressionInput.append(operator);
                isNewCalculation = false;
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Không thể tiếp tục từ kết quả lỗi", Toast.LENGTH_SHORT).show();
            }
        } else if (!currentInput.isEmpty()) {
            char lastChar = currentInput.charAt(currentInput.length() - 1);
            // isOperator đã được cập nhật để bao gồm '%'
            if (isOperator(lastChar)) {
                if (operator.equals("-") && (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/' || lastChar == '%')) { // Thêm '%' vào kiểm tra
                    editTextExpressionInput.append(operator);
                } else if (!operator.equals("-") && isOperator(lastChar)) { // Thêm '%' vào kiểm tra
                    editTextExpressionInput.setText(currentInput.substring(0, currentInput.length() - 1) + operator);
                    editTextExpressionInput.setSelection(editTextExpressionInput.length());
                }
            } else {
                editTextExpressionInput.append(operator);
            }
            isNewCalculation = false;
        } else {
            if (operator.equals("-")) {
                editTextExpressionInput.append(operator);
                isNewCalculation = false;
            }
        }
    }

    // isOperator đã được cập nhật để bao gồm '%'
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
    }

    private void evaluateAndDisplayResult() {
        String expression = editTextExpressionInput.getText().toString();
        if (expression.isEmpty()) {
            if (textViewResult.getText() != null && !textViewResult.getText().toString().isEmpty() && !textViewResult.getText().toString().equals("Lỗi cú pháp") && !textViewResult.getText().toString().equals("Lỗi toán học") && !textViewResult.getText().toString().equals("Lỗi")) {
                // Giữ nguyên kết quả cũ nếu input rỗng và kết quả trước đó không phải lỗi
            }
            isNewCalculation = true;
            return;
        }

        try {
            // Gọi hàm đánh giá biểu thức (đã được cập nhật để xử lý %)
            double result = evaluateExpression(expression);

            if (result == (long) result) {
                textViewResult.setText(String.valueOf((long) result));
            } else {
                textViewResult.setText(String.valueOf(result));
            }

            lastResult = result;
            editTextExpressionInput.setText("");
            isNewCalculation = true;

        } catch (IllegalArgumentException e) {
            textViewResult.setText("Lỗi cú pháp");
            Toast.makeText(this, "Biểu thức không hợp lệ: " + e.getMessage(), Toast.LENGTH_LONG).show();
            editTextExpressionInput.setText("");
            lastResult = 0.0;
            isNewCalculation = true;
        } catch (ArithmeticException e) {
            textViewResult.setText("Lỗi toán học");
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
            editTextExpressionInput.setText("");
            lastResult = 0.0;
            isNewCalculation = true;
        } catch (Exception e) {
            textViewResult.setText("Lỗi");
            Toast.makeText(this, "Đã xảy ra lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
            editTextExpressionInput.setText("");
            lastResult = 0.0;
            isNewCalculation = true;
        }
    }

    // --- PHẦN LOGIC QUAN TRỌNG: ĐÁNH GIÁ BIỂU THỨC SỐ HỌC VỚI ƯU TIÊN ---
    // Hàm này đã được cập nhật để xử lý toán tử '%'
    private double evaluateExpression(String expression) throws IllegalArgumentException, ArithmeticException {
        try {
            expression = expression.replace(" ", "");

            if (expression.startsWith("-")) {
                expression = "m" + expression.substring(1);
            }
            expression = expression.replace("*-", "*m");
            expression = expression.replace("/-", "/m");
            expression = expression.replace("+-", "+m");
            expression = expression.replace("%-", "%m"); // Xử lý dấu trừ sau %

            List<String> tokens = new ArrayList<>();
            StringBuilder currentNumber = new StringBuilder();

            for (int i = 0; i < expression.length(); i++) {
                char c = expression.charAt(i);
                if (Character.isDigit(c) || c == '.' || c == 'm') {
                    currentNumber.append(c);
                } else if (isOperator(c)) { // isOperator đã bao gồm '%'
                    if (currentNumber.length() > 0) {
                        tokens.add(currentNumber.toString().replace('m', '-'));
                        currentNumber.setLength(0);
                    }
                    tokens.add(String.valueOf(c));
                } else {
                    throw new IllegalArgumentException("Ký tự không hợp lệ trong biểu thức");
                }
            }
            if (currentNumber.length() > 0) {
                tokens.add(currentNumber.toString().replace('m', '-'));
            }

            if (!tokens.isEmpty() && (tokens.get(tokens.size()-1).length() == 1 && isOperator(tokens.get(tokens.size()-1).charAt(0)))) {
                throw new IllegalArgumentException("Biểu thức không thể kết thúc bằng toán tử");
            }

            // Bước 2: Đánh giá biểu thức theo quy tắc ưu tiên (*, /, % trước)
            for (int i = 0; i < tokens.size(); i++) {
                String token = tokens.get(i);
                if (token.equals("*") || token.equals("/") || token.equals("%")) { // Thêm '%' vào kiểm tra
                    if (i < 1 || i > tokens.size() - 2) {
                        throw new IllegalArgumentException("Cú pháp toán tử không hợp lệ");
                    }
                    double operand1 = Double.parseDouble(tokens.get(i - 1));
                    double operand2 = Double.parseDouble(tokens.get(i + 1));
                    double result = 0;

                    if (token.equals("*")) {
                        result = operand1 * operand2;
                    } else if (token.equals("/")) {
                        if (operand2 == 0) {
                            throw new ArithmeticException("Chia cho 0");
                        }
                        result = operand1 / operand2;
                    } else { // token.equals("%")
                        // Kiểm tra chia lấy dư cho 0
                        if (operand2 == 0) {
                            throw new ArithmeticException("Chia lấy dư cho 0");
                        }
                        result = operand1 % operand2;
                    }

                    tokens.remove(i - 1);
                    tokens.remove(i - 1);
                    tokens.remove(i - 1);
                    tokens.add(i - 1, String.valueOf(result));

                    i--;
                }
            }

            // Bước 3: Đánh giá biểu thức còn lại (+ và -)
            for (int i = 0; i < tokens.size(); i++) {
                String token = tokens.get(i);
                if (token.equals("+") || token.equals("-")) {
                    if (i < 1 || i > tokens.size() - 2) {
                        if (tokens.size() == 2 && tokens.get(0).equals("-") && i == 0) {
                            return -Double.parseDouble(tokens.get(1));
                        }
                        throw new IllegalArgumentException("Cú pháp toán tử không hợp lệ");
                    }
                    double operand1 = Double.parseDouble(tokens.get(i - 1));
                    double operand2 = Double.parseDouble(tokens.get(i + 1));
                    double result = 0;

                    if (token.equals("+")) {
                        result = operand1 + operand2;
                    } else { // token.equals("-")
                        result = operand1 - operand2;
                    }

                    tokens.remove(i - 1);
                    tokens.remove(i - 1);
                    tokens.remove(i - 1);
                    tokens.add(i - 1, String.valueOf(result));

                    i--;
                }
            }

            // Bước 4: Kết quả cuối cùng
            if (tokens.size() != 1) {
                throw new IllegalArgumentException("Biểu thức không thể đánh giá");
            }
            return Double.parseDouble(tokens.get(0));

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Lỗi định dạng số trong biểu thức");
        } catch (ArithmeticException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("Lỗi đánh giá biểu thức: " + e.getMessage());
        }
    }
    // --- KẾT THÚC PHẦN LOGIC ĐÁNH GIÁ ---
}