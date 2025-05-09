# Máy tính Bỏ Túi Thông Minh - Android

Đây là một ứng dụng máy tính bỏ túi đơn giản được phát triển cho nền tảng Android bằng ngôn ngữ Java. Ứng dụng cho phép người dùng thực hiện các phép tính số học cơ bản một cách dễ dàng và nhanh chóng.

## 🎥 Video Demo

Xem video demo hoạt động của ứng dụng tại đây:

https://github.com/user-attachments/assets/bb963bc6-10b2-49fd-8145-f37919e3cca1

*(Lưu ý: GitHub sẽ tự động hiển thị video nếu bạn dán link này trực tiếp khi chỉnh sửa file README.md trên giao diện web của GitHub.)*

## ✨ Tính năng nổi bật

* Thực hiện các phép tính cơ bản: cộng (`+`), trừ (`-`), nhân (`*`), chia (`/`).
* Tính toán phần trăm (`%`).
* Ưu tiên thực hiện các phép toán (nhân, chia, phần trăm sẽ được ưu tiên trước cộng và trừ).
* Hiển thị rõ ràng biểu thức đang được nhập và kết quả tính toán.
* Nút xóa toàn bộ biểu thức (`C`).
* Nút xóa ký tự cuối cùng (`DEL`) để dễ dàng sửa lỗi khi nhập.
* Giao diện người dùng trực quan và dễ sử dụng.
* **[Đang phát triển]** Các chức năng liên quan đến tính toán đa thức:
    * Cộng đa thức.
    * Trừ đa thức.
    * Nhân đa thức.
    
## 🛠️ Công nghệ sử dụng

* **Ngôn ngữ lập trình:** Java
* **Nền tảng:** Android SDK
* **Giao diện người dùng (UI):** XML
* **Công cụ xây dựng (Build Tool):** Gradle

## 🚀 Cài đặt và Chạy thử

Để cài đặt và chạy thử dự án này trên máy của bạn, hãy làm theo các bước sau:

1.  **Clone repository:**
    ```bash
    git clone [URL-cua-repository-cua-ban]
    ```
2.  **Mở project:** Mở project bằng Android Studio.
3.  **Build và Chạy:**
    * Đồng bộ hóa Gradle (Sync Project with Gradle Files).
    * Chọn thiết bị (máy ảo hoặc thiết bị Android thật).
    * Nhấn nút "Run" (▶️) để build và cài đặt ứng dụng.

## 📖 Hướng dẫn sử dụng

1.  Mở ứng dụng "Cal" (hoặc tên ứng dụng bạn đặt) trên thiết bị Android.
2.  Sử dụng các nút số để nhập các toán hạng.
3.  Chọn các nút phép toán (`+`, `-`, `*`, `/`, `%`) để thực hiện tính toán.
4.  Biểu thức bạn nhập sẽ được hiển thị ở phần trên của màn hình.
5.  Nhấn nút `=` để xem kết quả của phép tính. Kết quả sẽ hiển thị ở dòng dưới.
6.  Sử dụng nút `C` để xóa toàn bộ biểu thức và kết quả hiện tại, bắt đầu một phép tính mới.
7.  Sử dụng nút `DEL` để xóa ký tự cuối cùng trong biểu thức đang nhập.

## 🧪 Kiểm thử

Dự án có bao gồm các bài kiểm thử đơn vị (Unit Tests) cho logic tính toán cơ bản và các hàm liên quan đến đa thức. Ngoài ra, còn có các bài kiểm thử giao diện người dùng (UI Tests) sử dụng Espresso để đảm bảo trải nghiệm người dùng.

* **Unit Tests:** `app/src/test/java/com/example/cal/`
* **UI Tests (Instrumented Tests):** `app/src/androidTest/java/com/example/cal/`

---
