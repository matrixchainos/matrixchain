package org.matrixchain.api.http;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ChainParamsApi extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.getWriter().println("{\n" +
                "\"difficulty\": 1001,\n" +
                "\"number\": 1,\n" +
                "\"gasUsed\": 100000000,\n" +
                "\"coinbase\": \"155661\",\n" +
                "\"extraData\": \"supportConstant\",\n" +
                "\"nonce\": 12647813,\n" +
                "\"hash\": \"0810861a42ef3be9af05fa386\",\n" +
                "\"timestamp\": 1576464924176\n" +
                "}");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
