package com.solera.bankbackend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CommonService<E, R extends JpaRepository<E, Long>> {
    @Autowired
    protected R repository;

    @Transactional(readOnly = true)
    public List<E> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<E> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public E findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional()
    public E save(E entity) {
        return repository.save(entity);
    }

    @Transactional()
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
}
