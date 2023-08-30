package com.testejt.demo.business;

import com.testejt.demo.dto.ChefeSubordinado;
import com.backend.entity.Colaborador;
import com.testejt.demo.repository.ColaboradorRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColaboradorBusiness {

    @Autowired
    ColaboradorRepository colaboradorRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    public Colaborador findById(Integer id) throws Exception {
        return colaboradorRepository.findById(id).orElseThrow(() -> new Exception("nenhum colaborador encontrado"));
    }

    public List<Colaborador> findAll() {
        return colaboradorRepository.findAll();
    }

    public Colaborador save(Colaborador colaborador) {
//        colaborador.setScore(SenhaUtil.calculaComplexidade(colaborador.getSenha()));
//        colaborador.setSenha(SenhaUtil.encryptPassword(colaborador.getSenha()));

        colaborador = colaboradorRepository.save(colaborador);

        rabbitTemplate.convertAndSend("spring-boot-exchange", "foo.bar.baz", colaborador.getId());

        return colaborador;
    }

    public Colaborador associaSubordinado(ChefeSubordinado chefeSubordinado) {
        Colaborador chefe = colaboradorRepository.findById(chefeSubordinado.getIdChefe()).get();
        Colaborador subordinado = colaboradorRepository.findById(chefeSubordinado.getIdSubordinado()).get();

        subordinado.setChefe(chefe);

        return colaboradorRepository.save(subordinado);
    }
}
