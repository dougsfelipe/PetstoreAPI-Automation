package org.APITest.util;

import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Method;

public class RetryExtension implements TestExecutionExceptionHandler {

    private static final int MAX_RETRIES = 3;

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        Method testMethod = context.getRequiredTestMethod();
        Object testInstance = context.getRequiredTestInstance();

        for (int i = 1; i < MAX_RETRIES; i++) {
            try {
                System.out.println("🔁 Tentando novamente (" + (i + 1) + "/" + MAX_RETRIES + ")");
                testMethod.invoke(testInstance);
                return; // Se passou, retorna
            } catch (Exception e) {
                if (i == MAX_RETRIES - 1) throw e.getCause(); // Última tentativa, rethrow
            }
        }

        throw throwable; // Primeira exceção
    }
}
