package br.com.projeto.cadastro_animais.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class CpfValidator implements ConstraintValidator<CpfValido, String> {
    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext ctx) {
        if (cpf == null) return false;
        String numeros = cpf.replaceAll("\\D", "");
        if (numeros.length() != 11 || numeros.matches("(\\d)\\1{10}"))
            return false;
        int soma = 0;
        for (int i = 0; i < 9; i++)
            soma += (numeros.charAt(i) - '0') * (10 - i);
        int resto = (soma * 10) % 11;
        int digito1 = (resto == 10) ? 0 : resto;
        if (digito1 != numeros.charAt(9) - '0') return false;
        soma = 0;
        for (int i = 0; i < 10; i++)
            soma += (numeros.charAt(i) - '0') * (11 - i);
        resto = (soma * 10) % 11;
        int digito2 = (resto == 10) ? 0 : resto;
        return digito2 == numeros.charAt(10) - '0';
    }
}