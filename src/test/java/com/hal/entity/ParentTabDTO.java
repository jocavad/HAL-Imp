package com.hal.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
//import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDate;
//import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"parent_id", "parent_value"})//property order can be changed here
public class ParentTabDTO implements java.io.Serializable {

    @JsonProperty(value = "parent_id")
    private Integer n;
    @JsonProperty(value = "parent_value")
    private String v;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "child")
    private List<ChildTabDTO> ct = new ArrayList<>(0);
    @JsonDeserialize(using = LocalDateDeserializer.class)  
    @JsonSerialize(using = LocalDateSerializer.class)  
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonProperty(value = "parent_date")
    private LocalDate someDate;
    
    public ParentTabDTO() {
    }

    public ParentTabDTO(Integer n, String v) {
       this.n = n;
       this.v = v;
    }

    public String getV() {
        return this.v;
    }
    
    public void setV(String v) {
        this.v = v;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public List<ChildTabDTO> getCt() {
        return ct;
    }

    public void setCt(List<ChildTabDTO> ct) {
        this.ct = ct;
    }

    public LocalDate getSomeDate() {
        return someDate;
    }

    public void setSomeDate(LocalDate someDate) {
        this.someDate = someDate;
    }

}


