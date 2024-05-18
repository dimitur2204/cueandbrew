import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import via.dk.cueandbrew.model.Model;
import via.dk.cueandbrew.model.ModelManager;
import via.dk.cueandbrew.view.ViewHandler;
import via.dk.cueandbrew.viewmodel.Start.ManagerLoginViewModel;

public class ManagerLoginTest {
    private Model model;
    private ManagerLoginViewModel managerLoginViewModel;
    private ViewHandler viewHandler;
    @BeforeEach
    void setUp() {
        model = Mockito.mock(ModelManager.class);
        viewHandler = Mockito.mock(ViewHandler.class);
        managerLoginViewModel = new ManagerLoginViewModel(model, viewHandler);
    }
    @Test
    void testOnCancel() {
        managerLoginViewModel.onCancel();
        Mockito.verify(viewHandler).openStartView();
    }
    @Test
    void testOnLogin() {
        managerLoginViewModel.onLogin("login", "password");
        Mockito.verify(model).onLogin("login", "password", null);
    }
}
