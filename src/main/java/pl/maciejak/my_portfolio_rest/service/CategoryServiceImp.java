package pl.maciejak.my_portfolio_rest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.maciejak.my_portfolio_rest.service.interfaces.CategoryService;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImp implements CategoryService {

    @Override
    public List<?> get() {
        return List.of();
    }

    @Override
    public void post(String key) {

    }
}