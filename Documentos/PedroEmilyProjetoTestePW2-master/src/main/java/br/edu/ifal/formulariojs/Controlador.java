package br.edu.ifal.formulariojs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Controlador{
    @Autowired
    AlunoRepositorio estudante;
    @RequestMapping("/")
    public ModelAndView index(){
        return new ModelAndView("form.html");
    }

    @RequestMapping("/form")
    public String cadastro(Aluno aluno){
       String dados="";
        estudante.save(aluno);

        Iterable <Aluno> estudantes = estudante.findAll();
        for (Aluno estudant : estudantes) {
            dados+=estudant.getNome()+" - ";
            dados+=estudant.getEmail()+" - ";
            dados+=estudant.getCpf()+" - ";
            dados+=estudant.getModulo()+" - ";
            dados+=estudant.getSexo()+"./";
            //blob resover no banco 
        }
        return dados;
    }
}