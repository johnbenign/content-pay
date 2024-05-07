package com.task.threeline.content.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.threeline.content.dao.BuyContentRepository;
import com.task.threeline.content.dao.ContentRepository;
import com.task.threeline.content.dto.BuyContentRequest;
import com.task.threeline.content.dto.ContentRequest;
import com.task.threeline.content.dto.GeneralResponse;
import com.task.threeline.content.dto.WalletRequest;
import com.task.threeline.content.exception.BadRequestException;
import com.task.threeline.content.exception.InsufficientFundsException;
import com.task.threeline.content.exception.NotFoundException;
import com.task.threeline.content.mapper.ContentMapper;
import com.task.threeline.content.model.BuyContent;
import com.task.threeline.content.model.Content;
import com.task.threeline.content.service.ContentService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentServiceImpl implements ContentService {
    private final BuyContentRepository buyContentRepository;
    private final ObjectMapper mapper = new ObjectMapper();
    private final ContentRepository repository;
    private final WebClient webClient;

    @Override
    public GeneralResponse postContent(ContentRequest request){
        validateUser(request.getContentOwnerId());
        Content content = ContentMapper.mapToEntity(request);
        content = repository.save(content);
        return GeneralResponse.builder()
                .data(ContentMapper.mapToDto(content))
                .message("Content saved successfully")
                .status(HttpStatus.CREATED.value())
                .build();
    }

    @Override
    @Synchronized
    public GeneralResponse buyContent(BuyContentRequest request){
        BuyContent buyContent = new BuyContent();
        Content content = repository.findById(request.getContentId()).orElseThrow(() -> new NotFoundException("No content found with given id"));
        fetchUserDetails(request.getBuyerId());
        BigDecimal buyerWalletBalance = getWalletBalance(request.getBuyerId());
        if(buyerWalletBalance.doubleValue() < content.getCost().doubleValue())
            throw new InsufficientFundsException();
        buyContent.setBuyerId(request.getBuyerId());
        buyContent.setContent(content);
        buyContentRepository.save(buyContent);
        BigDecimal cost = content.getCost();
        BigDecimal clientOrgProfit = BigDecimal.valueOf(cost.doubleValue() * (10/100));
        BigDecimal contractOrgProfit = BigDecimal.valueOf(cost.doubleValue() * (5/100));
        BigDecimal contractCreatorProfit = cost.subtract(clientOrgProfit.add(contractOrgProfit));
        //debitBuyer
        debit(new WalletRequest(request.getBuyerId(), cost));
        //creditClientOrg
        JSONObject clientOrgJSON = findUserByEmail("clientorg@client.ng");
        Long clientOrgId = clientOrgJSON.getJSONObject("data").getLong("id");
        credit(new WalletRequest(clientOrgId, clientOrgProfit));
        //credit contractorOrg
        JSONObject contractOrgJSON = findUserByEmail("3line.management@3lineng.com");
        Long contractOrgId = contractOrgJSON.getJSONObject("data").getLong("id");
        credit(new WalletRequest(contractOrgId, contractOrgProfit));
        //credit content creator
        credit(new WalletRequest(content.getContentOwner(), contractCreatorProfit));

        return GeneralResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Content has successfully been purchased")
                .data("Content has successfully been purchased")
                .build();
    }

    private void debit(WalletRequest request){
        Mono<WalletRequest> requestMono = Mono.just(request);
        webClient.post()
                .uri("http://132.145.58.252:6062/wallet-api/debit")
                .body(requestMono, WalletRequest.class)
                .retrieve()
                .bodyToMono(GeneralResponse.class)
                .block();

    }

    private void credit(WalletRequest request){
        Mono<WalletRequest> requestMono = Mono.just(request);
        webClient.post()
                .uri("http://132.145.58.252:6062/wallet-api/credit")
                .body(requestMono, WalletRequest.class)
                .retrieve()
                .bodyToMono(GeneralResponse.class)
                .block();

    }

    private BigDecimal getWalletBalance(Long userId) {
        GeneralResponse response = webClient.get()
                .uri("http://132.145.58.252:6062/wallet-api/get-balance", uriBuilder -> uriBuilder.path("/" + userId).build())
                .retrieve()
                .bodyToMono(GeneralResponse.class)
                .block();
        if(response.getStatus() != HttpStatus.OK.value())
            throw new BadRequestException(response.getMessage());
        return BigDecimal.valueOf((double)response.getData());
    }

    private boolean validateUser(Long contentOwnerId) {
        JSONObject userJson = fetchUserDetails(contentOwnerId);
        String userType = userJson.getJSONObject("data").getString("userType");
        log.info("userType:::" + userType);
        if(!userType.equals("CONTENT_CREATOR"))
            throw new BadRequestException("Only content creators are allowed to post content");
        return true;
    }

    @SneakyThrows
    private JSONObject fetchUserDetails(Long userId) {
        try {
            GeneralResponse response = webClient.get()
                    .uri("http://132.145.58.252:6063/api/user", uriBuilder -> uriBuilder.path("/" + userId).build())
                    .retrieve()
                    .bodyToMono(GeneralResponse.class)
                    .block();
            log.info("fetch user response:" + response);
            if(response.getStatus() != HttpStatus.OK.value())
                throw new NotFoundException("User not found");
            return new JSONObject(response);
        }
        catch(WebClientResponseException e){
            log.error(e.getResponseBodyAsString());
            JSONObject errorJson = new JSONObject(e.getResponseBodyAsString());
            throw new BadRequestException(errorJson.getString("message"));
        }
    }

    private JSONObject findUserByEmail(String email) {
        try{
            GeneralResponse response = webClient.get()
                    .uri("http://132.145.58.252:6063/api/user/find-by-email", uriBuilder -> uriBuilder.path("/" + email).build())
                    .retrieve()
                    .bodyToMono(GeneralResponse.class)
                    .block();
            log.info("user response::::" + response);
            if (response.getStatus() != HttpStatus.OK.value())
                throw new NotFoundException("User not found");
            return new JSONObject(response);
        }catch(WebClientResponseException e){
            log.error(e.getResponseBodyAsString());
            JSONObject errorJson = new JSONObject(e.getResponseBodyAsString());
            throw new BadRequestException(errorJson.getString("message"));
        }
    }
}
