package via.dk.cueandbrew.view.MainPages;

import via.dk.cueandbrew.viewmodel.MainPages.CreateFeedbackViewModel;

public class CreateFeedbackController
{
 private CreateFeedbackViewModel viewModel;

 public void init(CreateFeedbackViewModel viewModel) {
   this.viewModel = viewModel;
 }

 public void onCancel() {
   this.viewModel.onCancel();
 }
}
