package com.testejt.demo.business;

import com.testejt.demo.dto.ChefeSubordinado;
import com.testejt.demo.entity.Colaborador;
import com.testejt.demo.repository.ColaboradorRepository;
import com.testejt.demo.util.SenhaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.List;

@Service
public class ColaboradorBusiness {

    @Autowired
    ColaboradorRepository colaboradorRepository;

    public Colaborador findById(Integer id) throws Exception {
        return colaboradorRepository.findById(id).orElseThrow(() -> new Exception("nenhum colaborador encontrado"));
    }

    public List<Colaborador> findAll() {
        return colaboradorRepository.findAll();
    }

    public Colaborador save(Colaborador colaborador) {
        colaborador.setScore(SenhaUtil.calculaComplexidade(colaborador.getSenha()));
        colaborador.setSenha(SenhaUtil.encryptPassword(colaborador.getSenha()));

        return colaboradorRepository.save(colaborador);
    }

    public Colaborador associaSubordinado(ChefeSubordinado chefeSubordinado) {
        Colaborador chefe = colaboradorRepository.findById(chefeSubordinado.getIdChefe()).get();
        Colaborador subordinado = colaboradorRepository.findById(chefeSubordinado.getIdSubordinado()).get();

        subordinado.setChefe(chefe);

        return colaboradorRepository.save(subordinado);
    }
}
