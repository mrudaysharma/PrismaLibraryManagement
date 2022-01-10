package com.prisma.librarymanagement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {
    @JsonProperty(value = "Name")
    private String name;
    @JsonProperty(value = "First name")
    private String firstName;
    @JsonProperty(value = "Member since")
    private String memberSince;
    @JsonProperty(value = "Member till")
    private String memberTill;
    @JsonProperty(value = "Gender")
    private char gender;
}
