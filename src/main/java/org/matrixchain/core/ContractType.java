package org.matrixchain.core;

public enum ContractType {
    Transfer("Transfer"),
    CreateSmartContract("CreateSmartContract"),
    TriggerSmartContract("TriggerSmartContract");

    private String type;

    ContractType(String type) {
        this.type = type;
    }
}
