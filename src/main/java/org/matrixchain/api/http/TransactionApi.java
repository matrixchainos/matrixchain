package org.matrixchain.api.http;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.matrixchain.core.Contract;
import org.matrixchain.core.Transaction;
import org.matrixchain.core.Transfer;
import org.matrixchain.core.TriggerSmartContract;
import org.matrixchain.service.MatrixChainService;
import org.matrixchain.util.InvokeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class TransactionApi extends HttpServlet {
    private final static ObjectMapper mapper;

    @Autowired
    private MatrixChainService matrixChainService;

    static {
        mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");

        String hash = req.getParameter("hash");

        Transaction transaction = matrixChainService.getTransactionByHash(hash);

        resp.getWriter().print(JSONObject.toJSONString(new InvokeMessage<>(transaction)));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");

        String transactionStr = req.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));
        System.out.println("contract: " + transactionStr);
        JSONObject transactionObject = JSONObject.parseObject(transactionStr);

        String ownerAddress = transactionObject.getString("ownerAddress");
        String sign = transactionObject.getString("signature");

        String contractType = transactionObject.getJSONObject("contract").getString("type");
        System.out.println("type: " + contractType);

        Contract contract;
        switch (contractType) {
            case "Transfer":
                contract = transactionObject.getObject("contract", Transfer.class);
                break;
            case "TriggerSmartContract":
                contract = transactionObject.getObject("contract", TriggerSmartContract.class);
                break;
            default:
                contract = null;
        }

        if (contract == null)
            resp.getWriter().print(JSONObject.toJSONString(new InvokeMessage<>("error!")));

        Transaction transaction = Transaction.create(ownerAddress, contract, sign);

        resp.getWriter().print(JSONObject.toJSONString(new InvokeMessage<>(transaction)));
    }
}
