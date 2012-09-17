package com.rosettastone.succor.model.bandit;

import java.util.Date;
import java.util.List;

/**
 * The {@code License} represents a licence object.
 *
 @see Event
 */

public class License {

    private Boolean testLicense;
    
    private Boolean grandfathered = Boolean.TRUE;
    
    private Boolean soloRight = Boolean.FALSE;
    
    private Date upgradedAt;

    private String creationAccountGuid;

    private String creationAccountType;

    private List<ProductRights> productRights;
    
    public Boolean getTestLicense() {
        return testLicense;
    }

    public void setTestLicense(Boolean testLicense) {
        this.testLicense = testLicense;
    }

    public Date getUpgradedAt() {
        return upgradedAt;
    }

    public void setUpgradedAt(Date upgradedAt) {
        this.upgradedAt = upgradedAt;
    }

    public String getCreationAccountGuid() {
        return creationAccountGuid;
    }

    public void setCreationAccountGuid(String creationAccountGuid) {
        this.creationAccountGuid = creationAccountGuid;
    }

    public String getCreationAccountType() {
        return creationAccountType;
    }

    public void setCreationAccountType(String creationAccountType) {
        this.creationAccountType = creationAccountType;
    }

    public List<ProductRights> getProductRights() {
        return productRights;
    }

    public void setProductRights(List<ProductRights> productRights) {
        this.productRights = productRights;
    }

	public void setGrandfathered(Boolean grandfathered) {
		this.grandfathered = grandfathered;
	}

	public Boolean getGrandfathered() {
		return grandfathered;
	}

	public void setSoloRight(Boolean soloRight) {
		this.soloRight = soloRight;
	}

	public Boolean hasSoloRight() {
		return soloRight;
	}

}
