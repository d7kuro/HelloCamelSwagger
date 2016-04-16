package hello.camel.swagger;

import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 返却するデータを作成するサンプル実装
 */
@Component
public class MyRoute extends RouteBuilder {

    public static final String ROUTE_ID = MyRoute.class.getSimpleName();
    public static final String IN = "direct:in" + ROUTE_ID;

    public void configure() throws Exception {
        from(IN).routeId(ROUTE_ID)
                .process(exchange -> {
                    Message in = exchange.getIn();
                    List<String> list = new ArrayList<>();
                    list.add("aaaaa");
                    in.setBody(list);
                })
                .to("log:test?showAll=true&multiline=true").id("logの処理");
    }
}
