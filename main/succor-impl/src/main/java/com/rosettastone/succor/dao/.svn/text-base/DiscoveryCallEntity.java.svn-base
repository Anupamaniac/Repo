package com.rosettastone.succor.dao;

import javax.persistence.*;

/***
 * The {@code DiscoveryCallEntity} represents discoveryCall object.
 */

@Entity
@Table(name = "OLAPDW.WC_DISCOVERY_CALL_MV")
public class DiscoveryCallEntity {

    @EmbeddedId
    private DiscoveryCallPK id;

    private String name;

    private String phone;

    private String city;

    @Column(name = "county")
    private String country;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "state_code")
    private String stateCode;

    @Column(name = "address_title")
    private String addressTitle;

    @Column(name = "st_address1")
    private String streetAddress1;

    @Column(name = "st_address2")
    private String streetAddress2;

    @Column(name = "x_actual_shipment_dt_wid")
    private Long actualShip;

    @Column(name = "sales_order_num")
    private String salesOrderNum;

    @Column(name = "operating_unit")
    private String operatingUnit;

    public DiscoveryCallPK getId() {
        return id;
    }

    public void setId(DiscoveryCallPK id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getAddressTitle() {
        return addressTitle;
    }

    public void setAddressTitle(String addressTitle) {
        this.addressTitle = addressTitle;
    }

    public String getStreetAddress1() {
        return streetAddress1;
    }

    public void setStreetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }

    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    public Long getActualShip() {
        return actualShip;
    }

    public void setActualShip(Long actualShip) {
        this.actualShip = actualShip;
    }

    public String getSalesOrderNum() {
        return salesOrderNum;
    }

    public void setSalesOrderNum(String salesOrderNum) {
        this.salesOrderNum = salesOrderNum;
    }

    public String getOperatingUnit() {
        return operatingUnit;
    }

    public void setOperatingUnit(String operatingUnit) {
        this.operatingUnit = operatingUnit;
    }

}
