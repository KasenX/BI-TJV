package cz.cvut.fit.tjv.eshop.web.client;

import cz.cvut.fit.tjv.eshop.web.model.ProductModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductClient {

    private final WebClient productWebClient;

    public ProductClient(@Value("${eshop_backend_url}") String backendUrl) {
        productWebClient = WebClient.create(backendUrl + "/products");
    }

    public Mono<ProductModel> fetchProduct(long id) {
        return productWebClient.get()
                .uri("/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ProductModel.class);
    }

    public Flux<ProductModel> fetchAllProducts() {
        return productWebClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ProductModel.class);
    }

    public Mono<ProductModel> create(ProductModel product) {
        return productWebClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .retrieve()
                .bodyToMono(ProductModel.class);
    }

    public Mono<ProductModel> update(ProductModel product, long id) {
        return productWebClient.put()
                .uri("/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .retrieve()
                .bodyToMono(ProductModel.class);
    }

    public Mono<Void> delete(long id) {
        return productWebClient.delete()
                .uri("/" + id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
