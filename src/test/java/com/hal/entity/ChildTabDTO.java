package com.hal.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class ChildTabDTO implements java.io.Serializable {

    private Integer n;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "childchild")
    private OtherEntity oe;
     
    public ChildTabDTO() {
    }

    public ChildTabDTO(Integer n) {
        this.n = n;
    }

    public Integer getN() {
        return this.n;
    }
    
    public void setN(Integer n) {
        this.n = n;
    }
    
    public OtherEntity getOe() {
        return oe;
    }

    public void setOe(OtherEntity oe) {
        this.oe = oe;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.n);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChildTabDTO other = (ChildTabDTO) obj;
        return Objects.equals(this.n, other.n);
    }
}