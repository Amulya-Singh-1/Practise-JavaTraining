//package global.terraops.iqtrace.erpinterface.service.impl;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import global.terraops.iqtrace.erpinterface.config.schedule.future.TaskSchedulingService;
//import global.terraops.iqtrace.erpinterface.dao.ErpInterfaceHdrDetailsRepository;
//import global.terraops.iqtrace.erpinterface.dao.ErpInterfaceHdrRepository;
//import global.terraops.iqtrace.erpinterface.entity.ErpInterfaceHdrDetails;
//import global.terraops.iqtrace.erpinterface.exception.ErpInterfaceException;
//import global.terraops.iqtrace.erpinterface.sap2020.datatransfer.externalservice.MasterProcessService;
//import global.terraops.iqtrace.erpinterface.sap2020.datatransfer.externalservice.MaterialReceivingService;
//import global.terraops.iqtrace.erpinterface.sap2020.datatransfer.externalservice.UserService;
//import global.terraops.iqtrace.erpinterface.service.CommonService;
//import global.terraops.iqtrace.erpinterface.service.ErpInterfaceHdrDetailsService;
//import global.terraops.iqtrace.erpinterface.service.ErpProcessService;
//import global.terraops.iqtrace.erpinterface.service.ScheduleJobInfoService;
//import jdk.jfr.Experimental;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.reactivestreams.Publisher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.*;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.logging.Logger;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static reactor.core.publisher.Mono.when;
//
//@SpringBootTest
//class ErpInterfaceHdrDetailsServiceImplTest {
//
//    @Mock
//    private ErpInterfaceHdrDetailsRepository erpInterfaceHdrDetailsRepository;
//
////    @Mock
////    private ErpInterfaceHdrDetailsService erpInterfaceHdrDetailsService;
//
//    @InjectMocks
//    private ErpInterfaceHdrDetailsServiceImpl erpInterfaceHdrDetailsService;
//
//    @Mock
//    private ErpInterfaceHdrRepository erpInterfaceHdrRepository;
//
//    @Mock
//    private ScheduleJobInfoService scheduleJobInfoService;
//
//    @Autowired
//    private TaskSchedulingService taskSchedulingService;
//
//    @Autowired
//    private ErpInterfaceProperty erpInterfaceProperty;
//
//    @Autowired
//    private MasterProcessService masterProcessService;
//
//    @Autowired
//    private MaterialReceivingService materialReceivingService;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private UserService userService;
//
//    @Value("${UrlForSapData}")
//    private String host;
//
//    @Value("${AuthorizedUser}")
//    private String authorizedUser;
//
//    @Value("${AuthorizedReadRole}")
//    private String authorizedReadRole;
//
//    @Value("${AuthorizedCreateRole}")
//    private String authorizedCreateRole;
//
//    @Value("${AuthorizedReadOrg}")
//    private String authorizedReadOrg;
//
//    @Value("${AuthorizedReadUser}")
//    private String authorizedReadUser;
//
//    @Mock
//    private CommonService commonService;
//
//    @Autowired
//    private ErpProcessService erpProcessService;
//
//    MockMvc mockMvc;
//
//    @Mock
//    private Logger logger;
//
//    @Autowired
//    WebApplicationContext webApplicationContext;
//
//    @BeforeEach
//    public void before(){
//        mockMvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }
//
//    private ErpInterfaceHdrDetails createMockErpInterfaceDtls() {
//        ErpInterfaceHdrDetails erpInterfaceHdrDetails = new ErpInterfaceHdrDetails();
//        erpInterfaceHdrDetails.setIntfId("intf0001");
//        erpInterfaceHdrDetails.setFrequency("*/1 * * * *");
//        erpInterfaceHdrDetails.setActive(true);
//        erpInterfaceHdrDetails.setCreatedBy(null);
//        erpInterfaceHdrDetails.setUpdatedBy(null);
//        erpInterfaceHdrDetails.setCreatedTime(null);
//        erpInterfaceHdrDetails.setUpdatedTime(null);
//        erpInterfaceHdrDetails.setAllowDuplicate(false);
//        erpInterfaceHdrDetails.setDataMappingConifg("PURCHASE ORDER HEADER");
//        erpInterfaceHdrDetails.setFileType("API");
//        erpInterfaceHdrDetails.setInOut("IN");
//        return erpInterfaceHdrDetails;
//    }
//
//    @Test
//    void getAllErpInterfaceHdrDetailsService_success() {
//        Integer pageNumber = 1;
//        Integer pageSize = 10;
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//
//        List<ErpInterfaceHdrDetails> erpInterfaceHdrDetailsList = new ArrayList<>();
//        // Populate erpInterfaceHdrDetailsList with some test data
//
//        Page<ErpInterfaceHdrDetails> page = new PageImpl<>(erpInterfaceHdrDetailsList, pageable, erpInterfaceHdrDetailsList.size());
////        when( erpInterfaceHdrDetailsRepository.findAll()).thenReturn(null);
////        when(erpInterfaceHdrDetailsRepository.findAll()).thenReturn(erpInterfaceHdrDetailsList);
//
////         Act
//        when((Publisher<?>) erpInterfaceHdrDetailsRepository.findAll(PageRequest.of(1, 1))).thenReturn(null);
//        Page<ErpInterfaceHdrDetails> result = erpInterfaceHdrDetailsService.getAllErpInterfaceHdrDetailsService(pageNumber, pageSize);
//
//        // Assert
//        assertEquals(erpInterfaceHdrDetailsList.size(), result.getContent().size());
//        // You can add more assertions based on your specific requirements.
//
//        // Verify that the repository method was called with the correct parameters
//        verify(erpInterfaceHdrDetailsRepository, times(1)).findAll(pageable);
//    }
//
//
//    @Test
//    void getAllErpInterfaceHdrDetailsService_invalid() {
//        Integer pageNumber = 1;
//        Integer pageSize = 10;
//
//        when( erpInterfaceHdrDetailsRepository.findAll() ).thenThrow(new ErpInterfaceException("Error"));
//
//        // Act
//        erpInterfaceHdrDetailsService.getAllErpInterfaceHdrDetailsService(pageNumber, pageSize);
//
//    }
//
//    @Test
//    void getListOfAllErpInterfaceDtls() {
//        ErpInterfaceHdrDetails mockErpInterfaceDtls = createMockErpInterfaceDtls();
//        Integer pageNo = 1;
//        String sortKey = "fieldName";
//        Integer recordsPerPage = 10;
//        String sortDir = "asc";
//
//        Pageable page = PageRequest.of(pageNo, recordsPerPage, Sort.by(Sort.Direction.ASC, sortKey));
//        Page<ErpInterfaceHdrDetails> expectedPage = new PageImpl<>(Collections.singletonList(mockErpInterfaceDtls));
//
//        when(erpInterfaceHdrDetailsRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(expectedPage);
//
//        Page<ErpInterfaceHdrDetails> result = erpInterfaceHdrDetailsService.getListOfAllErpInterfaceDtls(
//                mockErpInterfaceDtls, pageNo, sortKey, recordsPerPage, sortDir);
//
//        assertEquals(expectedPage, result);
//
//        verify(erpInterfaceHdrDetailsRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
//    }
//
//
//
//    @Test
//    void saveOrUpdateErpInterfaceHdrDetails() {
//    }
//
//    @Test
//    void deleteErpInterfaceHdr() {
//    }
//
//    @Test
//    void processAndScheduleBatchJobForInterfaceDtslByMultiThreading() {
//    }
//
//    @Test
//    void dataTransmissionFromSap() {
//    }
//
//    @Test
//    void getTotalRecordCountOfErpInterfaceDtls() {
//    }
//
//    @Test
//    void loadErpInterfaceDtlsByInflId() {
//    }
//
//    @Test
//    void updateScheduledFutureJobForErpInterface() {
//    }
//
//    @Test
//    void getErpInterfaceHdrDtls_success() {
//        String intfId = "sampleIntfId";
//        ErpInterfaceHdrDetails mockDetails = new ErpInterfaceHdrDetails();
//        mockDetails.setIntfId(intfId);
//        mockDetails.setSubPath("/sample/subpath");
//
//        when(erpInterfaceHdrDetailsRepository.getInterfaceDtlsFromIntfId(intfId)).thenReturn(mockDetails);
//
//        ErpInterfaceHdrDetails result= erpInterfaceHdrDetailsService.getErpInterfaceHdrDtls(intfId);
//
//        assertNotNull(result);
//        assertEquals("/sample/subpath", result.getSubPathObj());
//        assertEquals(mockDetails, result);
//
//        verify( erpInterfaceHdrDetailsRepository, times(1)).getInterfaceDtlsFromIntfId(intfId);
//    }
//
//    @Test
//    void getErpInterfaceDtls() {
//        Integer intfHdrId = 1;
//        ErpInterfaceHdrDetails erpInterfaceDtl = new ErpInterfaceHdrDetails();
//        erpInterfaceDtl.setCreatedBy(1);
//        erpInterfaceDtl.setUpdatedBy(2);
//
//        List<Object[]> userList = new ArrayList<>();
//        Object[] user1 = {1, "user1"};
//        Object[] user2 = {2, "user2"};
//        userList.add(user1);
//        userList.add(user2);
//
//        when((Publisher<?>) erpInterfaceHdrDetailsRepository.findByInterfaceHdrId(intfHdrId)).thenReturn(Collections.singletonList(erpInterfaceDtl));
//        when(commonService.getUserNameList()).thenReturn(userList);
//
//        // Act
//        List<ErpInterfaceHdrDetails> result = erpInterfaceHdrDetailsService.getErpInterfaceDtls(intfHdrId);
//
//        // Assert
//        assertEquals(1, result.size());
//        ErpInterfaceHdrDetails fetchedDtl = result.get(0);
//        assertEquals("user1", fetchedDtl.getCreatedByUser());
//        assertEquals("user2", fetchedDtl.getUpdatedByUser());
//
//        // Verify that the repository method and commonService method were called
//        verify(erpInterfaceHdrDetailsRepository, times(1)).findByInterfaceHdrId(intfHdrId);
//        verify(commonService, times(1)).getUserNameList();
//
//        // Verify that the logger was called with the correct message
//        verify(logger, times(1)).info("Inside getErpInterfaceHdrDtls method in service");
//
//    }
//}