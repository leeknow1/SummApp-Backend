package com.leeknow.summapp.service;

import com.leeknow.summapp.application.dto.ApplicationRequestDTO;
import com.leeknow.summapp.application.dto.ApplicationResponseDTO;
import com.leeknow.summapp.application.entity.Application;
import com.leeknow.summapp.application.enums.ApplicationStatus;
import com.leeknow.summapp.application.enums.ApplicationType;
import com.leeknow.summapp.application.repository.ApplicationRepository;
import com.leeknow.summapp.application.service.ApplicationService;
import com.leeknow.summapp.common.dto.DataSearchDTO;
import com.leeknow.summapp.common.enums.Language;
import com.leeknow.summapp.event.enums.EventType;
import com.leeknow.summapp.event.service.EventService;
import com.leeknow.summapp.message.dto.Message;
import com.leeknow.summapp.message.service.MessageService;
import com.leeknow.summapp.user.entity.User;
import com.leeknow.summapp.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationServiceTest {

    @InjectMocks
    private ApplicationService applicationService;

    @InjectMocks
    private MessageService messageService;

    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private UserService userService;
    @Mock
    private EventService eventService;
    @Mock
    private MessageRepository messageRepository;

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
        application.setStatusId(1);
        application.setTypeId(1);
        Application application2 = new Application();
        application2.setStatusId(1);
        application2.setTypeId(1);

        Page<Application> applications = new PageImpl<>(List.of(application, application2));

        //mock the calls
        when(applicationRepository.findAll(PageRequest.of(
                searchDTO.getPage(),
                searchDTO.getSize(),
                Sort.by(searchDTO.getSort())))).thenReturn(applications);

        //when
        Map<String, Page<ApplicationResponseDTO>> result = applicationService.findAll(searchDTO, Language.RUSSIAN);

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
        application.setStatusId(1);
        application.setTypeId(1);
        Application application2 = new Application();
        application2.setStatusId(1);
        application2.setTypeId(1);

        Page<Application> applications = new PageImpl<>(List.of(application, application2));

        //mock the calls
        when(userService.getCurrentUser()).thenReturn(user);
        when(applicationRepository.findAllByUser(user, PageRequest.of(
                searchDTO.getPage(),
                searchDTO.getSize(),
                Sort.by(searchDTO.getSort())))).thenReturn(applications);

        //when
        Map<String, Object> result = applicationService.findAllByCurrentUser(searchDTO, Language.RUSSIAN);

        //then
        assertNotNull(result);
        assertTrue(result.containsKey("applications"));
        assertNotNull(result.get("applications"));
        assertTrue(result.containsKey("total"));
        assertEquals(result.get("total"), 2);

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
        application.setStatusId(1);
        application.setTypeId(1);

        ApplicationResponseDTO responseDTO = new ApplicationResponseDTO();
        responseDTO.setApplicationId(1);

        //mock the calls
        when(applicationRepository.findById(1)).thenReturn(Optional.of(application));

        //when
        ApplicationResponseDTO applicationDto = applicationService.findById(1, Language.RUSSIAN);

        //then
        assertNotNull(applicationDto);
        assertInstanceOf(ApplicationResponseDTO.class, applicationDto);

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
        ApplicationResponseDTO applicationDto = applicationService.findById(1, Language.RUSSIAN);

        //then
        assertNull(applicationDto);

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
        Map<String, ApplicationResponseDTO> result = applicationService.save(requestDTO, Language.RUSSIAN);

        //then
        assertNotNull(result);
        assertTrue(result.containsKey("application"));
        assertNotNull(result.get("application"));
        assertEquals(result.get("application").getApplicationId(), 1);
        assertNotNull(result.get("application").getStatus());
        assertNotNull(result.get("application").getType());

        //verify
        verify(userService, times(1)).getCurrentUser();
        verify(applicationRepository, times(1)).save(any(Application.class));
        verify(eventService, times(1)).create(eq(EventType.APPLICATION_CREATED.getId()), any(Application.class));
    }

    @Test
    void setStatus() {
        //given
        Application application = new Application();
        application.setTypeId(1);
        Application createdApplication = new Application();
        createdApplication.setStatusId(ApplicationStatus.IN_PROGRESS.getId());
        createdApplication.setTypeId(1);

        Message message = new Message();
        message.setNameRu("Заявка успешно обновлена!");

        //mock the calls
        when(applicationRepository.findById(1)).thenReturn(Optional.of(application));
        when(applicationRepository.save(application)).thenReturn(createdApplication);
        when(messageRepository.findById(any())).thenReturn(Optional.of(message));

        //when
        Map<String, String> result = applicationService.setStatus(1, ApplicationStatus.IN_PROGRESS.getId(), Language.RUSSIAN);

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
