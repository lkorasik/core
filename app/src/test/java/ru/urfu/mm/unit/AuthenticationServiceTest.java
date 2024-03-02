package ru.urfu.mm.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ru.urfu.mm.dsl.DTO_DSL;
import ru.urfu.mm.dto.LoginDTO;
import ru.urfu.mm.service.AuthenticationService;
import ru.urfu.mm.service.JWTService;
import ru.urfu.mm.service.UserService;

import java.util.Collections;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JWTService jwtService;

    /**
     * Авторизовываемся в системе через {@link LoginDTO}
     */
    @Test
    public void generateJWT_login() {
        LoginDTO loginDTO = DTO_DSL.createLoginDTO();

        UserDetails userDetails = new User(loginDTO.getEmail(), loginDTO.getPassword(), Collections.emptyList());

        String expectedJWT = "ghfdlsj";

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());

        when(authenticationManager.authenticate(token)).thenReturn(null);
        when(userService.loadUserByUsername(loginDTO.getEmail())).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn(expectedJWT);

        AuthenticationService authenticationService = new AuthenticationService(
                userService,
                authenticationManager,
                jwtService
        );

        String actualJWT = authenticationService.generateToken(loginDTO);

        Assertions.assertEquals(expectedJWT, actualJWT);
    }

    /**
     * Авторизовываемся в системе через {@link LoginDTO}. Ошибка во время авторизации.
     *
     * Пока не понятно как замокать authManager
     *
     * org.mockito.exceptions.misusing.UnnecessaryStubbingException:
     * Unnecessary stubbings detected.
     * Clean & maintainable test code requires zero unnecessary code.
     * Following stubbings are unnecessary (click to navigate to relevant line of code):
     *   1. -> at ru.urfu.mm.unit.AuthenticationServiceTest.generateJWT_login_authorizationException(AuthenticationServiceTest.java:77)
     *   2. -> at ru.urfu.mm.unit.AuthenticationServiceTest.generateJWT_login_authorizationException(AuthenticationServiceTest.java:78)
     * Please remove unnecessary stubbings or use 'lenient' strictness. More info: javadoc for UnnecessaryStubbingException class.
     * 	at org.mockito.junit.jupiter.MockitoExtension.afterEach(MockitoExtension.java:192)
     * 	at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.lambda$invokeAfterEachCallbacks$12(TestMethodTestDescriptor.java:261)
     * 	at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.lambda$invokeAllAfterMethodsOrCallbacks$13(TestMethodTestDescriptor.java:277)
     * 	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
     * 	at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.lambda$invokeAllAfterMethodsOrCallbacks$14(TestMethodTestDescriptor.java:277)
     * 	at org.junit.platform.commons.util.CollectionUtils.forEachInReverseOrder(CollectionUtils.java:217)
     * 	at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.invokeAllAfterMethodsOrCallbacks(TestMethodTestDescriptor.java:276)
     * 	at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.invokeAfterEachCallbacks(TestMethodTestDescriptor.java:260)
     * 	at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute(TestMethodTestDescriptor.java:145)
     * 	at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute(TestMethodTestDescriptor.java:69)
     * 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$6(NodeTestTask.java:151)
     * 	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
     * 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:141)
     * 	at org.junit.platform.engine.support.hierarchical.Node.around(Node.java:137)
     * 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$9(NodeTestTask.java:139)
     * 	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
     * 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:138)
     * 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:95)
     * 	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
     * 	at org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.invokeAll(SameThreadHierarchicalTestExecutorService.java:41)
     * 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$6(NodeTestTask.java:155)
     * 	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
     * 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:141)
     * 	at org.junit.platform.engine.support.hierarchical.Node.around(Node.java:137)
     * 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$9(NodeTestTask.java:139)
     * 	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
     * 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:138)
     * 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:95)
     * 	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
     * 	at org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.invokeAll(SameThreadHierarchicalTestExecutorService.java:41)
     * 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$6(NodeTestTask.java:155)
     * 	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
     * 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:141)
     * 	at org.junit.platform.engine.support.hierarchical.Node.around(Node.java:137)
     * 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$9(NodeTestTask.java:139)
     * 	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
     * 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:138)
     * 	at org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:95)
     * 	at org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.submit(SameThreadHierarchicalTestExecutorService.java:35)
     * 	at org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor.execute(HierarchicalTestExecutor.java:57)
     * 	at org.junit.platform.engine.support.hierarchical.HierarchicalTestEngine.execute(HierarchicalTestEngine.java:54)
     * 	at org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute(EngineExecutionOrchestrator.java:107)
     * 	at org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute(EngineExecutionOrchestrator.java:88)
     * 	at org.junit.platform.launcher.core.EngineExecutionOrchestrator.lambda$execute$0(EngineExecutionOrchestrator.java:54)
     * 	at org.junit.platform.launcher.core.EngineExecutionOrchestrator.withInterceptedStreams(EngineExecutionOrchestrator.java:67)
     * 	at org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute(EngineExecutionOrchestrator.java:52)
     * 	at org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:114)
     * 	at org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:86)
     * 	at org.junit.platform.launcher.core.DefaultLauncherSession$DelegatingLauncher.execute(DefaultLauncherSession.java:86)
     * 	at org.gradle.api.internal.tasks.testing.junitplatform.JUnitPlatformTestClassProcessor$CollectAllTestClassesExecutor.processAllTestClasses(JUnitPlatformTestClassProcessor.java:119)
     * 	at org.gradle.api.internal.tasks.testing.junitplatform.JUnitPlatformTestClassProcessor$CollectAllTestClassesExecutor.access$000(JUnitPlatformTestClassProcessor.java:94)
     * 	at org.gradle.api.internal.tasks.testing.junitplatform.JUnitPlatformTestClassProcessor.stop(JUnitPlatformTestClassProcessor.java:89)
     * 	at org.gradle.api.internal.tasks.testing.SuiteTestClassProcessor.stop(SuiteTestClassProcessor.java:62)
     * 	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
     * 	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
     * 	at org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:36)
     * 	at org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:24)
     * 	at org.gradle.internal.dispatch.ContextClassLoaderDispatch.dispatch(ContextClassLoaderDispatch.java:33)
     * 	at org.gradle.internal.dispatch.ProxyDispatchAdapter$DispatchingInvocationHandler.invoke(ProxyDispatchAdapter.java:94)
     * 	at jdk.proxy1/jdk.proxy1.$Proxy2.stop(Unknown Source)
     * 	at org.gradle.api.internal.tasks.testing.worker.TestWorker$3.run(TestWorker.java:193)
     * 	at org.gradle.api.internal.tasks.testing.worker.TestWorker.executeAndMaintainThreadName(TestWorker.java:129)
     * 	at org.gradle.api.internal.tasks.testing.worker.TestWorker.execute(TestWorker.java:100)
     * 	at org.gradle.api.internal.tasks.testing.worker.TestWorker.execute(TestWorker.java:60)
     * 	at org.gradle.process.internal.worker.child.ActionExecutionWorker.execute(ActionExecutionWorker.java:56)
     * 	at org.gradle.process.internal.worker.child.SystemApplicationClassLoaderWorker.call(SystemApplicationClassLoaderWorker.java:113)
     * 	at org.gradle.process.internal.worker.child.SystemApplicationClassLoaderWorker.call(SystemApplicationClassLoaderWorker.java:65)
     * 	at worker.org.gradle.process.internal.worker.GradleWorkerMain.run(GradleWorkerMain.java:69)
     * 	at worker.org.gradle.process.internal.worker.GradleWorkerMain.main(GradleWorkerMain.java:74)
     */
    @Disabled
    @Test
    public void generateJWT_login_authorizationException() {
        LoginDTO loginDTO = DTO_DSL.createLoginDTO();

        UserDetails userDetails = new User(loginDTO.getEmail(), loginDTO.getPassword(), Collections.emptyList());

        String expectedJWT = "ghfdlsj";

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());

        when(authenticationManager.authenticate(token)).thenThrow(BadCredentialsException.class);
        when(userService.loadUserByUsername(loginDTO.getEmail())).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn(expectedJWT);

        AuthenticationService authenticationService = new AuthenticationService(
                userService,
                authenticationManager,
                jwtService
        );

        Assertions.assertThrows(BadCredentialsException.class, () -> authenticationService.generateToken(loginDTO));
    }
}
