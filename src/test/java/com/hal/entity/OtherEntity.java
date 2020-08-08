package com.hal.entity;

import java.util.Objects;

public class OtherEntity implements java.io.Serializable {

    private Integer n;
     
    public OtherEntity() {
    }

    public OtherEntity(Integer n) {
        this.n = n;
    }

    public Integer getN() {
        return this.n;
    }
    
    public void setN(Integer n) {
        this.n = n;
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
        final OtherEntity other = (OtherEntity) obj;
        return Objects.equals(this.n, other.n);
    }
}