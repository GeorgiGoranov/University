package gui;

import gui.booking.BookingDO;
import businesslogic.BusinessLogicAPI;
import gui.booking.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;


/**
 * Main GUI App. Gets Business Logic injected. Delegates the switching of scenes
 * to the SceneManager. The controllerFactory takes care of instantiating the
 * controllers. This enables you to use parameterized constructors and to inject
 * (as in the example below regarding the CustomerController) a link to the 
 * business logic layer. Invocation / initialization of this JavaFX app has been 
 * slightly adapted compared to a default generated JavaFX app. See documentation 
 * above show() method.
 * 
 * @author Informatics Fontys Venlo
 */
public class GUIApp extends Application {

    private BusinessLogicAPI businessLogicAPI;
    private SceneManager sceneManager;
    private final BookingDO bookingDO = new BookingDO();
    private static final String INITIAL_VIEW = "Login";
    private final Callback<Class<?>, Object> controllerFactory = (Class<?> c)
            -> {

        switch (c.getName()) {
            case "gui.SearchFlight":
                return new SearchFlight(this::getSceneManager, businessLogicAPI.getFlightManager());
            case "gui.Login":
                return new Login(this::getSceneManager);
            case "gui.ViewFlightController":
                return new ViewFlightController( this::getSceneManager);
            case "gui.booking.BookCustomerController":
                return new BookCustomerController(this::getSceneManager, bookingDO, businessLogicAPI.getCustomerManager());
            case "gui.CreateFlightController":
                return new CreateFlightController(this::getSceneManager, businessLogicAPI.getFlightManager());
            case "gui.booking.BookFlightController":
                return new BookFlightController(this::getSceneManager,businessLogicAPI.getFlightManager(), bookingDO);
            case "gui.booking.BookPassengerController":
                return new BookPassengerController(this::getSceneManager,businessLogicAPI.getBookingManager(), businessLogicAPI.getPassengerManager(), bookingDO);
            case "gui.ErrorController":
                return new ErrorController(this::getSceneManager);
            case "gui.CreateTicketController":
                return new CreateTicketController(this :: getSceneManager, businessLogicAPI.getTicketManager());
            case "gui.ManagementBoardController":
                return new ManagementBoardController(this :: getSceneManager, businessLogicAPI.getFlightManager());
            case "gui.ViewBookingController":
                return new ViewBookingController(this::getSceneManager, businessLogicAPI.getBookingManager(), businessLogicAPI.getPassengerManager(), bookingDO);
            case "gui.ChooseSeatController":
                return new ChooseSeatController(this::getSceneManager,bookingDO, businessLogicAPI.getFlightManager(), businessLogicAPI.getTicketManager(), businessLogicAPI.getBookingManager());
            case "gui.EditFlightController":
                return new EditFlightController(this::getSceneManager, businessLogicAPI.getFlightManager());
            default:
                return null;
        }
    };

    public GUIApp(BusinessLogicAPI businessLogicAPI) {
        this.businessLogicAPI = businessLogicAPI;
    }

    /**
     * Normally, a JavaFX application has a main method that invokes the
     * launch() method. Since we want to instantiate the GUIApp ourselves,
     * in order to be able to inject a link to the Business Logic, we have to
     * rewrite the initialization process. This method is meant for invocation
     * from the assembler. It will trigger the initialization of the JavaFX 
     * Toolkit. From a GUI test context, typically the init() method will be 
     * invoked with 'false' as parameter, since the JavaFXToolkit doesn't need
     * to be initialized in that case.
     * 
     * @return GUIApp 
     */
    public GUIApp show() {
        return init(true);
    }

    GUIApp init(boolean startJavaFXToolkit) {

        if (startJavaFXToolkit) {

            Platform.startup(() -> {
            });

            initializeSceneManager();

            Platform.runLater(() -> {
                Stage stage = new Stage();
                try {
                    start(stage);
                } catch (IOException ex) {
                    throw new IllegalStateException(ex);
                }
            });

        } else {
            initializeSceneManager();
        }

        return this;
    }

    private void initializeSceneManager() {
        sceneManager = new SceneManager(controllerFactory, INITIAL_VIEW);
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Demo Airline Information System");
        sceneManager.displayOn(stage, 1000, 500);
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }
}
