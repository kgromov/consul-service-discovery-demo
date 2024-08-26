package org.kgromov.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.javamoney.moneta.Money;

@Getter
@ToString
@AllArgsConstructor
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY, fieldVisibility = JsonAutoDetect.Visibility.NONE)
public class PersonInfo {
    private String name;
    private int age;
    private int height;
    private int weight;
    @JsonIgnore
    private Money salary;

    public String getSalaryToString() {
        return salary.toString();
    }
}
