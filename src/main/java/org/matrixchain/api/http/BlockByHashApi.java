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
public class BlockByHashApi extends HttpServlet {

    @Autowired
    private MatrixChainService matrixChainService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Block block;
        String hash = req.getParameter("hash");
        if (hash != null) {
            block = matrixChainService.getBlockByHash(hash);

        } else {
            long height = Long.parseLong(req.getParameter("height"));
            block = matrixChainService.getBlockByHeight(height);
        }

        resp.getWriter().print(JSONObject.toJSONString(new InvokeMessage<>(block)));
    }

}
