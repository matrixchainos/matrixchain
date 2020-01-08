package org.matrixchain.api.http;

import com.alibaba.fastjson.JSONObject;
import org.matrixchain.core.Block;
import org.matrixchain.service.MatrixChainService;
import org.matrixchain.util.InvokeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class BlockByHeightApi extends HttpServlet {

    @Autowired
    private MatrixChainService matrixChainService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        long height = Long.parseLong(req.getParameter("height"));

        Block block = matrixChainService.getBlockByHeight(height);

        resp.getWriter().print(JSONObject.toJSONString(new InvokeMessage<>(block)));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
