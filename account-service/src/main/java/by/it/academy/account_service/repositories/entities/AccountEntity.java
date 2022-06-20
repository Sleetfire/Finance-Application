package by.it.academy.account_service.repositories.entities;

import by.it.academy.account_service.models.enums.AccountType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "account", schema = "account_service")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @Version
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;
    private String title;
    private String description;

    @OneToOne
    @JoinColumn(name = "balance_uuid")
    private BalanceEntity balanceEntity;

    @Enumerated(EnumType.STRING)
    private AccountType type;
    private UUID currency;
    private String username;

    public AccountEntity(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, String title, String description,
                         BalanceEntity balanceEntity, AccountType type, UUID currency, String username) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.title = title;
        this.description = description;
        this.balanceEntity = balanceEntity;
        this.type = type;
        this.currency = currency;
        this.username = username;
    }

    public AccountEntity() {
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

    public BalanceEntity getBalanceEntity() {
        return balanceEntity;
    }

    public void setBalanceEntity(BalanceEntity balanceEntity) {
        this.balanceEntity = balanceEntity;
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
        AccountEntity that = (AccountEntity) o;
        return Objects.equals(uuid, that.uuid)
                && Objects.equals(dtCreate, that.dtCreate)
                && Objects.equals(dtUpdate, that.dtUpdate)
                && Objects.equals(title, that.title)
                && Objects.equals(description, that.description)
                && Objects.equals(balanceEntity, that.balanceEntity)
                && type == that.type
                && Objects.equals(currency, that.currency)
                && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, title, description, balanceEntity, type, currency, username);
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "uuid=" + uuid +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", balanceEntity=" + balanceEntity +
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
        private BalanceEntity balanceEntity;
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

        public Builder setBalanceEntity(BalanceEntity balanceEntity) {
            this.balanceEntity = balanceEntity;
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

        public AccountEntity build() {
            return new AccountEntity(this.uuid, this.dtCreate, this.dtUpdate, this.title, this.description,
                    this.balanceEntity, this.type, this.currency, username);
        }
    }
}
