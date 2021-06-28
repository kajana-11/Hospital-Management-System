package sample;

import java.sql.Date;
import java.sql.Time;

public class Visitor {

    private String purpose;
    private String name;
    private int phone;
    private int id;
    private Date date;
    private Time intime;
    private Time outime;
    private String note;
    private Byte[] attacheditems;

    public Visitor(String purpose, String name, int phone, int id) {
        this.purpose = purpose;
        this.name = name;
        this.phone = phone;
        this.id = id;
    }

    public Visitor(String purpose, String name, int phone, int id, Date date, Time intime, Time outime, String note, Byte[] attacheditems) {
        this.purpose = purpose;
        this.name = name;
        this.phone = phone;
        this.id = id;
        this.date = date;
        this.intime = intime;
        this.outime = outime;
        this.note = note;
        this.attacheditems = attacheditems;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getIntime() {
        return intime;
    }

    public void setIntime(Time intime) {
        this.intime = intime;
    }

    public Time getOutime() {
        return outime;
    }

    public void setOutime(Time outime) {
        this.outime = outime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Byte[] getAttacheditems() {
        return attacheditems;
    }

    public void setAttacheditems(Byte[] attacheditems) {
        this.attacheditems = attacheditems;
    }
}
