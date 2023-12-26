package com.example.workshift.rest.shop;

import com.example.workshift.business.shop.Shop;
import com.example.workshift.business.shop.ShopService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shops")
@AllArgsConstructor
public class ShopController {

    private ShopDomainMapper shopDomainMapper;
    private ShopService shopService;

    @PostMapping
    public ResponseEntity<Long> createShop(@RequestBody final CreateShopRequestDto createShopRequestDto) {
        Shop createdShop = shopService.createShop(shopDomainMapper.mapToCreateShopRequest(createShopRequestDto));
        return ResponseEntity.ok(createdShop.id());
    }
}
