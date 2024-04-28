package ru.urfu.mm.application.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.urfu.mm.application.dsl.ModuleDSL;
import ru.urfu.mm.application.gateway.ModuleGateway;
import ru.urfu.mm.application.usecase.get_all_modules.GetAllModules;
import ru.urfu.mm.domain.Module;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GetAllModulesTest {
    @Mock
    private ModuleGateway moduleGateway;

    /**
     * Извлекаем все модули.
     */
    @Test
    public void getAllModules() {
        List<Module> modules = List.of(ModuleDSL.create(), ModuleDSL.create());

        Mockito.when(moduleGateway.getAllModules()).thenReturn(modules);

        GetAllModules getAllModules = new GetAllModules(moduleGateway);
        List<Module> allModules = getAllModules.getAllModules();

        Assertions.assertEquals(modules.size(), allModules.size());
        allModules.forEach(module -> Assertions.assertTrue(modules.contains(module)));
    }

    /**
     * Извлекаем все модули. Модулей нет
     */
    @Test
    public void getAllModules_empty() {
        List<Module> modules = List.of();

        Mockito.when(moduleGateway.getAllModules()).thenReturn(modules);

        GetAllModules getAllModules = new GetAllModules(moduleGateway);
        List<Module> allModules = getAllModules.getAllModules();

        Assertions.assertEquals(0, allModules.size());
    }
}
