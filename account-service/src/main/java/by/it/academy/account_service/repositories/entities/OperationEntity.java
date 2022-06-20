package by.it.academy.account_service.repositories.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "operation", schema = "account_service")
public class OperationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Version
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;
    private LocalDateTime date;
    private String description;
    private UUID category;
    private BigDecimal value;
    private UUID currency;
    @ManyToOne
    @JoinColumn(nullable = false, name = "account_uuid")
    private AccountEntity accountEntity;
    private String username;

    public OperationEntity(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, LocalDateTime date, String description,
                           UUID category, BigDecimal value, UUID currency, AccountEntity accountEntity, String username) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.date = date;
        this.description = description;
        this.category = category;
        this.value = value;
        this.currency = currency;
        this.accountEntity = accountEntity;
        this.username = username;
    }

    public OperationEntity() {
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

    public AccountEntity getAccountEntity() {
        return accountEntity;
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
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
        OperationEntity that = (OperationEntity) o;
        return Objects.equals(uuid, that.uuid)
                && Objects.equals(dtCreate, that.dtCreate)
                && Objects.equals(dtUpdate, that.dtUpdate)
                && Objects.equals(date, that.date)
                && Objects.equals(description, that.description)
                && Objects.equals(category, that.category)
                && Objects.equals(value, that.value)
                && Objects.equals(currency, that.currency)
                && Objects.equals(accountEntity, that.accountEntity)
                && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, date, description, category, value, currency, accountEntity, username);
    }

    @Override
    public String toString() {
        return "OperationEntity{" +
                "uuid=" + uuid +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", value=" + value +
                ", currency=" + currency +
                ", accountEntity=" + accountEntity +
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
        private AccountEntity accountEntity;
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

        public Builder setAccountEntity(AccountEntity accountEntity) {
            this.accountEntity = accountEntity;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public OperationEntity build() {
            return new OperationEntity(this.uuid, this.dtCreate, this.dtUpdate, this.date, this.description, this.category,
                    this.value, this.currency, accountEntity, username);
        }
    }
}
