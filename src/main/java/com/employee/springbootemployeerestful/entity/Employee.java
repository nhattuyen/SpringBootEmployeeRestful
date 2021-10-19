package com.employee.springbootemployeerestful.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
@Table(name = "EMPLOYEE")
public class Employee {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "id", nullable = false)
    private String id;

    @NotNull
    @Column(name = "empNo", length = 30, nullable = false)
    private String empNo;

    @NotNull
    @Column(name="fullName", length = 128, nullable = false)
    private String fullname;

    @Temporal(TemporalType.DATE)
    @Column(name = "hireDate", nullable = false)
    private Date hireDate;

    public Employee() { super(); }

    public Employee(String id, String empNo , String fullname, String hireDate) throws ParseException {
        super();
        this.setId(id);
        this.empNo=empNo;
        this.fullname=fullname;
        this.setHireDare(hireDate);
    }

    public Employee(String id, String empNo , String fullname, Date hireDate) {
        super();
        this.id=id;
        this.empNo=empNo;
        this.fullname=fullname;
        this.hireDate=hireDate;
    }

    public void setHireDare(String hireDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        Date date = dateFormat.parse(hireDate);
        this.hireDate = date;
    };
    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Date getHireDate() {
        return hireDate;
    }
}
