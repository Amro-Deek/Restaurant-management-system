package application;

public class Employee {
	// --> Amro Deek <-- 
    private int empId;
    private int branchId;
    private String contactInfo;
    private String position;
    private String name;
    private int salary;

    // Constructor
    public Employee(int empId, int branchId, String contactInfo, String position, String name, int salary) {
        this.empId = empId;
        this.branchId = branchId;
        this.contactInfo = contactInfo;
        this.position = position;
        this.name = name;
        this.salary = salary;
    }

    // Default constructor
    public Employee() {
    }

    // Getters and Setters
    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", branchId=" + branchId +
                ", contactInfo='" + contactInfo + '\'' +
                ", position='" + position + '\'' +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}
