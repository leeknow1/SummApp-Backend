package com.leeknow.summapp.service;

import com.leeknow.summapp.dto.ApplicationRequestDTO;
import com.leeknow.summapp.dto.ApplicationResponseDTO;
import com.leeknow.summapp.dto.DataSearchDTO;
import com.leeknow.summapp.dto.UserResponseDTO;
import com.leeknow.summapp.entity.Application;
import com.leeknow.summapp.entity.User;
import com.leeknow.summapp.enums.ApplicationStatus;
import com.leeknow.summapp.enums.ApplicationType;
import com.leeknow.summapp.enums.EventType;
import com.leeknow.summapp.repository.ApplicationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationServiceTest {

    @InjectMocks
    private ApplicationService applicationService;

    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private UserService userService;
    @Mock
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        //given
        DataSearchDTO searchDTO = new DataSearchDTO();
        searchDTO.setSize(10);
        searchDTO.setPage(0);
        searchDTO.setSort("applicationId");

        Application application = new Application();
        Application application2 = new Application();

        Page<Application> applications = new PageImpl<>(List.of(application, application2));

        //mock the calls
        when(applicationRepository.findAll(PageRequest.of(
                        searchDTO.getPage(),
                        searchDTO.getSize(),
                        Sort.by(searchDTO.getSort())))).thenReturn(applications);

        //when
        Map<String, Page<ApplicationResponseDTO>> result = applicationService.findAll(searchDTO);

        //then
        assertNotNull(result);
        assertTrue(result.containsKey("applications"));
        assertNotNull(result.get("applications"));
        assertEquals(result.get("applications").getTotalElements(), 2);
        assertNotNull(result.get("applications").getContent());
        assertInstanceOf(ApplicationResponseDTO.class, result.get("applications").getContent().get(0));

        //verify
        verify(applicationRepository, times(1)).findAll(PageRequest.of(
                searchDTO.getPage(),
                searchDTO.getSize(),
                Sort.by(searchDTO.getSort())));
    }

    @Test
    void findAllByCurrentUser() {
        //given
        DataSearchDTO searchDTO = new DataSearchDTO();
        searchDTO.setSize(10);
        searchDTO.setPage(0);
        searchDTO.setSort("applicationId");

        User user = new User();

        Application application = new Application();
        Application application2 = new Application();

        Page<Application> applications = new PageImpl<>(List.of(application, application2));

        //mock the calls
        when(userService.getCurrentUser()).thenReturn(user);
        when(applicationRepository.findAllByUser(user, PageRequest.of(
                searchDTO.getPage(),
                searchDTO.getSize(),
                Sort.by(searchDTO.getSort())))).thenReturn(applications);

        //when
        Map<String, Page<ApplicationResponseDTO>> result = applicationService.findAllByCurrentUser(searchDTO);

        //then
        assertNotNull(result);
        assertTrue(result.containsKey("applications"));
        assertNotNull(result.get("applications"));
        assertEquals(result.get("applications").getTotalElements(), 2);
        assertNotNull(result.get("applications").getContent());
        assertInstanceOf(ApplicationResponseDTO.class, result.get("applications").getContent().get(0));

        //verify
        verify(userService, times(1)).getCurrentUser();
        verify(applicationRepository, times(1)).findAllByUser(user, PageRequest.of(
                searchDTO.getPage(),
                searchDTO.getSize(),
                Sort.by(searchDTO.getSort())));
    }

    @Test
    void findById() {
        //given
        Application application = new Application();
        application.setApplicationId(1);

        ApplicationResponseDTO responseDTO = new ApplicationResponseDTO();
        responseDTO.setApplicationId(1);

        //mock the calls
        when(applicationRepository.findById(1)).thenReturn(Optional.of(application));

        //when
        Map<String, Object> result = applicationService.findById(1);

        //then
        assertNotNull(result);
        assertTrue(result.containsKey("application"));
        assertNotNull(result.get("application"));
        assertInstanceOf(ApplicationResponseDTO.class, result.get("application"));
        assertTrue(result.containsKey("status"));
        assertNotNull(result.get("status"));
        assertEquals(result.get("status"), HttpStatus.OK.value());

        //verify
        verify(applicationRepository, times(1)).findById(1);
    }

    @Test
    void findByIdWhenApplicationIsNull() {
        //given
        Application application = new Application();
        application.setApplicationId(1);

        //mock the calls
        when(applicationRepository.findById(1)).thenReturn(Optional.empty());

        //when
        Map<String, Object> result = applicationService.findById(1);

        //then
        assertNotNull(result);
        assertTrue(result.containsKey("application"));
        assertNull(result.get("application"));
        assertTrue(result.containsKey("status"));
        assertNotNull(result.get("status"));
        assertEquals(result.get("status"), HttpStatus.NOT_FOUND.value());

        //verify
        verify(applicationRepository, times(1)).findById(1);
    }

    @Test
    void save() {
        //given
        ApplicationRequestDTO requestDTO = new ApplicationRequestDTO();
        requestDTO.setTypeId(ApplicationType.SHORT.getId());

        Application createdApplication = new Application();
        createdApplication.setApplicationId(1);
        createdApplication.setTypeId(ApplicationType.SHORT.getId());
        createdApplication.setStatusId(ApplicationStatus.CREATED.getId());

        User user = new User();

        //mock the calls
        when(userService.getCurrentUser()).thenReturn(user);
        when(applicationRepository.save(any(Application.class))).thenReturn(createdApplication);

        //when
        Map<String, ApplicationResponseDTO> result = applicationService.save(requestDTO);

        //then
        assertNotNull(result);
        assertTrue(result.containsKey("application"));
        assertNotNull(result.get("application"));
        assertEquals(result.get("application").getApplicationId(), 1);
        assertEquals(result.get("application").getStatusId(), ApplicationStatus.CREATED.getId());
        assertEquals(result.get("application").getTypeId(), ApplicationType.SHORT.getId());

        //verify
        verify(userService, times(1)).getCurrentUser();
        verify(applicationRepository, times(1)).save(any(Application.class));
        verify(eventService, times(1)).create(eq(EventType.APPLICATION_CREATED.getId()), any(Application.class));
    }

    @Test
    void setStatus() {
        //given
        Application application = new Application();
        Application createdApplication = new Application();
        createdApplication.setStatusId(ApplicationStatus.IN_PROGRESS.getId());

        //mock the calls
        when(applicationRepository.findById(1)).thenReturn(Optional.of(application));
        when(applicationRepository.save(application)).thenReturn(createdApplication);

        //when
        Map<String, String> result = applicationService.setStatus(1, ApplicationStatus.IN_PROGRESS.getId());

        //then
        assertNotNull(result);
        assertTrue(result.containsKey("message"));
        assertNotNull(result.get("message"));
        assertEquals(result.get("message"), "Заявка успешно обновлена!");

        //verity
        verify(applicationRepository, times(1)).findById(1);
        verify(applicationRepository, times(1)).save(application);
        verify(eventService, times(1)).create(EventType.APPLICATION_STATUS_CHANGED.getId(), application);
    }
}
