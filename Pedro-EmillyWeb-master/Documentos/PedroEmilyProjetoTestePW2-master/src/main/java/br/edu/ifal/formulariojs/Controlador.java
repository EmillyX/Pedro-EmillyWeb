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
    public ModelAndView index(Aluno aluno){
        ModelAndView retorno = new ModelAndView("form.html");
    retorno.addObject("aluno", aluno);
    return retorno;
    }

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
    }

     @RequestMapping("/atualizar_aluno/{idaluno}")
       public ModelAndView atualizar(@PathVariable("idaluno") long idaluno) {
        ModelAndView retorno = new ModelAndView("form.html");
        Optional<Aluno> opcao = estudante.findById(idaluno);
        System.out.println("----------------------------------------------------------");
        System.out.println(opcao.get().getId());
        if(opcao.isPresent()){
            Aluno aluno = opcao.get();
            System.out.println(aluno.getNome());
            retorno.addObject("aluno", aluno);
            return retorno;
        }

        return retorno;
        
        }

 @RequestMapping("/pesquisaraluno")
public ModelAndView pesquisar(String nomepesquisa){
ModelAndView modelAndView = new ModelAndView("registro.html");
modelAndView.addObject("alunos", estudante.findByNomeContaining(nomepesquisa)); 
return modelAndView;
    }

}
