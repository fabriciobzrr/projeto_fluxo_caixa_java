package exception;

public class FluxoDeCaixaException extends RuntimeException {
    public FluxoDeCaixaException(String mensagemErro) {
        super(mensagemErro);
    }
}
