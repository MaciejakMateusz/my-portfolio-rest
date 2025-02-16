package pl.maciejak.my_portfolio_rest.service.interfaces;

import java.util.List;

public interface CategoryService {
    List<?> get();

    void post(String key);
}
