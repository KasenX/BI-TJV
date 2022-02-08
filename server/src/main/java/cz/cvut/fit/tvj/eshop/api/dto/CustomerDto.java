package cz.cvut.fit.tvj.eshop.api.dto;

import com.fasterxml.jackson.annotation.JsonView;
import cz.cvut.fit.tvj.eshop.api.Views;

import java.util.Objects;

public class CustomerDto {

    @JsonView(Views.Public.class)
    private Long id;
    @JsonView(Views.Public.class)
    private String username;

    public CustomerDto() {

    }

    public CustomerDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        CustomerDto that = (CustomerDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
