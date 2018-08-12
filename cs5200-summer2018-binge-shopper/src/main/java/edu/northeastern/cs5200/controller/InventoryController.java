package edu.northeastern.cs5200.controller;

import edu.northeastern.cs5200.entity.InventoryEntity;
import edu.northeastern.cs5200.entity.ProductEntity;
import edu.northeastern.cs5200.entity.UserEntity;
import edu.northeastern.cs5200.exception.AccessDeniedException;
import edu.northeastern.cs5200.service.InventoryService;
import edu.northeastern.cs5200.service.ProductService;
import edu.northeastern.cs5200.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private UserService userService;

    @PostMapping("user/{userId}/addProduct")
    public InventoryEntity addProductsToSellerInventory(@PathVariable int userId, @RequestBody ProductEntity product, @RequestBody int price, @RequestBody int qty) throws AccessDeniedException {
        UserEntity user = userService.getUserById(userId);
        if(user.getUserType() != "Seller")
            throw new AccessDeniedException(new Throwable("User not a seller| Can't add inventory!"));
        return inventoryService.addInventory(user, product, price, qty);
    }

    @GetMapping("product/{productId}/getSeller")
    public List<InventoryEntity> getAllSellerForProduct(@PathVariable int productId){
        return inventoryService.getAllSellerForProduct(productId);
    }

    @PostMapping("user/{userId}/update")
    public InventoryEntity updateProductInSellerInventory(@PathVariable int userId, @RequestBody InventoryEntity inventory, HttpSession session){
        return inventoryService.updateInventory(userId, inventory);
    }

    @PostMapping("delete/{inventoryId}")
    public void deleteInventory(@PathVariable int inventoryId){
        inventoryService.deleteInventory(inventoryId);
    }
}
