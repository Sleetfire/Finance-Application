package by.it.academy.account_service.models.dto;

import by.it.academy.account_service.models.deserializers.LocalDateTimeDeserializer;
import by.it.academy.account_service.models.serializers.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Operation {
    @JsonProperty("uuid")
    private UUID uuid;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonProperty("dt_create")
    private LocalDateTime dtCreate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonProperty("dt_update")
    private LocalDateTime dtUpdate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonProperty("date")
    private LocalDateTime date;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category")
    private UUID category;

    @JsonProperty("value")
    private BigDecimal value;

    @JsonProperty("currency")
    private UUID currency;

    @JsonIgnore
    private Account account;

    @JsonIgnore
    private String username;

    public Operation(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, LocalDateTime date, String description,
                     UUID category, BigDecimal value, UUID currency, Account account, String username) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.date = date;
        this.description = description;
        this.category = category;
        this.value = value;
        this.currency = currency;
        this.account = account;
        this.username = username;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equals(uuid, operation.uuid)
                && Objects.equals(dtCreate, operation.dtCreate)
                && Objects.equals(dtUpdate, operation.dtUpdate)
                && Objects.equals(date, operation.date)
                && Objects.equals(description, operation.description)
                && Objects.equals(category, operation.category)
                && Objects.equals(value, operation.value)
                && Objects.equals(currency, operation.currency)
                && Objects.equals(account, operation.account)
                && Objects.equals(username, operation.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, date, description, category, value, currency, account, username);
    }

    @Override
    public String toString() {
        return "Operation{" +
                "uuid=" + uuid +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", value=" + value +
                ", currency=" + currency +
                ", account=" + account +
                ", username='" + username + '\'' +
                '}';
    }

    public static class Builder {
        private UUID uuid;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;
        private LocalDateTime date;
        private String description;
        private UUID category;
        private BigDecimal value;
        private UUID currency;
        private Account account;
        private String username;

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

        public Builder setDate(LocalDateTime date) {
            this.date = date;
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

        public Builder setAccount(Account account) {
            this.account = account;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Operation build() {
            return new Operation(this.uuid, this.dtCreate, this.dtUpdate, this.date, this.description, this.category,
                    this.value, this.currency, this.account, this.username);
        }
    }
}
