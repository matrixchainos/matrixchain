package org.matrixchain.api.http;

import com.alibaba.fastjson.JSONObject;
import org.matrixchain.core.Transaction;
import org.matrixchain.service.MatrixChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TransactionApi extends HttpServlet {
    @Autowired
    private MatrixChainService matrixChainService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Transaction transaction = matrixChainService.getTransaction();

        resp.getWriter().println(JSONObject.toJSONString(transaction));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
