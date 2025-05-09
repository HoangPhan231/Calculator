package com.example.cal;

// Lớp xử lý phân tích cú pháp chuỗi biểu thức thành đối tượng Polynomial
public class PolynomialParser {

    // Phương thức phân tích cú pháp (CẦN TRIỂN KHAI LOGIC PHỨC TẠP TẠI ĐÂY)
    public static Polynomial parse(String expression) throws IllegalArgumentException {
        // TODO: Triển khai logic phân tích cú pháp chuỗi biểu thức
        // Ví dụ các trường hợp cần xử lý:
        // "2x^2 + 3x - 5"
        // "-x^3 + 10"
        // "5.5"
        // "x"
        // "-x"
        // "4x^0"
        // "3(x+1)^2" (Nếu hỗ trợ dấu ngoặc và lũy thừa biểu thức) <-- Rất phức tạp!
        //
        // Quy trình cơ bản có thể bao gồm:
        // 1. Xóa bỏ khoảng trắng thừa.
        // 2. Chuẩn hóa chuỗi (ví dụ: thêm "+" trước số hạng đầu nếu không có dấu, thay "x" bằng "1x", "x^n" bằng "1x^n", "-x" bằng "-1x").
        // 3. Tách chuỗi thành các số hạng dựa trên dấu "+" hoặc "-".
        // 4. Với mỗi số hạng, phân tích hệ số và bậc.
        // 5. Tạo đối tượng Term và thêm vào Polynomial.
        //
        // Đây là một phần việc phức tạp, dễ gặp lỗi với các trường hợp nhập liệu khác nhau.
        // Cần xử lý ngoại lệ nếu chuỗi không hợp lệ.

        // Do độ phức tạp, tạm thời ném ngoại lệ hoặc trả về đa thức rỗng
        // hoặc chỉ xử lý các trường hợp rất đơn giản.
        // Ví dụ đơn giản KHÔNG ĐẦY ĐỦ: thử xử lý chuỗi dạng "ax^b" hoặc "a" hoặc "x^b"
        try {
            expression = expression.trim().toLowerCase(); // Xóa khoảng trắng và chuyển về chữ thường

            if (expression.isEmpty()) {
                return new Polynomial(); // Đa thức 0
            }

            // Xử lý trường hợp chỉ có số (hằng số)
            if (expression.matches("-?\\d*\\.?\\d+")) {
                try {
                    double coef = Double.parseDouble(expression);
                    Polynomial p = new Polynomial();
                    p.addTerm(coef, 0);
                    return p;
                } catch (NumberFormatException e) {
                    // Should not happen if regex matches, but good practice
                    throw new IllegalArgumentException("Lỗi phân tích số hạng hằng");
                }
            }

            // Xử lý trường hợp chỉ có 'x' hoặc '-x'
            if (expression.equals("x")) {
                Polynomial p = new Polynomial();
                p.addTerm(1.0, 1);
                return p;
            }
            if (expression.equals("-x")) {
                Polynomial p = new Polynomial();
                p.addTerm(-1.0, 1);
                return p;
            }

            // TODO: Thêm logic xử lý các dạng phức tạp hơn như "ax^b", "ax", "x^b", "ax^b + cx^d - e", v.v.
            // Bạn sẽ cần một state machine hoặc recursive descent parser cho biểu thức đầy đủ.

            // Nếu không thể phân tích được với logic hiện tại
            throw new IllegalArgumentException("Biểu thức không hợp lệ hoặc quá phức tạp để phân tích");

        } catch (Exception e) {
            // Bắt các ngoại lệ khác trong quá trình parsing
            throw new IllegalArgumentException("Lỗi phân tích cú pháp: " + e.getMessage());
        }
    }
}