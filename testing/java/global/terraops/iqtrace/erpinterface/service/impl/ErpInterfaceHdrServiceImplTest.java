package global.terraops.iqtrace.erpinterface.service.impl;

import global.terraops.iqtrace.erpinterface.dao.ErpInterfaceHdrDetailsRepository;
import global.terraops.iqtrace.erpinterface.dao.ErpInterfaceHdrRepository;
import global.terraops.iqtrace.erpinterface.dto.ErpInterfaceHdrDto;
import global.terraops.iqtrace.erpinterface.entity.ErpInterfaceHdr;
import global.terraops.iqtrace.erpinterface.entity.ErpInterfaceHdrDetails;
import global.terraops.iqtrace.erpinterface.exception.ErpInterfaceException;
import global.terraops.iqtrace.erpinterface.service.CommonService;
import global.terraops.iqtrace.erpinterface.service.ErpInterfaceHdrService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@RunWith(SpringRunner.class)
@SpringBootTest
class ErpInterfaceHdrServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErpInterfaceHdrServiceImpl.class);

    @MockBean
    private ErpInterfaceHdrRepository erpInterfaceHdrRepository;

    @MockBean
    private ErpInterfaceHdrDetailsRepository erpInterfaceHdrDetailsRepository;

    @Autowired
    private ErpInterfaceHdrService erpInterfaceHdrService;

    @MockBean
    private CommonService commonService;

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

