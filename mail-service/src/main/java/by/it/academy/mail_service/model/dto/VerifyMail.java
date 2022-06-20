package by.it.academy.mail_service.model.dto;

import java.util.Objects;

public class VerifyMail {

    private String email;
    private String name;
    private String link;

    public VerifyMail(String email, String name, String link) {
        this.email = email;
        this.name = name;
        this.link = link;
    }

    public VerifyMail() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerifyMail that = (VerifyMail) o;
        return Objects.equals(email, that.email)
                && Objects.equals(name, that.name)
                && Objects.equals(link, that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, link);
    }

    @Override
    public String toString() {
        return "VerifyMessage{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
