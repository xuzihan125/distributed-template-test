package com.project.homework.Controller;

import com.project.homework.Repository.HistoryRepository;
import com.project.homework.Repository.ProductRepository;
import com.project.homework.common.BaseResponse;
import com.project.homework.common.ErrorCode;
import com.project.homework.common.ResultUtils;
import com.project.homework.model.History;
import com.project.homework.model.Product;
import com.project.homework.vo.BuyVO;
import com.project.homework.vo.HistoryVO;
import com.project.homework.vo.Purchase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @GetMapping(path = "/getProduct") // Map ONLY POST Requests
    public @ResponseBody
    BaseResponse<List<Product>> getProduct() {
        List<Product> productList = (List<Product>) productRepository.findByQuantityGreaterThan(0);
        return ResultUtils.success(productList);
    }

    @PostMapping(path = "/addProduct") // Map ONLY POST Requests
    public @ResponseBody
    BaseResponse<String> addProduct(@RequestBody Product product) {
        if (product.getName() == null || product.getPrice() == null || product.getAmount() == null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "necessary value is not provided");
        }
        productRepository.save(product);
        return ResultUtils.success("success");
    }

    @PostMapping(path = "/getHistory") // Map ONLY POST Requests
    public @ResponseBody
    BaseResponse<List<Purchase>> getHistory(@RequestBody String username) {
        if(username == null || username.isBlank()){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "user should login first");
        }
        List<History> histories = historyRepository.findByUsernameOrderByPurchaseTime(username);
        //获得所有的id
        List<Long> productIdList = histories.stream().map(History::getProductId).distinct().collect(Collectors.toList());
        List<Product> productList = productRepository.findAllByIdIn(productIdList);
        Map<Long, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getId, product -> product));
        //创建回复信息
        Map<String, List<History>> group = histories.stream().collect(Collectors.groupingBy(History::getId));
        List<Purchase> purchaseList = new ArrayList<>();
        group.forEach((id, historyList)->{
            Purchase purchase = new Purchase();
            purchase.setId(id);
            double cost = 0;
            boolean success = false, fail = false;
            List<HistoryVO> list = new ArrayList<>();
            //几种状态：还在处理，全部成功，部分成功，全部失败
            for(History history:historyList){
                Product product = productMap.get(history.getProductId());
                cost += product.getPrice()*history.getQuantity();
                if(history.getStatus().equals("processing")){
                    purchase.setStatus("processing");
                }
                else if(history.getStatus().equals("success")){
                    success=true;
                }
                else{
                    fail=true;
                }
                HistoryVO historyVo = new HistoryVO();
                historyVo.setName(product.getName());
                historyVo.setPrice(product.getPrice());
                historyVo.setStatus(history.getStatus());
                historyVo.setQuantity(history.getQuantity());
                historyVo.setDate(history.getPurchaseTime());
                list.add(historyVo);
            }
            purchase.setTime(historyList.get(0).getPurchaseTime());
            purchase.setDetail(list);
            purchase.setCost(cost);
            if(purchase.getStatus()==null){
                if(success && fail){
                    purchase.setStatus("partial success");
                }
                else if(success){
                    purchase.setStatus("success");
                }
                else if(fail){
                    purchase.setStatus("fail");
                }
            }
            purchaseList.add(purchase);
        });
        List<Purchase> sortedPurchaseList = purchaseList.stream().sorted(Comparator.comparing(Purchase::getTime).reversed()).collect(Collectors.toList());
        return ResultUtils.success(sortedPurchaseList);
    }

    @PostMapping(path = "/buyProduct") // Map ONLY POST Requests
    public @ResponseBody
    BaseResponse<String> buyProduct(@RequestBody BuyVO buyVO) {
        Map<Long, Integer> cart = buyVO.getCart();
        String username = buyVO.getUsername();
        if(username == null || username.isBlank()){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "user should login first");
        }
        UUID id = UUID.randomUUID();
        Date currentTime = new Date();
        for(Map.Entry<Long, Integer> entry:cart.entrySet()){
            History history = new History();
            history.setProductId(entry.getKey());
            history.setQuantity(entry.getValue());
            history.setId(id.toString());
            history.setStatus("processing");
            history.setUsername(username);
            history.setPurchaseTime(currentTime);
            historyRepository.save(history);
        }
        Thread thread = new Thread(()->{
            for(Map.Entry<Long, Integer> entry:cart.entrySet()){
                boolean status = purchaseProduct(entry.getKey(), entry.getValue());
                History history = historyRepository.findByProductIdAndUsernameAndId(entry.getKey(), username, id.toString()).get(0);
                if(status){
                    history.setStatus("success");
                }
                else{
                    history.setStatus("fail");
                }
                historyRepository.save(history);
            }
        });
        thread.start();
        return ResultUtils.success("the order is under process");
    }

    @Transactional
    public boolean purchaseProduct(Long productId, int quantity) {
        int product = productRepository.findByIdForUpdate(productId, quantity);
        return product == 1;
    }
}