//    @BeforeAll
//    public void setUp() {
//        erpInterfaceHdrRepository = mock(ErpInterfaceHdrRepository.class);
//        commonService = mock(CommonService.class);
//        erpInterfaceHdrService = new ErpInterfaceHdrServiceImpl(erpInterfaceHdrRepository, commonService);
//    }

    @BeforeEach
    public void before(){
        mockMvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private ErpInterfaceHdrDto createMockErpInterfaceHdrDto(){
        ErpInterfaceHdrDto erpInterfaceHdr=new ErpInterfaceHdrDto();
        erpInterfaceHdr.setIntfHdrId(1);
        erpInterfaceHdr.setErpName("erp");
        erpInterfaceHdr.setUserName("abcdef");

        return erpInterfaceHdr;
    }

    private ErpInterfaceHdr createMockErpInterfaceHdr(){
        ErpInterfaceHdr erpInterfaceHdr=new ErpInterfaceHdr();
        erpInterfaceHdr.setIntfHdrId(1);
        erpInterfaceHdr.setErpName("erp");
        erpInterfaceHdr.setUserName("abcdef");

        return erpInterfaceHdr;
    }

    @Test
    void getAllErpInterfaceHdr_success() {
        int pageNumber = 0;
        int pageSize = 10;

        List<ErpInterfaceHdr> erpInterfaceHdrList = new ArrayList<>();
        ErpInterfaceHdr erpInterfaceHdr = new ErpInterfaceHdr();
        erpInterfaceHdr.setCreatedBy(1);
        erpInterfaceHdr.setUpdatedBy(2);
        erpInterfaceHdr.setOrgId(1);
        erpInterfaceHdrList.add(erpInterfaceHdr);

        Page<ErpInterfaceHdr> pageableResult = new PageImpl<>(erpInterfaceHdrList);
        when(erpInterfaceHdrRepository.findAll(PageRequest.of(pageNumber, pageSize))).thenReturn(pageableResult);

        ArrayList<Object[]> userList = new ArrayList<>();
        Object[] user1 = { 1, "User1" };
        Object[] user2 = { 2, "User2" };
        userList.add(user1);
        userList.add(user2);
        when(commonService.getUserNameList()).thenReturn(userList);

        ArrayList<Object[]> orgList = new ArrayList<>();
        Object[] org = { 1, "ORG_CODE" };
        orgList.add(org);
        when(commonService.getOrgList()).thenReturn(orgList);

        Page<ErpInterfaceHdr> result = erpInterfaceHdrService.getAllErpInterfaceHdr(pageNumber, pageSize, "UTC");

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        ErpInterfaceHdr fetchedErpHdr = result.getContent().get(0);
        assertEquals("User1", fetchedErpHdr.getCreatedByUser());
        assertEquals("User2", fetchedErpHdr.getUpdatedByUser());
        assertEquals("ORG_CODE", fetchedErpHdr.getOrgCode());

        verify(erpInterfaceHdrRepository, times(1)).findAll(PageRequest.of(pageNumber, pageSize));
        verify(commonService, times(1)).getUserNameList();
        verify(commonService, times(1)).getOrgList();
    }

    @Test
    void getAllErpInterfaceHdr_invalid(){
        int pageNumber = 0;
        int pageSize = 10;

//        List<ErpInterfaceHdr> erpInterfaceHdrList = new ArrayList<>();
//        ErpInterfaceHdr erpInterfaceHdr = new ErpInterfaceHdr();
//        erpInterfaceHdr.setCreatedBy(1);
//        erpInterfaceHdr.setUpdatedBy(2);
//        erpInterfaceHdr.setOrgId(1);
//        erpInterfaceHdrList.add(erpInterfaceHdr);

//        Page<ErpInterfaceHdr> pageableResult = new PageImpl<>(erpInterfaceHdrList);

        when(erpInterfaceHdrRepository.findAll(PageRequest.of(pageNumber,pageSize))).thenThrow(new ErpInterfaceException("Exception in getAllErpInterfaceHdr method in ErpInterfaceHdrServiceImpl class"));

         ErpInterfaceException exception= assertThrows(ErpInterfaceException.class, ()->{
           erpInterfaceHdrService.getAllErpInterfaceHdr(pageNumber,pageSize,"UTC");
        });

        assertEquals("Exception in getAllErpInterfaceHdr method in ErpInterfaceHdrServiceImpl class",
                exception.getMessage());

//        verify( erpInterfaceHdrService, times(1)).getAllErpInterfaceHdr(pageNumber, pageSize,"UTC");

    }

    @Test
    void saveErpInterfaceHdr_success(){
            ErpInterfaceHdr erpInterfaceHdr = createMockErpInterfaceHdr();
            ErpInterfaceHdrDto erpInterfaceHdrDto = createMockErpInterfaceHdrDto();
//            ErpInterfaceHdrServiceImpl erpInterfaceHdrService = new ErpInterfaceHdrServiceImpl(erpInterfaceHdrRepository);

            when(erpInterfaceHdrRepository.save(any())).thenReturn(erpInterfaceHdr);

            ErpInterfaceHdr result = erpInterfaceHdrService.saveErpInterfaceHdr(erpInterfaceHdrDto);

            assertNotNull(result);
            verify(erpInterfaceHdrRepository, times(1)).save(any(ErpInterfaceHdr.class));
    }

    @Test
    void saveErpInterfaceHdr_invalid(){
        ErpInterfaceHdrDto erpInterfaceHdrDto = new ErpInterfaceHdrDto();
//        ErpInterfaceHdrServiceImpl erpInterfaceHdrService = new ErpInterfaceHdrServiceImpl(erpInterfaceHdrRepository);

        when(erpInterfaceHdrRepository.save(any(ErpInterfaceHdr.class))).thenThrow(new DataIntegrityViolationException("Duplicate Value Present"));

        assertThrows(ErpInterfaceException.class, () -> {
            erpInterfaceHdrService.saveErpInterfaceHdr(erpInterfaceHdrDto);
        });

        verify(erpInterfaceHdrRepository, times(1)).save(any(ErpInterfaceHdr.class));
    }

    @Test
    void saveErpInterfaceHdr_exception(){
        ErpInterfaceHdrDto erpInterfaceHdrDto = new ErpInterfaceHdrDto();
//        ErpInterfaceHdrServiceImpl erpInterfaceHdrService = new ErpInterfaceHdrServiceImpl(erpInterfaceHdrRepository);

        when(erpInterfaceHdrRepository.save(any(ErpInterfaceHdr.class))).thenThrow(new ErpInterfaceException("Exception is saveErpInterfaceHdr method in ErpInterfaceHdrServiceImpl class"));

        assertThrows(ErpInterfaceException.class, () -> {
            erpInterfaceHdrService.saveErpInterfaceHdr(erpInterfaceHdrDto);
        });

        verify(erpInterfaceHdrRepository, times(1)).save(any(ErpInterfaceHdr.class));
    }

    @Test
    void deleteErpInterfaceHdr_success() {
        Integer intfHdrId = 1;
//        ErpInterfaceHdrServiceImpl erpInterfaceHdrService = new ErpInterfaceHdrServiceImpl(erpInterfaceHdrRepository, erpInterfaceHdrDetailsRepository);

        when(erpInterfaceHdrRepository.existsById(intfHdrId)).thenReturn(true);

        String result = erpInterfaceHdrService.deleteErpInterfaceHdr(intfHdrId);

        assertEquals("Data deleted successfully", result);
        verify(erpInterfaceHdrRepository, times(1)).deleteById(intfHdrId);
    }

    @Test
    void deleteErpInterfaceHdr_notFound(){
        Integer intfHdrId = 1;
//        ErpInterfaceHdrServiceImpl erpInterfaceHdrService = new ErpInterfaceHdrServiceImpl(erpInterfaceHdrRepository, erpInterfaceHdrDetailsRepository);

        when(erpInterfaceHdrRepository.existsById(intfHdrId)).thenReturn(false);

        String result = erpInterfaceHdrService.deleteErpInterfaceHdr(intfHdrId);

        assertEquals("No such intfHdrId exists to delete data", result);

        verify(erpInterfaceHdrRepository, never()).deleteById(intfHdrId);
    }

    @Test
    void deleteErpInterfaceHdr_exception() {
        Integer intfHdrId = 1;

        when(erpInterfaceHdrRepository.existsById(intfHdrId)).thenThrow(new ErpInterfaceException("Exception in deleteErpInterfaceHdr method in ErpInterfaceHdrServiceImpl class"));

        assertThrows(ErpInterfaceException.class, () -> {
            erpInterfaceHdrService.deleteErpInterfaceHdr(intfHdrId);
        });
    }

    @Test
    void getErpIntfHdrByIntfHdrId_success() {
        // FIND AN ID WHICH CORRESPONDS TO A ERPINTERFACEHDR
        Integer intfHdrId = 1;
        ErpInterfaceHdr expectedErpInterfaceHdr = new ErpInterfaceHdr(); // create your expected object here
        expectedErpInterfaceHdr.setOrgId(1);

        when(erpInterfaceHdrRepository.findById(intfHdrId)).thenReturn(Optional.of(expectedErpInterfaceHdr));
        when(commonService.getOrgCodeByOrgId(expectedErpInterfaceHdr.getOrgId())).thenReturn("ORG_CODE");

        // Act
        ErpInterfaceHdr result = erpInterfaceHdrService.getErpIntfHdrByIntfHdrId(intfHdrId);
        result.setOrgId(1);
        assertNotNull(result);
        assertEquals(intfHdrId, result.getOrgId());

//        assertEquals(expectedErpInterfaceHdr, result);
    }

    @Test
    void getErpIntfHdrByIntfHdrId_invalid(){
        Integer intfHdrId=1;

        ErpInterfaceHdr expected= new ErpInterfaceHdr();
//        expected.setOrgId(1);

        when(erpInterfaceHdrRepository.findById(intfHdrId)).thenReturn(Optional.empty());

        ErpInterfaceHdr actual= erpInterfaceHdrService.getErpIntfHdrByIntfHdrId(intfHdrId);

        assertNull(actual);

    }

}