package cn.foxkiar.loongarch.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public class NoPermissionException extends javax.security.sasl.AuthenticationException {
    @Override
    public void printStackTrace() {
    }

    @Override
    public void printStackTrace(PrintStream s) {
    }

    @Override
    public void printStackTrace(PrintWriter s) {
    }
}
