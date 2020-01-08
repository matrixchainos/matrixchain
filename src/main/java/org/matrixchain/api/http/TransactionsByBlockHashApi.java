package org.matrixchain.api.http;

import com.alibaba.fastjson.JSONObject;
import org.matrixchain.core.Transaction;
import org.matrixchain.service.MatrixChainService;
import org.matrixchain.util.InvokeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class TransactionsByBlockHashApi extends HttpServlet {
    @Autowired
    private MatrixChainService matrixChainService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String hash = req.getParameter("hash");

        List<Transaction> transactionList = matrixChainService.getTransactionListByBlockHash(hash);

        resp.getWriter().print(JSONObject.toJSONString(new InvokeMessage<>(transactionList)));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
