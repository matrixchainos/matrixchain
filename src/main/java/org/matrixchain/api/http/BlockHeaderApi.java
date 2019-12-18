package org.matrixchain.api.http;

import com.alibaba.fastjson.JSONObject;
import org.matrixchain.core.BlockHeader;
import org.matrixchain.service.MatrixChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class BlockHeaderApi extends HttpServlet {

    @Autowired
    private MatrixChainService matrixChainService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("-------BlockHeaderApi-------");
        BlockHeader header = matrixChainService.getBlockHeader();

        resp.getWriter().println(JSONObject.toJSON(header));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
