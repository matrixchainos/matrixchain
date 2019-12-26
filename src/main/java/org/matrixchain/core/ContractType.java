package org.matrixchain.core;

public enum ContractType {
    TRANSFER("Transfer"),
    TRIGGERCONTRACT("TriggerContract");

    private String type;
    ContractType(String type) {
        this.type = type;
    }
}
