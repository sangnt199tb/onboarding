package com.example.onboarding.presentation.service.impl;

import com.example.onboarding.integration.model.CallOnlineRequest;
import com.example.onboarding.integration.model.CallOnlineResponse;
import com.example.onboarding.integration.model.CommerceResponse;
import com.example.onboarding.integration.model.credit.AccountingIntegrationResponse;
import com.example.onboarding.integration.model.credit.BankTransactionIntegrationResponse;
import com.example.onboarding.integration.model.credit.CreditCardIntegrationResponse;
import com.example.onboarding.integration.model.credit.CustomerIntegrationResponse;
import com.example.onboarding.integration.model.ekyc.EkycResponse;
import com.example.onboarding.integration.model.ekyc.FaceRetrievalResponse;
import com.example.onboarding.integration.service.CallOnlineService;
import com.example.onboarding.integration.service.CreditOnlineService;
import com.example.onboarding.presentation.configuration.AppConfiguration;
import com.example.onboarding.presentation.exception.ErrorCode;
import com.example.onboarding.presentation.exception.OnboardingException;
import com.example.onboarding.presentation.model.*;
import com.example.onboarding.presentation.model.multithreadedtest.*;
import com.example.onboarding.presentation.service.TestCollectionService;
import com.example.onboarding.presentation.util.LogUtil;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TestCollectionServiceImpl implements TestCollectionService {
    private final CallOnlineService callOnlineService;
    private final AppConfiguration configuration;
    private final CreditOnlineService creditOnlineService;

    @Autowired
    @Qualifier("taskExecutor")
    private Executor taskExecutor;

    @Autowired
    public TestCollectionServiceImpl(CallOnlineService callOnlineService, AppConfiguration configuration, CreditOnlineService creditOnlineService) {
        this.callOnlineService = callOnlineService;
        this.configuration = configuration;
        this.creditOnlineService = creditOnlineService;
    }

    @Override
    public void testCollection() {
        try {
            System.out.println("====VD1 create map====");

            Map<String, List<String>> classStudents  = new HashMap<>();

            addStudent(classStudents, "Math", "Alice");
            addStudent(classStudents, "Math", "Bob");
            addStudent(classStudents, "Physics", "Charlie");
            addStudent(classStudents, "Physics", "David");
            addStudent(classStudents, "Chemistry", "Eve");

            System.out.println("TestCollectionServiceImpl testCollection classStudents keySet(): " + LogUtil.toJson(classStudents.keySet()));
            System.out.println("TestCollectionServiceImpl testCollection classStudents: " + LogUtil.toJson(classStudents));

            for (String className: classStudents.keySet()) {
                System.out.println("TestCollectionServiceImpl testCollection className: " + className);
                for (String student : classStudents.get(className)) {
                    System.out.println("- " + student);
                }
            }

            System.out.println("=====Vd2===");
            Map<String, Integer> integerMap = new HashMap<>();
            integerMap.put("java", 1);
            integerMap.put("python", 2);
            integerMap.put("C++", 3);
            integerMap.put("js", 4);

            System.out.println(integerMap.get("java"));
            System.out.println(integerMap.containsKey("python"));
            System.out.println(integerMap.containsValue(5));

            integerMap.remove("js");
            System.out.println("TestCollectionServiceImpl vd2 integerMap: " + LogUtil.toJson(integerMap));

            System.out.println("====vd3 duyet map bang for-each====");
            System.out.println("====TestCollectionServiceImpl vd3 entrySet: ====" + integerMap.entrySet());
            for (Map.Entry<String, Integer> entry : integerMap.entrySet()) {
                System.out.println(entry.getKey() + "->" + entry.getValue());
            }

            System.out.println("===Vd3 map voi stream API====");
            Map<String, Integer> mapNumber = Map.of("Java", 8, "Python", 10, "C++", 5);
            List<String> result = mapNumber.entrySet().stream()
                    .filter(e -> e.getValue() > 8)
                    .map(Map.Entry :: getKey)
                    .collect(Collectors.toList());
            System.out.println("====Vd3 TestCollectionServiceImpl result: ====" + LogUtil.toJson(result));

            System.out.println("====vd4 tao stream tu array====");
            // Với mảng số nguyên
            int[] intArr = {1, 2, 3};
            IntStream intStream = Arrays.stream(intArr);
            System.out.println(intStream);

            System.out.println("====Vd5 filter theo điều kiện====");
            String[] arr = {"Apple", "Banana", "Avocado"};
            Arrays.stream(arr).filter(s->s.startsWith("A")).forEach(System.out::println);


            System.out.println("====VD6 - map biến đổi phần từ====");
            int[] numbers = {1,2,3};
            int[] numberX2 = Arrays.stream(numbers).map(n -> n*2).toArray();
            System.out.println("TestCollectionServiceImpl testCollection numberX2: " + LogUtil.toJson(numberX2));


            System.out.println("====VD7 - Sắp xếp====");
            int[] number = {7,2,4,1,7,5,9};
            int[] numberSapXep = Arrays.stream(number).sorted().toArray();
            System.out.println("TestCollectionServiceImpl testCollection numberSapXep: " + LogUtil.toJson(numberSapXep));

            System.out.println("====VD8 - distinct loại bỏ phần từ trừng====");
            int[] soHoc = {7,2,4,1,7,5,9};
            int[] soHocDistinct = Arrays.stream(soHoc).distinct().sorted().toArray();
            System.out.println("TestCollectionServiceImpl testCollection soHocDistinct: " + LogUtil.toJson(soHocDistinct));

            // array list
            List<String> cardItems = new ArrayList<>();
            cardItems.add("laptop");
            cardItems.add("chuột không dây");
            cardItems.add("bàn phỉm");
            System.out.println("TestCollectionServiceImpl testCollection cardItems: " + LogUtil.toJson(cardItems));

            //HashSet - danh sách email kh đăng ký nhận bản tin
            Set<String> emailList = new HashSet<>();
            emailList.add("a@gmail.com");
            emailList.add("b@gmail.com");
            emailList.add("a@gmail.com"); // sẽ không được thêm vào lần 2
            System.out.println("TestCollectionServiceImpl testCollection emailList: " + LogUtil.toJson(emailList));

            // hash map - lương nhân viên
            Map<String, Double> salaryMap = new HashMap<>();
            salaryMap.put("Nguyễn Văn A", 1500.0);
            salaryMap.put("Nguyễn Thị B", 3000.0);
            salaryMap.put("Nguyễn Thị C", 4000.0);
            System.out.println("Lương của B: " + salaryMap.get("Nguyễn Thị B"));

            //Queue - Hệ thống chăm sóc KH, KH gọi đến tổng đài, hệ thống cần chăm sóc theo thứ tự gọi vào
            Queue<String> callQueue = new LinkedList<>();
            callQueue.add("KHách hàng A");
            callQueue.add("khách hàng B");
            callQueue.add("Khách hàng C");

            while (!callQueue.isEmpty()){
                System.out.println("Phục vụ: " + callQueue.poll());
            }

        } catch (Exception e){
            System.out.println("====TestCollectionServiceImpl testCollection with error detail: " + e.toString());
        }
    }

    @Override
    public CallOnlineResponse testCallOnline() {
        try {
            CallOnlineRequest request =  CallOnlineRequest.builder()
                    .key("value")
                    .build();
            return callOnlineService.testPostCallOnline(request);
        } catch (Exception e){
            System.out.println("TestCollectionServiceImpl testCallOnline with error detail: " + e);
            throw new OnboardingException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CommerceResponse getListCommerceById(CommercePresentationRequest request) {
        return callOnlineService.getListCommerceById(request);
    }

    @Override
    public FaceMatchingResponse compareFace(FaceMatchingRequest request) {
        try {
            FaceMatchingResponse faceMatchingResponse = new FaceMatchingResponse();

            EkycResponse response = callOnlineService.compareFaceIntegration(request);

            System.out.println("TestCollectionServiceImpl compareFace response: " + LogUtil.toJson(response));

            Float faceMatching = Float.valueOf(configuration.getThresholdScoreFaceMatch());
            Float liveness = Float.valueOf(configuration.getThresholdScoreLiveness());
            Float retrival = Float.valueOf(configuration.getThresholdScoreRetrival());

            System.out.println("====TestCollectionServiceImpl compareFace faceMatching: " + faceMatching);
            System.out.println("====TestCollectionServiceImpl compareFace liveness: " + liveness);
            System.out.println("====TestCollectionServiceImpl compareFace retrival: " + retrival);

            Float faceMatchingEkyc = response.getResults().getFace_matching().getScore();
            if((faceMatchingEkyc - faceMatching) > 0){
                faceMatchingResponse.setResultFaceMatching("MATCH");
            } else {
                faceMatchingResponse.setResultFaceMatching("UN_MATCH");
            }

            if(StringUtils.equalsIgnoreCase(response.getResults().getLiveness_check().getStatus(), "passed")){
                faceMatchingResponse.setResultLiveness("PASS");
            } else {
                faceMatchingResponse.setResultLiveness("FAILED");
            }

            boolean resultFaceRetrival = response.getResults().getFace_retrieval().getTop_matches().stream()
                    .anyMatch(e -> (e.getMatch_score() - retrival) < 0);
            if(resultFaceRetrival){
                faceMatchingResponse.setResultFaceRetrival("PASS");
            } else {
                faceMatchingResponse.setResultFaceRetrival("FAILED");
            }

            return faceMatchingResponse;
        } catch (Exception e){
            System.out.println("TestCollectionServiceImpl compareFace with error detail: " + e);
            throw new OnboardingException(ErrorCode.FACE_MATCH_FAILED);
        }
    }

    @Override
    public GetListRetrievalResponse getListRetrieval(GetListRetrievalRequest request) {
        try {
            System.out.println("====Start TestCollectionServiceImpl getListRetrieval====");

            FaceRetrievalResponse faceRetrievalResponse = callOnlineService.getListRetrieval(request);

            System.out.println("TestCollectionServiceImpl getListRetrieval faceRetrievalResponse: " + LogUtil.toJson(faceRetrievalResponse));

            GetListRetrievalResponse response = new GetListRetrievalResponse();
            response.setStatus(faceRetrievalResponse.getResults().getStatus());
            response.setMatchCount(faceRetrievalResponse.getResults().getMatch_count());
            response.setRetrievalDetailResponseList(
                    faceRetrievalResponse.getResults().getMatches().stream().map(
                            e -> {
                                RetrievalDetailResponse retrievalDetailResponse = new RetrievalDetailResponse();
                                retrievalDetailResponse.setMatchScore(e.getMatch_score());
                                retrievalDetailResponse.setPersonId(e.getPerson_id());
                                retrievalDetailResponse.setFullName(e.getFull_name());
                                retrievalDetailResponse.setDob(e.getDob());

                                InfoCard infoCard = new InfoCard();
                                infoCard.setIcNumber(e.getId_card().getNumber());
                                infoCard.setIssuedBy(e.getId_card().getIssued_by());
                                infoCard.setIssuedDate(e.getId_card().getIssued_date());
                                retrievalDetailResponse.setInfoCard(infoCard);

                                retrievalDetailResponse.setSimilarityMethod(e.getSimilarity_method());
                                retrievalDetailResponse.setMatchedAt(e.getMatched_at());

                                MetaDateRetrieval metaDateRetrieval = new MetaDateRetrieval();
                                metaDateRetrieval.setLocation(e.getMeta().getLocation());
                                metaDateRetrieval.setSource(e.getMeta().getSource());
                                metaDateRetrieval.setLastVerified(e.getMeta().getLast_verified());
                                retrievalDetailResponse.setMetaDateRetrieval(metaDateRetrieval);

                                return retrievalDetailResponse;
                            }
                    ).collect(Collectors.toList())
            );

            System.out.println("TestCollectionServiceImpl getListRetrieval response: " + LogUtil.toJson(response));
            return response;

        } catch (Exception e){
            System.out.println("TestCollectionServiceImpl getListRetrieval with error detail: " + e);
            throw e;
        }
    }

    @Override
    public SummaryResponse getTestThread(SummaryRequest request) {
        return getTestThreadCompletableFuture(request).join();
    }

    public CompletableFuture<SummaryResponse> getTestThreadCompletableFuture(SummaryRequest request) {
        try {
            CompletableFuture<AccountingIntegrationResponse> accounting = callAccounting(request);
            CompletableFuture<BankTransactionIntegrationResponse> bank = callBank(request);
            CompletableFuture<CreditCardIntegrationResponse> creditCard = callCreditCard(request);
            CompletableFuture<CustomerIntegrationResponse> customer = callCustomer(request);

            return CompletableFuture.allOf(accounting, bank, creditCard, customer)
                    .thenApply(v->{
                        SummaryResponse response = new SummaryResponse();

                        AccountingIntegrationResponse accountingIntegrationResponse = accounting.join();
                        AccountingResponse accountingResponse = new AccountingResponse();
                        accountingResponse.setTotalDebt(accountingIntegrationResponse.getTotalDebt());
                        accountingResponse.setLatestInvoice(accountingIntegrationResponse.getLatestInvoice());
                        response.setAccounting(accountingResponse);

                        BankTransactionIntegrationResponse bankTransactionIntegrationResponse = bank.join();
                        BankTransactionResponse bankTransactionResponse = new BankTransactionResponse();
                        bankTransactionResponse.setRecentTransactions(bankTransactionIntegrationResponse.getRecentTransactions());
                        response.setBankTransaction(bankTransactionResponse);

                        CreditCardIntegrationResponse creditCardIntegrationResponse = creditCard.join();
                        CreditCardResponse creditCardResponse = new CreditCardResponse();
                        creditCardResponse.setCardLimit(creditCardIntegrationResponse.getCardLimit());
                        creditCardResponse.setCardType(creditCardIntegrationResponse.getCardType());
                        response.setCreditCard(creditCardResponse);

                        CustomerIntegrationResponse customerIntegrationResponse = customer.join();
                        CustomerResponse customerResponse = new CustomerResponse();
                        customerResponse.setName(customerIntegrationResponse.getName());
                        customerResponse.setTier(customerIntegrationResponse.getTier());
                        response.setCustomer(customerResponse);

                        return response;
                    });

        } catch (Exception e){
            System.out.println("TestCollectionServiceImpl getTestThread with error detail: " + e);
            throw e;
        }
    }


    public CompletableFuture<AccountingIntegrationResponse> callAccounting(SummaryRequest request){
        AccountingRequest accountingRequest = new AccountingRequest();
        accountingRequest.setToken("123");
        accountingRequest.setUserId(request.getUserId());
        return CompletableFuture.supplyAsync(() ->{
            System.out.println("Start callAccounting thread name: " + Thread.currentThread().getName());
            AccountingIntegrationResponse response = creditOnlineService.getAccount(accountingRequest);
            System.out.println("End callAccounting thread name: " + Thread.currentThread().getName());
            return response;
        }, taskExecutor);
    }

    public CompletableFuture<BankTransactionIntegrationResponse> callBank(SummaryRequest request){
        BankTransactionRequest bankTransactionRequest = new BankTransactionRequest();
        bankTransactionRequest.setUserId(request.getUserId());

        return CompletableFuture.supplyAsync(() ->{
            System.out.println("Start callBank thread name: " + Thread.currentThread().getName());
            BankTransactionIntegrationResponse response = creditOnlineService.getBankTransaction(bankTransactionRequest);
            System.out.println("End callBank thread name: " + Thread.currentThread().getName());
            return response;
        }, taskExecutor);
    }

    public CompletableFuture<CreditCardIntegrationResponse> callCreditCard(SummaryRequest request){
        CreditCardRequest creditCardRequest = new CreditCardRequest();
        creditCardRequest.setUserId(request.getUserId());

        return CompletableFuture.supplyAsync(() ->{
            System.out.println("Start callCreditCard thread name: " + Thread.currentThread().getName());
            CreditCardIntegrationResponse response = creditOnlineService.getCreditCard(creditCardRequest);
            System.out.println("End callCreditCard thread name: " + Thread.currentThread().getName());
            return response;
        }, taskExecutor);
    }

    public CompletableFuture<CustomerIntegrationResponse> callCustomer(SummaryRequest request){
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Start callCustomer thread name: " + Thread.currentThread().getName());
            CustomerRequest customerRequest = new CustomerRequest();
            customerRequest.setUserId(request.getUserId());
            CustomerIntegrationResponse response = creditOnlineService.getCustomer(customerRequest);
            System.out.println("End callCustomer thread name: " + Thread.currentThread().getName());
            return response;
        }, taskExecutor);
    }


    public static void addStudent(Map<String, List<String>> map, String className, String studentName){
        map.computeIfAbsent(className, k-> new ArrayList<>()).add(studentName);
    }

}
