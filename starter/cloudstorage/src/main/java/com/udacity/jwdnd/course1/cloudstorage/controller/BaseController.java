package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.BaseEntity;
import com.udacity.jwdnd.course1.cloudstorage.service.BaseService;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import javax.validation.Valid;
import java.util.List;

public abstract class BaseController<E extends BaseEntity> {

    private BaseService<E> baseService;
    protected static final String HOME_REDIRECT = "redirect:/home";

    public BaseController(BaseService<E> baseService) {
        this.baseService = baseService;
    }

    public List<E> fetchAll() { return baseService.fetchAll(); }

    @GetMapping("/{id}")
    public E fetchById(@PathVariable Long id) throws NotFoundException { return baseService.fetchById(id); }

    @PostMapping
    public String save(@ModelAttribute @Valid E entity) {
        baseService.save(entity);
        return HOME_REDIRECT;
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute @Valid E entity) throws NotFoundException {
        baseService.update(entity, entity.getId());
        return HOME_REDIRECT;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws NotFoundException {
        baseService.delete(id);
        return HOME_REDIRECT;
    }
}
