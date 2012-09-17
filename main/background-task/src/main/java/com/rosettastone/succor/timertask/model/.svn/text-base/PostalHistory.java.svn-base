package com.rosettastone.succor.timertask.model;

import java.util.Date;

import javax.persistence.*;

import com.rosettastone.succor.model.parature.ContactReasonType;
import com.rosettastone.succor.model.parature.TicketLanguageType;

/**
 *
 */
@Entity
@Table(name = "postal_history")
public class PostalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PostalHistoryStatus status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "processed_at")
    private Date processedAt;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "contact_reason")
    @Enumerated(EnumType.STRING)
    private ContactReasonType contactReason;

    @Column(name = "product_level")
    private String productLevel;

    @Enumerated(EnumType.STRING)
    private TicketLanguageType language;

    private String email;

    private String guid;
    
    @Column(name = "support_locale")
    private String supportLocale;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PostalHistoryStatus getStatus() {
        return status;
    }

    public void setStatus(PostalHistoryStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public ContactReasonType getContactReason() {
        return contactReason;
    }

    public void setContactReason(ContactReasonType contactReason) {
        this.contactReason = contactReason;
    }

    public String getProductLevel() {
        return productLevel;
    }

    public void setProductLevel(String productLevel) {
        this.productLevel = productLevel;
    }

    public TicketLanguageType getLanguage() {
        return language;
    }

    public void setLanguage(TicketLanguageType language) {
        this.language = language;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Date getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(Date processedAt) {
        this.processedAt = processedAt;
    }

    public String getSupportLocale() {
        return supportLocale;
    }

    public void setSupportLocale(String supportLocale) {
        this.supportLocale = supportLocale;
    }

    @Override
    public String toString() {
        return "PostalHistory{" +
                "id=" + id +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", processedAt=" + processedAt +
                ", customerName='" + customerName + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", contactReason=" + contactReason +
                ", productLevel='" + productLevel + '\'' +
                ", language=" + language +
                ", email='" + email + '\'' +
                ", guid='" + guid + '\'' +
                ", supportLocale='" + supportLocale + '\'' +
                '}';
    }
}
