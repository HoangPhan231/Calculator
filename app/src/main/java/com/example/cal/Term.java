package com.example.cal;

import java.util.Locale;
import java.util.Objects;

// Lớp biểu diễn một số hạng của đa thức (ví dụ: 3x^2)
public class Term {
    private double coefficient; // Hệ số (ví dụ: 3.0)
    private int power;        // Bậc (ví dụ: 2)

    public Term(double coefficient, int power) {
        this.coefficient = coefficient;
        this.power = power;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public int getPower() {
        return power;
    }

    // Cộng hệ số với một giá trị khác
    public void addCoefficient(double value) {
        this.coefficient += value;
    }

    // Phương thức để so sánh hai Term (dựa trên bậc)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Term term = (Term) o;
        return power == term.power; // So sánh bằng nếu cùng bậc
    }

    @Override
    public int hashCode() {
        return Objects.hash(power);
    }

    // Biểu diễn số hạng dưới dạng chuỗi (ví dụ: "3.0x^2", "5.0", "-1.0x")
    @Override
    public String toString() {
        if (coefficient == 0) {
            return ""; // Không hiển thị số hạng có hệ số 0
        }

        StringBuilder sb = new StringBuilder();

        // Xử lý dấu của hệ số
        if (coefficient > 0) {
            sb.append("+");
        }
        // Nếu là số hạng hằng hoặc bậc 1, không hiển thị hệ số 1 hoặc -1 trừ khi power = 0
        if (Math.abs(coefficient) != 1 || power == 0) {
            sb.append(String.format(Locale.US, "%.2f", coefficient)); // Định dạng 2 chữ số thập phân
        } else if (coefficient == -1 && power != 0) {
            sb.append("-"); // Hiển thị "-" cho hệ số -1 trừ khi power = 0
        }


        if (power > 0) {
            sb.append("x");
            if (power > 1) {
                sb.append("^").append(power);
            }
        }

        return sb.toString();
    }
}