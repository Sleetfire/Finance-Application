package by.it.academy.account_scheduler_service.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Operation {

    @JsonIgnore
    private UUID uuid;
    @JsonIgnore
    private LocalDateTime dtCreate;
    @JsonIgnore
    private LocalDateTime dtUpdate;
    @JsonProperty("account")
    private UUID account;
    @JsonProperty("description")
    private String description;
    @JsonProperty("category")
    private UUID category;
    @JsonProperty("value")
    private BigDecimal value;
    @JsonProperty("currency")
    private UUID currency;

    public Operation(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, UUID account, String description,
                     UUID category, BigDecimal value, UUID currency) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.account = account;
        this.description = description;
        this.category = category;
        this.value = value;
        this.currency = currency;
    }

    public Operation() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public UUID getAccount() {
        return account;
    }

    public void setAccount(UUID account) {
        this.account = account;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getCategory() {
        return category;
    }

    public void setCategory(UUID category) {
        this.category = category;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public UUID getCurrency() {
        return currency;
    }

    public void setCurrency(UUID currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equals(uuid, operation.uuid)
                && Objects.equals(dtCreate, operation.dtCreate)
                && Objects.equals(dtUpdate, operation.dtUpdate)
                && Objects.equals(account, operation.account)
                && Objects.equals(description, operation.description)
                && Objects.equals(category, operation.category)
                && Objects.equals(value, operation.value)
                && Objects.equals(currency, operation.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, account, description, category, value, currency);
    }

    @Override
    public String toString() {
        return "Operation{" +
                "uuid=" + uuid +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                ", account=" + account +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", value=" + value +
                ", currency=" + currency +
                '}';
    }

    public static class Builder {
        private UUID uuid;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;
        private UUID account;
        private String description;
        private UUID category;
        private BigDecimal value;
        private UUID currency;

        private Builder() {
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public Builder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setDtCreate(LocalDateTime dtCreate) {
            this.dtCreate = dtCreate;
            return this;
        }

        public Builder setDtUpdate(LocalDateTime dtUpdate) {
            this.dtUpdate = dtUpdate;
            return this;
        }

        public Builder setAccount(UUID account) {
            this.account = account;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setCategory(UUID category) {
            this.category = category;
            return this;
        }

        public Builder setValue(BigDecimal value) {
            this.value = value;
            return this;
        }

        public Builder setCurrency(UUID currency) {
            this.currency = currency;
            return this;
        }

        public Operation build() {
            return new Operation(this.uuid, this.dtCreate, this.dtUpdate, this.account, this.description, this.category,
                    this.value, this.currency);
        }
    }
}
