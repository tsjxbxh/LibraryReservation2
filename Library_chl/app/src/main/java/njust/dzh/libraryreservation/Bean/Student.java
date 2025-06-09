package njust.dzh.libraryreservation.Bean;

public class Student {
    // 学号
    private String account;
    // 姓名
    private String name;
    // 年龄
    private String age;
    // 电话
    private String phone;
    // 学院
    private String college;

    public Student(String account, String name, String age, String phone, String college) {
        this.account = account;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.college = college;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
