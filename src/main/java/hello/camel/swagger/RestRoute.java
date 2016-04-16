package hello.camel.swagger;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import static org.apache.camel.model.rest.RestParamType.body;
import static org.apache.camel.model.rest.RestParamType.path;

/**
 * RESTの設定
 */
@Component
public class RestRoute extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        restConfiguration().component("netty4-http").bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true") // jsonの整形
                .contextPath("/").port(18181) // httpサーバ (netty)の設定
                .apiContextPath("/api-doc") // swagger api doc の url
                .apiProperty("api.title", "Example API") // api title
                .apiProperty("api.version", "1.2.3") // api version
                .enableCORS(true) // rest用
                .apiProperty("cors", "true"); // api doc用


        // user
        rest("/user").description("ユーザーサービス")

                // HTTP GET
                .get("/{id}").description("IDからユーザー情報を取得")
                // パラメータの説明
                .param().name("id").type(path).description("取得するユーザーのID").dataType("integer").endParam()
                .to(MyRoute.IN)

                // HTTP POST
                .post("").description("ユーザーを新規追加")
                .param().name("body").type(body).description("").required(false).endParam()

                // 処理（別のルートにデータを流す）
                .to(MyRoute.IN);

        // item
        rest("/items").description("アイテムサービス")

                // HTTP GET
                .get("/{id}").description("ユーザーをIDで検索")
                // パラメータの説明
                .param().name("id").type(path).description("指定IDのユーザーが利用できるアイテム").dataType("integer").endParam()

                // 処理（別のルートにデータを流す）
                .to(MyRoute.IN);


    }}
