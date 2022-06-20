package by.it.academy.mail_service.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ReportEmail {

    @JsonProperty("file_url")
    private String fileUrl;
    @JsonProperty("email")
    private String email;
    @JsonProperty("description")
    private String description;

    public ReportEmail(String fileUrl, String email, String description) {
        this.fileUrl = fileUrl;
        this.email = email;
        this.description = description;
    }

    public ReportEmail() {
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportEmail that = (ReportEmail) o;
        return Objects.equals(fileUrl, that.fileUrl)
                && Objects.equals(email, that.email)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileUrl, email, description);
    }

    @Override
    public String toString() {
        return "ReportEmail{" +
                "fileUrl='" + fileUrl + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public static class Builder {
        private String fileUrl;
        private String email;
        private String description;

        private Builder() {
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public Builder setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public ReportEmail build() {
            return new ReportEmail(this.fileUrl, this.email, this.description);
        }
    }
}
