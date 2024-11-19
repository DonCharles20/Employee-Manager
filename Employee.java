
//package finalproject;

public class Employee {
    private int id;
    private String name;
    private String address;
    private double hours;
    private double rate;
    private char sex;
    private int age;
    private boolean active;
    private String ssn;
    private String phone; // Add this field STEP 1
    // add the following 2 fields !!! Do them 1 at a time!!!!
    private String city;
    private double gpa;

    // HERE IS SOME EXTRA CREDIT !!!!!!
    private char gender;
    private int dependents;
    private boolean graduated;
    // also, add accelerators to the buttons NEW SAVE View EDIT Delete
    // center the menu in the window!!!

    public boolean isGraduated() {
        return graduated;
    }

    public void setGraduated(boolean graduated) {
        this.graduated = graduated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getSSN() {
        return ssn;
    }

    public void setSSN(String ssn) {
        this.ssn = ssn;
    }

    public String getPhone() { // STEP 2
        return phone;
    }

    public void setPhone(String phone) { // Step 2
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getGPA() {
        return gpa;
    }

    public void setGPA(double gpa) {
        this.gpa = gpa;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getDependents() {
        return dependents;
    }

    public void setDependents(int dependents) {
        this.dependents = dependents;
    }

    // =========================================================================================

    double calculateGrossPay() {
        return (hours * rate);
    }

    double calculateFederalTax() {
        double yearlyIncome = calculateGrossPay() * 52;
        double taxRate;

        if (yearlyIncome < 30000.00)
            taxRate = .28;
        else if (yearlyIncome < 50000.00)
            taxRate = .32;
        else
            taxRate = .38;

        return (calculateGrossPay() * taxRate);
    }

    double calculateStateTax() {
        return (calculateGrossPay() * .0561);
    }

    double calculateNetPay() {
        return (calculateGrossPay() - calculateFederalTax() - calculateStateTax());
    }

    // Note toString returns name only because it is used by the JList widget to
    // populate
    // the users in the pick list.
    public String toString() {
        return name;
    }
}
