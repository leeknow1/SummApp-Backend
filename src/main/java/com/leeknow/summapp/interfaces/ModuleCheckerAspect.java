package com.leeknow.summapp.interfaces;

import com.leeknow.summapp.entity.Role;
import com.leeknow.summapp.entity.User;
import com.leeknow.summapp.enums.ModuleEnums;
import com.leeknow.summapp.repository.ModuleRepository;
import com.leeknow.summapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor
public class ModuleCheckerAspect {

    private final ModuleRepository moduleRepository;
    private final UserService userService;

    @Before("@annotation(ModuleChecker)")
    public void checkModuleAccessMethod(JoinPoint joinPoint) throws AccessDeniedException {
        User user = userService.getCurrentUser();

        Class<?> aClass = joinPoint.getTarget().getClass();
        Method method = Arrays.stream(aClass.getMethods())
                .filter(mt -> mt.getName().equals(joinPoint.getSignature().getName())).findFirst().orElse(null);

        if (method != null && method.isAnnotationPresent(ModuleChecker.class)) {
            ModuleChecker annotation = method.getAnnotation(ModuleChecker.class);
            ModuleEnums[] modules = annotation.value();
            check(modules, user);
        }
    }

    @Before("execution(* *(..)) && @within(ModuleChecker)")
    public void checkModuleAccessCLass(JoinPoint joinPoint) throws AccessDeniedException {
        User user = userService.getCurrentUser();

        Class<?> aClass = joinPoint.getTarget().getClass();
        if(aClass.isAnnotationPresent(ModuleChecker.class)) {
            ModuleChecker annotation = aClass.getAnnotation(ModuleChecker.class);
            ModuleEnums[] modules = annotation.value();
            check(modules, user);
        }
    }

    private void check(ModuleEnums[] modules, User user) throws AccessDeniedException {
        List<Integer> roleIds = user.getRoles().stream().map(Role::getRoleId).collect(Collectors.toList());
        for (ModuleEnums module : modules) {
            int access = moduleRepository.getUserModule(module.getId(), StringUtils.join(roleIds, ","));
            if (access == 0) {
                throw new AccessDeniedException("Нет доступа к модулю.");
            }
        }
    }
}
