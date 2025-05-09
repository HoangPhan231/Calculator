package com.example.cal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Lớp biểu diễn một đa thức (danh sách các số hạng)
public class Polynomial {
    private List<Term> terms;

    public Polynomial() {
        terms = new ArrayList<>();
    }

    public List<Term> getTerms() {
        return terms;
    }

    // Thêm một số hạng vào đa thức
    public void addTerm(double coefficient, int power) {
        if (coefficient == 0) return; // Bỏ qua số hạng có hệ số 0

        // Kiểm tra xem đã tồn tại số hạng cùng bậc chưa
        for (Term term : terms) {
            if (term.getPower() == power) {
                term.addCoefficient(coefficient); // Nếu có, cộng hệ số
                // Sau khi cộng, kiểm tra lại nếu hệ số mới là 0 thì xóa số hạng này
                if (term.getCoefficient() == 0) {
                    terms.remove(term);
                }
                normalize(); // Chuẩn hóa lại sau khi thay đổi
                return;
            }
        }

        // Nếu chưa tồn tại, thêm số hạng mới
        terms.add(new Term(coefficient, power));
        normalize(); // Chuẩn hóa lại sau khi thêm
    }

    // Chuẩn hóa đa thức: gộp các số hạng cùng bậc và sắp xếp theo bậc giảm dần
    private void normalize() {
        // Sắp xếp theo bậc giảm dần
        Collections.sort(terms, Comparator.comparingInt(Term::getPower).reversed());

        // Gộp các số hạng cùng bậc (lý thuyết addTerm đã xử lý phần lớn, nhưng phòng hờ)
        // TODO: Cần logic gộp các số hạng cùng bậc nếu addTerm không cover hết edge cases

        // Loại bỏ các số hạng có hệ số gần bằng 0 (do phép tính thập phân)
        terms.removeIf(term -> Math.abs(term.getCoefficient()) < 1e-9); // Sử dụng ngưỡng nhỏ
    }


    // --- Các phép toán ---

    // Cộng hai đa thức
    public Polynomial add(Polynomial other) {
        Polynomial result = new Polynomial();
        // Thêm tất cả các số hạng từ đa thức hiện tại
        for (Term term : this.terms) {
            result.addTerm(term.getCoefficient(), term.getPower());
        }
        // Thêm tất cả các số hạng từ đa thức khác (addTerm sẽ tự gộp)
        for (Term term : other.terms) {
            result.addTerm(term.getCoefficient(), term.getPower());
        }
        result.normalize();
        return result;
    }

    // Trừ hai đa thức
    public Polynomial subtract(Polynomial other) {
        Polynomial result = new Polynomial();
        // Thêm tất cả các số hạng từ đa thức hiện tại
        for (Term term : this.terms) {
            result.addTerm(term.getCoefficient(), term.getPower());
        }
        // Trừ tất cả các số hạng từ đa thức khác (nhân hệ số với -1 rồi thêm)
        for (Term term : other.terms) {
            result.addTerm(-term.getCoefficient(), term.getPower());
        }
        result.normalize();
        return result;
    }

    // Nhân hai đa thức
    public Polynomial multiply(Polynomial other) {
        Polynomial result = new Polynomial();
        // Mỗi số hạng của đa thức thứ nhất nhân với mỗi số hạng của đa thức thứ hai
        for (Term term1 : this.terms) {
            for (Term term2 : other.terms) {
                double newCoefficient = term1.getCoefficient() * term2.getCoefficient();
                int newPower = term1.getPower() + term2.getPower();
                result.addTerm(newCoefficient, newPower); // addTerm sẽ tự gộp
            }
        }
        result.normalize();
        return result;
    }

    // Chia hai đa thức (Đây là phần rất phức tạp - Cần triển khai thuật toán chia đa thức)
    public Polynomial divide(Polynomial other) throws ArithmeticException {
        // TODO: Triển khai thuật toán chia đa thức (polynomial long division)
        // Cần xử lý trường hợp chia cho đa thức 0
        if (other.isZero()) {
            throw new ArithmeticException("Không thể chia cho đa thức 0");
        }
        // Cần xử lý trường hợp chia cho đa thức bậc cao hơn
        if (!this.isZero() && !other.isZero() && this.getDegree() < other.getDegree()) {
            // Kết quả là 0 với phần dư là chính nó
            // Tùy yêu cầu, bạn có thể trả về 0 hoặc ném ngoại lệ
            // Đối với máy tính đơn giản, có thể chỉ cần xử lý các trường hợp chia hết
            // hoặc hiển thị kết quả dưới dạng phân số đa thức.
            // Ví dụ đơn giản: Nếu bậc nhỏ hơn thì kết quả thương là 0
            return new Polynomial(); // Trả về đa thức 0
        }
        // TODO: Triển khai logic chia phức tạp ở đây...
        // Đây là một thuật toán lặp đi lặp lại
        // Ví dụ: (x^2 + 2x + 1) / (x + 1) = x + 1
        // Đây là phần code cần đầu tư thời gian để triển khai đúng.
        // Tạm thời trả về một đa thức rỗng hoặc ném ngoại lệ
        throw new UnsupportedOperationException("Phép chia đa thức chưa được triển khai đầy đủ");
    }

    // Kiểm tra xem đa thức có phải là 0 không
    public boolean isZero() {
        return terms.isEmpty() || (terms.size() == 1 && terms.get(0).getCoefficient() == 0);
    }

    // Lấy bậc cao nhất của đa thức
    public int getDegree() {
        if (terms.isEmpty()) return -1; // Bậc của đa thức 0 không xác định, thường coi là -1
        // Đã normalize nên số hạng đầu tiên có bậc cao nhất
        return terms.get(0).getPower();
    }


    // Biểu diễn đa thức dưới dạng chuỗi
    @Override
    public String toString() {
        if (terms.isEmpty()) {
            return "0"; // Đa thức rỗng là 0
        }

        StringBuilder sb = new StringBuilder();
        for (Term term : terms) {
            String termStr = term.toString();
            if (!termStr.isEmpty()) {
                sb.append(termStr);
            }
        }

        // Loại bỏ dấu '+' ở đầu nếu có
        if (sb.length() > 0 && sb.charAt(0) == '+') {
            sb.deleteCharAt(0);
        }
        if (sb.length() == 0) return "0"; // Trường hợp chỉ có các số hạng hệ số 0 bị loại bỏ

        return sb.toString().trim(); // trim để loại bỏ khoảng trắng thừa
    }
}
