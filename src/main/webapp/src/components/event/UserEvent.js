import React, { Component } from 'react';
import AddDialog from "../adddialog/AddDialog";
import axios from "axios";
import Snackbar from "@material-ui/core/Snackbar";
import Alert from "@material-ui/lab/Alert";
import UserTable from "../usertable/UserTable";
import EventMap from "../map/EventMap";
import Geocode from "react-geocode";
export default class Event extends Component{

    eventApplyFields = [
        {id:"nameAndSurname", label:"Name Surname",type:"text"},
        {id:"tckn", label:"Tckn",type:"text"},
        {id:"email", label:"E-mail",type:"text"},
        {id:"participantName", label:"Participant Name",type:"text"}

    ]

    constructor(props) {
        super(props);
        this.state = {
            isDialogOpen:false,
            rows:[],
            snackbarProperties:{
                isOpen:false,
                message:"",
                severity:""
            },
            mapMarker:{
                lat: 39.890469,
                lng: 32.775120
            },
            mapPosition:{
                lat: 39.890469,
                lng: 32.775120
            },
            clickedEventId:""
        }

    }

    componentDidMount() {
        axios.get("/participants",)
            .then(response => {this.setState({rows:response.data})})
    }

    handleDialogOpen = (eventId) => {
        this.setState({isDialogOpen:true,
        clickedEventId:eventId
        })
    }
    handleDialogClose = () => {
        this.setState({isDialogOpen:false})
    }



    onApply = (clickedEventId,data) => {
        console.log(data);
        axios.post("/participants/"+clickedEventId,data)
            .then(response=>{
                this.snackbarOpen("You applied the event successfully!","success");
                this.handleDialogClose();
            })
            .catch(error => {
                if(error.response.status === 400){
                    this.snackbarOpen(error.response.data.errors[0].defaultMessage,"error");

                }
                if(error.response.status === 500){
                    this.snackbarOpen(error.response.data,"error")
                    this.handleDialogClose();
                }
            })
    }
    handleAddress =(clickedEventId) => {

        axios.get("/participants/"+clickedEventId+"/address")
            .then(addressResponse  => {
                Geocode.setApiKey("AIzaSyD2b21JUW-ET4zrY5_-JKhzAnbIwxh0vsY");
                Geocode.fromAddress(addressResponse.data.address).then(
                    response => {
                        const { lat, lng } = response.results[0].geometry.location;
                        this.setState(
                            prevState => {
                                let mapMarker = {...prevState.mapMarker}
                                mapMarker.lat =lat;
                                mapMarker.lng = lng;
                                let mapPosition =  {...prevState.mapPosition}
                                mapPosition.lat = lat;
                                mapPosition.lng = lng;
                                return {mapMarker,mapPosition};

                            }
                        )

                    })
            })
    }


    snackbarOpen = (message,severity) => {
        this.setState(prevState => {
            let snackbarProperties = {...prevState.snackbarProperties}
            snackbarProperties.isOpen = true;
            snackbarProperties.message=message;
            snackbarProperties.severity=severity;
            return {snackbarProperties};
        })
    }
    snackbarClose = () => {
        this.setState(prevState => {
            let snackbarProperties = {...prevState.snackbarProperties}
            snackbarProperties.isOpen = false;
            snackbarProperties.message="";
            snackbarProperties.severity="";
            return {snackbarProperties};
        })
    }
    toggleAddParticipantModal = () => {
        this.setState({isDialogOpen: !this.state.isDialogOpen})
    }






    render() {
        return(

            <div>
                <UserTable rows={this.state.rows} onApply={this.handleDialogOpen} onAddress={this.handleAddress}/>
                <Snackbar open={this.state.snackbarProperties.isOpen} autoHideDuration={5000} onClose={this.snackbarClose}
                          anchorOrigin={{vertical: 'top', horizontal: 'right'}}>
                    <Alert onClose={this.snackbarClose} severity={this.state.snackbarProperties.severity}>
                        {this.state.snackbarProperties.message}
                    </Alert>
                </Snackbar>
                <AddDialog title= "Apply Event" isOpen={this.state.isDialogOpen} eventId={this.state.clickedEventId}
                           onSubmit={this.onApply} handleOpen = {this.handleDialogOpen} handleClose={this.handleDialogClose} textFields = {this.eventApplyFields} />
                <EventMap mapMarker={this.state.mapMarker} mapPosition={this.state.mapPosition}/>
            </div>

        );
    }
}
