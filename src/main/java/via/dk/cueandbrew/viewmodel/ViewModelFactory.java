package via.dk.cueandbrew.viewmodel;

import via.dk.cueandbrew.model.CreateReservationModel;

import java.io.IOException;
public class ViewModelFactory {
    private final CreateReservationViewModel createReservationViewModel;
    public ViewModelFactory(CreateReservationModel model) throws IOException {
        this.createReservationViewModel = new CreateReservationViewModel(model);
    }

    public CreateReservationViewModel getCreateReservationViewModel() {
        return createReservationViewModel;
    }
}
