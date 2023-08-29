package com.testejt.demo.controller;

import com.testejt.demo.business.ColaboradorBusiness;
import com.testejt.demo.dto.ChefeSubordinado;
import com.testejt.demo.entity.Colaborador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colaborador")
public class ColaboradorController {

    @Autowired
    ColaboradorBusiness colaboradorBusiness;

    @GetMapping("/{id}")
    public Colaborador get(@PathVariable Integer id) throws Exception {
        return colaboradorBusiness.findById(id);
    }

    @GetMapping
    public List<Colaborador> getAll() {
        return colaboradorBusiness.findAll();
    }

    @PostMapping
    public Colaborador post(@RequestBody Colaborador colaborador) {
        return colaboradorBusiness.save(colaborador);
    }

    @PostMapping("/associa-chefe")
    public Colaborador associaChefe(@RequestBody ChefeSubordinado chefe) {
        return colaboradorBusiness.associaSubordinado(chefe);
    }

}
