package com.example.autoworkshop;

public class RequestFields {
    String CarName, CarNumber, CarImage;
    Boolean RequestStatus;
    String InspectionForm;
    String Image;


    public RequestFields(){

    }
    public RequestFields (String CarName, String CarNumber, String CarImage,Boolean RequestStatus, String InspectionForm){
        this.CarName = CarName;
        this.CarNumber = CarNumber;
        this.CarImage = CarImage;

        this.RequestStatus = RequestStatus;
        this.InspectionForm = InspectionForm;
    }

    public String getInspectionForm() {
        return InspectionForm;
    }

    public void setInspectionForm(String inspectionForm) {
        InspectionForm = inspectionForm;
    }

    public Boolean getRequestStatus() {
        return RequestStatus;
    }

    public void setRequestStatus(Boolean requestStatus) {
        RequestStatus = requestStatus;
    }

    public String getCarName() {
        return CarName;
    }

    public void setCarName(String carName) {
        CarName = carName;
    }

    public String getCarNumber() {
        return CarNumber;
    }

    public void setCarNumber(String carNumber) {
        CarNumber = carNumber;
    }

    public String getCarImage() {
        return CarImage;
    }

    public void setCarImage(String carImage) {
        CarImage = carImage;
    }
}
