package pl.maciejak.my_portfolio_rest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
public class Category implements Comparable<Category>, Serializable {

    private Long id;

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated;

    @Override
    public int compareTo(Category other) {
        return this.name.compareTo(other.name);
    }

}