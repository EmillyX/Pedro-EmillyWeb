package br.edu.ifal.formulariojs;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class Controlador{
    @Autowired
    AlunoRepositorio estudante;
    @RequestMapping("/")
    public ModelAndView index(){
        return new ModelAndView("form.html");
    }

    /*@RequestMapping("/form")
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
    }  */

    @RequestMapping("/cadastrar_aluno")
    public ModelAndView cadastroAluno(Aluno aluno, RedirectAttributes redirect) {
        estudante.save(aluno);
        ModelAndView retorno = new ModelAndView("redirect:/listar_alunos");
        redirect.addFlashAttribute("mensagem", aluno.getNome() + " cadastrado com sucesso.");
        return retorno;
    }

    @RequestMapping("/listar_alunos")
    public ModelAndView listarAlunos() {
        ModelAndView retorno = new ModelAndView("registro.html");
        Iterable<Aluno> alunos = estudante.findAll();
        retorno.addObject("alunos", alunos);
        return retorno;
    }

    @RequestMapping("/excluir_aluno/{idAluno}")
    public ModelAndView excluirAlunos(@PathVariable("idAluno") Long alunoID, RedirectAttributes redirect) {
        Optional<Aluno> opcao = estudante.findById(alunoID);
        ModelAndView retorno = new ModelAndView("redirect:/listar_alunos");        
        if(opcao.isPresent()){
            Aluno a = opcao.get();
            estudante.deleteById(a.getId());            
            redirect.addFlashAttribute("mensagem", a.getNome() + " excluído com sucesso.");
            return retorno;
        } else {
            redirect.addFlashAttribute("mensagem", "Aluno " + alunoID + " não existe no Banco de Dados.");
            return retorno;
        }

        /*@RequestMapping("/atualizar_aluno/{idAluno}")
        public ModelAndView atualizar(@PathVariable("idAluno") Long alunoID){
            Optional<Aluno> opcao = estudante.findById(alunoID);
            ModelAndView retorno = new ModelAndView("form.html");
            if(opcao.isPresent()){
                Aluno a = opcao.get();
                retorno.addObject("aluno", a);
                return retorno;
            }
            return retorno;
        }*/        
    }


}