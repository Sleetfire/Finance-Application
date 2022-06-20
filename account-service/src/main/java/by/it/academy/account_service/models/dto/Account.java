package by.it.academy.account_service.models.dto;

import by.it.academy.account_service.models.deserializers.LocalDateTimeDeserializer;
import by.it.academy.account_service.models.enums.AccountType;
import by.it.academy.account_service.models.serializers.BalanceSerializer;
import by.it.academy.account_service.models.serializers.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Account {
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

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonSerialize(using = BalanceSerializer.class)
    @JsonProperty("balance")
    private Balance balance;

    @JsonProperty("type")
    private AccountType type;

    @JsonProperty("currency")
    private UUID currency;

    @JsonIgnore
    private String username;

    public Account(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, String title, String description,
                   Balance balance, AccountType type, UUID currency, String username) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.title = title;
        this.description = description;
        this.balance = balance;
        this.type = type;
        this.currency = currency;
        this.username = username;
    }

    public Account() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public UUID getCurrency() {
        return currency;
    }

    public void setCurrency(UUID currency) {
        this.currency = currency;
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
        Account account = (Account) o;
        return Objects.equals(uuid, account.uuid)
                && Objects.equals(dtCreate, account.dtCreate)
                && Objects.equals(dtUpdate, account.dtUpdate)
                && Objects.equals(title, account.title)
                && Objects.equals(description, account.description)
                && Objects.equals(balance, account.balance)
                && type == account.type
                && Objects.equals(currency, account.currency)
                && Objects.equals(username, account.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, title, description, balance, type, currency, username);
    }

    @Override
    public String toString() {
        return "Account{" +
                "uuid=" + uuid +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", balance=" + balance +
                ", type=" + type +
                ", currency=" + currency +
                ", username='" + username + '\'' +
                '}';
    }

    public static class Builder {
        private UUID uuid;
        private LocalDateTime dtCreate;
        private LocalDateTime dtUpdate;
        private String title;
        private String description;
        private Balance balance;
        private AccountType type;
        private UUID currency;
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

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setBalance(Balance balance) {
            this.balance = balance;
            return this;
        }

        public Builder setType(AccountType type) {
            this.type = type;
            return this;
        }

        public Builder setCurrency(UUID currency) {
            this.currency = currency;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Account build() {
            return new Account(this.uuid, this.dtCreate, this.dtUpdate, this.title, this.description, this.balance,
                    this.type, this.currency, username);
        }
    }
}
