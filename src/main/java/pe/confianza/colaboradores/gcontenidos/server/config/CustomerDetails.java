package pe.confianza.colaboradores.gcontenidos.server.config;

import java.security.Principal;

public class CustomerDetails implements Principal {
    private final String uuid;
    private final String documentNumber;
    private final String documentType;
    private final String country;

    private CustomerDetails(CustomerDetails.CustomerDetailsBuilder builder) {
        this.uuid = builder.uuid;
        this.documentNumber = builder.documentNumber;
        this.documentType = builder.documentType;
        this.country = builder.country;
    }

    public String getName() {
        return this.uuid;
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getDocumentNumber() {
        return this.documentNumber;
    }

    public String getDocumentType() {
        return this.documentType;
    }

    public String getCountry() {
        return this.country;
    }

    public static class CustomerDetailsBuilder {
        private final String uuid;
        private String documentNumber;
        private String documentType;
        private String country;

        public CustomerDetailsBuilder(String uuid) {
            this.uuid = uuid;
        }

        public CustomerDetails.CustomerDetailsBuilder documentNumber(String documentNumber) {
            this.documentNumber = documentNumber;
            return this;
        }

        public CustomerDetails.CustomerDetailsBuilder documentType(String documentType) {
            this.documentType = documentType;
            return this;
        }

        public CustomerDetails.CustomerDetailsBuilder country(String country) {
            this.country = country;
            return this;
        }

        public CustomerDetails build() {
            return new CustomerDetails(this);
        }
    }
}
