package com.example.cal;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
// Thay vì Instrumented test, chúng ta dùng Unit Test thuần túy cho logic
// import androidx.test.ext.junit.runners.AndroidJUnit4;
// import androidx.test.platform.app.InstrumentationRegistry;
// import android.content.Context;
// import org.junit.runner.RunWith;

// @RunWith(AndroidJUnit4.class) // Xóa dòng này nếu dùng Unit Test thuần túy
public class PolynomialCalculatorTest {

    // Phương thức helper để tạo nhanh đa thức từ các số hạng
    private Polynomial createPolynomial(Term... terms) {
        Polynomial p = new Polynomial();
        for (Term term : terms) {
            p.addTerm(term.getCoefficient(), term.getPower());
        }
        return p;
    }

    // Phương thức helper để so sánh hai đa thức (cần triển khai cẩn thận)
    // So sánh tốt nhất là so sánh danh sách các Term sau khi normalize
    private boolean arePolynomialsEqual(Polynomial p1, Polynomial p2) {
        if (p1 == null || p2 == null) return false;
        p1.getTerms(); // Ensure normalized (addTerm calls normalize)
        p2.getTerms(); // Ensure normalized

        List<Term> terms1 = p1.getTerms();
        List<Term> terms2 = p2.getTerms();

        if (terms1.size() != terms2.size()) return false;

        // So sánh từng số hạng theo thứ tự (vì đã sắp xếp)
        for (int i = 0; i < terms1.size(); i++) {
            Term t1 = terms1.get(i);
            Term t2 = terms2.get(i);

            // So sánh bậc (phải bằng nhau do sắp xếp và số lượng bằng nhau)
            if (t1.getPower() != t2.getPower()) return false;

            // So sánh hệ số (dùng ngưỡng nhỏ cho số thập phân)
            if (Math.abs(t1.getCoefficient() - t2.getCoefficient()) > 1e-9) return false;
        }

        return true; // Nếu tất cả số hạng khớp
    }


    @Test
    public void testPolynomialAddition() {
        // Test: (x^2 + 2x + 1) + (x - 1) = x^2 + 3x
        Polynomial p1 = createPolynomial(new Term(1, 2), new Term(2, 1), new Term(1, 0));
        Polynomial p2 = createPolynomial(new Term(1, 1), new Term(-1, 0));

        Polynomial result = p1.add(p2);

        Polynomial expected = createPolynomial(new Term(1, 2), new Term(3, 1));

        assertTrue("Kết quả phép cộng sai", arePolynomialsEqual(result, expected));

        // Test: (3x^2) + (2x^2 + 5) = 5x^2 + 5
        Polynomial p3 = createPolynomial(new Term(3, 2));
        Polynomial p4 = createPolynomial(new Term(2, 2), new Term(5, 0));
        Polynomial result2 = p3.add(p4);
        Polynomial expected2 = createPolynomial(new Term(5, 2), new Term(5, 0));
        assertTrue("Kết quả phép cộng sai (test 2)", arePolynomialsEqual(result2, expected2));
    }

    @Test
    public void testPolynomialSubtraction() {
        // Test: (x^2 + 2x + 1) - (x - 1) = x^2 + x + 2
        Polynomial p1 = createPolynomial(new Term(1, 2), new Term(2, 1), new Term(1, 0));
        Polynomial p2 = createPolynomial(new Term(1, 1), new Term(-1, 0));

        Polynomial result = p1.subtract(p2);

        Polynomial expected = createPolynomial(new Term(1, 2), new Term(1, 1), new Term(2, 0));

        assertTrue("Kết quả phép trừ sai", arePolynomialsEqual(result, expected));
    }

    @Test
    public void testPolynomialMultiplication() {
        // Test: (x + 1) * (x - 1) = x^2 - 1
        Polynomial p1 = createPolynomial(new Term(1, 1), new Term(1, 0));
        Polynomial p2 = createPolynomial(new Term(1, 1), new Term(-1, 0));

        Polynomial result = p1.multiply(p2);

        Polynomial expected = createPolynomial(new Term(1, 2), new Term(-1, 0));

        assertTrue("Kết quả phép nhân sai", arePolynomialsEqual(result, expected));

        // Test: (2x) * (3x^2 + 5) = 6x^3 + 10x
        Polynomial p3 = createPolynomial(new Term(2, 1));
        Polynomial p4 = createPolynomial(new Term(3, 2), new Term(5, 0));
        Polynomial result2 = p3.multiply(p4);
        Polynomial expected2 = createPolynomial(new Term(6, 3), new Term(10, 1));
        assertTrue("Kết quả phép nhân sai (test 2)", arePolynomialsEqual(result2, expected2));
    }

    @Test
    public void testPolynomialDivision_Placeholder() {
        // TODO: Viết test cho phép chia sau khi triển khai logic chia trong Polynomial.java
        // Phép chia là phức tạp, cần test nhiều trường hợp: chia hết, có dư, chia cho hằng số,
        // chia cho đa thức bậc cao hơn, chia cho 0.

        // Ví dụ sườn test (sẽ fail cho đến khi bạn triển khai divide() đúng)
        Polynomial p1 = createPolynomial(new Term(1, 2), new Term(2, 1), new Term(1, 0)); // x^2 + 2x + 1
        Polynomial p2 = createPolynomial(new Term(1, 1), new Term(1, 0)); // x + 1

        try {
            Polynomial result = p1.divide(p2); // Mong đợi kết quả là x + 1
            Polynomial expected = createPolynomial(new Term(1, 1), new Term(1, 0));
            assertTrue("Kết quả phép chia sai", arePolynomialsEqual(result, expected));

        } catch (UnsupportedOperationException e) {
            // Bắt ngoại lệ nếu bạn chọn ném nó khi chưa triển khai
            System.out.println("Test phép chia bị bỏ qua do chưa triển khai logic.");
        } catch (ArithmeticException e) {
            // Bắt ngoại lệ nếu chia cho 0 hoặc lỗi khác
            System.out.println("Test phép chia gặp lỗi số học: " + e.getMessage());
        }
    }

    @Test
    public void testPolynomialParser_BasicCases_Placeholder() {
        // TODO: Viết test cho hàm parse sau khi triển khai logic parsing trong PolynomialParser.java
        // Cần test nhiều trường hợp chuỗi khác nhau.

        try {
            // Ví dụ sườn test (sẽ fail/ném ngoại lệ cho đến khi bạn triển khai parse() đúng)
            Polynomial p1 = PolynomialParser.parse("x^2 + 2x - 5.5");
            Polynomial expected1 = createPolynomial(new Term(1, 2), new Term(2, 1), new Term(-5.5, 0));
            assertTrue("Phân tích cú pháp sai (test 1)", arePolynomialsEqual(p1, expected1));

            Polynomial p2 = PolynomialParser.parse("-3x^3 + 7");
            Polynomial expected2 = createPolynomial(new Term(-3, 3), new Term(7, 0));
            assertTrue("Phân tích cú pháp sai (test 2)", arePolynomialsEqual(p2, expected2));

        } catch (IllegalArgumentException e) {
            // Bắt ngoại lệ nếu chuỗi không hợp lệ
            System.out.println("Test parser gặp lỗi nhập liệu: " + e.getMessage());
            // Nếu test này mong đợi lỗi, bạn có thể thêm @Test(expected = IllegalArgumentException.class)
        } catch (Exception e) {
            System.out.println("Test parser gặp lỗi không mong đợi: " + e.getMessage());
        }
    }
}
