package sample.cafekiosk.spring.api.service.product;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;

@Transactional(readOnly = true) //기본이 false고, true 값으로 주면 읽기 전용 트랜잭션이 열림 (CRUD 에서 CUD 동작 X)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 동시성 이슈 고려
     * 빈도수가 낮은 편이라 엄청 크리티컬 하진 않는 경우는 시스템에서 알아서
     * 재시도를 해서 통과를 할 수 있게 하는 방법도 있을 거고 조금 더 이제 크리티컬한 케이스
     * 동시 접속사가 너무 많은 케이스에는 UUID 활용해서 아예 번호 자체가 유니크하게 나오니까.. 활용해볼 수도 있다.
     */
    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        String nextProductNumber = createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    private String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber();
        if (latestProductNumber == null) {
            return "001";
        }
        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumbeInt = latestProductNumberInt + 1;

        return String.format("%03d", nextProductNumbeInt);
    }

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .toList();
    }
}
