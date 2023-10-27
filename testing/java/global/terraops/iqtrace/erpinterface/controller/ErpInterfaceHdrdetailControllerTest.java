package global.terraops.iqtrace.erpinterface.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import global.terraops.iqtrace.erpinterface.dto.ErpInterfaceHdrDetailsDto;
import global.terraops.iqtrace.erpinterface.dto.RequestParamDTO;
import global.terraops.iqtrace.erpinterface.dto.Response;
import global.terraops.iqtrace.erpinterface.dto.ResponseDataDTO;
import global.terraops.iqtrace.erpinterface.entity.ErpInterfaceHdrDetails;
import global.terraops.iqtrace.erpinterface.service.ErpInterfaceHdrDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ErpInterfaceHdrdetailControllerTest {

	@MockBean
	private ErpInterfaceHdrDetailsService erpInterfaceHdrDetailsService;

	@Mock
	ErpInterfaceHdrdetailController erpInterfaceHdrDtlsController;

	@InjectMocks
	private ErpInterfaceHdrdetailController erpInterfaceHdrdetailController;

	@Autowired
	private WebApplicationContext webApplicationContext;

	MockMvc mockMvc;

	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	ErpInterfaceHdrDetails erpInterfaceHdrDetails = ErpInterfaceHdrDetails.builder().intfId("intf0001").active(true)
			.createdBy(null).createdTime(null).updatedBy(null).updatedTime(null).allowDuplicate(false)
			.dataMappingConifg("PURCHASE ORDER HEADER").fileType("API").frequency("*/1 * * * *").inOut("IN").build();

	@Test
	void getAllErpInterfaceHdrDetails_test() throws Exception {
		List<ErpInterfaceHdrDetails> yourErpInterfaceHdrDetailsList = new ArrayList<>();
		Pageable pageable = PageRequest.of(0, 100, Sort.by(Sort.Order.asc("yourSortProperty")));
		Page<ErpInterfaceHdrDetails> pageOfErpInterfaceHdrDetails = new PageImpl<>(yourErpInterfaceHdrDetailsList, pageable, yourErpInterfaceHdrDetailsList.size());
		RequestParamDTO requestParamDTO = new RequestParamDTO(0, 100);
		when(erpInterfaceHdrDetailsService.getAllErpInterfaceHdrDetailsService(requestParamDTO.getPageNo(), requestParamDTO.getRecordsPerPage())).thenReturn(pageOfErpInterfaceHdrDetails);
		mockMvc.perform(MockMvcRequestBuilders.get("/erp/erpInterfaceHdrDtls").contentType(MediaType.APPLICATION_JSON).header("tenant-Id", "SEI")
				.content(new ObjectMapper().writeValueAsString(requestParamDTO))).andDo(print()).andExpect(status().isOk());
	}

//	@Test
//	public void getAllErpInterfaceHdrDetails_success() throws Exception{
//		int pageNo=1, reccordsPerPage=10;
//		Page<ErpInterfaceHdrDetails> mockPage=new PageImpl<>(Collections.emptyList());
//
////		List<ErpInterfaceHdrDetails> yourErpInterfaceHdrDetailsList = new ArrayList<>();
////		Pageable pageable = PageRequest.of(0, 100, Sort.by(Sort.Order.asc("yourSortProperty")));
////		Page<ErpInterfaceHdrDetails> pageOfErpInterfaceHdrDetails = new PageImpl<>(yourErpInterfaceHdrDetailsList, pageable, yourErpInterfaceHdrDetailsList.size());
//		RequestParamDTO requestParamDTO = new RequestParamDTO(0, 100);
//
//		when(erpInterfaceHdrDetailsService.getAllErpInterfaceHdrDetailsService(pageNo,reccordsPerPage)).thenReturn(mockPage);
//
//		ResponseDataDTO dto = new ResponseDataDTO();
//		dto.setPageNo(1);
//		dto.setPageSize(10);
//		dto.setTotalPages(20L);
//		dto.setContent(Collections.emptyList());
//
//		Response response = new Response(HttpStatus.OK.value(),
//				"Erp Interface Header Details fetched successfully!", "", true, dto);
//
//		mockMvc.perform(MockMvcRequestBuilders.get("/erp/erpInterfaceHdrDtls")
////				.param("pageNo", "1")
////			    .param("recordsPerPage", "10")
////						.content(new ObjectMapper().writeValueAsString(dto))
//						.content(new ObjectMapper().writeValueAsString(requestParamDTO))
//						.header("tenant-Id", "SEI")
//				.contentType(MediaType.APPLICATION_JSON))
////				.content(new ObjectMapper().writeValueAsString(new RequestParamDTO())))
//				.andExpect(status().isOk());
//	}

	@Test
	public void getAllErpInterfaceHdrDetails_invalid() throws Exception {
		int pageNo = 1, reccordsPerPage = 10;
		Page<ErpInterfaceHdrDetails> mockPage = new PageImpl<>(Collections.emptyList());

		when(erpInterfaceHdrDetailsService.getAllErpInterfaceHdrDetailsService(pageNo,reccordsPerPage)).thenThrow(new RuntimeException("Error occured while fetching Erp Interface Header Details"));

		mockMvc.perform(MockMvcRequestBuilders.get("/erp/erpInterfaceHdrDtls")
//				.content()
				.header("tenant-Id", "SEI")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());

	}


	@Test
	public void saveUpdateErpInterfaceHdrDetails_success() throws Exception{
		ErpInterfaceHdrDetailsDto erpInterfaceHdrDetailsDto=new ErpInterfaceHdrDetailsDto();

		when(erpInterfaceHdrDetailsService.saveOrUpdateErpInterfaceHdrDetails(erpInterfaceHdrDetailsDto)).thenReturn("Data saved successfully!");

		mockMvc.perform(MockMvcRequestBuilders.post("/erp/erpInterfaceHdrDtls/{intfHdrId}", 1)
//						.param("erpInterfaceHdrDetailsDto",new ObjectMapper().writeValueAsString(erpInterfaceHdrDetailsDto))
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(erpInterfaceHdrDetailsDto)))
				.andExpect(status().isOk());
	}

	@Test
	public void saveUpdateErpInterfaceHdrDetails_invalid() throws Exception{
		ErpInterfaceHdrDetailsDto erpInterfaceHdrDetailsDto=new ErpInterfaceHdrDetailsDto();

//		when(erpInterfaceHdrDetailsService.saveOrUpdateErpInterfaceHdrDetails(erpInterfaceHdrDetailsDto)).thenReturn("Data cannot be saved!");
		when(erpInterfaceHdrDetailsService.saveOrUpdateErpInterfaceHdrDetails(erpInterfaceHdrDetailsDto)).thenThrow(new RuntimeException("Error occured while save Or Update ErpInterfaceHdr Details"));

		mockMvc.perform(MockMvcRequestBuilders.post("/erp/erpInterfaceHdrDtls/{intfHdrId}", 1)
//						.param("erpInterfaceHdrDetailsDto",new ObjectMapper().writeValueAsString(erpInterfaceHdrDetailsDto))
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(erpInterfaceHdrDetailsDto)))
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void deleteErpInterfaceHdr_success() throws Exception{
		String intfId="1";

		when(erpInterfaceHdrDetailsService.deleteErpInterfaceHdr(intfId)).thenReturn("Data deleted successfully");

		mockMvc.perform(MockMvcRequestBuilders.delete("/erp/erpInterfaceHdrDtls/{intfId}", "1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void deleteErpInterfaceHdr_invalid() throws Exception{
		String intfId="1";

		when(erpInterfaceHdrDetailsService.deleteErpInterfaceHdr(intfId)).thenThrow(new RuntimeException("Failed to delete  given intfId"));

		mockMvc.perform(MockMvcRequestBuilders.delete("/erp/erpInterfaceHdrDtls/{intfId}", "1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
	}



	@Test
	public void getErpInterfaceHdrDtls_sucess() throws Exception {
		String intfId = "1";
		ErpInterfaceHdrDetails erpInterfaceHdrDetails = new ErpInterfaceHdrDetails();

		when(erpInterfaceHdrDetailsService.getErpInterfaceHdrDtls(intfId)).thenReturn(erpInterfaceHdrDetails);

		mockMvc.perform(MockMvcRequestBuilders.get("/erp/erpInterfaceHdrDtls/{intfId}", 1)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void getErpInterfaceHdrDtls_invalid() throws Exception {
		String intfId = "1";
		ErpInterfaceHdrDetails erpInterfaceHdrDetails = new ErpInterfaceHdrDetails();

		when(erpInterfaceHdrDetailsService.getErpInterfaceHdrDtls(intfId)).thenThrow(new RuntimeException("Failed to fetch Erp Interface Header Details for intfId -" + intfId + " "));

		mockMvc.perform(MockMvcRequestBuilders.get("/erp/erpInterfaceHdrDtls/{intfId}", 1)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());

	}

	@Test
	public void getErpInterfaceDtls_sucess() throws Exception {
		Integer intfId = 1;
		List<ErpInterfaceHdrDetails> erpInterfaceHdrDetails = List.of(new ErpInterfaceHdrDetails());

		when(erpInterfaceHdrDetailsService.getErpInterfaceDtls(intfId)).thenReturn(erpInterfaceHdrDetails);

		mockMvc.perform(MockMvcRequestBuilders.get("/erp/erpInterfaceDtls/{intfId}", 1)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	public void getErpInterfaceDtls_invalid() throws Exception{
		Integer intfId=1;
		List<ErpInterfaceHdrDetails> erpInterfaceHdrDetails=List.of(new ErpInterfaceHdrDetails());

		when(erpInterfaceHdrDetailsService.getErpInterfaceDtls(intfId)).thenThrow(new RuntimeException());

		mockMvc.perform(MockMvcRequestBuilders.get("/erp/erpInterfaceDtls/{intfId}",1)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
	}

}


//	@Test
//	public void testGetAllErpInterfaceHdrDetails() throws Exception {
//		// Arrange
//		int pageNo = 1;
//		int recordsPerPage = 10;
//		Page<ErpInterfaceHdrDetails> mockPage = new PageImpl<>(Collections.emptyList());
//		when(erpInterfaceHdrDetailsService.getAllErpInterfaceHdrDetailsService(pageNo, recordsPerPage)).thenReturn(mockPage);
//
//		// Act
//		mockMvc.perform(get("/erpInterfaceHdrDtls")
//						.param("pageNo", String.valueOf(pageNo))
//						.param("recordsPerPage", String.valueOf(recordsPerPage))
//						.header("tenant-Id", "yourTenantId")
//						.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
//				.andExpect(jsonPath("$.message").value("Erp Interface Header Details fetched successfully!"))
//				.andExpect(jsonPath("$.success").value(true))
//				.andExpect(jsonPath("$.data.pageNo").value(mockPage.getNumber()))
//				.andExpect(jsonPath("$.data.pageSize").value(mockPage.getSize()))
//				.andExpect(jsonPath("$.data.totalPages").value(mockPage.getTotalPages()))
//				.andExpect(jsonPath("$.data.content").isArray())
//				.andExpect(jsonPath("$.data.content").isEmpty());
//
//		// Assert
//		verify(erpInterfaceHdrDetailsService, times(1)).getAllErpInterfaceHdrDetailsService(pageNo, recordsPerPage);
//		verifyNoMoreInteractions(erpInterfaceHdrDetailsService);
//	}
//
