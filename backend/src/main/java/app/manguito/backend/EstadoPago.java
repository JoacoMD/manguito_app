package app.manguito.backend;

public enum EstadoPago {
    APROBADO("approved"),
    EN_PROCESO("in_process"),
    RECHAZADO("rejected"),
    PENDIENTE("pending")
    ;


    private final String codigo;

    EstadoPago(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
